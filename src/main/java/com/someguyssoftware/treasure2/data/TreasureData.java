/**
 * 
 */
package com.someguyssoftware.treasure2.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.someguyssoftware.gottschcore.random.RandomWeightedCollection;
import com.someguyssoftware.gottschcore.spatial.ICoords;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.block.TreasureBlocks;
import com.someguyssoftware.treasure2.chest.ChestEnvironment;
import com.someguyssoftware.treasure2.config.TreasureConfig;
import com.someguyssoftware.treasure2.enums.ChestGeneratorType;
import com.someguyssoftware.treasure2.enums.PitTypes;
import com.someguyssoftware.treasure2.enums.Pits;
import com.someguyssoftware.treasure2.enums.Rarity;
import com.someguyssoftware.treasure2.enums.LevelGenerators;
import com.someguyssoftware.treasure2.generator.ChestGeneratorData;
import com.someguyssoftware.treasure2.generator.GeneratorData;
import com.someguyssoftware.treasure2.generator.GeneratorResult;
import com.someguyssoftware.treasure2.generator.chest.IChestGenerator;
import com.someguyssoftware.treasure2.generator.pit.AirPitGenerator;
import com.someguyssoftware.treasure2.generator.pit.BigBottomMobTrapPitGenerator;
import com.someguyssoftware.treasure2.generator.pit.CollapsingTrapPitGenerator;
import com.someguyssoftware.treasure2.generator.pit.IPitGenerator;
import com.someguyssoftware.treasure2.generator.pit.LavaSideTrapPitGenerator;
import com.someguyssoftware.treasure2.generator.pit.LavaTrapPitGenerator;
import com.someguyssoftware.treasure2.generator.pit.MobTrapPitGenerator;
import com.someguyssoftware.treasure2.generator.pit.SimplePitGenerator;
import com.someguyssoftware.treasure2.generator.pit.StructurePitGenerator;
import com.someguyssoftware.treasure2.generator.pit.TntTrapPitGenerator;
import com.someguyssoftware.treasure2.generator.pit.VolcanoPitGenerator;
import com.someguyssoftware.treasure2.generator.well.IWellGenerator;
import com.someguyssoftware.treasure2.generator.well.WellGenerator;
import com.someguyssoftware.treasure2.registry.ChestRegistry;
import com.someguyssoftware.treasure2.registry.SimpleListRegistry;

import net.minecraft.world.level.block.Block;

/**
 * @author Mark Gottschling on Aug 28, 2020
 *
 */
// TODO rename to TreasureGenerators and move to base .generator package
public class TreasureData {
	// chest map by rarity and mapping flag - ** possible replacement for CHESTS_BY_RARITY **
	public static final Table<Rarity, ChestEnvironment, Block> CHESTS_BY_RARITY_FLAGS = HashBasedTable.create();

	// chest map by rarity
	public static final Multimap<Rarity, Block> CHESTS_BY_RARITY= ArrayListMultimap.create();

	// chest map by name
	public static final HashMap<String, Block> CHESTS_BY_NAME = new HashMap<>();

	// chest generators by rarity and environment
	public static final Table<Rarity, LevelGenerators, RandomWeightedCollection<IChestGenerator>> CHEST_GENS = HashBasedTable.create();

	// the pit chestGeneratorsMap
	public static final Table<PitTypes, Pits, IPitGenerator<GeneratorResult<ChestGeneratorData>>> PIT_GENS =  HashBasedTable.create();

	// well generator(s)
	public static final IWellGenerator<GeneratorResult<GeneratorData>> WELL_GEN = new WellGenerator();

	public static final List<Rarity> RARITIES = new ArrayList<>();

	public static final Map<LevelGenerators, List<Rarity>> RARITIES_MAP = new HashMap<>();

	public static final Map<String, ChestRegistry> CHEST_REGISTRIES = new HashMap<>(); 

	// simple registries
	public static final Map<String, SimpleListRegistry<ICoords>> WELL_REGISTRIES = new HashMap<>();
	public static final Map<String, SimpleListRegistry<ICoords>> WITHER_TREE_REGISTRIES = new HashMap<>();
	
