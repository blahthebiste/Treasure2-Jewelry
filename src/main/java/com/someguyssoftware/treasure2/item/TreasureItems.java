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
package com.someguyssoftware.treasure2.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.someguyssoftware.gottschcore.item.ModItem;
import com.someguyssoftware.gottschcore.loot.LootTableShell;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.block.TreasureBlocks;
import com.someguyssoftware.treasure2.capability.CharmableCapability;
import com.someguyssoftware.treasure2.capability.CharmableCapability.InventoryType;
import com.someguyssoftware.treasure2.capability.CharmableCapabilityProvider;
import com.someguyssoftware.treasure2.capability.ICharmableCapability;
import com.someguyssoftware.treasure2.charm.TreasureCharms;
import com.someguyssoftware.treasure2.config.TreasureConfig;
import com.someguyssoftware.treasure2.config.TreasureConfig.KeyID;
import com.someguyssoftware.treasure2.config.TreasureConfig.LockID;
import com.someguyssoftware.treasure2.enums.Category;
import com.someguyssoftware.treasure2.enums.Rarity;
import com.someguyssoftware.treasure2.loot.TreasureLootTableMaster2.SpecialLootTables;
import com.someguyssoftware.treasure2.loot.TreasureLootTableRegistry;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

/**
 * @author Mark Gottschling on Aug 12, 2020
 * This class has the register event handler for all custom items.
 * This class uses @Mod.EventBusSubscriber so the event handler has to be static
 * This class uses @ObjectHolder to get a reference to the items
 *
 */
@Mod.EventBusSubscriber(modid = Treasure.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Treasure.MODID)
public class TreasureItems {

	public static Item LOGO;// = new ModItem(Treasure.MODID, "treasure_tab", new Item.Properties());
	public static Item TREASURE_TOOL;// = new TreasureToolItem(Treasure.MODID, "treasure_tool", new Item.Properties());

	// keys
	public static KeyItem WOOD_KEY;
	public static KeyItem STONE_KEY;
	public static KeyItem EMBER_KEY;
	public static KeyItem LEAF_KEY;
	public static KeyItem LIGHTNING_KEY;
	public static KeyItem IRON_KEY;
	public static KeyItem GOLD_KEY;
	public static KeyItem METALLURGISTS_KEY;
	public static KeyItem DIAMOND_KEY;
	public static KeyItem EMERALD_KEY;
	public static KeyItem RUBY_KEY;
	public static KeyItem SAPPHIRE_KEY;
	public static KeyItem JEWELLED_KEY;

	public static KeyItem WITHER_KEY;

	public static KeyItem BONE_KEY;
	public static KeyItem SKELETON_KEY;
	public static KeyItem SPIDER_KEY;
	public static KeyItem DRAGON_KEY;
	public static KeyItem MASTER_KEY;

	public static KeyItem PILFERERS_LOCK_PICK;
	public static KeyItem THIEFS_LOCK_PICK;

	public static KeyRingItem KEY_RING;
	public static PouchItem POUCH;

	// locks
	public static LockItem WOOD_LOCK;
	public static LockItem STONE_LOCK;
	public static LockItem EMBER_LOCK;
	public static LockItem LEAF_LOCK;
	public static LockItem IRON_LOCK;
	public static LockItem GOLD_LOCK;
	public static LockItem DIAMOND_LOCK;
	public static LockItem EMERALD_LOCK;
	public static LockItem RUBY_LOCK;
	public static LockItem SAPPHIRE_LOCK;
	public static LockItem SPIDER_LOCK;
	public static LockItem WITHER_LOCK;

	// coins
	public static Item COPPER_COIN;
	public static Item SILVER_COIN;
	public static Item GOLD_COIN;	

	// gems
	public static Item TOPAZ;
	public static Item ONYX;
	public static Item SAPPHIRE;
	public static Item RUBY;
	public static Item WHITE_PEARL;
	public static Item BLACK_PEARL;

	// charms
	public static CharmItem COPPER_CHARM;
	public static CharmItem SILVER_CHARM;
	public static CharmItem GOLD_CHARM;
	public static CharmItem CHARM_BOOK;

	// adornments
	public static Adornment COPPER_RING;
	public static Adornment SILVER_RING;
	public static Adornment GOLD_RING;
	
