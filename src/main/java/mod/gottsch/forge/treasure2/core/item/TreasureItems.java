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
package mod.gottsch.forge.treasure2.core.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

import mod.gottsch.forge.treasure2.core.item.weapon.*;
import mod.gottsch.forge.treasure2.core.util.LangUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.someguyssoftware.gottschcore.item.ModItem;
import com.someguyssoftware.gottschcore.loot.LootTableShell;

import mod.gottsch.forge.treasure2.core.Treasure;
import mod.gottsch.forge.treasure2.core.adornment.AdornmentSize;
import mod.gottsch.forge.treasure2.core.adornment.TreasureAdornmentRegistry;
import mod.gottsch.forge.treasure2.core.block.TreasureBlocks;
import mod.gottsch.forge.treasure2.core.capability.AdornmentCapabilityProvider;
import mod.gottsch.forge.treasure2.core.capability.CharmableCapability;
import mod.gottsch.forge.treasure2.core.capability.CharmableCapabilityProvider;
import mod.gottsch.forge.treasure2.core.capability.DurabilityCapability;
import mod.gottsch.forge.treasure2.core.capability.ICharmableCapability;
import mod.gottsch.forge.treasure2.core.capability.IDurabilityCapability;
import mod.gottsch.forge.treasure2.core.capability.IRunestonesCapability;
import mod.gottsch.forge.treasure2.core.capability.InventoryType;
import mod.gottsch.forge.treasure2.core.capability.RunestonesCapability;
import mod.gottsch.forge.treasure2.core.capability.RunestonesCapabilityProvider;
import mod.gottsch.forge.treasure2.core.capability.TreasureCapabilities;
import mod.gottsch.forge.treasure2.core.capability.modifier.GreatAdornmentLevelModifier;
import mod.gottsch.forge.treasure2.core.capability.modifier.ILevelModifier;
import mod.gottsch.forge.treasure2.core.capability.modifier.LordsAdornmentLevelModifier;
import mod.gottsch.forge.treasure2.core.capability.modifier.NoLevelModifier;
import mod.gottsch.forge.treasure2.core.charm.AegisCharm;
import mod.gottsch.forge.treasure2.core.charm.Charm;
import mod.gottsch.forge.treasure2.core.charm.CheatDeathCharm;
import mod.gottsch.forge.treasure2.core.charm.DrainCharm;
import mod.gottsch.forge.treasure2.core.charm.FireImmunityCharm;
import mod.gottsch.forge.treasure2.core.charm.GreaterHealingCharm;
import mod.gottsch.forge.treasure2.core.charm.HealingCharm;
import mod.gottsch.forge.treasure2.core.charm.ICharm;
import mod.gottsch.forge.treasure2.core.charm.IlluminationCharm;
import mod.gottsch.forge.treasure2.core.charm.LifeStrikeCharm;
import mod.gottsch.forge.treasure2.core.charm.ReflectionCharm;
import mod.gottsch.forge.treasure2.core.charm.RuinCurse;
import mod.gottsch.forge.treasure2.core.charm.SatietyCharm;
import mod.gottsch.forge.treasure2.core.charm.ShieldingCharm;
import mod.gottsch.forge.treasure2.core.charm.TreasureCharmRegistry;
import mod.gottsch.forge.treasure2.core.config.TreasureConfig;
import mod.gottsch.forge.treasure2.core.config.TreasureConfig.KeyID;
import mod.gottsch.forge.treasure2.core.config.TreasureConfig.LockID;
import mod.gottsch.forge.treasure2.core.enums.AdornmentType;
import mod.gottsch.forge.treasure2.core.enums.Category;
import mod.gottsch.forge.treasure2.core.enums.Rarity;
import mod.gottsch.forge.treasure2.core.loot.TreasureLootTableMaster2.SpecialLootTables;
import mod.gottsch.forge.treasure2.core.loot.TreasureLootTableRegistry;
import mod.gottsch.forge.treasure2.core.material.CharmableMaterial;
import mod.gottsch.forge.treasure2.core.material.TreasureArmorMaterial;
import mod.gottsch.forge.treasure2.core.material.TreasureCharmableMaterials;
import mod.gottsch.forge.treasure2.core.rune.AngelsRune;
import mod.gottsch.forge.treasure2.core.rune.DoubleChargeRune;
import mod.gottsch.forge.treasure2.core.rune.GreaterManaRune;
import mod.gottsch.forge.treasure2.core.rune.IRuneEntity;
import mod.gottsch.forge.treasure2.core.rune.TreasureRunes;
import mod.gottsch.forge.treasure2.core.util.ModUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author Mark Gottschling on Aug 12, 2020
 * This class has the register event handler for all custom items.
 * This class uses @Mod.EventBusSubscriber so the event handler has to be static
 *
 */
