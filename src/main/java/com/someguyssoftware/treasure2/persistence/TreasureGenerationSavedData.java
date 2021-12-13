/*
 * This file is part of  Treasure2.
 * Copyright (c) 2021, Mark Gottschling (gottsch)
 * 
 * All rights reserved.
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
package com.someguyssoftware.treasure2.persistence;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.someguyssoftware.gottschcore.spatial.Coords;
import com.someguyssoftware.gottschcore.spatial.ICoords;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.chest.ChestEnvironment;
import com.someguyssoftware.treasure2.chest.ChestInfo;
import com.someguyssoftware.treasure2.data.TreasureData;
import com.someguyssoftware.treasure2.enums.Rarity;
import com.someguyssoftware.treasure2.registry.ChestRegistry;
import com.someguyssoftware.treasure2.registry.SimpleListRegistry;
import com.someguyssoftware.treasure2.world.gen.feature.TreasureFeatures;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.server.ServerLevel;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.LevelSavedData;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * 
 * @author Mark Gottschling on Jan 22, 2018
 *
 */
public class TreasureGenerationSavedData extends LevelSavedData {	
	private static final String TREASURE_GEN_TAG_NAME = "treasureGenerator";
	
	public static final String GEN_DATA_KEY = Treasure.MODID + ":generationData";

	private static final String KEY_TAG_NAME = "key";
	private static final String CHEST_REGISTRY_TAG_NAME = "chestRegistry";
	private static final String REGISTRY_TAG_NAME = "registry";
	private static final String WELL_REGISTRIES_TAG_NAME = "wellRegistries";
	private static final String WITHER_TREE_REGISTRIES_TAG_NAME = "witherTreeRegistries";
	private static final String COORDS_TAG_NAME = "coords";

	private static final String RARITY_TAG_NAME = "rarity";
	private static final String RARITIES_TAG_NAME = "rarities";
	private static final String DIMENSION_ID_TAG_NAME = "dimensionID";
	private static final String DIMENSIONS_TAG_NAME = "dimensions";
	private static final String BIOME_ID_TAG_NAME = "biomeID";
	private static final String BIOMES_TAG_NAME = "biomes";
	
	// New tags names
	private static final String CHUNKS_SINCE_LAST_FEATURE_TAG_NAME = "chunksSinceLastFeature";
	private static final String CHUNKS_SINCE_LAST_RARITY_FEATURE_TAG_NAME = "chunksSinceLastRarityFeature";
	
	/**
	 * Empty constructor
	 */
	public TreasureGenerationSavedData() {
		super(GEN_DATA_KEY);
	}
	