	public static Adornment COPPER_NECKLACE;
	public static Adornment SILVER_NECKLACE;
	public static Adornment GOLD_NECKLACE;
	
	public static Adornment COPPER_BRACELET;
	public static Adornment SILVER_BRACELET;
	public static Adornment GOLD_BRACELET;
	
	// special adornments
	public static Adornment ANGELS_RING; // 3x level15 innate
	public static Adornment RING_OF_FORTITUDE; // 2x level15 innate
	public static Adornment MEDICS_TOKEN; // 1x level20 innate
	public static Adornment SALANDAARS_WARD = null; // 1x level20 innate
	public static Adornment ADEPHAGIAS_BOUNTY; // 1x level20 innate
	public static Adornment MIRTHAS_TORCH;
	public static Adornment POCKET_WATCH;
	public static Adornment  PEASANTS_FORTUNE;
		
	// wither items
	public static Item WITHER_STICK_ITEM;
	public static Item WITHER_ROOT_ITEM;

	// other
	public static Item SKELETON;
	//	public static Item EYE_PATCH;

	// swords
	public static Item SKULL_SWORD;

	// key map
	public static Multimap<Rarity, KeyItem> keys;
	// lock map
	public static Multimap<Rarity, LockItem> locks;

	/**
	 * The actual event handler that registers the custom items.
	 *
	 * @param event The event this event handler handles
	 */
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {

		/*
		 *  initialize items
		 */
		LOGO = new ModItem(Treasure.MODID, "treasure_tab", new Item.Properties());
		TREASURE_TOOL = new TreasureToolItem(Treasure.MODID, "treasure_tool", new Item.Properties());

		// KEYS
		WOOD_KEY = new KeyItem(Treasure.MODID, KeyID.WOOD_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.woodKeyMaxUses.get()))
				.setCategory(Category.ELEMENTAL)
				.setRarity(Rarity.COMMON)
				.setCraftable(false);

