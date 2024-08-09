/*
 * This file is part of  Treasure2.
 * Copyright (c) 2022 Mark Gottschling (gottsch)
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
package mod.gottsch.forge.treasure2.datagen;

import mod.gottsch.forge.treasure2.Treasure;
import mod.gottsch.forge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.forge.treasure2.core.item.TreasureItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Mark Gottschling on Sep 9, 2022
 *
 */
public class ItemModelsProvider extends ItemModelProvider {

	public ItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Treasure.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		// tabs
		singleTexture(TreasureItems.TREASURE_TAB.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/treasure_tab"));

		// keys
		singleTexture(TreasureItems.WOOD_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/wood_key"));
		
		singleTexture(TreasureItems.STONE_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/stone_key"));
		
		singleTexture(TreasureItems.LEAF_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/leaf_key"));
		
		singleTexture(TreasureItems.EMBER_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/ember_key"));
		
		singleTexture(TreasureItems.LIGHTNING_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/lightning_key"));
		
		singleTexture(TreasureItems.IRON_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/iron_key"));
		
		singleTexture(TreasureItems.GOLD_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/gold_key"));
		
		singleTexture(TreasureItems.METALLURGISTS_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/metallurgists_key"));
		
		singleTexture(TreasureItems.DIAMOND_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/diamond_key"));
		
		singleTexture(TreasureItems.EMERALD_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/emerald_key"));
		
		singleTexture(TreasureItems.TOPAZ_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/topaz_key"));
		
		singleTexture(TreasureItems.ONYX_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/onyx_key"));
		
		singleTexture(TreasureItems.RUBY_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/ruby_key"));
		
		singleTexture(TreasureItems.SAPPHIRE_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/sapphire_key"));
		
		singleTexture(TreasureItems.JEWELLED_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/jewelled_key"));
				
		singleTexture(TreasureItems.SPIDER_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/spider_key"));
		
		singleTexture(TreasureItems.WITHER_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/wither_key"));
		
		singleTexture(TreasureItems.ONE_KEY.get().getRegistryName().getPath(),
				modLoc("item/vertical_left_key"), "layer0", modLoc("item/key/one_key"));
		
		singleTexture(TreasureItems.SKELETON_KEY.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/skeleton_key"));

		singleTexture(TreasureItems.PILFERERS_LOCK_PICK.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/pilferers_lock_pick"));
		
		singleTexture(TreasureItems.THIEFS_LOCK_PICK.get().getRegistryName().getPath(),
				modLoc("item/horizontal_left_key"), "layer0", modLoc("item/key/thiefs_lock_pick"));
		
		// locks
		singleTexture(TreasureItems.WOOD_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/wood_lock"));

		singleTexture(TreasureItems.STONE_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/stone_lock"));
		
		singleTexture(TreasureItems.LEAF_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/leaf_lock"));
		
		singleTexture(TreasureItems.EMBER_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/ember_lock"));
		
		singleTexture(TreasureItems.IRON_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/iron_lock"));
		
		singleTexture(TreasureItems.GOLD_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/gold_lock"));
		
		singleTexture(TreasureItems.DIAMOND_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/diamond_lock"));
		
		singleTexture(TreasureItems.EMERALD_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/emerald_lock"));
		
		singleTexture(TreasureItems.TOPAZ_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/topaz_lock"));
		
		singleTexture(TreasureItems.ONYX_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/onyx_lock"));
		
		
		singleTexture(TreasureItems.RUBY_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/ruby_lock"));
		
		singleTexture(TreasureItems.SAPPHIRE_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/sapphire_lock"));
		
		singleTexture(TreasureItems.SPIDER_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/spider_lock"));
		
		singleTexture(TreasureItems.WITHER_LOCK.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/lock/wither_lock"));
		
		// key ring
		singleTexture(TreasureItems.KEY_RING.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/key/key_ring"));
		
		// wealth
		singleTexture(TreasureItems.COPPER_COIN.get().getRegistryName().getPath(),
				modLoc("item/coin"), "layer0", modLoc("item/coin/copper_coin"));
		singleTexture(TreasureItems.SILVER_COIN.get().getRegistryName().getPath(),
				modLoc("item/coin"), "layer0", modLoc("item/coin/silver_coin"));
		singleTexture(TreasureItems.GOLD_COIN.get().getRegistryName().getPath(),
				modLoc("item/coin"), "layer0", modLoc("item/coin/gold_coin"));
		
		singleTexture(TreasureItems.TOPAZ.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/gem/topaz"));
		singleTexture(TreasureItems.ONYX.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/gem/onyx"));
		singleTexture(TreasureItems.RUBY.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/gem/ruby"));
		singleTexture(TreasureItems.SAPPHIRE.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/gem/sapphire"));
		singleTexture(TreasureItems.WHITE_PEARL.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/gem/white_pearl"));
		singleTexture(TreasureItems.BLACK_PEARL.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/gem/black_pearl"));
		
		singleTexture(TreasureItems.POUCH.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/pouch"));
		// other
		singleTexture(TreasureItems.TREASURE_TOOL.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/treasure_tool"));
		singleTexture(TreasureItems.SKELETON_ITEM.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/skeleton_item"));
		singleTexture(TreasureItems.EYE_PATCH.get().getRegistryName().getPath(),
				mcLoc("item/generated"), "layer0", modLoc("item/eye_patch"));
		
		// short swords
		singleTexture(TreasureItems.COPPER_SHORT_SWORD.get().getRegistryName().getPath(),
				modLoc("item/short_sword"), "layer0", modLoc("item/weapon/copper_short_sword"));
		
		singleTexture(TreasureItems.CHIPPED_COPPER_SHORT_SWORD.get().getRegistryName().getPath(),
				modLoc("item/short_sword"), "layer0", modLoc("item/weapon/chipped_copper_short_sword"));	
		
		singleTexture(TreasureItems.IRON_SHORT_SWORD.get().getRegistryName().getPath(),
				modLoc("item/short_sword"), "layer0", modLoc("item/weapon/iron_short_sword"));
		
		singleTexture(TreasureItems.CHIPPED_IRON_SHORT_SWORD.get().getRegistryName().getPath(),
				modLoc("item/short_sword"), "layer0", modLoc("item/weapon/chipped_iron_short_sword"));	
		
		singleTexture(TreasureItems.STEEL_SHORT_SWORD.get().getRegistryName().getPath(),
				modLoc("item/short_sword"), "layer0", modLoc("item/weapon/steel_short_sword"));
		
		singleTexture(TreasureItems.CHIPPED_STEEL_SHORT_SWORD.get().getRegistryName().getPath(),
				modLoc("item/short_sword"), "layer0", modLoc("item/weapon/chipped_steel_short_sword"));	
		
		// rapier
		singleTexture(TreasureItems.COPPER_RAPIER.get().getRegistryName().getPath(),
				mcLoc("item/handheld"), "layer0", modLoc("item/weapon/copper_rapier"));
		
		// longswords
		// add skull sword here
		singleTexture(TreasureItems.STEEL_SWORD.get().getRegistryName().getPath(),
				mcLoc("item/handheld"), "layer0", modLoc("item/weapon/steel_sword"));
		
		singleTexture(TreasureItems.SKULL_SWORD.get().getRegistryName().getPath(),
				mcLoc("item/handheld"), "layer0", modLoc("item/weapon/skull_sword2"));
		
		singleTexture(TreasureItems.SWORD_OMENS.get().getRegistryName().getPath(),
				mcLoc("item/handheld"), "layer0", modLoc("item/weapon/sword_of_omens"));
		
		// broad / bastard swords
		singleTexture(TreasureItems.IRON_BROADSWORD.get().getRegistryName().getPath(),
				mcLoc("item/handheld"), "layer0", modLoc("item/weapon/iron_broadsword"));
		singleTexture(TreasureItems.STEEL_BROADSWORD.get().getRegistryName().getPath(),
				mcLoc("item/handheld"), "layer0", modLoc("item/weapon/steel_broadsword"));
		
		// large swords (x32)
		singleTexture(TreasureItems.SWORD_POWER.get().getRegistryName().getPath(),
				mcLoc("item/handheld"), "layer0", modLoc("item/weapon/sword_of_power"));
		
		singleTexture(TreasureItems.CALLANDOR.getId().getPath(),
				mcLoc("item/handheld"), "layer0", modLoc("item/weapon/callandor"));
		
		singleTexture(TreasureItems.BLACK_SWORD.get().getRegistryName().getPath(),
				modLoc("item/large_sword"), "layer0", modLoc("item/weapon/the_black_sword_x32"));
		
		singleTexture(TreasureItems.OATHBRINGER.get().getRegistryName().getPath(),
				modLoc("item/large_sword"), "layer0", modLoc("item/weapon/oathbringer_x32"));
		
		// machetes
		singleTexture(TreasureItems.STEEL_MACHETE.get().getRegistryName().getPath(),
				modLoc("item/machete"), "layer0", modLoc("item/weapon/steel_machete"));	
		singleTexture(TreasureItems.SHADOW_MACHETE.get().getRegistryName().getPath(),
				modLoc("item/machete"), "layer0", modLoc("item/weapon/shadow_machete"));	
		
		// falchions
		singleTexture(TreasureItems.IRON_FALCHION.get().getRegistryName().getPath(),
				modLoc("item/single_edge_sword"), "layer0", modLoc("item/weapon/iron_falchion"));
	
		singleTexture(TreasureItems.STEEL_FALCHION.get().getRegistryName().getPath(),
				modLoc("item/single_edge_sword"), "layer0", modLoc("item/weapon/steel_falchion"));
		
		singleTexture(TreasureItems.SHADOW_FALCHION.get().getRegistryName().getPath(),
				modLoc("item/single_edge_sword"), "layer0", modLoc("item/weapon/shadow_falchion"));
		
		// scythes
		singleTexture(TreasureItems.ORCUS.get().getRegistryName().getPath(),
				modLoc("item/scythe"), "layer0", modLoc("item/weapon/orcus_x32"));
		
		// katanas
		singleTexture(TreasureItems.SNAKE_EYES_KATANA.get().getRegistryName().getPath(),
				modLoc("item/single_edge_sword"), "layer0", modLoc("item/weapon/snake_eyes_katana"));
		
		singleTexture(TreasureItems.STORM_SHADOWS_KATANA.get().getRegistryName().getPath(),
				modLoc("item/single_edge_sword"), "layer0", modLoc("item/weapon/storm_shadows_katana"));
		
		// hammers / maces / mauls
		singleTexture(TreasureItems.IRON_MACE.get().getRegistryName().getPath(),
				mcLoc("item/handheld"), "layer0", modLoc("item/weapon/iron_mace"));
		
		singleTexture(TreasureItems.STEEL_MACE.get().getRegistryName().getPath(),
				mcLoc("item/handheld"), "layer0", modLoc("item/weapon/steel_mace"));
		
		singleTexture(TreasureItems.MJOLNIR.get().getRegistryName().getPath(),
				mcLoc("item/handheld"), "layer0", modLoc("item/weapon/mjolnir"));
		
		// axes
		singleTexture(TreasureItems.COPPER_BROAD_AXE.get().getRegistryName().getPath(),
				modLoc("item/single_edge_sword"), "layer0", modLoc("item/weapon/copper_broad_axe"));
		singleTexture(TreasureItems.IRON_BROAD_AXE.get().getRegistryName().getPath(),
				modLoc("item/single_edge_sword"), "layer0", modLoc("item/weapon/iron_broad_axe"));
		singleTexture(TreasureItems.STEEL_BROAD_AXE.get().getRegistryName().getPath(),
				modLoc("item/single_edge_sword"), "layer0", modLoc("item/weapon/steel_broad_axe"));
		
		singleTexture(TreasureItems.IRON_DWARVEN_AXE.get().getRegistryName().getPath(),
				modLoc("item/single_edge_sword"), "layer0", modLoc("item/weapon/iron_dwarven_axe"));
		
		singleTexture(TreasureItems.AXE_DURIN.get().getRegistryName().getPath(),
				modLoc("item/single_edge_sword"), "layer0", modLoc("item/weapon/axe_of_durin"));
		
		singleTexture(TreasureItems.HEADSMANS_AXE.get().getRegistryName().getPath(),
				mcLoc("item/handheld"), "layer0", modLoc("item/weapon/headsmans_axe"));
		
		// block items
//		withExistingParent(TreasureItems.WOOD_CHEST_ITEM.get().getRegistryName().getPath(), modLoc("block/wood_chest"));
    	
		withExistingParent(TreasureItems.SPANISH_MOSS_ITEM.get().getRegistryName().getPath(), modLoc("block/spanish_moss"));
		
		withExistingParent(TreasureItems.TOPAZ_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/topaz_ore"));
		withExistingParent(TreasureItems.ONYX_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/onyx_ore"));
		withExistingParent(TreasureItems.RUBY_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/ruby_ore"));
		withExistingParent(TreasureItems.SAPPHIRE_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/sapphire_ore"));
		
		withExistingParent(TreasureItems.DEEPSLATE_TOPAZ_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/deepslate_topaz_ore"));
		withExistingParent(TreasureItems.DEEPSLATE_ONYX_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/deepslate_onyx_ore"));
		withExistingParent(TreasureItems.DEEPSLATE_RUBY_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/deepslate_ruby_ore"));
		withExistingParent(TreasureItems.DEEPSLATE_SAPPHIRE_ORE_ITEM.get().getRegistryName().getPath(), modLoc("block/deepslate_sapphire_ore"));
		
		withExistingParent(TreasureItems.WISHING_WELL_ITEM.get().getRegistryName().getPath(), modLoc("block/wishing_well_block"));
		withExistingParent(TreasureItems.DESERT_WISHING_WELL_ITEM.get().getRegistryName().getPath(), modLoc("block/desert_wishing_well_block"));
		
		TreasureBlocks.CHESTS.forEach(g -> {
			withExistingParent(g.get().getRegistryName().getPath(), modLoc("block/" + g.get().getRegistryName().getPath()));
		});
		
		TreasureBlocks.GRAVESTONES.forEach(g -> {
			withExistingParent(g.get().getRegistryName().getPath(), modLoc("block/" + g.get().getRegistryName().getPath()));
		});
		
		TreasureBlocks.GRAVESTONE_SPAWNERS.forEach(g -> {
			withExistingParent(g.get().getRegistryName().getPath(), modLoc("block/" + g.get().getRegistryName().getPath()));
		});
		
		// eggs
		withExistingParent(TreasureItems.BOUND_SOUL_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(TreasureItems.WOOD_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(TreasureItems.PIRATE_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(TreasureItems.VIKING_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(TreasureItems.CAULDRON_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(TreasureItems.CRATE_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(TreasureItems.MOLDY_CRATE_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(TreasureItems.CARDBOARD_BOX_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(TreasureItems.MILK_CRATE_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

		// topaz rings
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("topaz_iron_ring")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/topaz_iron_ring"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("topaz_copper_ring")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/topaz_copper_ring"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("topaz_silver_ring")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/topaz_silver_ring"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("topaz_gold_ring")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/topaz_gold_ring"));
		//        
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_iron_ring")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_iron_ring"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_copper_ring")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_copper_ring"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_silver_ring")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_silver_ring"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_gold_ring")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_gold_ring"));
		//        
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_blood_ring")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_blood_ring"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_black_ring")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_black_ring"));
		//        
		//        // topaz necklaces
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("topaz_iron_necklace")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/topaz_iron_necklace"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("topaz_copper_necklace")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/topaz_copper_necklace"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("topaz_silver_necklace")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/topaz_silver_necklace"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("topaz_gold_necklace")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/topaz_gold_necklace"));
		//        
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_iron_necklace")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_iron_necklace"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_copper_necklace")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_copper_necklace"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_silver_necklace")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_silver_necklace"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_gold_necklace")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_gold_necklace"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_blood_necklace")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_blood_necklace"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_black_necklace")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_black_necklace"));
		//        
		//        // topaz bracelets
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("topaz_iron_bracelet")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/topaz_iron_bracelet"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("topaz_copper_bracelet")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/topaz_copper_bracelet"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("topaz_silver_bracelet")).getRegistryName().getPath(),
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/topaz_silver_bracelet"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("topaz_gold_bracelet")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/topaz_gold_bracelet"));
		//        
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_iron_bracelet")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_iron_bracelet"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_copper_bracelet")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_copper_bracelet"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_silver_bracelet")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_silver_bracelet"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_gold_bracelet")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_gold_bracelet"));
		//        
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_blood_bracelet")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_blood_bracelet"));
		//        singleTexture(TreasureItems.ADORNMENT_ITEMS.get(modLoc("great_topaz_black_bracelet")).getRegistryName().getPath(),        		
		//        		mcLoc("treasure2:item/adornment"), "layer0", modLoc("item/adornments/great_topaz_black_bracelet"));
	}
}
