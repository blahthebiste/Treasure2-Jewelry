/*
 * This file is part of  Treasure2.
 * Copyright (c) 2021 Mark Gottschling (gottsch)
 *
 * Treasure2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Treasure2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Treasure2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.forge.treasure2.core.world.feature;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.Codec;

import mod.gottsch.forge.gottschcore.enums.IRarity;
import mod.gottsch.forge.gottschcore.random.RandomHelper;
import mod.gottsch.forge.gottschcore.spatial.Coords;
import mod.gottsch.forge.gottschcore.spatial.ICoords;
import mod.gottsch.forge.gottschcore.world.IWorldGenContext;
import mod.gottsch.forge.gottschcore.world.WorldGenContext;
import mod.gottsch.forge.gottschcore.world.WorldInfo;
import mod.gottsch.forge.treasure2.Treasure;
import mod.gottsch.forge.treasure2.api.TreasureApi;
import mod.gottsch.forge.treasure2.core.cache.FeatureCaches;
import mod.gottsch.forge.treasure2.core.cache.SimpleDistanceCache;
import mod.gottsch.forge.treasure2.core.config.Config;
import mod.gottsch.forge.treasure2.core.enums.Rarity;
import mod.gottsch.forge.treasure2.core.generator.ChestGeneratorData;
import mod.gottsch.forge.treasure2.core.generator.GeneratorData;
import mod.gottsch.forge.treasure2.core.generator.GeneratorResult;
import mod.gottsch.forge.treasure2.core.generator.chest.IChestGenerator;
import mod.gottsch.forge.treasure2.core.generator.well.IWellGenerator;
import mod.gottsch.forge.treasure2.core.persistence.TreasureSavedData;
import mod.gottsch.forge.treasure2.core.registry.DimensionalGeneratedCache;
import mod.gottsch.forge.treasure2.core.registry.GeneratedCache;
import mod.gottsch.forge.treasure2.core.registry.RarityLevelWeightedChestGeneratorRegistry;
import mod.gottsch.forge.treasure2.core.registry.WellGeneratorRegistry;
import mod.gottsch.forge.treasure2.core.registry.support.GeneratedChestContext;
import mod.gottsch.forge.treasure2.core.registry.support.GeneratedContext;
import mod.gottsch.forge.treasure2.core.structure.StructureCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * 
 * @author Mark Gottschling
 *
 */
public class WellFeature extends Feature<NoneFeatureConfiguration> implements IChestFeature {

	private int waitChunksCount = 0;

	/**
	 * 
	 * @param configuration
	 */
	public WellFeature(Codec<NoneFeatureConfiguration> configuration) {
		super(configuration);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel genLevel = context.level();
		ResourceLocation dimension = WorldInfo.getDimension(genLevel.getLevel());

		// test the dimension
		if (!meetsDimensionCriteria(dimension)) { 
			return false;
		}
		
		// get the well registry
		SimpleDistanceCache<GeneratedContext> cache = FeatureCaches.WELL_CACHE.getDimensionDistanceCache().get(dimension);
		if (cache == null) {
			Treasure.LOGGER.debug("GeneratedRegistry is null for dimension & WELL_CACHE. This shouldn't be. Should be initialized.");
			return false;
		}
		
		if (!meetsWorldAgeCriteria(context.level(), cache)) {
			this.waitChunksCount++;
			FeatureCaches.WELL_CACHE.setDelayCount(waitChunksCount);
			return false;
		}
		
		// the get first surface y (could be leaves, trunk, water, etc)
		ICoords spawnCoords = WorldInfo.getDryLandSurfaceCoords(genLevel, context.chunkGenerator(), new Coords(context.origin().offset(WorldInfo.CHUNK_RADIUS - 1, 0, WorldInfo.CHUNK_RADIUS - 1)));
		if (spawnCoords == Coords.EMPTY) {
			return false;
		}
		
		if (!meetsBiomeCriteria(genLevel.getLevel(), spawnCoords,
				(List<String>)Config.SERVER.wells.biomes.whiteList.get(), (List<String>)Config.SERVER.wells.biomes.blackList.get())) {
			return false;
		}
		
		// check against all registered wells
		if (meetsProximityCriteria(genLevel.getLevel(), spawnCoords, Config.SERVER.wells.minBlockDistance.get(), cache)) {
			Treasure.LOGGER.trace("The distance to the nearest well is less than the minimun required.");
			return false;
		}
		
		// NOTE no longer checking against the chest cache
		
		// check if meets the probability criteria
		if (!meetsProbabilityCriteria(context.random())) {
			return failAndPlacehold(genLevel, cache, spawnCoords);
		}

		// select a well generator
		IWorldGenContext worldContext = new WorldGenContext(context);
		IWellGenerator<GeneratorResult<? extends GeneratorData>> wellGenerator = selectGenerator(worldContext, spawnCoords);
		
		// generate structure
		Optional<GeneratorResult<? extends GeneratorData>> wellGenerationResult = wellGenerator.generate(worldContext, spawnCoords);
		if (wellGenerationResult.isEmpty()) {
			Treasure.LOGGER.debug("well result is empty?!");
			return false;
		}
		Treasure.LOGGER.debug("well result -> {}", wellGenerationResult.toString());

		// if a treasure chest was generated
		if (wellGenerationResult.get().getData() instanceof ChestGeneratorData) {
			Treasure.LOGGER.debug("getting well chest data...");
			ChestGeneratorData chestGenData = (ChestGeneratorData) wellGenerationResult.get().getData();
			// TODO GeneratorResult(s) need to be revamped with Optional
			ICoords chestCoords = chestGenData.getCoords();
			Treasure.LOGGER.debug("chest coords -> {}", chestCoords);
			// if chest is generated
			if (chestCoords != null) {
				BlockState chestState = chestGenData.getState();
				// wells aren't tied to any rarity, so randomly select one
				IRarity rarity = (IRarity) RarityLevelWeightedChestGeneratorRegistry.getNextRarity(dimension, FeatureType.TERRANEAN);

				// get the chest generator
				Treasure.LOGGER.debug("attempting to fetch chest generator for rarity -> {}", rarity);
				IChestGenerator chestGenerator = RarityLevelWeightedChestGeneratorRegistry.getNextGenerator(rarity, FeatureType.TERRANEAN);

				if (chestGenerator != null) {
					GeneratorResult<ChestGeneratorData> chestGenerationResult = chestGenerator.generate(new FeatureGenContext(context, FeatureType.TERRANEAN), chestCoords, rarity, chestState);
					if (chestGenerationResult.isSuccess()) {
						Treasure.LOGGER.debug("generating chest is success");
						// NOTE don't require the registry in order to create the chest for a well - it'd be nice to register the chest though
						// get the chest registry
						GeneratedCache<GeneratedChestContext> chestCache = DimensionalGeneratedCache.getChestGeneratedCache(dimension, FeatureType.TERRANEAN);
						if (chestCache != null) {
							chestGenerationResult.getData().setPit(false);
							chestGenerationResult.getData().setRarity(rarity);
							cacheGeneratedChest(context.level(), rarity, FeatureType.TERRANEAN, chestCache, chestGenerationResult);
							updateChestGeneratorRegistry(dimension, rarity, FeatureType.TERRANEAN);
						}
					}
				} else {
					Treasure.LOGGER.debug("unable to locate chest generator for rarity -> {}", rarity);
				}
			}
		}

		// update cache and mark dirty
		GeneratedContext genContext = new GeneratedContext();
		genContext.setCoords(wellGenerationResult.get().getData().getSpawnCoords());
		genContext.setRarity(Rarity.NONE);
		cache.cache(spawnCoords, genContext);

		// save world data
		TreasureSavedData savedData = TreasureSavedData.get(genLevel.getLevel());
		if (savedData != null) {
			savedData.setDirty();
		}
		
		return true;
	}