	public static void initialize() {
		// TODO finish later. but use meta data to populate the table map
		CHESTS_BY_RARITY_FLAGS.put(Rarity.COMMON, ChestEnvironment.SURFACE, TreasureBlocks.WOOD_CHEST);

		// setup chest collection generator maps
		if (TreasureConfig.CHESTS.surfaceChests.configMap.get(Rarity.COMMON).isEnableChest()) {
			addRarityToMap(LevelGenerators.SURFACE_CHEST, Rarity.COMMON);
			CHEST_GENS.put(Rarity.COMMON, LevelGenerators.SURFACE_CHEST, new RandomWeightedCollection<>());
			CHEST_GENS.get(Rarity.COMMON, LevelGenerators.SURFACE_CHEST).add(1, ChestGeneratorType.COMMON.getChestGenerator());
		}
		if (TreasureConfig.CHESTS.surfaceChests.configMap.get(Rarity.UNCOMMON).isEnableChest()) {
			addRarityToMap(LevelGenerators.SURFACE_CHEST, Rarity.UNCOMMON);
			CHEST_GENS.put(Rarity.UNCOMMON, LevelGenerators.SURFACE_CHEST, new RandomWeightedCollection<>());
			CHEST_GENS.get(Rarity.UNCOMMON, LevelGenerators.SURFACE_CHEST).add(1, ChestGeneratorType.UNCOMMON.getChestGenerator());
		}
		if (TreasureConfig.CHESTS.surfaceChests.configMap.get(Rarity.SCARCE).isEnableChest()) {
			addRarityToMap(LevelGenerators.SURFACE_CHEST, Rarity.SCARCE);
			CHEST_GENS.put(Rarity.SCARCE, LevelGenerators.SURFACE_CHEST, new RandomWeightedCollection<>());
			CHEST_GENS.get(Rarity.SCARCE, LevelGenerators.SURFACE_CHEST).add(75, ChestGeneratorType.SCARCE.getChestGenerator());
			CHEST_GENS.get(Rarity.SCARCE, LevelGenerators.SURFACE_CHEST).add(25, ChestGeneratorType.SKULL.getChestGenerator());
		}
		if (TreasureConfig.CHESTS.surfaceChests.configMap.get(Rarity.RARE).isEnableChest()) {
			addRarityToMap(LevelGenerators.SURFACE_CHEST, Rarity.RARE);
			CHEST_GENS.put(Rarity.RARE, LevelGenerators.SURFACE_CHEST, new RandomWeightedCollection<>());
			CHEST_GENS.get(Rarity.RARE, LevelGenerators.SURFACE_CHEST).add(85, ChestGeneratorType.RARE.getChestGenerator());
			CHEST_GENS.get(Rarity.RARE, LevelGenerators.SURFACE_CHEST).add(15, ChestGeneratorType.GOLD_SKULL.getChestGenerator());
		}
		if (TreasureConfig.CHESTS.surfaceChests.configMap.get(Rarity.EPIC).isEnableChest()) {
			addRarityToMap(LevelGenerators.SURFACE_CHEST, Rarity.EPIC);
			CHEST_GENS.put(Rarity.EPIC, LevelGenerators.SURFACE_CHEST, new RandomWeightedCollection<>());
			CHEST_GENS.get(Rarity.EPIC, LevelGenerators.SURFACE_CHEST).add(240, ChestGeneratorType.EPIC.getChestGenerator());
			CHEST_GENS.get(Rarity.EPIC, LevelGenerators.SURFACE_CHEST).add(30, ChestGeneratorType.CRYSTAL_SKULL.getChestGenerator());
			CHEST_GENS.get(Rarity.EPIC, LevelGenerators.SURFACE_CHEST).add(30, ChestGeneratorType.CAULDRON.getChestGenerator());
		}
		
		// submerged chests
		if (TreasureConfig.CHESTS.submergedChests.configMap.get(Rarity.COMMON).isEnableChest()) {
			addRarityToMap(LevelGenerators.SUBMERGED_CHEST, Rarity.COMMON);
			CHEST_GENS.put(Rarity.COMMON, LevelGenerators.SUBMERGED_CHEST, new RandomWeightedCollection<>());
			CHEST_GENS.get(Rarity.COMMON, LevelGenerators.SUBMERGED_CHEST).add(1, ChestGeneratorType.COMMON.getChestGenerator());
		}
		if (TreasureConfig.CHESTS.submergedChests.configMap.get(Rarity.UNCOMMON).isEnableChest()) {
			addRarityToMap(LevelGenerators.SUBMERGED_CHEST, Rarity.UNCOMMON);
			CHEST_GENS.put(Rarity.UNCOMMON, LevelGenerators.SUBMERGED_CHEST, new RandomWeightedCollection<>());
			CHEST_GENS.get(Rarity.UNCOMMON, LevelGenerators.SUBMERGED_CHEST).add(1, ChestGeneratorType.UNCOMMON.getChestGenerator());
		}
		if (TreasureConfig.CHESTS.submergedChests.configMap.get(Rarity.SCARCE).isEnableChest()) {
			addRarityToMap(LevelGenerators.SUBMERGED_CHEST, Rarity.SCARCE);
			CHEST_GENS.put(Rarity.SCARCE, LevelGenerators.SUBMERGED_CHEST, new RandomWeightedCollection<>());
			CHEST_GENS.get(Rarity.SCARCE, LevelGenerators.SUBMERGED_CHEST).add(75, ChestGeneratorType.SCARCE.getChestGenerator());
			CHEST_GENS.get(Rarity.SCARCE, LevelGenerators.SUBMERGED_CHEST).add(25, ChestGeneratorType.SKULL.getChestGenerator());
		}
		if (TreasureConfig.CHESTS.submergedChests.configMap.get(Rarity.RARE).isEnableChest()) {
			addRarityToMap(LevelGenerators.SUBMERGED_CHEST, Rarity.RARE);
			CHEST_GENS.put(Rarity.RARE, LevelGenerators.SUBMERGED_CHEST, new RandomWeightedCollection<>());
			CHEST_GENS.get(Rarity.RARE, LevelGenerators.SUBMERGED_CHEST).add(85, ChestGeneratorType.RARE.getChestGenerator());
			CHEST_GENS.get(Rarity.RARE, LevelGenerators.SUBMERGED_CHEST).add(15, ChestGeneratorType.GOLD_SKULL.getChestGenerator());
		}
		if (TreasureConfig.CHESTS.submergedChests.configMap.get(Rarity.EPIC).isEnableChest()) {
			addRarityToMap(LevelGenerators.SUBMERGED_CHEST, Rarity.EPIC);
			CHEST_GENS.put(Rarity.EPIC, LevelGenerators.SUBMERGED_CHEST, new RandomWeightedCollection<>());
			CHEST_GENS.get(Rarity.EPIC, LevelGenerators.SUBMERGED_CHEST).add(240, ChestGeneratorType.EPIC.getChestGenerator());
			CHEST_GENS.get(Rarity.EPIC, LevelGenerators.SUBMERGED_CHEST).add(30, ChestGeneratorType.CRYSTAL_SKULL.getChestGenerator());
			CHEST_GENS.get(Rarity.EPIC, LevelGenerators.SUBMERGED_CHEST).add(30, ChestGeneratorType.CAULDRON.getChestGenerator());
		}
		
		// setup pit generators
		PIT_GENS.put(PitTypes.STANDARD, Pits.SIMPLE_PIT, new SimplePitGenerator());
		PIT_GENS.put(PitTypes.STRUCTURE, Pits.SIMPLE_PIT, new StructurePitGenerator(new SimplePitGenerator()));

		PIT_GENS.put(PitTypes.STANDARD, Pits.AIR_PIT,  new AirPitGenerator());
		PIT_GENS.put(PitTypes.STRUCTURE, Pits.AIR_PIT, new StructurePitGenerator(new AirPitGenerator()));

		PIT_GENS.put(PitTypes.STANDARD, Pits.LAVA_SIDE_TRAP_PIT,  new LavaSideTrapPitGenerator());
		PIT_GENS.put(PitTypes.STRUCTURE, Pits.LAVA_SIDE_TRAP_PIT,  new StructurePitGenerator(new LavaSideTrapPitGenerator()));

		PIT_GENS.put(PitTypes.STANDARD, Pits.LAVA_TRAP_PIT,  new LavaTrapPitGenerator());

		PIT_GENS.put(PitTypes.STANDARD, Pits.TNT_TRAP_PIT,  new TntTrapPitGenerator());
		PIT_GENS.put(PitTypes.STRUCTURE, Pits.TNT_TRAP_PIT,  new StructurePitGenerator(new TntTrapPitGenerator()));

		PIT_GENS.put(PitTypes.STANDARD, Pits.MOB_TRAP_PIT,  new MobTrapPitGenerator());
		PIT_GENS.put(PitTypes.STRUCTURE, Pits.MOB_TRAP_PIT,  new StructurePitGenerator(new MobTrapPitGenerator()));

		PIT_GENS.put(PitTypes.STANDARD, Pits.BIG_BOTTOM_MOB_TRAP_PIT,  new BigBottomMobTrapPitGenerator());

		PIT_GENS.put(PitTypes.STANDARD, Pits.COLLAPSING_TRAP_PIT,  new CollapsingTrapPitGenerator());
		PIT_GENS.put(PitTypes.STRUCTURE, Pits.COLLAPSING_TRAP_PIT, new StructurePitGenerator(new CollapsingTrapPitGenerator()));
        
		PIT_GENS.put(PitTypes.STANDARD, Pits.VOLCANO_PIT,  new VolcanoPitGenerator());

		for (String dimension : TreasureConfig.GENERAL.dimensionsWhiteList.get()) {
			Treasure.LOGGER.debug("white list dimension -> {}", dimension);
			CHEST_REGISTRIES.put(dimension, new ChestRegistry());
			WELL_REGISTRIES.put(dimension, new SimpleListRegistry<>(TreasureConfig.WELLS.registrySize.get()));
			WITHER_TREE_REGISTRIES.put(dimension, new SimpleListRegistry<>(TreasureConfig.WITHER_TREE.registrySize.get()));
		}
	}

	public static void addRarityToMap(LevelGenerators worldGen, Rarity rarity) {
		if (!RARITIES_MAP.containsKey(worldGen)) {
			RARITIES_MAP.put(worldGen, new ArrayList<>());
		}
		if (!RARITIES_MAP.get(worldGen).contains(rarity)) {
			RARITIES_MAP.get(worldGen).add(rarity);
		}
	}
}