	/**
	 * 
	 * @param key
	 */
	public TreasureGenerationSavedData(String key) {
		super(key);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.world.LevelSavedData#readFromNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void load(CompoundTag tag) {
		Treasure.LOGGER.debug("loading treasure2 saved gen data...");

		// treasure generation tag
		CompoundTag genTag = tag.getCompound(TREASURE_GEN_TAG_NAME);

		/*
		 * features
		 */
		TreasureFeatures.PERSISTED_FEATURES.forEach(feature -> {
			Treasure.LOGGER.debug("loading feature -> {}", ((IForgeRegistryEntry)feature).getRegistryName().toString());
			CompoundTag featureTag = genTag.getCompound(((IForgeRegistryEntry)feature).getRegistryName().toString());
			if (featureTag != null) {
				ListTag dimTagList = featureTag.getList(DIMENSIONS_TAG_NAME, 10);
				dimTagList.forEach(dimTag -> {
					String dimensionID = ((CompoundTag) dimTag).getString(DIMENSION_ID_TAG_NAME);
					Treasure.LOGGER.debug("loading dimension -> {}", dimensionID);
					int chunksSince = ((CompoundTag) dimTag).getInt(CHUNKS_SINCE_LAST_FEATURE_TAG_NAME);
					feature.getChunksSinceLastDimensionFeature().put(dimensionID, chunksSince);
					Treasure.LOGGER.debug("loading chunks since -> {}", chunksSince);
					ListTag rarityTagList = ((CompoundTag) dimTag).getList(RARITIES_TAG_NAME, 10);
					if (rarityTagList != null && !rarityTagList.isEmpty()) {
						Treasure.LOGGER.debug("rarity tag list size -> {}", rarityTagList.size());
						for (int index = 0; index < rarityTagList.size(); index++) {
							CompoundTag rarityTag = rarityTagList.getCompound(index);
							String rarityID = rarityTag.getString(RARITY_TAG_NAME);
							Treasure.LOGGER.debug("loading rarity ID -> {}", rarityID);
							Rarity rarity = Rarity.getByValue(rarityID);
							if (rarity != null) {
								int chunksSinceRarityFeature = rarityTag.getInt(CHUNKS_SINCE_LAST_RARITY_FEATURE_TAG_NAME);
								Treasure.LOGGER.debug("loading chunks since last rarity -> {} chunks -> {}", rarity, chunksSinceRarityFeature);
								if (feature.getChunksSinceLastDimensionRarityFeature().containsKey(dimensionID)) {
									feature.getChunksSinceLastDimensionRarityFeature().get(dimensionID).put(rarity, chunksSinceRarityFeature);
								}
							}
						}
					}					
				});
			}
		});
		
        /*
         * chest registry
         */
        ListTag chestRegistriesTag = genTag.getList("chestRegistries", 10);
        if (chestRegistriesTag != null) {
        	Treasure.LOGGER.debug("loading chest registries...");
            chestRegistriesTag.forEach(dimTag -> {
                String dimensionID = ((CompoundTag) dimTag).getString(DIMENSION_ID_TAG_NAME);
                Treasure.LOGGER.debug("loading dimension -> {}", dimensionID);
                // get the registry
                ListTag registries = ((CompoundTag) dimTag).getList(CHEST_REGISTRY_TAG_NAME, 10);
                registries.forEach(registryTag -> {
                	CompoundTag nbt = (CompoundTag)registryTag;
                    String key = ((CompoundTag)registryTag).getString(KEY_TAG_NAME);
                    String rarity = ((CompoundTag)registryTag).getString(RARITY_TAG_NAME);
                    CompoundTag coords = ((CompoundTag)registryTag).getCompound(COORDS_TAG_NAME);
                    int x = coords.getInt("x");
                    int y = coords.getInt("y");
                    int z = coords.getInt("z");
                    Treasure.LOGGER.debug("loading chest registry entry -> k:{} r:{} x:{} y:{} z:{}", key, rarity, x, y, z);
                    
                    ChestInfo info = new ChestInfo(Rarity.getByValue(rarity), new Coords(x, y, z));
                    
                    if (nbt.contains("markers")) {
                    	info.setMarkers(nbt.getBoolean("markers"));
                    }
                    if (nbt.contains("structure")) {
                    	info.setStructure(nbt.getBoolean("structure"));
                    }
                    if (nbt.contains("pit")) {
                    	info.setPit(nbt.getBoolean("pit"));
                    }
                    if (nbt.contains("discovered")) {
                    	info.setDiscovered(nbt.getBoolean("discovered"));
                    }
                    if (nbt.contains("environment")) {
                    	info.setEnvironment(ChestEnvironment.valueOf(nbt.getString("environment")));
                    }
                    if (nbt.contains("registryName")) {
                    	info.setRegistryName(new ResourceLocation(nbt.getString("registryName")));
                    }
                    if (nbt.contains("mappedFrom")) {
                    	info.setTreasureMapFrom(loadCoords(nbt, "mappedFrom"));
                    }
                 
                    TreasureData.CHEST_REGISTRIES.get(dimensionID).register(Rarity.getByValue(rarity), key, info);
                });
                    
            });
        }
        
        /*
         * well registries
         */
        Treasure.LOGGER.debug("loading well registries...");
        ListTag wellRegistryList = genTag.getList(WELL_REGISTRIES_TAG_NAME, 10);
        if (wellRegistryList != null) {
        	wellRegistryList.forEach(dimensionCompound -> {
        		loadRegistry(dimensionCompound, TreasureData.WELL_REGISTRIES);
        	});
        }
        
        /*
         * wither tree registries
         */
        Treasure.LOGGER.debug("loading wither tree registries...");
        ListTag witherTreeRegistryList = genTag.getList(WITHER_TREE_REGISTRIES_TAG_NAME, 10);
        if (witherTreeRegistryList != null) {
        	witherTreeRegistryList.forEach(dimensionCompound -> {
        		loadRegistry(dimensionCompound, TreasureData.WITHER_TREE_REGISTRIES);
        	});
        }
	}

