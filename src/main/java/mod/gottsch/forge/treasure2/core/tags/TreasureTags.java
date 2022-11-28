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
package mod.gottsch.forge.treasure2.core.tags;

import mod.gottsch.forge.gottschcore.enums.IRarity;
import mod.gottsch.forge.treasure2.Treasure;
import mod.gottsch.forge.treasure2.core.enums.Rarity;
import mod.gottsch.forge.treasure2.core.item.TreasureItems;
import mod.gottsch.forge.treasure2.core.registry.ChestRegistry;
import mod.gottsch.forge.treasure2.core.registry.KeyLockRegistry;
import mod.gottsch.forge.treasure2.core.registry.RarityRegistry;
import mod.gottsch.forge.treasure2.core.registry.WishableRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 
 * @author Mark Gottschling on Nov 11, 2022
 *
 */
@Mod.EventBusSubscriber(modid = Treasure.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TreasureTags {
	
	public static class Items {
		// keys
		public static final TagKey<Item> COMMON_KEY = mod(Treasure.MODID, "key/common");
		public static final TagKey<Item> UNCOMMON_KEY = mod(Treasure.MODID, "key/uncommon");
		public static final TagKey<Item> SCARCE_KEY = mod(Treasure.MODID, "key/scarce");
		public static final TagKey<Item> RARE_KEY = mod(Treasure.MODID, "key/rare");
		public static final TagKey<Item> EPIC_KEY = mod(Treasure.MODID, "key/epic");
		public static final TagKey<Item> LEGENDARY_KEYS = mod(Treasure.MODID, "key/legendary");
		public static final TagKey<Item> MYTHICAL_KEY = mod(Treasure.MODID, "key/mythical");
		
		// locks
		public static final TagKey<Item> COMMON_LOCKS = mod(Treasure.MODID, "lock/common");
		public static final TagKey<Item> UNCOMMON_LOCKS = mod(Treasure.MODID, "lock/uncommon");
		public static final TagKey<Item> SCARCE_LOCKS = mod(Treasure.MODID, "lock/scarce");
		public static final TagKey<Item> RARE_LOCKS = mod(Treasure.MODID, "lock/rare");
		public static final TagKey<Item> EPIC_LOCKS = mod(Treasure.MODID, "lock/epic");
		public static final TagKey<Item> LEGENDARY_LOCKS = mod(Treasure.MODID, "lock/legendary");
		public static final TagKey<Item> MYTHICAL_LOCKS = mod(Treasure.MODID, "lock/mythical");

		// wishables
		// future: items belonging to _custom do not implement IWishable and can be any item from any mod
		// they require checks in WorldEntityEvent
//		public static final TagKey<Item> CUSTOM_WISHABLE = mod(Treasure.MODID, "wishable/_custom");
		
		public static final TagKey<Item> COMMON_WISHABLE = mod(Treasure.MODID, "wishable/common");
		public static final TagKey<Item> UNCOMMON_WISHABLE = mod(Treasure.MODID, "wishable/uncommon");
		public static final TagKey<Item> SCARCE_WISHABLE = mod(Treasure.MODID, "wishable/scarce");
		public static final TagKey<Item> RARE_WISHABLE = mod(Treasure.MODID, "wishable/rare");
		public static final TagKey<Item> EPIC_WISHABLE = mod(Treasure.MODID, "wishable/epic");
		public static final TagKey<Item> LEGENDARY_WISHABLE = mod(Treasure.MODID, "wishable/legendary");
		public static final TagKey<Item> MYTHICAL_WISHABLE = mod(Treasure.MODID, "wishable/mythical");
//		public static final TagKey<Item> SPECIAL_WISHABLE = mod(Treasure.MODID, "wishable/_special");

		
		// other
		public static final TagKey<Item> POUCH = mod(Treasure.MODID, "pouch");
				
		public static TagKey<Item> mod(String domain, String path) {
			return ItemTags.create(new ResourceLocation(domain, path));
		}
	}
	
	public static class Blocks {
		// chests
		public static final TagKey<Block> COMMON_CHESTS = mod(Treasure.MODID, "chests/common");
		public static final TagKey<Block> UNCOMMON_CHESTS = mod(Treasure.MODID, "chests/uncommon");
		public static final TagKey<Block> SCARCE_CHESTS = mod(Treasure.MODID, "chests/scarce");
		public static final TagKey<Block> RARE_CHESTS = mod(Treasure.MODID, "chests/rare");
		public static final TagKey<Block> EPIC_CHESTS = mod(Treasure.MODID, "chests/epic");
		public static final TagKey<Block> LEGENDARY_CHESTS = mod(Treasure.MODID, "chests/legendary");
		public static final TagKey<Block> MYTHICAL_CHESTS = mod(Treasure.MODID, "chests/mythical");
		
		public static TagKey<Block> mod(String domain, String path) {
			return BlockTags.create(new ResourceLocation(domain, path));
		}
	}
	
	@SubscribeEvent
	public static void registerTags(TagsUpdatedEvent event) {
		Treasure.LOGGER.info("in tags updated event");
		// clear key/locks registries
		KeyLockRegistry.clearKeysByRarity();
		KeyLockRegistry.clearLocksByRarity();
		
		// add all key/locks to registries
		KeyLockRegistry.getKeys().forEach(key -> {
			// NOTE ItemStack.is() is just a wrapper for Item.Holder.Reference.is()
			// so, it is being accessed directly here instead of creating a new ItemStack first.
			Holder.Reference<Item> holder = key.get().builtInRegistryHolder();
			
			for (IRarity rarity : RarityRegistry.getValues()) {
				TagKey<Item> tag = RarityRegistry.getKeyTag(rarity);
				if (tag != null && holder.is(tag)) {
					// register the key in the key-lock registry by rarity
					KeyLockRegistry.registerKeyByRarity(rarity, key);
					Treasure.LOGGER.info("registering key -> {} by rarity -> {}", key.get().getRegistryName(), rarity);
					break;
				}
			}			
		});
		
		KeyLockRegistry.getLocks().forEach(lock -> {
			Holder.Reference<Item> holder = lock.get().builtInRegistryHolder();
			
			for (IRarity rarity : RarityRegistry.getValues()) {
				TagKey<Item> tag = RarityRegistry.getLockTag(rarity);
				if (tag != null && holder.is(tag)) {
					// register the lock in the key-lock registry by rarity
					KeyLockRegistry.registerLockByRarity(rarity, lock);
					Treasure.LOGGER.info("registering lock -> {} by rarity -> {}", lock.get().getRegistryName(), rarity);
					break;
				}
			}			
		});
		
		ChestRegistry.getAll().forEach(chest -> {
			Holder.Reference<Block> holder = chest.get().builtInRegistryHolder();
			
			for (IRarity rarity : RarityRegistry.getValues()) {
				TagKey<Block> tag = RarityRegistry.getChestTag(rarity);
				if (tag != null && holder.is(tag)) {
					ChestRegistry.registerByRarity(rarity, chest);
					Treasure.LOGGER.info("registering chest -> {} by rarity -> {}", chest.get().getRegistryName(), rarity);
					break;
				}
			}			
		});
		
		WishableRegistry.getAll().forEach(wishable -> {
			Holder.Reference<Item> holder = wishable.get().builtInRegistryHolder();
			
			for (IRarity rarity : RarityRegistry.getValues()) {
				TagKey<Item> tag = RarityRegistry.getWishableTag(rarity);
				if (tag != null && holder.is(tag)) {
					// register the wishable in the wishable registry by rarity
					WishableRegistry.registerByRarity(rarity, wishable);
					Treasure.LOGGER.info("registering wishable -> {} by rarity -> {}", wishable.get().getRegistryName(), rarity);
					break;
				}
			}
			// special tag
//			TagKey<Item> specialTag = TreasureTags.Items.SPECIAL_WISHABLE;
//			if (specialTag != null && holder.is(specialTag)) {
//				// ???
//			}
		});
	}
}