@Mod.EventBusSubscriber(modid = Treasure.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TreasureItems {
	// deferred registries
	final static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Treasure.MODID);

	// tabs
	public static RegistryObject<Item> TREASURE_TAB = ITEMS.register("treasure_tab", () -> new ModItem(new Item.Properties()));
	public static RegistryObject<Item> ADORNMENTS_TAB = ITEMS.register("adornments_tab", () -> new ModItem(new Item.Properties()));

	// property supplier
	public static final Supplier<Item.Properties> TREASURE_PROPS_SUPPLIER = () -> new Item.Properties().tab(TreasureItemGroups.TREASURE_ITEM_GROUP);

	// keys
	public static RegistryObject<KeyItem> WOOD_KEY = ITEMS.register(KeyID.WOOD_KEY_ID, () -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.woodKeyMaxUses.get()))
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.COMMON)
			.setCraftable(false));

	public static RegistryObject<KeyItem> STONE_KEY = ITEMS.register(KeyID.STONE_KEY_ID, () -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.stoneKeyMaxUses.get()))
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.COMMON)
			.setCraftable(false));

	public static RegistryObject<KeyItem> EMBER_KEY = ITEMS.register(KeyID.EMBER_KEY_ID, () -> new EmberKey(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.emberKeyMaxUses.get()))
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.SCARCE)
			.setCraftable(false));   

	public static RegistryObject<KeyItem> LEAF_KEY = ITEMS.register(KeyID.LEAF_KEY_ID, () -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.leafKeyMaxUses.get()))
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.UNCOMMON)
			.setCraftable(false));    

	public static RegistryObject<KeyItem> LIGHTNING_KEY = ITEMS.register(KeyID.LIGHTNING_KEY_ID, () -> new LightningKey(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.lightningKeyMaxUses.get()))
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.SCARCE)
			.setBreakable(false)
			.setCraftable(false));

	public static RegistryObject<KeyItem> IRON_KEY = ITEMS.register(KeyID.IRON_KEY_ID, () -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.ironKeyMaxUses.get()))
			.setCategory(Category.METALS)
			.setRarity(Rarity.UNCOMMON)
			.setCraftable(false));

	public static RegistryObject<KeyItem> GOLD_KEY = ITEMS.register(KeyID.GOLD_KEY_ID, () -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.goldKeyMaxUses.get()))
			.setCategory(Category.METALS)
			.setRarity(Rarity.SCARCE)
			.setCraftable(false));

	public static RegistryObject<KeyItem> METALLURGISTS_KEY = ITEMS.register(KeyID.METALLURGISTS_KEY_ID, () -> new MetallurgistsKey(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.metallurgistsKeyMaxUses.get()))
			.setCategory(Category.METALS)
			.setRarity(Rarity.RARE)
			.setBreakable(false)
			.setCraftable(false));

	public static RegistryObject<KeyItem> DIAMOND_KEY = ITEMS.register(KeyID.DIAMOND_KEY_ID, () -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.diamondKeyMaxUses.get()))
			.setCategory(Category.GEMS)
			.setRarity(Rarity.RARE)
			.setBreakable(false)
			.setCraftable(false));

	public static RegistryObject<KeyItem> EMERALD_KEY = ITEMS.register(KeyID.EMERALD_KEY_ID, () -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.emeraldKeyMaxUses.get()))
			.setCategory(Category.GEMS)
			.setRarity(Rarity.RARE)
			.setBreakable(false)
			.setCraftable(false));

	public static RegistryObject<KeyItem> TOPAZ_KEY = ITEMS.register("topaz_key", 
			() -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.topazKeyMaxUses.get()))
			.setCategory(Category.GEMS)
			.setRarity(Rarity.SCARCE)
			.setBreakable(false)
			.setCraftable(true));
	
	public static RegistryObject<KeyItem> ONYX_KEY = ITEMS.register("onyx_key", 
			() -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.onyxKeyMaxUses.get()))
			.setCategory(Category.GEMS)
			.setRarity(Rarity.SCARCE)
			.setBreakable(false)
			.setCraftable(true));
	
	public static RegistryObject<KeyItem> RUBY_KEY = ITEMS.register(KeyID.RUBY_KEY_ID, 
			() -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.rubyKeyMaxUses.get()))
			.setCategory(Category.GEMS)
			.setRarity(Rarity.EPIC)
			.setBreakable(false)
			.setCraftable(true));

	public static RegistryObject<KeyItem> SAPPHIRE_KEY = ITEMS.register(KeyID.SAPPHIRE_KEY_ID, 
			() -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.sapphireKeyMaxUses.get()))
			.setCategory(Category.GEMS)
			.setRarity(Rarity.EPIC)
			.setBreakable(false)
			.setCraftable(true));

	public static RegistryObject<KeyItem> JEWELLED_KEY = ITEMS.register(KeyID.JEWELLED_KEY_ID, 
			() -> new JewelledKey(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.jewelledKeyMaxUses.get()))
			.setCategory(Category.GEMS)
			.setRarity(Rarity.EPIC)
			.setBreakable(false)
			.setCraftable(false));

	public static RegistryObject<KeyItem> SKELETON_KEY = ITEMS.register(KeyID.SKELETON_KEY_ID, 
			() -> new SkeletonKey(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.skeletonKeyMaxUses.get()))
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.RARE)
			.setBreakable(false)
			.setCraftable(false));

	public static RegistryObject<KeyItem> WITHER_KEY = ITEMS.register(KeyID.WITHER_KEY_ID, 
			() -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.witherKeyMaxUses.get()))
			.setCategory(Category.WITHER)
			.setRarity(Rarity.RARE)
			.setBreakable(false)
			.setCraftable(true));


	public static RegistryObject<KeyItem> SPIDER_KEY = ITEMS.register(KeyID.SPIDER_KEY_ID, 
			() -> new KeyItem(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.spiderKeyMaxUses.get()))
			.setCategory(Category.MOB)
			.setRarity(Rarity.SCARCE)
			.setBreakable(true)
			.setCraftable(true));

	public static RegistryObject<KeyItem> PILFERERS_LOCK_PICK = ITEMS.register(KeyID.PILFERERS_LOCK_PICK_ID, 
			() -> new PilferersLockPick(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.pilferersLockPickMaxUses.get()))
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.COMMON)
			.setBreakable(true)
			.setCraftable(true)
			.setSuccessProbability(32));

	public static RegistryObject<KeyItem> THIEFS_LOCK_PICK = ITEMS.register(KeyID.THIEFS_LOCK_PICK_ID, 
			() -> new ThiefsLockPick(new Item.Properties().durability(TreasureConfig.KEYS_LOCKS.thiefsLockPickMaxUses.get()))
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.UNCOMMON)
			.setBreakable(true)
			.setCraftable(true)
			.setSuccessProbability(48));

	public static RegistryObject<KeyItem> ONE_KEY = ITEMS.register("one_key", 
			() -> new OneKey(new Item.Properties().durability(1000)) {
				
				public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
					if (!EnchantmentHelper.hasVanishingCurse(stack)) {
						stack.enchant(Enchantments.VANISHING_CURSE, 1);
					}
					return super.initCapabilities(stack, nbt);
				}
			}
			.setCategory(Category.MAGIC)
			.setRarity(Rarity.EPIC)
			.setBreakable(false)
			.setCraftable(false)
			.setSuccessProbability(100));
	
	// FUTURE
	public static RegistryObject<KeyItem> DRAGON_KEY;
	public static RegistryObject<KeyItem> MASTER_KEY;

	public static RegistryObject<KeyRingItem> KEY_RING = ITEMS.register(KeyID.KEY_RING_ID, () -> new KeyRingItem(new Item.Properties()));

	// locks
	public static final Supplier<Item.Properties> LOCK_ITEM_PROPERTIES = () -> new Item.Properties().tab(TreasureItemGroups.TREASURE_ITEM_GROUP);
	public static RegistryObject<LockItem> WOOD_LOCK = ITEMS.register(LockID.WOOD_LOCK_ID, () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {WOOD_KEY.get(), LIGHTNING_KEY.get()})
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.COMMON));
	public static RegistryObject<LockItem> STONE_LOCK = ITEMS.register(LockID.STONE_LOCK_ID, () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {STONE_KEY.get(), LIGHTNING_KEY.get()})
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.COMMON));
	public static RegistryObject<LockItem> EMBER_LOCK = ITEMS.register(LockID.EMBER_LOCK_ID, () -> new EmberLock(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {EMBER_KEY.get(), LIGHTNING_KEY.get()})
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.SCARCE));
	public static RegistryObject<LockItem> LEAF_LOCK = ITEMS.register(LockID.LEAF_LOCK_ID, () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {LEAF_KEY.get(), LIGHTNING_KEY.get()})
			.setCategory(Category.ELEMENTAL)
			.setRarity(Rarity.UNCOMMON));  
	public static RegistryObject<LockItem> IRON_LOCK = ITEMS.register(LockID.IRON_LOCK_ID, () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {IRON_KEY.get(), METALLURGISTS_KEY.get()})
			.setCategory(Category.METALS)
			.setRarity(Rarity.UNCOMMON));
	public static RegistryObject<LockItem> GOLD_LOCK = ITEMS.register(LockID.GOLD_LOCK_ID, () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {GOLD_KEY.get(), METALLURGISTS_KEY.get()})
			.setCategory(Category.METALS)
			.setRarity(Rarity.SCARCE));
	public static RegistryObject<LockItem> DIAMOND_LOCK = ITEMS.register(LockID.DIAMOND_LOCK_ID, () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {DIAMOND_KEY.get(), JEWELLED_KEY.get()})
			.setCategory(Category.GEMS)
			.setRarity(Rarity.RARE));
	public static RegistryObject<LockItem> EMERALD_LOCK = ITEMS.register(LockID.EMERALD_LOCK_ID, () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {EMERALD_KEY.get(), JEWELLED_KEY.get()})
			.setCategory(Category.GEMS)
			.setRarity(Rarity.RARE));
	public static RegistryObject<LockItem> TOPAZ_LOCK = ITEMS.register("topaz_lock", () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {TOPAZ_KEY.get(), JEWELLED_KEY.get()})
			.setCategory(Category.GEMS)
			.setRarity(Rarity.SCARCE));
	public static RegistryObject<LockItem> ONYX_LOCK = ITEMS.register("onyx_lock", () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {ONYX_KEY.get(), JEWELLED_KEY.get()})
			.setCategory(Category.GEMS)
			.setRarity(Rarity.SCARCE));	
	public static RegistryObject<LockItem> RUBY_LOCK = ITEMS.register(LockID.RUBY_LOCK_ID, () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {RUBY_KEY.get(), JEWELLED_KEY.get()})
			.setCategory(Category.GEMS)
			.setRarity(Rarity.EPIC));
	public static RegistryObject<LockItem> SAPPHIRE_LOCK = ITEMS.register(LockID.SAPPHIRE_LOCK_ID, () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {SAPPHIRE_KEY.get(), JEWELLED_KEY.get()})
			.setCategory(Category.GEMS)
			.setRarity(Rarity.EPIC));
	public static RegistryObject<LockItem> SPIDER_LOCK = ITEMS.register(LockID.SPIDER_LOCK_ID, () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {SPIDER_KEY.get()})
			.setCategory(Category.MOB)
			.setRarity(Rarity.SCARCE));
	public static RegistryObject<LockItem> WITHER_LOCK = ITEMS.register(LockID.WITHER_LOCK_ID, () -> new LockItem(LOCK_ITEM_PROPERTIES.get(), new KeyItem[] {WITHER_KEY.get()})
			.setCategory(Category.WITHER)
			.setRarity(Rarity.SCARCE));

	// coins
	public static RegistryObject<Item> COPPER_COIN = ITEMS.register(TreasureConfig.ItemID.COPPER_COIN_ID, () -> new WealthItem(new Item.Properties()));
	public static RegistryObject<Item> SILVER_COIN = ITEMS.register(TreasureConfig.ItemID.SILVER_COIN_ID, () -> new WealthItem(new Item.Properties()) {
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
	});
	public static RegistryObject<Item> GOLD_COIN = ITEMS.register(TreasureConfig.ItemID.GOLD_COIN_ID, () -> new WealthItem(new Item.Properties()) {
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
	});
	
	// gems
	public static RegistryObject<Item> TOPAZ = ITEMS.register(TreasureConfig.ItemID.TOPAZ_ID, () -> new GemItem(new Item.Properties()) {
		@Override
		public List<LootTableShell> getLootTables() {
			return TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.SCARCE);
		}
		@Override
		public ItemStack getDefaultLootKey (Random random) {
			List<KeyItem> keys = new ArrayList<>(TreasureItems.keys.get(Rarity.SCARCE));
			return new ItemStack(keys.get(random.nextInt(keys.size())));
		}
	});
	public static RegistryObject<Item> ONYX = ITEMS.register(TreasureConfig.ItemID.ONYX_ID, () -> new GemItem(new Item.Properties()) {
		@Override
		public List<LootTableShell> getLootTables() {
			return TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.RARE);
		}
		@Override
		public ItemStack getDefaultLootKey (Random random) {
			List<KeyItem> keys = new ArrayList<>(TreasureItems.keys.get(Rarity.RARE));
			return new ItemStack(keys.get(random.nextInt(keys.size())));
		}
	});
	public static RegistryObject<Item> RUBY = ITEMS.register(TreasureConfig.ItemID.RUBY_ID, () -> new GemItem(new Item.Properties()) {
		@Override
		public List<LootTableShell> getLootTables() {
			return TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.RARE);
		}
		@Override
		public ItemStack getDefaultLootKey (Random random) {
			List<KeyItem> keys = new ArrayList<>(TreasureItems.keys.get(Rarity.RARE));
			return new ItemStack(keys.get(random.nextInt(keys.size())));
		}
	});
	public static RegistryObject<Item> SAPPHIRE = ITEMS.register(TreasureConfig.ItemID.SAPPHIRE_ID, () -> new GemItem(new Item.Properties()) {
		@Override
		public List<LootTableShell> getLootTables() {
			return TreasureLootTableRegistry.getLootTableMaster().getLootTableByRarity(Rarity.EPIC);
		}
		@Override
		public ItemStack getDefaultLootKey (Random random) {
			List<KeyItem> keys = new ArrayList<>(TreasureItems.keys.get(Rarity.EPIC));
			return new ItemStack(keys.get(random.nextInt(keys.size())));
		}
	});
	public static RegistryObject<Item> WHITE_PEARL = ITEMS.register(TreasureConfig.ItemID.WHITE_PEARL_ID, () -> new GemItem(new Item.Properties()) {
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
	});
	public static RegistryObject<Item> BLACK_PEARL = ITEMS.register(TreasureConfig.ItemID.BLACK_PEARL_ID, () -> new GemItem(new Item.Properties()) {
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
	});
	
	// charms
	public static RegistryObject<CharmItem> CHARM_BOOK = ITEMS.register("charm_book", () -> new CharmBook(new Item.Properties()) {
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			ICharmableCapability cap = new CharmableCapability.Builder(1, 0, 0).with($ -> {
				$.innate = true;
				$.imbuing = true;
				$.source = true;
				$.executing = false;
				$.baseMaterial = TreasureCharmableMaterials.CHARM_BOOK.getName();
				$.sourceItem = Items.AIR.getRegistryName();
			}).build();
			// TEMP test with charm
			cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(HealingCharm.TYPE, 1))).get().createEntity());
			return new CharmableCapabilityProvider(cap);
		}
	});
	
	// pouch
	public static RegistryObject<PouchItem> POUCH = ITEMS.register("pouch", () -> new PouchItem(new Item.Properties()));
	
	// adornments
	public static RegistryObject<Adornment> ANGELS_RING;
	public static RegistryObject<Item> RING_OF_FORTITUDE;
	public static RegistryObject<Item> PEASANTS_FORTUNE;
	public static RegistryObject<Item> GOTTSCHS_RING_OF_MOON;
	public static RegistryObject<Item> GOTTSCHS_AMULET_OF_HEAVENS;
	public static RegistryObject<Item> CASTLE_RING;
	public static RegistryObject<Item> SHADOWS_GIFT;
	public static RegistryObject<Item> BRACELET_OF_WONDER;
	public static RegistryObject<Item> RING_OF_LIFE_DEATH;

	public static RegistryObject<Item> MEDICS_TOKEN;
	public static RegistryObject<Item> SALANDAARS_WARD;	
	public static RegistryObject<Item> ADEPHAGIAS_BOUNTY;
	public static RegistryObject<Item> MIRTHAS_TORCH;	
	//	public static RegistryObject<Item> DWARVEN_TALISMAN;
	//	public static Adornment MINERS_FRIEND;

	public static RegistryObject<Adornment> POCKET_WATCH;

	// runestones
	public static RegistryObject<RunestoneItem> MANA_RUNESTONE = ITEMS.register("mana_runestone", () -> new RunestoneItem(new Item.Properties()) {
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			IRunestonesCapability cap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
				$.bindable = true;
			}).build();
			cap.add(InventoryType.INNATE, TreasureRunes.RUNE_OF_MANA.createEntity());	
			return new RunestonesCapabilityProvider(cap);
		}
	});
	
	public static RegistryObject<RunestoneItem> GREATER_MANA_RUNESTONE = ITEMS.register("greater_mana_runestone", () -> new RunestoneItem(new Item.Properties()) {
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			IRunestonesCapability cap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
				$.bindable = true;
			}).build();
			cap.add(InventoryType.INNATE, TreasureRunes.RUNE_OF_GREATER_MANA.createEntity());	
			return new RunestonesCapabilityProvider(cap);
		}
	});
	
	public static RegistryObject<RunestoneItem> DURABILITY_RUNESTONE = ITEMS.register("durability_runestone", () -> new RunestoneItem(new Item.Properties()) {
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			IRunestonesCapability cap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
				$.bindable = true;
			}).build();
			cap.add(InventoryType.INNATE, TreasureRunes.RUNE_OF_DURABILITY.createEntity());	
			return new RunestonesCapabilityProvider(cap);
		}
	});
	public static RegistryObject<RunestoneItem> QUALITY_RUNESTONE = ITEMS.register("quality_runestone", () -> new RunestoneItem(new Item.Properties()) {
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			IRunestonesCapability cap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
				$.bindable = true;
			}).build();
			cap.add(InventoryType.INNATE, TreasureRunes.RUNE_OF_QUALITY.createEntity());	
			return new RunestonesCapabilityProvider(cap);
		}
	});
	public static RegistryObject<RunestoneItem> EQUIP_MANA_RUNESTONE = ITEMS.register("equip_mana_runestone", () -> new RunestoneItem(new Item.Properties()) {
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			IRunestonesCapability cap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
				$.bindable = true;
			}).build();
			cap.add(InventoryType.INNATE, TreasureRunes.RUNE_OF_EQUIP_AS_MANA.createEntity());	
			return new RunestonesCapabilityProvider(cap);
		}
	});
	public static RegistryObject<RunestoneItem> ANVIL_RUNESTONE = ITEMS.register("anvil_runestone", () -> new RunestoneItem(new Item.Properties()) {
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			IRunestonesCapability cap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
				$.bindable = true;
				//						$.sourceItem = AMETHYST.getRegistryName();
			}).build();
			cap.add(InventoryType.INNATE, TreasureRunes.RUNE_OF_ANVIL.createEntity());	
			return new RunestonesCapabilityProvider(cap);
		}
	});
	public static RegistryObject<RunestoneItem> ANGELS_RUNESTONE = ITEMS.register("angels_runestone", () -> new RunestoneItem(new Item.Properties()) {
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			IRunestonesCapability cap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
				$.bindable = true;
			}).build();
			cap.add(InventoryType.INNATE, TreasureRunes.RUNE_OF_ANGELS.createEntity());	
			return new RunestonesCapabilityProvider(cap);
		}		
	});
	public static RegistryObject<RunestoneItem> PERSISTENCE_RUNESTONE = ITEMS.register("persistence_runestone", () -> new RunestoneItem(new Item.Properties()) {
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			IRunestonesCapability cap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
				$.bindable = true;
			}).build();
			cap.add(InventoryType.INNATE, TreasureRunes.RUNE_OF_PERSISTENCE.createEntity());	
			return new RunestonesCapabilityProvider(cap);
		}		
	});
	public static RegistryObject<RunestoneItem> SOCKETS_RUNESTONE = ITEMS.register("sockets_runestone", () -> new RunestoneItem(new Item.Properties()) {
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			IRunestonesCapability cap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
				$.bindable = true;
			}).build();
			cap.add(InventoryType.INNATE, TreasureRunes.RUNE_OF_SOCKETS.createEntity());	
			return new RunestonesCapabilityProvider(cap);
		}		
	});
	public static RegistryObject<RunestoneItem> DOUBLE_CHARGE_RUNESTONE = ITEMS.register("double_charge_runestone", () -> new RunestoneItem(new Item.Properties()) {
		public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
			IRunestonesCapability cap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
				$.bindable = true;
			}).build();
			cap.add(InventoryType.INNATE, TreasureRunes.RUNE_OF_DOUBLE_CHARGE.createEntity());	
			return new RunestonesCapabilityProvider(cap);
		}		
	});


	// wither items
	public static RegistryObject<WitherStickItem> WITHER_STICK_ITEM = ITEMS.register(TreasureConfig.ItemID.WITHER_STICK_ITEM_ID, () -> new WitherStickItem(TreasureBlocks.WITHER_BRANCH, new Item.Properties()));
	
	public static RegistryObject<WitherRootItem> WITHER_ROOT_ITEM = ITEMS.register(TreasureConfig.ItemID.WITHER_ROOT_ITEM_ID, () -> new WitherRootItem(TreasureBlocks.WITHER_ROOT, new Item.Properties()));

	/*
	 * weapons
	 */
	// short swords
	public static final RegistryObject<SwordItem> COPPER_SHORT_SWORD = ITEMS.register("copper_short_sword",
			() -> new Sword(TreasureItemTier.COPPER, 2.5f, -2.0F, TREASURE_PROPS_SUPPLIER.get()));

	public static final RegistryObject<Item> CHIPPED_COPPER_SHORT_SWORD = ITEMS.register("chipped_copper_short_sword",
			() -> new Sword(TreasureItemTier.COPPER, 2.4f, -2.0F, TREASURE_PROPS_SUPPLIER.get()));

	public static final RegistryObject<Item> IRON_SHORT_SWORD = ITEMS.register("iron_short_sword",
			() -> new Sword(ItemTier.IRON, 2.5f, -2.0F, TREASURE_PROPS_SUPPLIER.get()));

	public static final RegistryObject<Item> CHIPPED_IRON_SHORT_SWORD = ITEMS.register("chipped_iron_short_sword",
			() -> new Sword(ItemTier.IRON, 2.4f, -2.0F, TREASURE_PROPS_SUPPLIER.get()));

	public static final RegistryObject<Item> STEEL_SHORT_SWORD = ITEMS.register("steel_short_sword",
			() -> new Sword(TreasureItemTier.STEEL, 2.5f, -2.0F, TREASURE_PROPS_SUPPLIER.get()));

	public static final RegistryObject<Item> CHIPPED_STEEL_SHORT_SWORD = ITEMS.register("chipped_steel_short_sword",
			() -> new Sword(TreasureItemTier.STEEL, 2.4f, -2.0F, TREASURE_PROPS_SUPPLIER.get()));

	// rapier
	public static final RegistryObject<Item> COPPER_RAPIER = ITEMS.register("copper_rapier",
			() -> new Sword(TreasureItemTier.COPPER, 2.6f, -2.0F, TREASURE_PROPS_SUPPLIER.get()));

	// longswords (steel, skull, shadow, +)
	public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword",
			() -> new SwordItem(TreasureItemTier.STEEL, 3, -2.4F, TREASURE_PROPS_SUPPLIER.get()));

	public static final RegistryObject<SwordItem> SKULL_SWORD = ITEMS.register(TreasureConfig.ItemID.SKULL_SWORD_ID, 
			() -> new Sword(TreasureItemTier.SKULL, 3, -2.4F, TREASURE_PROPS_SUPPLIER.get()));

	// large sword
	public static final RegistryObject<Item> SWORD_POWER = ITEMS.register("sword_of_power",
			() -> new UniqueSword(TreasureItemTier.MYTHICAL, 3, -2.4F, TREASURE_PROPS_SUPPLIER.get()));

	public static final RegistryObject<Item> BLACK_SWORD = ITEMS.register("black_sword",
			() -> new UniqueSword(TreasureItemTier.MYTHICAL, 3, -2.4F, TREASURE_PROPS_SUPPLIER.get()));

	public static final RegistryObject<Item> OATHBRINGER = ITEMS.register("oathbringer",
			() -> new UniqueSword(TreasureItemTier.MYTHICAL, 3, -2.0F, 50, 7F, TREASURE_PROPS_SUPPLIER.get()) {
				@Override
				public  void appendHoverExtras(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
					appendHoverExtrasMultiline(stack, world, tooltip,flag);
				}
			});

	public static final RegistryObject<Item> SWORD_OMENS = ITEMS.register("sword_of_omens",
			() -> new UniqueSword(TreasureItemTier.EPIC, 3, -2.4F, 35, 5F, TREASURE_PROPS_SUPPLIER.get()) {
				@Override
				public  void appendHoverExtras(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
					appendHoverExtrasMultiline(stack, world, tooltip,flag);
				}
			});

	public static final RegistryObject<Item> CALLANDOR = ITEMS.register("callandor",
			() -> new UniqueSword(TreasureItemTier.MYTHICAL, 3, -2.0F, 75, 9F, TREASURE_PROPS_SUPPLIER.get()) {
				@Override
				public  void appendHoverExtras(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
					appendHoverExtrasMultiline(stack, world, tooltip,flag);
				}
			});

	// broad/bastard swords (steel, black)
	public static final RegistryObject<Item> IRON_BROADSWORD = ITEMS.register("iron_broadsword",
			() -> new Sword(ItemTier.IRON, 3.5f, -2.8F, TREASURE_PROPS_SUPPLIER.get()));

	public static final RegistryObject<Item> STEEL_BROADSWORD = ITEMS.register("steel_broadsword",
			() -> new Sword(TreasureItemTier.STEEL, 3.5f, -2.8F, TREASURE_PROPS_SUPPLIER.get()));

	// scythes
	public static final RegistryObject<Item> ORCUS = ITEMS.register("orcus",
			() -> new UniqueSword(TreasureItemTier.LEGENDARY, 3, -2.4F, 40, 5F, TREASURE_PROPS_SUPPLIER.get()) {
				@Override
				public  void appendHoverExtras(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
					appendHoverExtrasMultiline(stack, world, tooltip,flag);
				}
			});

	// katanas (steel, shadow + )
	public static final RegistryObject<Item> SNAKE_EYES_KATANA = ITEMS.register("snake_eyes_katana",
			() -> new UniqueSword(TreasureItemTier.RARE, 3, -1.5F, 25, 5f, TREASURE_PROPS_SUPPLIER.get()) {
				@Override
				public  void appendHoverExtras(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
					appendHoverExtrasMultiline(stack, world, tooltip,flag);
				}
			});

	public static final RegistryObject<Item> STORM_SHADOWS_KATANA = ITEMS.register("storm_shadows_katana",
			() -> new UniqueSword(TreasureItemTier.RARE, 3, -1.5f, 25, 5f, TREASURE_PROPS_SUPPLIER.get()) {
				@Override
				public  void appendHoverExtras(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
					appendHoverExtrasMultiline(stack, world, tooltip,flag);
				}
			});

	// machetes (steel, shadow)
	public static final RegistryObject<Item> STEEL_MACHETE = ITEMS.register("steel_machete",
			() -> new Sword(TreasureItemTier.STEEL, 2.7F, -2.6F, TREASURE_PROPS_SUPPLIER.get()));
	public static final RegistryObject<Item> SHADOW_MACHETE = ITEMS.register("shadow_machete",
			() -> new Sword(TreasureItemTier.SHADOW, 2.7F, -2.6F, TREASURE_PROPS_SUPPLIER.get()));

	// flachions (steel, shadow)
	public static final RegistryObject<Item> IRON_FALCHION = ITEMS.register("iron_falchion",
			() -> new Sword(ItemTier.IRON, 2.8F, -2.4F, TREASURE_PROPS_SUPPLIER.get()));
	public static final RegistryObject<Item> STEEL_FALCHION = ITEMS.register("steel_falchion",
			() -> new Sword(TreasureItemTier.STEEL, 2.8F, -2.4F, TREASURE_PROPS_SUPPLIER.get()));
	public static final RegistryObject<Item> SHADOW_FALCHION = ITEMS.register("shadow_falchion",
			() -> new Sword(TreasureItemTier.SHADOW, 2.8F, -2.4F, TREASURE_PROPS_SUPPLIER.get()));

	// hammers / maces / mauls
	public static final RegistryObject<Item> IRON_MACE = ITEMS.register("iron_mace",
			() -> new Sword(ItemTier.IRON, TreasureWeapons.HAMMER_BASE_DAMAGE, TreasureWeapons.HAMMER_BASE_SPEED, TREASURE_PROPS_SUPPLIER.get()));
	public static final RegistryObject<Item> STEEL_MACE = ITEMS.register("steel_mace",
			() -> new Sword(TreasureItemTier.STEEL, TreasureWeapons.HAMMER_BASE_DAMAGE, TreasureWeapons.HAMMER_BASE_SPEED, TREASURE_PROPS_SUPPLIER.get()));

	public static final RegistryObject<Item> MJOLNIR = ITEMS.register("mjolnir",
			() -> new UniqueSword(TreasureItemTier.MYTHICAL,
					TreasureWeapons.HAMMER_BASE_DAMAGE, TreasureWeapons.HAMMER_BASE_SPEED + 0.7f,
					75, 9F, TREASURE_PROPS_SUPPLIER.get()) {
				@Override
				public  void appendHoverExtras(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
					appendHoverExtrasMultiline(stack, world, tooltip,flag);
				}
			});

	// axes
	//	WOODEN_AXE = new AxeItem(Tiers.WOOD, 6.0F, -3.2F
	//	STONE_AXE = new AxeItem(Tiers.STONE, 7.0F, -3.2F
	//	IRON_AXE = new AxeItem(Tiers.IRON, 6.0F, -3.1F  // USE AS DEFAULT STATS FOR AXES
	//	DIAMOND_AXE = new AxeItem(Tiers.DIAMOND, 5.0F, -3.0F
	//	NETHERITE_AXE = new AxeItem(Tiers.NETHERITE, 5.0F, -3.0F
	public static final RegistryObject<Item> COPPER_BROAD_AXE = ITEMS.register("copper_broad_axe",
			() -> new Axe(TreasureItemTier.COPPER, TreasureWeapons.AXE_BASE_DAMAGE - 1.0F, TreasureWeapons.AXE_BASE_SPEED + 0.2f, TREASURE_PROPS_SUPPLIER.get()));
	public static final RegistryObject<Item> IRON_BROAD_AXE = ITEMS.register("iron_broad_axe",
			() -> new Axe(ItemTier.IRON, TreasureWeapons.AXE_BASE_DAMAGE - 1.0F, TreasureWeapons.AXE_BASE_SPEED + 0.2f, TREASURE_PROPS_SUPPLIER.get()));
	public static final RegistryObject<Item> STEEL_BROAD_AXE = ITEMS.register("steel_broad_axe",
			() -> new Axe(TreasureItemTier.STEEL, TreasureWeapons.AXE_BASE_DAMAGE - 1.0F, TreasureWeapons.AXE_BASE_SPEED + 0.2f, TREASURE_PROPS_SUPPLIER.get()));

	public static final RegistryObject<Item> IRON_DWARVEN_AXE = ITEMS.register("iron_dwarven_axe",
			() -> new Axe(ItemTier.IRON, TreasureWeapons.AXE_BASE_DAMAGE + 0.5F, TreasureWeapons.AXE_BASE_SPEED + 0.1f, TREASURE_PROPS_SUPPLIER.get()));


	public static final RegistryObject<Item> AXE_DURIN = ITEMS.register("axe_of_durin",
			() -> new UniqueAxe(TreasureItemTier.LEGENDARY, TreasureWeapons.AXE_BASE_DAMAGE, TreasureWeapons.AXE_BASE_SPEED + 0.5f,
					65, 7F, TREASURE_PROPS_SUPPLIER.get()) {
				@Override
				public  void appendHoverExtras(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
					appendHoverExtrasMultiline(stack, world, tooltip,flag);
				}
			});

	public static final RegistryObject<Item> HEADSMANS_AXE = ITEMS.register("headsmans_axe",
			() -> new UniqueAxe(TreasureItemTier.EPIC, TreasureWeapons.AXE_BASE_DAMAGE, TreasureWeapons.AXE_BASE_SPEED + 0.3f,
					55, 6F, TREASURE_PROPS_SUPPLIER.get()) {
				@Override
				public  void appendHoverExtras(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
					appendHoverExtrasMultiline(stack, world, tooltip,flag);
				}
			});

	// other
	public static final RegistryObject<Item> EYE_PATCH = ITEMS.register(TreasureConfig.ItemID.EYE_PATCH_ID, 
			() ->  new DyeableArmorItem(TreasureArmorMaterial.PATCH, EquipmentSlotType.HEAD, (new Item.Properties()).tab(TreasureItemGroups.TREASURE_ITEM_GROUP)));

	//	public static RegistryObject<SpanishMossItem> SPANISH_MOSS = ITEMS.register("spanish_moss", () -> new SpanishMossItem(new Item.Properties()));

	public static RegistryObject<SkeletonItem> SKELETON = ITEMS.register(TreasureConfig.ItemID.SKELETON_ITEM_ID, () -> new SkeletonItem(TreasureBlocks.SKELETON, new Item.Properties()));

	public static RegistryObject<Item> TREASURE_TOOL = ITEMS.register("treasure_tool", () -> new TreasureToolItem(new Item.Properties()));
	
	// TODO potions

	// key map
	public static Multimap<Rarity, KeyItem> keys = ArrayListMultimap.create();
	// lock map
	public static Multimap<Rarity, LockItem> locks = ArrayListMultimap.create();

	/*
	 *  items caches
	 */
	public static final Map<ResourceLocation, Item> ALL_ITEMS = new HashMap<>();
	public static final Map<ResourceLocation, CharmItem> CHARM_ITEMS = new HashMap<>();
	public static final Map<ResourceLocation, Adornment> ADORNMENT_ITEMS = new HashMap<>();

	static {				
		ANGELS_RING = ITEMS.register("angels_ring", () -> new NamedAdornment(AdornmentType.RING, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(3, 1, 1)
						.with($ -> {
							$.innate = true;
							$.imbuable = true;
							$.socketable = true;
							$.source = false;
							$.executing = true;
							$.namedByCharm = false;
							$.namedByMaterial = false;
							$.baseMaterial = TreasureCharmableMaterials.GOLD.getName();
							$.sourceItem = WHITE_PEARL.get().getRegistryName();
							$.levelModifier = new GreatAdornmentLevelModifier();
						}).build();
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(AegisCharm.AEGIS_TYPE, 16))).get().createEntity());
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(GreaterHealingCharm.HEALING_TYPE, 16))).get().createEntity());
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(FireImmunityCharm.FIRE_IMMUNITY_TYPE, 16))).get().createEntity());

				IDurabilityCapability durabilityCap = new DurabilityCapability(1000, 1000);
				durabilityCap.setMaxRepairs(1);
				durabilityCap.setRepairs(1);

				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
					$.socketable = false;
				}).build();
				IRuneEntity runeEntity = TreasureRunes.RUNE_OF_ANGELS.createEntity();
				runestonesCap.add(InventoryType.INNATE, runeEntity);
				((AngelsRune)runeEntity.getRunestone()).initCapabilityApply(cap, durabilityCap, runeEntity);

				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});

		RING_OF_FORTITUDE = ITEMS.register("ring_of_fortitude", () -> new NamedAdornment(AdornmentType.RING, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(2, 1, 1).with($ -> {
					$.innate = true;
					$.imbuable = true;
					$.socketable = true;
					$.source = false;
					$.executing = true;
					$.namedByCharm = false;
					$.namedByMaterial = false;
					$.baseMaterial = TreasureCharmableMaterials.GOLD.getName();
					$.sourceItem = BLACK_PEARL.get().getRegistryName();
					$.levelModifier = new GreatAdornmentLevelModifier();
				}).build();
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(AegisCharm.AEGIS_TYPE, 16))).get().createEntity());
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(ReflectionCharm.REFLECTION_TYPE, 16))).get().createEntity());

				IDurabilityCapability durabilityCap = new DurabilityCapability();
				durabilityCap.setInfinite(true);
				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(0, 0, 1).with($ -> {
					$.socketable = true;
				}).build();

				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});

		// (mythical)
		SHADOWS_GIFT = ITEMS.register("shadows_gift", () -> new NamedAdornment(AdornmentType.RING, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {

				/*
				 *  add enchantment. kinda hacky
				 */
				if (!EnchantmentHelper.hasVanishingCurse(stack)) {
					stack.enchant(Enchantments.VANISHING_CURSE, 1);
				}

				ICharmableCapability cap = new CharmableCapability.Builder(1, 0, 1).with($ -> {
					$.innate = true;
					$.imbuable = true;
					$.socketable = true;
					$.source = false;
					$.executing = true;
					$.namedByCharm = false;
					$.namedByMaterial = false;
					$.baseMaterial = TreasureCharmableMaterials.BLACK.getName();
					$.sourceItem = BLACK_PEARL.get().getRegistryName();
					$.levelModifier = new NoLevelModifier();
				}).build();
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(DrainCharm.DRAIN_TYPE, 25))).get().createEntity());

				IDurabilityCapability durabilityCap = new DurabilityCapability(500, 500, TreasureCharmableMaterials.BLACK);
				durabilityCap.setInfinite(true);
				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(0, 0, 1).with($ -> {
					$.socketable = true;
				}).build();
				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});

		// (mythical)
		RING_OF_LIFE_DEATH = ITEMS.register("ring_of_life_death", () -> new NamedAdornment(AdornmentType.RING, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				/*
				 *  add enchantment. kinda hacky
				 */
				if (!EnchantmentHelper.hasVanishingCurse(stack)) {
					stack.enchant(Enchantments.VANISHING_CURSE, 1);
				}

				ICharmableCapability cap = new CharmableCapability.Builder(3, 1, 1).with($ -> {
					$.innate = true;
					$.imbuable = true;
					$.socketable = true;
					$.source = false;
					$.executing = true;
					$.namedByCharm = false;
					$.namedByMaterial = false;
					$.baseMaterial = TreasureCharmableMaterials.BLOOD.getName();
					$.sourceItem = WHITE_PEARL.get().getRegistryName();
					$.levelModifier = new GreatAdornmentLevelModifier();
				}).build();
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(LifeStrikeCharm.LIFE_STRIKE_TYPE, 25))).get().createEntity());
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(CheatDeathCharm.TYPE, 25))).get().createEntity());
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(RuinCurse.RUIN_TYPE, 15))).get().createEntity());

				IDurabilityCapability durabilityCap = new DurabilityCapability(500, 500, TreasureCharmableMaterials.BLOOD);
				durabilityCap.setInfinite(true);
				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(0, 0, 1).with($ -> {
					$.socketable = true;
				}).build();
				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});

		CASTLE_RING = ITEMS.register("castle_ring", () -> new NamedAdornment(AdornmentType.RING, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(2, 1, 1).with($ -> {
					$.innate = true;
					$.imbuable = true;
					$.socketable = true;
					$.source = false;
					$.executing = true;
					$.namedByCharm = false;
					$.namedByMaterial = false;
					$.baseMaterial = TreasureCharmableMaterials.SILVER.getName();
					$.sourceItem = SAPPHIRE.get().getRegistryName();
					$.levelModifier = new NoLevelModifier();
				}).build();
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(ShieldingCharm.SHIELDING_TYPE, 10))).get().createEntity());
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(ReflectionCharm.REFLECTION_TYPE, 10))).get().createEntity());

				IDurabilityCapability durabilityCap = new DurabilityCapability(1000, 1000, TreasureCharmableMaterials.SILVER);
				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(0, 0, 1).with($ -> {
					$.socketable = true;
				}).build();
				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});

		PEASANTS_FORTUNE = ITEMS.register("peasants_fortune", () -> new NamedAdornment(AdornmentType.RING, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(0, 0, 2).with($ -> { // use STONE as source Item so it can't be upgraded
					$.innate = false;
					$.imbuable = false;
					$.socketable = true;
					$.source = false;
					$.executing = true;
					$.baseMaterial = TreasureCharmableMaterials.IRON.getName();
					$.sourceItem =  Item.byBlock(Blocks.STONE).getRegistryName();
					$.levelModifier = new GreatAdornmentLevelModifier();
				}).build();

				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(0, 0, 1).with($ -> {
					$.socketable = true;
				}).build();

				IDurabilityCapability durabilityCap = new DurabilityCapability(500, 500, TreasureCharmableMaterials.IRON);
				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});

		/*
		 * special 4million download ring. will auto place in your backpack on new world for 1 month (Dec 2021).
		 * (legendary)
		 */
		GOTTSCHS_RING_OF_MOON = ITEMS.register("gottschs_ring_of_moon", () -> new NamedAdornment(AdornmentType.RING, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				if (!EnchantmentHelper.hasVanishingCurse(stack)) {
					stack.enchant(Enchantments.VANISHING_CURSE, 1);
				}
				ICharmableCapability cap = new CharmableCapability.Builder(4, 0, 0).with($ -> {
					$.innate = true;
					$.imbuable = false;
					$.socketable = false;
					$.source = false;
					$.executing = true;
					$.baseMaterial = TreasureCharmableMaterials.BLACK.getName();
					$.sourceItem =  SAPPHIRE.get().getRegistryName();
					$.levelModifier = new NoLevelModifier();
				}).build();
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(HealingCharm.TYPE, 21))).get().createEntity());
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(DrainCharm.DRAIN_TYPE, 21))).get().createEntity());
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(ReflectionCharm.REFLECTION_TYPE, 21))).get().createEntity());
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(LifeStrikeCharm.LIFE_STRIKE_TYPE, 21))).get().createEntity());

				IDurabilityCapability durabilityCap = new DurabilityCapability();
				durabilityCap.setInfinite(true);

				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(0, 0, 1).with($ -> {
					$.socketable = true;
				}).build();							
				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});

		/*
		 * special 5 million download ring. will auto place in your backpack on new world for 1 month (June 15-July 15 2021).
		 * (legendary)
		 */
		GOTTSCHS_AMULET_OF_HEAVENS = ITEMS.register("gottschs_amulet_of_heavens", () -> new NamedAdornment(AdornmentType.NECKLACE, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				if (!EnchantmentHelper.hasVanishingCurse(stack)) {
					stack.enchant(Enchantments.VANISHING_CURSE, 1);
				}
				ICharmableCapability cap = new CharmableCapability.Builder(0, 0, 1).with($ -> {
					$.innate = true;
					$.imbuable = false;
					$.socketable = true;
					$.source = false;
					$.executing = true;
					$.baseMaterial = TreasureCharmableMaterials.GOLD.getName();
					$.sourceItem =  SAPPHIRE.get().getRegistryName();
					$.levelModifier = new GreatAdornmentLevelModifier();
				}).build();

				cap.getCharmEntities().get(InventoryType.SOCKET).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(GreaterHealingCharm.HEALING_TYPE, 25))).get().createEntity());

				IDurabilityCapability durabilityCap = new DurabilityCapability();
				durabilityCap.setInfinite(true);

				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(2, 0, 0).with($ -> {
					$.socketable = false;
				}).build();					
				IRuneEntity runeEntity = TreasureRunes.RUNE_OF_DOUBLE_CHARGE.createEntity();	
				runestonesCap.add(InventoryType.INNATE, runeEntity);
				((DoubleChargeRune)runeEntity.getRunestone()).initCapabilityApply(cap, runeEntity);
				runeEntity = TreasureRunes.RUNE_OF_ANGELS.createEntity();
				runestonesCap.add(InventoryType.INNATE, runeEntity);
				((AngelsRune)runeEntity.getRunestone()).initCapabilityApply(cap, durabilityCap, runeEntity);
				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});

		// (legendary)
		BRACELET_OF_WONDER = ITEMS.register("bracelet_of_wonder", () -> new NamedAdornment(AdornmentType.BRACELET, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				if (!EnchantmentHelper.hasVanishingCurse(stack)) {
					stack.enchant(Enchantments.VANISHING_CURSE, 1);
				}
				ICharmableCapability cap = new CharmableCapability.Builder(2, 1, 1).with($ -> {
					$.innate = true;
					$.imbuable = true;
					$.socketable = true;
					$.source = false;
					$.executing = true;
					$.baseMaterial = TreasureCharmableMaterials.GOLD.getName();
					$.sourceItem =  BLACK_PEARL.get().getRegistryName();
					$.levelModifier = new GreatAdornmentLevelModifier();
				}).build();
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(ShieldingCharm.SHIELDING_TYPE, 24))).get().createEntity());
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(ReflectionCharm.REFLECTION_TYPE, 24))).get().createEntity());

				IDurabilityCapability durabilityCap = new DurabilityCapability();
				durabilityCap.setInfinite(true);

				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(0, 0, 1).with($ -> {
					$.socketable = true;
				}).build();							
				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});

		// (epic)
		MEDICS_TOKEN = ITEMS.register("medics_token", () -> new NamedAdornment(AdornmentType.NECKLACE, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(1, 0, 0).with($ -> {
					$.innate = true;
					$.imbuable = false;
					$.socketable = false;
					$.source = false;
					$.executing = true;
					$.baseMaterial = TreasureCharmableMaterials.GOLD.getName();
					$.sourceItem = SAPPHIRE.get().getRegistryName();
					$.levelModifier = new GreatAdornmentLevelModifier();
				}).build();
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(GreaterHealingCharm.HEALING_TYPE, 20))).get().createEntity());
				IDurabilityCapability durabilityCap = new DurabilityCapability();
				durabilityCap.setInfinite(true);
				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
					$.socketable = false;
				}).build();
				IRuneEntity runeEntity = TreasureRunes.RUNE_OF_GREATER_MANA.createEntity();	
				runestonesCap.add(InventoryType.INNATE, runeEntity);
				((GreaterManaRune)runeEntity.getRunestone()).initCapabilityApply(cap, runeEntity);
				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});

		// (epic)
		ADEPHAGIAS_BOUNTY = ITEMS.register("adephagias_bounty", () -> new NamedAdornment(AdornmentType.BRACELET, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(1, 0, 0).with($ -> {
					$.innate = true;
					$.imbuable = false;
					$.socketable = false;
					$.source = false;
					$.executing = true;
					$.baseMaterial = TreasureCharmableMaterials.GOLD.getName();
					$.sourceItem = Items.EMERALD.getRegistryName();
					$.levelModifier = new GreatAdornmentLevelModifier();
				}).build();
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(SatietyCharm.SATIETY_TYPE, 20))).get().createEntity());
				IDurabilityCapability durabilityCap = new DurabilityCapability();
				durabilityCap.setInfinite(true);
				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
					$.socketable = false;
				}).build();
				IRuneEntity runeEntity = TreasureRunes.RUNE_OF_GREATER_MANA.createEntity();	
				runestonesCap.add(InventoryType.INNATE, runeEntity);
				((GreaterManaRune)runeEntity.getRunestone()).initCapabilityApply(cap, runeEntity);
				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});

		// (epic)
		SALANDAARS_WARD = ITEMS.register("salandaars_ward", () -> new NamedAdornment(AdornmentType.NECKLACE, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(1, 0, 0).with($ -> {
					$.innate = true;
					$.imbuable = false;
					$.socketable = false;
					$.source = false;
					$.executing = true;
					$.baseMaterial = TreasureCharmableMaterials.GOLD.getName();
					$.sourceItem = RUBY.get().getRegistryName();
					$.levelModifier = new GreatAdornmentLevelModifier();
				}).build();
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(ShieldingCharm.SHIELDING_TYPE, 20))).get().createEntity());
				IDurabilityCapability durabilityCap = new DurabilityCapability();
				durabilityCap.setInfinite(true);
				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(1, 0, 0).with($ -> {
					$.socketable = false;
				}).build();
				IRuneEntity runeEntity = TreasureRunes.RUNE_OF_GREATER_MANA.createEntity();	
				runestonesCap.add(InventoryType.INNATE, runeEntity);
				((GreaterManaRune)runeEntity.getRunestone()).initCapabilityApply(cap, runeEntity);
				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});

		// (epic)
		MIRTHAS_TORCH = ITEMS.register("mirthas_torch", () -> new NamedAdornment(AdornmentType.BRACELET, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(1, 0, 0).with($ -> {
					$.innate = true;
					$.imbuable = false;
					$.socketable = false;
					$.source = false;
					$.executing = true;
					$.baseMaterial = TreasureCharmableMaterials.GOLD.getName();
					$.sourceItem = WHITE_PEARL.get().getRegistryName();
					$.levelModifier = new GreatAdornmentLevelModifier();
				}).build();
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(IlluminationCharm.ILLUMINATION_TYPE, 20))).get().createEntity());
				IDurabilityCapability durabilityCap = new DurabilityCapability();
				durabilityCap.setInfinite(true);
				return new AdornmentCapabilityProvider(cap, durabilityCap);
			}
		});

		POCKET_WATCH = ITEMS.register("pocket_watch", () -> new NamedAdornment(AdornmentType.POCKET, TreasureAdornmentRegistry.GREAT, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(0, 0, 2).with($ -> {
					$.innate = false;
					$.imbuable = false;
					$.socketable = true;
					$.source = false;
					$.executing = true;
					$.baseMaterial = TreasureCharmableMaterials.GOLD.getName();
					$.sourceItem = TOPAZ.get().getRegistryName();
					$.levelModifier = new GreatAdornmentLevelModifier();
				}).build();
				IDurabilityCapability durabilityCap = new DurabilityCapability(1000, 1000, TreasureCharmableMaterials.GOLD);
				IRunestonesCapability runestonesCap = new RunestonesCapability.Builder(0, 0, 2).with($ -> {
					$.socketable = true;
				}).build();
				return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
			}
		});
	}

	/**
	 * The actual event handler that registers the custom items.
	 *
	 * @param event The event this event handler handles
	 */
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		keys.put(WOOD_KEY.get().getRarity(), WOOD_KEY.get());
		keys.put(STONE_KEY.get().getRarity(), STONE_KEY.get());
		keys.put(EMBER_KEY.get().getRarity(), EMBER_KEY.get());
		keys.put(LEAF_KEY.get().getRarity(), LEAF_KEY.get());
		keys.put(LIGHTNING_KEY.get().getRarity(), LIGHTNING_KEY.get());
		keys.put(IRON_KEY.get().getRarity(), IRON_KEY.get());
		keys.put(GOLD_KEY.get().getRarity(), GOLD_KEY.get());
		keys.put(DIAMOND_KEY.get().getRarity(), DIAMOND_KEY.get());
		keys.put(EMERALD_KEY.get().getRarity(), EMERALD_KEY.get());
		keys.put(TOPAZ_KEY.get().getRarity(), TOPAZ_KEY.get());
		keys.put(ONYX_KEY.get().getRarity(), ONYX_KEY.get());
		keys.put(RUBY_KEY.get().getRarity(), RUBY_KEY.get());
		keys.put(SAPPHIRE_KEY.get().getRarity(), SAPPHIRE_KEY.get());
		keys.put(JEWELLED_KEY.get().getRarity(), JEWELLED_KEY.get());
		keys.put(METALLURGISTS_KEY.get().getRarity(), METALLURGISTS_KEY.get());
		keys.put(SKELETON_KEY.get().getRarity(), SKELETON_KEY.get());
		keys.put(SPIDER_KEY.get().getRarity(), SPIDER_KEY.get());
		keys.put(WITHER_KEY.get().getRarity(), WITHER_KEY.get());

		locks.put(WOOD_LOCK.get().getRarity(), WOOD_LOCK.get());
		locks.put(STONE_LOCK.get().getRarity(), STONE_LOCK.get());
		locks.put(EMBER_LOCK.get().getRarity(), EMBER_LOCK.get());
		locks.put(LEAF_LOCK.get().getRarity(), LEAF_LOCK.get());
		locks.put(IRON_LOCK.get().getRarity(), IRON_LOCK.get());
		locks.put(GOLD_LOCK.get().getRarity(), GOLD_LOCK.get());
		locks.put(DIAMOND_LOCK.get().getRarity(), DIAMOND_LOCK.get());
		locks.put(EMERALD_LOCK.get().getRarity(), EMERALD_LOCK.get());
		locks.put(RUBY_LOCK.get().getRarity(), RUBY_LOCK.get());
		locks.put(SAPPHIRE_LOCK.get().getRarity(), SAPPHIRE_LOCK.get());
		locks.put(SPIDER_LOCK.get().getRarity(), SPIDER_LOCK.get());
		// NOTE wither lock is a special and isn't used in the general locks list

		// ADORNMENTS		
		TreasureAdornmentRegistry.register(TreasureCharmableMaterials.GOLD.getName(), TOPAZ.get().getRegistryName(), POCKET_WATCH.get());
		
		List<Item> adornments;
		AdornmentItemsBuilder builder = new AdornmentItemsBuilder(Treasure.MODID);
		adornments = builder.useBaseDefaults().build();
		adornments.addAll(builder.useSourceDefaults().build());
		adornments.addAll(builder.clear().useGreatDefaults().build());
		adornments.addAll(builder.useSourceDefaults().build());
		
		List<Item> charms = new ArrayList<>();
		charms.add(createCharm(TreasureCharmableMaterials.COPPER, Items.AIR));
		charms.add(createCharm(TreasureCharmableMaterials.SILVER, Items.AIR));
		charms.add(createCharm(TreasureCharmableMaterials.GOLD, Items.AIR));
		charms.add(createCharm(TreasureCharmableMaterials.GOLD, TOPAZ.get()));
		charms.add(createCharm(TreasureCharmableMaterials.GOLD, ONYX.get()));
		charms.add(createCharm(TreasureCharmableMaterials.GOLD, Items.DIAMOND));
		charms.add(createCharm(TreasureCharmableMaterials.GOLD, Items.EMERALD));
		charms.add(createCharm(TreasureCharmableMaterials.GOLD, RUBY.get()));
		charms.add(createCharm(TreasureCharmableMaterials.GOLD, SAPPHIRE.get()));
		charms.add(createCharm(TreasureCharmableMaterials.LEGENDARY, Items.AIR));
		charms.add(createCharm(TreasureCharmableMaterials.MYTHICAL, Items.AIR));
		
		// RUNESTONE
		TreasureRunes.register(TreasureRunes.RUNE_OF_MANA, MANA_RUNESTONE.get());
		TreasureRunes.register(TreasureRunes.RUNE_OF_GREATER_MANA, GREATER_MANA_RUNESTONE.get());
		TreasureRunes.register(TreasureRunes.RUNE_OF_DURABILITY, DURABILITY_RUNESTONE.get());
		TreasureRunes.register(TreasureRunes.RUNE_OF_QUALITY, QUALITY_RUNESTONE.get());
		TreasureRunes.register(TreasureRunes.RUNE_OF_EQUIP_AS_MANA, EQUIP_MANA_RUNESTONE.get());
		TreasureRunes.register(TreasureRunes.RUNE_OF_ANVIL, ANVIL_RUNESTONE.get());
		TreasureRunes.register(TreasureRunes.RUNE_OF_ANGELS, ANGELS_RUNESTONE.get());
		TreasureRunes.register(TreasureRunes.RUNE_OF_PERSISTENCE, PERSISTENCE_RUNESTONE.get());
		TreasureRunes.register(TreasureRunes.RUNE_OF_SOCKETS, SOCKETS_RUNESTONE.get());
		TreasureRunes.register(TreasureRunes.RUNE_OF_DOUBLE_CHARGE, DOUBLE_CHARGE_RUNESTONE.get());

		// add charms
		charms.forEach(charm -> {
			ALL_ITEMS.put(charm.getRegistryName(), charm);
			CHARM_ITEMS.put(charm.getRegistryName(), (CharmItem) charm);
			event.getRegistry().register(charm);
		});

		// add adornments
		adornments.forEach(a -> {
			ALL_ITEMS.put(a.getRegistryName(), a); // NOTE this ITEMS was a local cache
			ADORNMENT_ITEMS.put(a.getRegistryName(), (Adornment) a);
			event.getRegistry().register(a);
		});
	}

	/**
	 * 
	 */
	public static void register() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(eventBus);		
	}

	// TODO move charms code in here
	public static class CharmItemsBuilder {
		
	}
	
	/*
	 * 
	 */
	public static class AdornmentItemsBuilder {
		protected List<AdornmentType> types = new ArrayList<>();
		protected List<AdornmentSize> sizes = new ArrayList<>();
		protected List<CharmableMaterial> materials = new ArrayList<>();
		protected List<ResourceLocation> sources = new ArrayList<>();

		protected Map<CharmableMaterial, Integer> materialInnates = Maps.newHashMap();		
		protected Map<AdornmentSize, ILevelModifier> levelModifiers = Maps.newHashMap();

		protected String modid;
		
		/**
		 * 
		 */
		public AdornmentItemsBuilder(String modid) {
			this.modid = modid;
		}

		public AdornmentItemsBuilder clear() {
			types.clear();
			sizes.clear();
			materials.clear();
			sources.clear();
			materialInnates.clear();
			levelModifiers.clear();
			return this;
		}
		
		/*
		 * convenience setup
		 */
		public AdornmentItemsBuilder useBaseDefaults() {
			types(AdornmentType.BRACELET, AdornmentType.NECKLACE, AdornmentType.RING);
			sizes(TreasureAdornmentRegistry.STANDARD);
			materials(TreasureCharmableMaterials.IRON, 
				TreasureCharmableMaterials.COPPER, 
				TreasureCharmableMaterials.SILVER, 
				TreasureCharmableMaterials.GOLD);
			mapInnate(TreasureCharmableMaterials.IRON, 0);
			mapInnate(TreasureCharmableMaterials.COPPER, 1);
			mapInnate(TreasureCharmableMaterials.SILVER, 2);
			mapInnate(TreasureCharmableMaterials.GOLD, 3);
			mapLevelModifier(TreasureAdornmentRegistry.STANDARD, new NoLevelModifier());
			mapLevelModifier(TreasureAdornmentRegistry.GREAT, new GreatAdornmentLevelModifier());
			mapLevelModifier(TreasureAdornmentRegistry.LORDS, new LordsAdornmentLevelModifier());
			return this;
		}
		
		/*
		 * Sources have to be newly constructed resource locations,
		 * because the items are deferred and aren't registered yet.
		 * (Only the vanilla minecraft items are registered at this point)
		 */
		public AdornmentItemsBuilder useSourceDefaults() {
			sources(
					Items.DIAMOND.getRegistryName(),
					Items.EMERALD.getRegistryName(),
					new ResourceLocation(Treasure.MODID, "topaz"),
					new ResourceLocation(Treasure.MODID, "onyx"),
					new ResourceLocation(Treasure.MODID, "ruby"),
					new ResourceLocation(Treasure.MODID, "sapphire"),
					new ResourceLocation(Treasure.MODID, "white_pearl"),
					new ResourceLocation(Treasure.MODID, TreasureConfig.ItemID.BLACK_PEARL_ID)
					);
			return this;
		}
		
		public AdornmentItemsBuilder useGreatDefaults() {
			types(AdornmentType.BRACELET, AdornmentType.NECKLACE, AdornmentType.RING);
			sizes(TreasureAdornmentRegistry.GREAT);
			materials(
					TreasureCharmableMaterials.IRON, 
					TreasureCharmableMaterials.COPPER, 
					TreasureCharmableMaterials.SILVER, 
					TreasureCharmableMaterials.GOLD,
					TreasureCharmableMaterials.BLOOD,
					TreasureCharmableMaterials.BLACK
					);
			mapInnate(TreasureCharmableMaterials.IRON, 0);
			mapInnate(TreasureCharmableMaterials.COPPER, 1);
			mapInnate(TreasureCharmableMaterials.SILVER, 2);
			mapInnate(TreasureCharmableMaterials.GOLD, 3);
			mapInnate(TreasureCharmableMaterials.BLOOD, 2);
			mapInnate(TreasureCharmableMaterials.BLACK, 3);
			mapLevelModifier(TreasureAdornmentRegistry.STANDARD, new NoLevelModifier());
			mapLevelModifier(TreasureAdornmentRegistry.GREAT, new GreatAdornmentLevelModifier());
			mapLevelModifier(TreasureAdornmentRegistry.LORDS, new LordsAdornmentLevelModifier());
			
			return this;
		}
		
		public AdornmentItemsBuilder types(AdornmentType... types) {
			getTypes().addAll(Arrays.asList(types));
			return this;
		}
		
		public AdornmentItemsBuilder sizes(AdornmentSize... sizes) {
			getSizes().addAll(Arrays.asList(sizes));
			return this;
		}
		
		public AdornmentItemsBuilder materials(CharmableMaterial... materials) {
			getMaterials().addAll(Arrays.asList(materials));
			return this;
		}
		
		public AdornmentItemsBuilder sources(ResourceLocation... sources) {
			getSources().addAll(Arrays.asList(sources));
			return this;
		}
		
		public AdornmentItemsBuilder mapInnate(CharmableMaterial material, int num) {
			getMaterialInnates().put(material, num);
			return this;
		}
		
		public AdornmentItemsBuilder mapLevelModifier(AdornmentSize size, ILevelModifier modifier) {
			getLevelModifiers().put(size, modifier);
			return this;
		}
		
		/**
		 * Legacy method.
		 * For use in Treasure2 1.16.5 in the current setup, where the adornments
		 * are created in the RegistryEvent and registered directly with the registry.
		 * ie. NOT DeferredRegistry
		 * This allows previously deferred items to be registered and used within
		 * the adornment creation.
		 * @return
		 */
		public List<Item> build() {
			List<Item> adornments = new ArrayList<>();
			
			if (types.isEmpty()) return adornments;
			if (sizes.isEmpty()) return adornments;
			
			List<ResourceLocation> tempSources = new ArrayList<>();
			if (sources.isEmpty()) {
				tempSources.add(Items.AIR.getRegistryName());
			}
			else {
				tempSources.addAll(sources);
			}
			
			// create the adornment
			types.forEach(type -> {
				sizes.forEach(size -> {
					materials.forEach(material -> {
						tempSources.forEach(source -> {
							Adornment a = createAdornment(type, material, size, source);
							Treasure.LOGGER.debug("adding adornment item -> {}", a.getRegistryName());
							adornments.add(a);
							TreasureAdornmentRegistry.register(material.getName(), source, a);
						});
					});
				});
			});
			return adornments;
		}		
	
		/**
		 * Deferred build.
		 * This method returns a list of String/Supplier Pairs.
		 * This method can be used in a static call of a class to construct RegistryObjects
		 * The String in the Pair is used as the name (without the namespace)
		 * NOTE adornments are deferred and thus are not registered in
		 * TreasureAdornmentRegistry yet. That would have to happen in a post init like event.
		 * @return
		 */
		public List<Pair<String, Supplier<Adornment>>> deferredBuild() {
			List<Pair<String, Supplier<Adornment>>> adornments = new ArrayList<>();
			
			if (types.isEmpty()) return adornments;
			if (sizes.isEmpty()) return adornments;
			
			List<ResourceLocation> tempSources = new ArrayList<>();
			if (sources.isEmpty()) {
				tempSources.add(Items.AIR.getRegistryName());
			}
			else {
				tempSources.addAll(sources);
			}
			
			// create the adornment
			types.forEach(type -> {
				sizes.forEach(size -> {
					materials.forEach(material -> {
						tempSources.forEach(source -> {
							// build the name
							String name = (size == TreasureAdornmentRegistry.STANDARD ? "" : size.getName() + "_") + (source == Items.AIR.getRegistryName() ? "" :  source.getPath() + "_") + material.getName().getPath() + "_" + type.toString();
							// build the adornment supplier
							Supplier<Adornment> a = deferredCreateAdornment(type, material, size, source);
							Treasure.LOGGER.debug("adding deferred adornment item -> {}", name);
							
							// build a pair
							Pair<String, Supplier<Adornment>> pair = Pair.of(name, a);
							adornments.add(pair);
						});
					});
				});
			});
			return adornments;
		}

		/**
		 * Legacy method.
		 * For use in Treasure2 1.16.5 in the current setup, where the adornments
		 * are created in the RegistryEvent and registered directly with the registry.
		 * ie. NOT DeferredRegistry
		 * This allows previously deferred items to be registered and used within
		 * the adornment creation.
		 * @param type
		 * @param material
		 * @param size
		 * @param source
		 * @return
		 */
		public Adornment createAdornment(AdornmentType type, CharmableMaterial material,	AdornmentSize size, ResourceLocation source) {
			String name = (size == TreasureAdornmentRegistry.STANDARD ? "" : size.getName() + "_") + (source == Items.AIR.getRegistryName() ? "" :  source.getPath() + "_") + material.getName().getPath() + "_" + type.toString();
			@SuppressWarnings("deprecation")
			Adornment a = new Adornment(modid, name, type, size, new Item.Properties().tab(TreasureItemGroups.ADORNMENTS_TAB)) {
				public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
					// calculate the innate size
					int innateSize = materialInnates.get(material);
					if (size == TreasureAdornmentRegistry.GREAT && material == TreasureCharmableMaterials.IRON) {
						innateSize++;
					}
					ICharmableCapability cap = new CharmableCapability.Builder(innateSize, 1, 1)
							.with($ -> {
								$.innate = material == TreasureCharmableMaterials.IRON ? false : true;
								$.imbuable = (material == TreasureCharmableMaterials.IRON || material == TreasureCharmableMaterials.COPPER) ? false : true;
								$.socketable = true;
								$.source = false;
								$.executing = true;
								$.namedByCharm = true;
								$.namedByMaterial = true;
								$.baseMaterial = material.getName();
								$.levelModifier = levelModifiers.get(size);
								$.sourceItem = source;
							}).build();

					IRunestonesCapability runestonesCap = new RunestonesCapability(0, 0, 0);
					if (cap.getMaxCharmLevel() >= 8) {
						runestonesCap = new RunestonesCapability.Builder(0, 0, 1).with($ -> {
							$.socketable = true;
						}).build();
					}
					int durability = (innateSize + 2) * material.getMaxLevel() * material.getDurability();
					IDurabilityCapability durabilityCap = new DurabilityCapability(durability, durability);
					durabilityCap.setMaxRepairs(material.getMaxRepairs());
					durabilityCap.setRepairs(durabilityCap.getMaxRepairs());

					return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
				}
			};
			// return the adornment
			return a;
		}
		
		public Supplier<Adornment> deferredCreateAdornment(AdornmentType type, CharmableMaterial material,	AdornmentSize size, ResourceLocation source) {
			return () -> new Adornment(type, size, new Item.Properties().tab(TreasureItemGroups.ADORNMENTS_TAB)) {
				@Override
				public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
					try {
					// calculate the innate size
					int innateSize = materialInnates.get(material);
					if (size == TreasureAdornmentRegistry.GREAT && material == TreasureCharmableMaterials.IRON) {
						innateSize++;
					}
					ICharmableCapability cap = new CharmableCapability.Builder(innateSize, 1, 1)
							.with($ -> {
								$.innate = material == TreasureCharmableMaterials.IRON ? false : true;
								$.imbuable = (material == TreasureCharmableMaterials.IRON || material == TreasureCharmableMaterials.COPPER) ? false : true;
								$.socketable = true;
								$.source = false;
								$.executing = true;
								$.namedByCharm = true;
								$.namedByMaterial = true;
								$.baseMaterial = material.getName();
								$.levelModifier = levelModifiers.get(size);
								$.sourceItem = source;
							}).build();

					IRunestonesCapability runestonesCap = new RunestonesCapability(0, 0, 0);
					if (cap.getMaxCharmLevel() >= 8) {
						runestonesCap = new RunestonesCapability.Builder(0, 0, 1).with($ -> {
							$.socketable = true;
						}).build();
					}
					int durability = (innateSize + 2) * material.getMaxLevel() * material.getDurability();
					IDurabilityCapability durabilityCap = new DurabilityCapability(durability, durability);
					durabilityCap.setMaxRepairs(material.getMaxRepairs());
					durabilityCap.setRepairs(durabilityCap.getMaxRepairs());
					
					return new AdornmentCapabilityProvider(cap, runestonesCap, durabilityCap);
					}
					catch(Exception e) {
						Treasure.LOGGER.error("failed in initCaps", e);
					}
					return null;
				}
			};
		}
		
		public List<AdornmentType> getTypes() {
			return types;
		}

		public List<AdornmentSize> getSizes() {
			return sizes;
		}

		public List<CharmableMaterial> getMaterials() {
			return materials;
		}

		public List<ResourceLocation> getSources() {
			return sources;
		}

		public Map<CharmableMaterial, Integer> getMaterialInnates() {
			return materialInnates;
		}

		public Map<AdornmentSize, ILevelModifier> getLevelModifiers() {
			return levelModifiers;
		}

		public String getModid() {
			return modid;
		}
	}

	private static CharmItem createCharm(CharmableMaterial material, Item source) {
		String name = (source == Items.AIR ? 
				material.getName().getPath() : source.getRegistryName().getPath())  + "_charm";
		Treasure.LOGGER.debug("creating charmItem -> {}", name);
		CharmItem charm = new CharmItem(Treasure.MODID, name, new Item.Properties()) {
			public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
				ICharmableCapability cap = new CharmableCapability.Builder(1, 0, 0).with($ -> {
					$.innate= true;
					$.bindable = true;
					$.source = true;
					$.executing = true;
					$.namedByMaterial = true;
					$.baseMaterial = material.getName();
					$.sourceItem =source.getRegistryName();
				}).build();
				// TEMP test with charm
				cap.getCharmEntities().get(InventoryType.INNATE).add(TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(HealingCharm.TYPE, 1))).get().createEntity());

				return new CharmableCapabilityProvider(cap);
			}
		};
		// register the charm