	/**
	 * 
	 * @param dimensionNBT
	 * @param registryMap
	 */
	private void loadRegistry(INBT dimensionNBT, Map<String, SimpleListRegistry<ICoords>> registryMap) {
        String dimensionID = ((CompoundTag) dimensionNBT).getString(DIMENSION_ID_TAG_NAME);
        Treasure.LOGGER.debug("\t...loading dimension -> {}", dimensionID);
        // get the registry
        ListTag registryList = ((CompoundTag) dimensionNBT).getList(REGISTRY_TAG_NAME, 10);
        registryList.forEach(registryNBT -> {
            int x = ((CompoundTag)registryNBT).getInt("x");
            int y = ((CompoundTag)registryNBT).getInt("y");
            int z = ((CompoundTag)registryNBT).getInt("z");
            Treasure.LOGGER.debug("\t...loading registry coords -> x:{} y:{} z:{}", x, y, z);
            registryMap.get(dimensionID).register(new Coords(x, y, z));
        });
	}

	/*
	 * NOTE thrown exceptions are silently handled, so they need to be caught here instead
	 *  (non-Javadoc)
	 * @see net.minecraft.world.LevelSavedData#writeToNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public CompoundTag save(CompoundTag tag) {
		try {
			// create a treasure compound			
			CompoundTag genTag = new CompoundTag();
			
			// add main treasure tag
			tag.put(TREASURE_GEN_TAG_NAME, genTag);
			
			// for each feature
			TreasureFeatures.PERSISTED_FEATURES.forEach(feature -> {
				Treasure.LOGGER.debug("saving feature -> {}", ((IForgeRegistryEntry)feature).getRegistryName().toString());
				CompoundTag featureTag = new CompoundTag();
				// add the feature gen last count to the treasure compound for each dimension
				ListTag dimTagList = new ListTag();
				for (Entry<String, Integer> entry : feature.getChunksSinceLastDimensionFeature().entrySet()) {
					Treasure.LOGGER.debug("saving feature dimension ID -> {}", entry.getKey());
					
					CompoundTag dimensionTag = new CompoundTag();
					dimensionTag.putString(DIMENSION_ID_TAG_NAME, entry.getKey());
					dimensionTag.putInt(CHUNKS_SINCE_LAST_FEATURE_TAG_NAME, entry.getValue());
					Treasure.LOGGER.debug("saving chunks since last feature -> {}", entry.getValue());
					
					// get the rarity map
					if (feature.getChunksSinceLastDimensionRarityFeature() != null && feature.getChunksSinceLastDimensionRarityFeature().containsKey(entry.getKey())) {
						Map<Rarity, Integer> rarityMap = feature.getChunksSinceLastDimensionRarityFeature().get(entry.getKey());
						Treasure.LOGGER.debug("saving rarity map size -> {}", rarityMap.size());
						
						ListTag rarityTagList = new ListTag();
						for (Entry<Rarity, Integer> rarityEntry : rarityMap.entrySet()) {
							CompoundTag rarityTag = new CompoundTag();
							rarityTag.putString(RARITY_TAG_NAME, rarityEntry.getKey().getValue());
							rarityTag.putInt(CHUNKS_SINCE_LAST_RARITY_FEATURE_TAG_NAME, rarityEntry.getValue());
							Treasure.LOGGER.debug("saving chunks since last rarity -> {} ({}) chunks -> {}", rarityEntry.getKey(), rarityEntry.getKey().getValue(), rarityEntry.getValue());
							rarityTagList.add(rarityTag);
						}						
						dimensionTag.put(RARITIES_TAG_NAME, rarityTagList);
					}
					dimTagList.add(dimensionTag);
				}
				
				featureTag.put(DIMENSIONS_TAG_NAME, dimTagList);
				genTag.put(((IForgeRegistryEntry)feature).getRegistryName().toString(), featureTag);
			});
			
			/*
			 * chest registries
			 */
			ListTag chestRegistries = new ListTag();
			TreasureData.CHEST_REGISTRIES.entrySet().forEach(entry -> {
				String dimensionName = entry.getKey();
				ChestRegistry registry = entry.getValue();
				CompoundTag dimTag = new CompoundTag();
				dimTag.putString(DIMENSION_ID_TAG_NAME, dimensionName);
				ListTag chestRegistryTagList = new ListTag();
				registry.getValues().forEach(chestInfo -> {
					CompoundTag chestInfoEntry = new CompoundTag();
					if (chestInfo.getCoords() != null) {
						CompoundTag coords = new CompoundTag();					
						coords.putInt("x", chestInfo.getCoords().getX());
						coords.putInt("y", chestInfo.getCoords().getY());
						coords.putInt("z", chestInfo.getCoords().getZ());
						chestInfoEntry.put(COORDS_TAG_NAME, coords);
						chestInfoEntry.putString(KEY_TAG_NAME, chestInfo.getCoords().toShortString());
					}
					chestInfoEntry.putString(RARITY_TAG_NAME, chestInfo.getRarity().getValue());
					chestInfoEntry.putBoolean("markers", chestInfo.hasMarkers());
					chestInfoEntry.putBoolean("structure", chestInfo.isStructure());
					chestInfoEntry.putBoolean("pit", chestInfo.isPit());
					chestInfoEntry.putBoolean("discovered", chestInfo.isDiscovered());
					
					if (chestInfo.getEnvironment() != null) {
						chestInfoEntry.putString("environment", chestInfo.getEnvironment().name());
					}
					if (chestInfo.getRegistryName() != null) {
						chestInfoEntry.putString("registryName", chestInfo.getRegistryName().toString());
					}
					if (chestInfo.getTreasureMapFromCoords().isPresent()) {
						CompoundTag mappedFrom = saveCoords(chestInfo.getTreasureMapFromCoords().get());
						chestInfoEntry.put("mappedFrom", mappedFrom);
					}
					
					// add entry to list
					chestRegistryTagList.add(chestInfoEntry);
				});
				dimTag.put(CHEST_REGISTRY_TAG_NAME, chestRegistryTagList);
				chestRegistries.add(dimTag);
			});
			// delete current tag
			genTag.remove("chestRegistries");
			// add new values
			genTag.put("chestRegistries", chestRegistries);