		STONE_KEY = new KeyItem(Treasure.MODID, KeyID.STONE_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.stoneKeyMaxUses.get()))
				.setCategory(Category.ELEMENTAL)
				.setRarity(Rarity.COMMON)
				.setCraftable(false);

		EMBER_KEY = new EmberKey(Treasure.MODID, KeyID.EMBER_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.emberKeyMaxUses.get()))
				.setCategory(Category.ELEMENTAL)
				.setRarity(Rarity.SCARCE)
				.setCraftable(false);   

		LEAF_KEY = new KeyItem(Treasure.MODID, KeyID.LEAF_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.leafKeyMaxUses.get()))
				.setCategory(Category.ELEMENTAL)
				.setRarity(Rarity.UNCOMMON)
				.setCraftable(false);    

		LIGHTNING_KEY = new LightningKey(Treasure.MODID, KeyID.LIGHTNING_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.lightningKeyMaxUses.get()))
				.setCategory(Category.ELEMENTAL)
				.setRarity(Rarity.SCARCE)
				.setBreakable(false)
				.setCraftable(false);

		IRON_KEY = new KeyItem(Treasure.MODID, KeyID.IRON_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.ironKeyMaxUses.get()))
				.setCategory(Category.METALS)
				.setRarity(Rarity.UNCOMMON)
				.setCraftable(false);

		GOLD_KEY = new KeyItem(Treasure.MODID, KeyID.GOLD_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.goldKeyMaxUses.get()))
				.setCategory(Category.METALS)
				.setRarity(Rarity.SCARCE)
				.setCraftable(false);

		DIAMOND_KEY = new KeyItem(Treasure.MODID, KeyID.DIAMOND_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.diamondKeyMaxUses.get()))
				.setCategory(Category.GEMS)
				.setRarity(Rarity.RARE)
				.setBreakable(false)
				.setCraftable(false);

		EMERALD_KEY = new KeyItem(Treasure.MODID, KeyID.EMERALD_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.emeraldKeyMaxUses.get()))
				.setCategory(Category.GEMS)
				.setRarity(Rarity.RARE)
				.setBreakable(false)
				.setCraftable(false);

		RUBY_KEY = new KeyItem(Treasure.MODID, KeyID.RUBY_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.rubyKeyMaxUses.get()))
				.setCategory(Category.GEMS)
				.setRarity(Rarity.EPIC)
				.setBreakable(false)
				.setCraftable(true);

		SAPPHIRE_KEY = new KeyItem(Treasure.MODID, KeyID.SAPPHIRE_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.sapphireKeyMaxUses.get()))
				.setCategory(Category.GEMS)
				.setRarity(Rarity.EPIC)
				.setBreakable(false)
				.setCraftable(true);

		JEWELLED_KEY = new JewelledKey(Treasure.MODID, KeyID.JEWELLED_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.jewelledKeyMaxUses.get()))
				.setCategory(Category.GEMS)
				.setRarity(Rarity.EPIC)
				.setBreakable(false)
				.setCraftable(false);

		METALLURGISTS_KEY = new MetallurgistsKey(Treasure.MODID, KeyID.METALLURGISTS_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.metallurgistsKeyMaxUses.get()))
				.setCategory(Category.METALS)
				.setRarity(Rarity.RARE)
				.setBreakable(false)
				.setCraftable(false);

		SKELETON_KEY = new SkeletonKey(Treasure.MODID, KeyID.SKELETON_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.skeletonKeyMaxUses.get()))
				.setCategory(Category.ELEMENTAL)
				.setRarity(Rarity.RARE)
				.setBreakable(false)
				.setCraftable(false);

		SPIDER_KEY = new KeyItem(Treasure.MODID, KeyID.SPIDER_KEY_ID, new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.spiderKeyMaxUses.get()))
				.setCategory(Category.MOB)
				.setRarity(Rarity.SCARCE)
				.setBreakable(true)
				.setCraftable(true);

		WITHER_KEY = new KeyItem(Treasure.MODID, KeyID.WITHER_KEY_ID,
				new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.witherKeyMaxUses.get()))
				.setCategory(Category.WITHER)
				.setRarity(Rarity.RARE)
				.setBreakable(false)
				.setCraftable(true);

		PILFERERS_LOCK_PICK = new PilferersLockPick(Treasure.MODID, KeyID.PILFERERS_LOCK_PICK_ID, 
				new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.pilferersLockPickMaxUses.get()))
				.setCategory(Category.ELEMENTAL)
				.setRarity(Rarity.COMMON)
				.setBreakable(true)
				.setCraftable(true)
				.setSuccessProbability(24);

		THIEFS_LOCK_PICK = new ThiefsLockPick(Treasure.MODID, KeyID.THIEFS_LOCK_PICK_ID, 
				new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.thiefsLockPickMaxUses.get()))
				.setCategory(Category.ELEMENTAL)
				.setRarity(Rarity.UNCOMMON)
				.setBreakable(true)
				.setCraftable(true)
				.setSuccessProbability(32);

		KEY_RING = new KeyRingItem(Treasure.MODID, KeyID.KEY_RING_ID, new Item.Properties());

		keys = ArrayListMultimap.create();
		keys.put(WOOD_KEY.getRarity(), WOOD_KEY);
		// TODO finish list

		// LOCKS
		WOOD_LOCK = new LockItem(Treasure.MODID, LockID.WOOD_LOCK_ID, new Item.Properties(), new KeyItem[] {WOOD_KEY})
				.setCategory(Category.ELEMENTAL)
				.setRarity(Rarity.COMMON);

		STONE_LOCK = new LockItem(Treasure.MODID, LockID.STONE_LOCK_ID, new Item.Properties(), new KeyItem[] {STONE_KEY})
				.setCategory(Category.ELEMENTAL)
				.setRarity(Rarity.COMMON);

		EMBER_LOCK = new EmberLock(Treasure.MODID, LockID.EMBER_LOCK_ID, new Item.Properties(), new KeyItem[] {EMBER_KEY})
				.setCategory(Category.ELEMENTAL)
				.setRarity(Rarity.SCARCE);
		LEAF_LOCK = new LockItem(Treasure.MODID, LockID.LEAF_LOCK_ID, new Item.Properties(), new KeyItem[] {LEAF_KEY})
				.setCategory(Category.ELEMENTAL)
				.setRarity(Rarity.UNCOMMON);  

		IRON_LOCK = new LockItem(Treasure.MODID, LockID.IRON_LOCK_ID, new Item.Properties(), new KeyItem[] {IRON_KEY})
				.setCategory(Category.METALS)
				.setRarity(Rarity.UNCOMMON);
		GOLD_LOCK = new LockItem(Treasure.MODID, LockID.GOLD_LOCK_ID, new Item.Properties(), new KeyItem[] {GOLD_KEY})
				.setCategory(Category.METALS)
				.setRarity(Rarity.SCARCE);
		DIAMOND_LOCK = new LockItem(Treasure.MODID, LockID.DIAMOND_LOCK_ID, new Item.Properties(), new KeyItem[] {DIAMOND_KEY})
				.setCategory(Category.GEMS)
				.setRarity(Rarity.RARE);
		EMERALD_LOCK = new LockItem(Treasure.MODID, LockID.EMERALD_LOCK_ID, new Item.Properties(), new KeyItem[] {EMERALD_KEY})
				.setCategory(Category.GEMS)
				.setRarity(Rarity.RARE);
		RUBY_LOCK = new LockItem(Treasure.MODID, LockID.RUBY_LOCK_ID, new Item.Properties(), new KeyItem[] {RUBY_KEY})
				.setCategory(Category.GEMS)
				.setRarity(Rarity.EPIC);
		SAPPHIRE_LOCK = new LockItem(Treasure.MODID, LockID.SAPPHIRE_LOCK_ID, new Item.Properties(), new KeyItem[] {SAPPHIRE_KEY})
				.setCategory(Category.GEMS)
				.setRarity(Rarity.EPIC);
		SPIDER_LOCK = new LockItem(Treasure.MODID, LockID.SPIDER_LOCK_ID, new Item.Properties(), new KeyItem[] {SPIDER_KEY})
				.setCategory(Category.POTION)
				.setRarity(Rarity.SCARCE);
		WITHER_LOCK = new LockItem(Treasure.MODID, LockID.WITHER_LOCK_ID, new Item.Properties(), new KeyItem[] {WITHER_KEY})
				.setCategory(Category.WITHER)
				.setRarity(Rarity.SCARCE);

		locks = ArrayListMultimap.create();
		locks.put(WOOD_LOCK.getRarity(), WOOD_LOCK);
		locks.put(STONE_LOCK.getRarity(), STONE_LOCK);
		locks.put(EMBER_LOCK.getRarity(), EMBER_LOCK);
		locks.put(LEAF_LOCK.getRarity(), LEAF_LOCK);
		locks.put(IRON_LOCK.getRarity(), IRON_LOCK);
		locks.put(GOLD_LOCK.getRarity(), GOLD_LOCK);
		locks.put(DIAMOND_LOCK.getRarity(), DIAMOND_LOCK);
		locks.put(EMERALD_LOCK.getRarity(), EMERALD_LOCK);
		locks.put(RUBY_LOCK.getRarity(), RUBY_LOCK);
		locks.put(SAPPHIRE_LOCK.getRarity(), SAPPHIRE_LOCK);
		locks.put(SPIDER_LOCK.getRarity(), SPIDER_LOCK);
		// NOTE wither lock is a special and isn't used in the general locks list

		// COINS
		COPPER_COIN = new WealthItem(Treasure.MODID, TreasureConfig.ItemID.COPPER_COIN_ID, new Item.Properties());		
		SILVER_COIN = new WealthItem(Treasure.MODID, TreasureConfig.ItemID.SILVER_COIN_ID, new Item.Properties()) {
			@Override
			public List<LootTableShell> getLootTables() {
				List<LootTableShell> lootTables = new ArrayList<>();
				lootTables.addAll(TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.UNCOMMON));
				lootTables.addAll(TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.SCARCE));
				return lootTables;
			}
			@Override
			public ItemStack getDefaultLootKey (Random random) {
				List<KeyItem> keys = new ArrayList<>(TreasureItems.keys.get(Rarity.UNCOMMON));
				return new ItemStack(keys.get(random.nextInt(keys.size())));
			}
		};
		GOLD_COIN = new WealthItem(Treasure.MODID, TreasureConfig.ItemID.GOLD_COIN_ID, new Item.Properties()) {
			@Override
			public List<LootTableShell> getLootTables() {
				List<LootTableShell> lootTables = new ArrayList<>();
				lootTables.addAll(TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.SCARCE));
				lootTables.addAll(TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.RARE));
				return lootTables;
			}
			@Override
			public ItemStack getDefaultLootKey (Random random) {
				List<KeyItem> keys = new ArrayList<>(TreasureItems.keys.get(Rarity.SCARCE));
				return new ItemStack(keys.get(random.nextInt(keys.size())));
			}
		};

		// GEMS
		TOPAZ = new WealthItem(Treasure.MODID, TreasureConfig.ItemID.TOPAZ_ID, new Item.Properties()) {
			@Override
			public List<LootTableShell> getLootTables() {
				return TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.SCARCE);
			}
			@Override
			public ItemStack getDefaultLootKey (Random random) {
				List<KeyItem> keys = new ArrayList<>(TreasureItems.keys.get(Rarity.SCARCE));
				return new ItemStack(keys.get(random.nextInt(keys.size())));
			}
		};
		ONYX = new WealthItem(Treasure.MODID, TreasureConfig.ItemID.ONYX_ID, new Item.Properties()) {
			@Override
			public List<LootTableShell> getLootTables() {
				return TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.RARE);
			}
			@Override
			public ItemStack getDefaultLootKey (Random random) {
				List<KeyItem> keys = new ArrayList<>(TreasureItems.keys.get(Rarity.RARE));
				return new ItemStack(keys.get(random.nextInt(keys.size())));
			}
		};
		
		RUBY = new WealthItem(Treasure.MODID, TreasureConfig.ItemID.RUBY_ID, new Item.Properties()) {
			@Override
			public List<LootTableShell> getLootTables() {
				return TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.RARE);
			}
			@Override
			public ItemStack getDefaultLootKey (Random random) {
				List<KeyItem> keys = new ArrayList<>(TreasureItems.keys.get(Rarity.RARE));
				return new ItemStack(keys.get(random.nextInt(keys.size())));
			}
		};
		SAPPHIRE = new WealthItem(Treasure.MODID, TreasureConfig.ItemID.SAPPHIRE_ID, new Item.Properties()) {
			@Override
			public List<LootTableShell> getLootTables() {
				return TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.EPIC);
			}
			@Override
			public ItemStack getDefaultLootKey (Random random) {
				List<KeyItem> keys = new ArrayList<>(TreasureItems.keys.get(Rarity.EPIC));
				return new ItemStack(keys.get(random.nextInt(keys.size())));
			}
		};

		// PEARLS
		WHITE_PEARL = new WealthItem(Treasure.MODID, TreasureConfig.ItemID.WHITE_PEARL_ID, new Item.Properties()) {
			@Override
			public List<LootTableShell> getLootTables() {
				List<LootTableShell> lootTables = new ArrayList<>();
				lootTables.add(TreasureLootTableRegistry.getLootTableMaster().getSpecialLootTable(SpecialLootTables.WHITE_PEARL_WELL));
				return lootTables;
			}
			@Override
			public ItemStack getDefaultLootKey (Random random) {
				return new ItemStack(Items.DIAMOND);
			}
		};
		BLACK_PEARL = new WealthItem(Treasure.MODID, TreasureConfig.ItemID.BLACK_PEARL_ID, new Item.Properties()) {
			@Override
			public List<LootTableShell> getLootTables() {
				List<LootTableShell> lootTables = new ArrayList<>();
				lootTables.add(TreasureLootTableRegistry.getLootTableMaster().getSpecialLootTable(SpecialLootTables.BLACK_PEARL_WELL));
				return lootTables;
			}
			@Override
			public ItemStack getDefaultLootKey (Random random) {
				return new ItemStack(Items.EMERALD);
			}			
		};

		// CHARMS
		COPPER_CHARM = new CharmItem(Treasure.MODID, TreasureConfig.ItemID.COPPER_CHARM, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 1);
					$.bindable(true);
					$.source(true);
					$.executing(true);
					$.namedByMaterial(true);
					$.baseMaterial(TreasureCharms.COPPER.getName());
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		SILVER_CHARM = new CharmItem(Treasure.MODID, TreasureConfig.ItemID.SILVER_CHARM, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 1);
					$.bindable(true);
					$.source(true);
					$.executing(true);
					$.namedByMaterial(true);
					$.baseMaterial(TreasureCharms.SILVER.getName());
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		GOLD_CHARM = new CharmItem(Treasure.MODID, TreasureConfig.ItemID.GOLD_CHARM, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 1);
					$.bindable(true);
					$.source(true);
					$.executing(true);
					$.namedByMaterial(true);
					$.baseMaterial(TreasureCharms.GOLD.getName());
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		CHARM_BOOK = new CharmBook(Treasure.MODID, TreasureConfig.ItemID.CHARM_BOOK, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 1);
					$.imbuing(true);
					$.source(true);
					$.executing(false);
					$.baseMaterial(TreasureCharms.CHARM_BOOK.getName());
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};

		POUCH = new PouchItem(Treasure.MODID, "pouch", new Item.Properties());
		
		// adornments
		COPPER_RING = new Adornment(Treasure.MODID, TreasureConfig.ItemID.COPPER_RING, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 1);
					$.imbue(true, 1);
					$.socketable(true, 1);
					$.source(false);
					$.executing(true);
					$.namedByCharm(true);
					$.namedByMaterial(true);
					$.baseMaterial = TreasureCharms.COPPER.getName();
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		SILVER_RING = new Adornment(Treasure.MODID, TreasureConfig.ItemID.SILVER_RING, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 2);
					$.imbue(true, 1);
					$.socketable(true, 1);
					$.source(false);
					$.executing(true);
					$.namedByCharm(true);
					$.namedByMaterial(true);
					$.baseMaterial = TreasureCharms.SILVER.getName();
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		GOLD_RING = new Adornment(Treasure.MODID, TreasureConfig.ItemID.GOLD_RING, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 3);
					$.imbue(true, 1);
					$.socketable(true, 1);
					$.source(false);
					$.executing(true);
					$.namedByCharm(true);
					$.namedByMaterial(true);
					$.baseMaterial = TreasureCharms.GOLD.getName();
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		COPPER_NECKLACE = new Adornment(Treasure.MODID, TreasureConfig.ItemID.COPPER_NECKLACE, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 1);
					$.imbue(true, 1);
					$.socketable(true, 1);
					$.source(false);
					$.executing(true);
					$.namedByCharm(true);
					$.namedByMaterial(true);
					$.baseMaterial = TreasureCharms.COPPER.getName();
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		SILVER_NECKLACE = new Adornment(Treasure.MODID, TreasureConfig.ItemID.SILVER_NECKLACE, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 2);
					$.imbue(true, 1);
					$.socketable(true, 1);
					$.source(false);
					$.executing(true);
					$.namedByCharm(true);
					$.namedByMaterial(true);
					$.baseMaterial = TreasureCharms.SILVER.getName();
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		GOLD_NECKLACE = new Adornment(Treasure.MODID, TreasureConfig.ItemID.GOLD_NECKLACE, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 3);
					$.imbue(true, 1);
					$.socketable(true, 1);
					$.source(false);
					$.executing(true);
					$.namedByCharm(true);
					$.namedByMaterial(true);
					$.baseMaterial = TreasureCharms.GOLD.getName();
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
				
		COPPER_BRACELET = new Adornment(Treasure.MODID, TreasureConfig.ItemID.COPPER_BRACELET, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 1);
					$.imbue(true, 1);
					$.socketable(true, 1);
					$.source(false);
					$.executing(true);
					$.namedByCharm(true);
					$.namedByMaterial(true);
					$.baseMaterial = TreasureCharms.COPPER.getName();
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		SILVER_BRACELET = new Adornment(Treasure.MODID, TreasureConfig.ItemID.SILVER_BRACELET, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 2);
					$.imbue(true, 1);
					$.socketable(true, 1);
					$.source(false);
					$.executing(true);
					$.namedByCharm(true);
					$.namedByMaterial(true);
					$.baseMaterial = TreasureCharms.SILVER.getName();
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		GOLD_BRACELET = new Adornment(Treasure.MODID, TreasureConfig.ItemID.GOLD_BRACELET, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.AIR.getRegistryName()).with($ -> {
					$.innate(true, 3);
					$.imbue(true, 1);
					$.socketable(true, 1);
					$.source(false);
					$.executing(true);
					$.namedByCharm(true);
					$.namedByMaterial(true);
					$.baseMaterial = TreasureCharms.GOLD.getName();
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		// SPECIAL ADORNMENTS
		ANGELS_RING = new Adornment(Treasure.MODID, "angels_ring", new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(WHITE_PEARL.getRegistryName()).with($ -> {
					$.innate(true, 3);
					$.imbuable = false;
					$.socketable = false;
					$.source(false);
					$.executing(true);
					$.baseMaterial = TreasureCharms.GOLD.getName();
				}).build();
				cap.getCharmEntities()[InventoryType.INNATE.getValue()].add(TreasureCharms.AEGIS_15.createEntity());
				cap.getCharmEntities()[InventoryType.INNATE.getValue()].add(TreasureCharms.GREATER_HEALING_15.createEntity());
				cap.getCharmEntities()[InventoryType.INNATE.getValue()].add(TreasureCharms.FIRE_IMMUNITY_15.createEntity());
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		RING_OF_FORTITUDE = new Adornment(Treasure.MODID, "ring_of_fortitude", new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(BLACK_PEARL.getRegistryName()).with($ -> {
					$.innate(true, 2);
					$.imbuable = false;
					$.socketable = false;
					$.source(false);
					$.executing(true);
					$.baseMaterial = TreasureCharms.GOLD.getName();
				}).build();
				cap.getCharmEntities()[InventoryType.INNATE.getValue()].add(TreasureCharms.AEGIS_15.createEntity());
				cap.getCharmEntities()[InventoryType.INNATE.getValue()].add(TreasureCharms.REFLECTION_15.createEntity());
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		MEDICS_TOKEN = new Adornment(Treasure.MODID, "medics_token", new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(SAPPHIRE.getRegistryName()).with($ -> {
					$.innate(true, 1);
					$.imbuable = false;
					$.socketable = false;
					$.source(false);
					$.executing(true);
					$.baseMaterial = TreasureCharms.GOLD.getName();
				}).build();
				cap.getCharmEntities()[InventoryType.INNATE.getValue()].add(TreasureCharms.GREATER_HEALING_20.createEntity());
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		ADEPHAGIAS_BOUNTY = new Adornment(Treasure.MODID, "adephagias_bounty", new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.EMERALD.getRegistryName()).with($ -> {
					$.innate(true, 1);
					$.imbuable = false;
					$.socketable = false;
					$.source(false);
					$.executing(true);
					$.baseMaterial = TreasureCharms.GOLD.getName();
				}).build();
				cap.getCharmEntities()[InventoryType.INNATE.getValue()].add(TreasureCharms.SATIETY_20.createEntity());
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		SALANDAARS_WARD = new Adornment(Treasure.MODID, "salandaars_ward", new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(RUBY.getRegistryName()).with($ -> {
					$.innate(true, 1);
					$.imbuable = false;
					$.socketable = false;
					$.source(false);
					$.executing(true);
					$.baseMaterial = TreasureCharms.GOLD.getName();
				}).build();
				cap.getCharmEntities()[InventoryType.INNATE.getValue()].add(TreasureCharms.SHIELDING_20.createEntity());
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		MIRTHAS_TORCH = new Adornment(Treasure.MODID, "mirthas_torch", new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(WHITE_PEARL.getRegistryName()).with($ -> {
					$.innate(true, 1);
					$.imbuable = false;
					$.socketable = false;
					$.source(false);
					$.executing(true);
					$.baseMaterial = TreasureCharms.GOLD.getName();
				}).build();
				cap.getCharmEntities()[InventoryType.INNATE.getValue()].add(TreasureCharms.ILLUMINATION_21.createEntity());
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		POCKET_WATCH = new Adornment(Treasure.MODID, "pocket_watch", new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(TOPAZ.getRegistryName()).with($ -> {
					$.innate = false;
					$.imbuable = false;
					$.socketable(true, 2);
					$.source(false);
					$.executing(true);
					$.baseMaterial = TreasureCharms.GOLD.getName();
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		PEASANTS_FORTUNE = new Adornment(Treasure.MODID, "peasants_fortune", new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(Items.STONE.getRegistryName()).with($ -> { // use STONE as source Item so it can't be upgraded
					$.innate = false;
					$.imbuable = false;
					$.socketable(true, 2);
					$.source(false);
					$.executing(true);
					$.baseMaterial = TreasureCharms.COPPER.getName();
				}).build();
				return new CharmableCapabilityProvider(cap);
			}
		};
		
		// WITHER ITEMS
		WITHER_STICK_ITEM = new WitherStickItem(Treasure.MODID, TreasureConfig.ItemID.WITHER_STICK_ITEM_ID, TreasureBlocks.WITHER_BRANCH, new Item.Properties());
		WITHER_ROOT_ITEM = new WitherRootItem(Treasure.MODID, TreasureConfig.ItemID.WITHER_ROOT_ITEM_ID, TreasureBlocks.WITHER_ROOT, new Item.Properties());

		// OTHER
		SKELETON = new SkeletonItem(Treasure.MODID, TreasureConfig.ItemID.SKELETON_ITEM_ID, TreasureBlocks.SKELETON, new Item.Properties());

		SKULL_SWORD = new SwordItem(TreasureItemTier.SKULL, 3, -2.4F, new Item.Properties().tab(TreasureItemGroups.MOD_ITEM_GROUP))
				.setRegistryName(Treasure.MODID, TreasureConfig.ItemID.SKULL_SWORD_ID);
		//		EYE_PATCH = new DyeableArmorItem(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, (new Item.Properties()).group(TreasureItemGroups.MOD_ITEM_GROUP))
		//				.setRegistryName(Treasure.MODID, TreasureConfig.ItemID.EYE_PATCH_ID);

		/*
		 * register items (make sure you always set the registry name).
		 */
		event.getRegistry().registerAll(
				LOGO,
				TREASURE_TOOL,
				WOOD_LOCK,
				STONE_LOCK,
				EMBER_LOCK,
				LEAF_LOCK,
				IRON_LOCK,
				GOLD_LOCK,
				DIAMOND_LOCK,
				EMERALD_LOCK,
				RUBY_LOCK,
				SAPPHIRE_LOCK,
				SPIDER_LOCK,
				WITHER_LOCK,
				WOOD_KEY,
				STONE_KEY,
				EMBER_KEY,
				LEAF_KEY,
				LIGHTNING_KEY,
				IRON_KEY,
				GOLD_KEY,
				DIAMOND_KEY,
				EMERALD_KEY,
				RUBY_KEY,
				SAPPHIRE_KEY,
				JEWELLED_KEY,
				METALLURGISTS_KEY,
				SKELETON_KEY,
				SPIDER_KEY,
				WITHER_KEY,
				PILFERERS_LOCK_PICK,
				THIEFS_LOCK_PICK,
				KEY_RING,
				COPPER_COIN,
				SILVER_COIN,
				GOLD_COIN,
				COPPER_CHARM,
				SILVER_CHARM,
				GOLD_CHARM,
				CHARM_BOOK,
				POUCH,
				TOPAZ,
				ONYX,
				RUBY,
				SAPPHIRE,
				WHITE_PEARL,
				BLACK_PEARL,
				WITHER_STICK_ITEM,
				WITHER_ROOT_ITEM,
				SKULL_SWORD,
				SKELETON,
				COPPER_RING,
				SILVER_RING,
				GOLD_RING,
				ANGELS_RING,
				RING_OF_FORTITUDE,
				COPPER_NECKLACE,
				SILVER_NECKLACE,
				GOLD_NECKLACE,
				MEDICS_TOKEN,
				ADEPHAGIAS_BOUNTY,
				SALANDAARS_WARD,
				MIRTHAS_TORCH,
				POCKET_WATCH,
				PEASANTS_FORTUNE,
				COPPER_BRACELET,
				SILVER_BRACELET,
				GOLD_BRACELET
				);
	}

	/**
	 * Register the {@link IItemColor} handlers.
	 *
	 * @param event The event
	 */
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerItemColours(final ColorHandlerEvent.Item event) {
		final BlockColors blockColors = event.getBlockColors();
		final ItemColors itemColors = event.getItemColors();

		// Use the Block's colour handler for an ItemBlock
		final IItemColor itemBlockColourHandler = (stack, tintIndex) -> {
			final BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
			return blockColors.getColor(state, null, null, tintIndex);
		};

		itemColors.register(itemBlockColourHandler, TreasureBlocks.FALLING_GRASS);
	}

	/**
	 * 
	 * @author Mark Gottschling on Aug 12, 2020
	 *
	 */
	public static class ModItemGroup extends ItemGroup {
		private final Supplier<ItemStack> iconSupplier;

		public ModItemGroup(final String name, final Supplier<ItemStack> iconSupplier) {
			super(name);
			this.iconSupplier = iconSupplier;
		}

		@Override
		public ItemStack makeIcon() {
			return iconSupplier.get();
		}
	}
}