//		ITEMS.register(name, () -> charm);
		// return the charm
		return charm;
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
	 * @param level
	 * @return
	 */
	public static CharmItem getCharmItemByLevel(int level) {
		CharmItem resultItem = null;
		List<CharmItem> charms = new ArrayList<>(CHARM_ITEMS.values());
		Collections.sort(charms, charmLevelComparator);
		for (Item item : charms) {
			Treasure.LOGGER.debug("charm item -> {}", ((CharmItem)item).getRegistryName());
			ItemStack itemStack = new ItemStack(item);			
			// get the capability
			ICharmableCapability cap = itemStack.getCapability(TreasureCapabilities.CHARMABLE).map(c -> c).orElseThrow(() -> new IllegalStateException());
			if (cap != null) {
				Treasure.LOGGER.debug("name -> {}, charm level -> {}, level -> {}", itemStack.getDisplayName().getString(), cap.getMaxCharmLevel(), level);
				if (cap.getMaxCharmLevel() >= level) {
					resultItem = (CharmItem)item;
					break;
				}
			}
		}
		return resultItem;
	}

	/**
	 * 
	 * @param charmName
	 * @param level
	 * @return
	 */
	public static Optional<ItemStack> getCharm(String charmName, int level, int itemType) {
		Item charmItem = null;
		if (itemType == 1) {
			charmItem = TreasureItems.CHARM_BOOK.get();
		}
		else {
			charmItem = getCharmItemByLevel(level);
		}
		
		// get the charm
		Optional<ICharm> charm = TreasureCharmRegistry.get(ModUtils.asLocation(Charm.Builder.makeName(charmName, level)));
		if (!charm.isPresent()) {
			return Optional.empty();
		}

		/*
		 *  add charm to charmItem
		 *  note: an itemStack is being created, call the initCapabilities() method. ensure to remove any charms
		 */
		ItemStack charmStack = new ItemStack(charmItem);
		charmStack.getCapability(TreasureCapabilities.CHARMABLE).ifPresent(cap -> {
			cap.clearCharms();
			cap.add(InventoryType.INNATE, charm.get().createEntity());
		});
		return Optional.of(charmStack);
	}
	
	public static Comparator<CharmItem> charmLevelComparator = new Comparator<CharmItem>() {
		@Override
		public int compare(CharmItem p1, CharmItem p2) {
			ItemStack stack1 = new ItemStack(p1);
			ItemStack stack2 = new ItemStack(p2);
			// use p1 < p2 because the sort should be ascending
			if (stack1.getCapability(TreasureCapabilities.CHARMABLE).map(cap -> cap.getMaxCharmLevel()).orElse(0) 
					> stack2.getCapability(TreasureCapabilities.CHARMABLE).map(cap -> cap.getMaxCharmLevel()).orElse(0)) {
				// greater than
				return 1;
			}
			else {
				// less than
				return -1;
			}
		}
	};

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