			/*
			 * well registries
			 */
			ListTag wellRegistries = new ListTag();
			TreasureData.WELL_REGISTRIES.entrySet().forEach(entry -> {
				saveRegistry(wellRegistries, entry.getKey(), entry.getValue());
			});
			updateCompound(genTag, WELL_REGISTRIES_TAG_NAME, wellRegistries);
			
			/*
			 * wither registries
			 */
			ListTag witherTreeRegistries = new ListTag();
			TreasureData.WITHER_TREE_REGISTRIES.entrySet().forEach(entry -> {
				saveRegistry(witherTreeRegistries, entry.getKey(), entry.getValue());
			});
			updateCompound(genTag, WITHER_TREE_REGISTRIES_TAG_NAME, witherTreeRegistries);			
		}
		catch(Exception e) {
			e.printStackTrace();
			Treasure.LOGGER.error("An exception occurred:", e);
		}
	
		return tag;
	}

	/**
	 * 
	 * @param coords
	 * @return
	 */
	private CompoundTag saveCoords(ICoords coords) {
		CompoundTag nbt = new CompoundTag();					
		nbt.putInt("x", coords.getX());
		nbt.putInt("y", coords.getY());
		nbt.putInt("z", coords.getZ());
		return nbt;
	}
	
	/**
	 * 
	 * @param nbt
	 * @param name
	 * @return
	 */
	private ICoords loadCoords(CompoundTag nbt, String name) {
        CompoundTag coords = nbt.getCompound(name);
        int x = coords.getInt("x");
        int y = coords.getInt("y");
        int z = coords.getInt("z");
        return new Coords(x, y, z);
	}

	/**
	 * 
	 * @param compound
	 * @param name
	 * @param nbt
	 */
	private void updateCompound(CompoundTag compound, String name, INBT nbt) {
		// delete current tag
		compound.remove(name);
		// add new values
		compound.put(name, nbt);		
	}

	/**
	 * 
	 * @param registryList
	 * @param dimension
	 * @param registry
	 */
	private void saveRegistry(ListTag registryList, String dimension, SimpleListRegistry<ICoords> registry) {
		CompoundTag dimensionCompound = new CompoundTag();
		dimensionCompound.putString(DIMENSION_ID_TAG_NAME, dimension);
		ListTag coordsList = new ListTag();
		registry.getValues().forEach(coords -> {
			CompoundTag coordsCompound = new CompoundTag();
			coordsCompound.putInt("x", coords.getX());
			coordsCompound.putInt("y", coords.getY());
			coordsCompound.putInt("z", coords.getZ());
			// add entry to list
			coordsList.add(coordsCompound);
		});
		dimensionCompound.put(REGISTRY_TAG_NAME, coordsList);
		registryList.add(dimensionCompound);
	}

	/**
	 * @param world
	 * @return
	 */
	public static TreasureGenerationSavedData get(ILevel world) {
		DimensionSavedDataManager storage = ((ServerLevel)world).getDataStorage();
		TreasureGenerationSavedData data = (TreasureGenerationSavedData) storage.computeIfAbsent(TreasureGenerationSavedData::new, GEN_DATA_KEY);
		
		if (data == null) {
			data = new TreasureGenerationSavedData();
			storage.set(data);
		}
		return data;
	}
}