	/**
	 * @param world
	 * @param cache
	 * @return
	 */
	private boolean meetsWorldAgeCriteria(ServerLevelAccessor world, SimpleDistanceCache<GeneratedContext> cache) {
		// wait count check		
		// TODO since wells are very rare, a well may not generated before the world is save and player exits
		// in this case the waitChunksCount would be reset when the world restarts. this value needs to be saved.
		if (cache.getValues().isEmpty() && waitChunksCount < Config.SERVER.wells.waitChunks.get()) {
			Treasure.LOGGER.debug("world is too young");
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param world
	 * @param coords
	 * @param minDistance
	 * @param registry
	 * @return
	 */
	public boolean meetsProximityCriteria(ServerLevelAccessor world, ICoords coords, int minDistance, SimpleDistanceCache<GeneratedContext> registry) {
		if (registry == null || registry.getValues().isEmpty()) {
			Treasure.LOGGER.debug("unable to locate the GeneratedRegistry or the registry doesn't contain any values");
			return false;
		}

		// generate a box with coords as center and minDistance as radius
		ICoords startBox = new Coords(coords.getX() - minDistance, 0, coords.getZ() - minDistance);
		ICoords endBox = new Coords(coords.getX() + minDistance, 0, coords.getZ() + minDistance);

		// find if box overlaps anything in the registry
		if (registry.withinArea(startBox, endBox)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param random
	 * @return
	 */
	private boolean meetsProbabilityCriteria(RandomSource random) {
		if (!RandomHelper.checkProbability(random, Config.SERVER.wells.probability.get())) {
			Treasure.LOGGER.debug("does not meet generate probability.");
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param genLevel
	 * @param registry
	 * @param spawnCoords
	 * @return
	 */
	private boolean failAndPlacehold(WorldGenLevel genLevel, SimpleDistanceCache<GeneratedContext> registry, ICoords spawnCoords) {
		// add placeholder
		GeneratedContext genContext = new GeneratedContext(Rarity.NONE, spawnCoords);
		registry.cache(spawnCoords, genContext);
		// need to save on fail
		TreasureSavedData savedData = TreasureSavedData.get(genLevel.getLevel());
		if (savedData != null) {
			savedData.setDirty();
		}
		return false;
	}
	
	/**
	 * 
	 * @param context
	 * @param coords
	 * @return
	 */
	public IWellGenerator<GeneratorResult<? extends GeneratorData>> selectGenerator(IWorldGenContext context, ICoords coords) {
		List<IWellGenerator<GeneratorResult<? extends GeneratorData>>> generators = WellGeneratorRegistry.get(StructureCategory.TERRANEAN);
		IWellGenerator<GeneratorResult<? extends GeneratorData>> generator = generators.get(context.random().nextInt(generators.size()));
		return generator;
	}
}
