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
package com.someguyssoftware.treasure2.eventhandler;

import com.someguyssoftware.gottschcore.mod.IMod;
import com.someguyssoftware.gottschcore.world.WorldInfo;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.data.TreasureData;
import com.someguyssoftware.treasure2.loot.TreasureLootTableRegistry;
import com.someguyssoftware.treasure2.persistence.TreasureGenerationSavedData;
import com.someguyssoftware.treasure2.registry.TreasureDecayRegistry;
import com.someguyssoftware.treasure2.registry.TreasureMetaRegistry;
import com.someguyssoftware.treasure2.registry.TreasureTemplateRegistry;
import com.someguyssoftware.treasure2.world.gen.feature.TreasureFeatures;

import net.minecraft.world.level.Level;
import net.minecraft.world.server.ServerLevel;
import net.minecraftforge.event.world.LevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

/**
 * @author Mark Gottschling on Jun 29, 2018
 *
 */
//@Mod.EventBusSubscriber(modid = Treasure.MODID, bus = EventBusSubscriber.Bus.MOD)
public class LevelEventHandler {

	// reference to the mod.
	private IMod mod;

	/**
	 * 
	 */
	public LevelEventHandler(IMod mod) {
		setMod(mod);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLevelLoad(LevelEvent.Load event) {

		/*
		 * On load of dimension 0 (overworld), initialize the loot table's context and other static loot tables
		 */
		if (WorldInfo.isServerSide((Level)event.getLevel())) {
			ServerLevel world = (ServerLevel) event.getLevel();
			Treasure.LOGGER.info("In world load event for dimension {}", WorldInfo.getDimension(world).toString());
			if (WorldInfo.isSurfaceLevel(world)) {
				// called once to initiate world-level properties in the registries
				TreasureLootTableRegistry.initialize(world);
				TreasureTemplateRegistry.initialize(world);

				// register mod's loot tables
				TreasureLootTableRegistry.register();
				TreasureMetaRegistry.register(getMod().getId());
				TreasureTemplateRegistry.register(getMod().getId());
				TreasureDecayRegistry.register(getMod().getId());

				/*
				 * clear the current Level Gens values and reload
				 */
				TreasureFeatures.PERSISTED_FEATURES.forEach(feature -> {
					feature.init();
				});

				/*
				 * un-load the chest registry
				 */
				TreasureData.CHEST_REGISTRIES.entrySet().forEach(entry -> {
					//				Treasure.logger.debug("Chest registry size BEFORE cleaning -> {}", ChestRegistry.getInstance().getValues().size());
					entry.getValue().clear();
					//				Treasure.logger.debug("Chest registry size AFTER cleaning -> {}", ChestRegistry.getInstance().getValues().size());
				});		
				TreasureGenerationSavedData.get(world);
				//			Treasure.logger.debug("Chest registry size after world event load -> {}", ChestRegistry.getInstance().getValues().size());
			}	
		}
	}

//	@SubscribeEvent
//	public void lootLoad(LootTableLoadEvent event) {
//
//		if (event.getName().equals(LootTables.SIMPLE_DUNGEON/*"minecraft:chests/simple_dungeon"*/)) {
//			Treasure.LOGGER.debug("processing loot table event for table -> {}", event.getName().toString());
//			// load a loot table
//			/* TODO this doesn't load because it is not mapped - only the high level chests are, not the pools
//			 * must update to mimic INJECT tables but for ALL pool tables ie mapping and registering,
//			 * then they will be available through the table master
//			 */
//
//			ResourceLocation location = new ResourceLocation(Treasure.MODID, "pools/treasure/scarce");
//			Optional<LootTableShell>lootTableShell = TreasureLootTableRegistry.getLootTableMaster().getLootTableByResourceLocation(location);
//			if (lootTableShell.isPresent()) {
//				Treasure.LOGGER.debug("using loot table shell -> {}, {}", lootTableShell.get().getCategory(), lootTableShell.get().getRarity());
//				List<LootPoolShell> lootPoolShells = lootTableShell.get().getPools();
//				if (lootPoolShells != null && lootPoolShells.size() > 0) {
//					LOGGER.debug("# of pools -> {}", lootPoolShells.size());
//				}
//			}
//			else {
//				Treasure.LOGGER.debug("can't find loot table shell");
//			}
//			// get vanilla table
//			LootTable lootTable = event.getLootTableManager().get(location);
//			Treasure.LOGGER.debug("loot table -> {}", lootTable);
//			
//			LootTable vanillaTable = event.getLootTableManager().get(event.getName());
//			Treasure.LOGGER.debug("vanilla loot table -> {}", vanillaTable);
//			LootPool pool = lootTable.getPool("scarce_treasure");
//			if (pool == null) {
//				Treasure.LOGGER.debug("pool is null");
//			}
//			if (event.getTable() == null) {
//				Treasure.LOGGER.debug("eventTable is null");
//			}
//			else {
//				Treasure.LOGGER.debug("eventTable -> {}", event.getTable());
//			}
////			Treasure.LOGGER.debug("attempting to add pool -> {} to table -> {}", pool.getName(), event.getTable());
////			event.getTable().addPool(pool);		
//			vanillaTable.addPool(pool);
//		}
//	}

	@SubscribeEvent
	public void onServerStopping(FMLServerStoppingEvent event) {
		Treasure.LOGGER.debug("Closing out of world.");
		// clear all resource managers
		TreasureLootTableRegistry.getLootTableMaster().clear();
		TreasureTemplateRegistry.getTemplateManager().clear();
		TreasureMetaRegistry.getMetaManager().clear();
		TreasureDecayRegistry.getDecayManager().clear();
	}

	/**
	 * @return the mod
	 */
	public IMod getMod() {
		return mod;
	}

	/**
	 * @param mod the mod to set
	 */
	public void setMod(IMod mod) {
		this.mod = mod;
	}

}
