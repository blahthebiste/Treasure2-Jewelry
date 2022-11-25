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
import mod.gottsch.forge.treasure2.core.util.LangUtil;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * 
 * @author Mark Gottschling on Apr 6, 2022
 *
 */
public class LanguageGen extends LanguageProvider {

    public LanguageGen(DataGenerator gen, String locale) {
        super(gen, Treasure.MODID, locale);
    }
    
    @Override
    protected void addTranslations() {
    	// tabs
        add("itemGroup." + Treasure.MODID, "Treasure2");
        add("itemGroup." + Treasure.MODID + ".adornments_tab", "Treasure2 Adornments");
        
        // keys
        add(TreasureItems.WOOD_KEY.get(), "Wood Key");
        add(TreasureItems.STONE_KEY.get(), "Stone Key");
        add(TreasureItems.LEAF_KEY.get(), "Leaf Key");
        add(TreasureItems.EMBER_KEY.get(), "Ember Key");
        add(TreasureItems.LIGHTNING_KEY.get(), "Lightning Key");
        
        add(TreasureItems.IRON_KEY.get(), "Iron Key");
        add(TreasureItems.GOLD_KEY.get(), "Gold Key");
        add(TreasureItems.METALLURGISTS_KEY.get(), "Metallurgists Key");
        
        add(TreasureItems.DIAMOND_KEY.get(), "Diamond Key");
        add(TreasureItems.EMERALD_KEY.get(), "Emerald Key");
        add(TreasureItems.RUBY_KEY.get(), "Ruby Key");
        add(TreasureItems.SAPPHIRE_KEY.get(), "Sapphire Key");
        add(TreasureItems.JEWELLED_KEY.get(), "Jewelled Key");
        
        add(TreasureItems.SPIDER_KEY.get(), "Spider Key");
        add(TreasureItems.WITHER_KEY.get(), "Wither Key");
        
        add(TreasureItems.SKELETON_KEY.get(), "Skeleton Key");        
        add(TreasureItems.PILFERERS_LOCK_PICK.get(), "Pilferer's Lock Pick");
        add(TreasureItems.THIEFS_LOCK_PICK.get(), "Thief's Lock Pick");
        
        // locks
        add(TreasureItems.WOOD_LOCK.get(), "Wood Lock");
        add(TreasureItems.STONE_LOCK.get(), "Stone Lock");
        add(TreasureItems.LEAF_LOCK.get(), "Leaf Lock");
        add(TreasureItems.EMBER_LOCK.get(), "Ember Lock");
        add(TreasureItems.IRON_LOCK.get(), "Iron Lock");
        add(TreasureItems.GOLD_LOCK.get(), "Gold Lock");
        
        add(TreasureItems.DIAMOND_LOCK.get(), "Diamond Lock");
        add(TreasureItems.EMERALD_LOCK.get(), "Emerald Lock");
        add(TreasureItems.RUBY_LOCK.get(), "Ruby Lock");
        add(TreasureItems.SAPPHIRE_LOCK.get(), "Sapphire Lock");
        
        add(TreasureItems.SPIDER_LOCK.get(), "Spider Lock");
        add(TreasureItems.WITHER_LOCK.get(), "Wither Lock");
        
        // key ring
        add(TreasureItems.KEY_RING.get(), "Key Ring");     
        
        // chests
        add(TreasureBlocks.WOOD_CHEST.get(), "Wood Chest");
        
        /*
         *  Util.tooltips
         */
        // general
        add(LangUtil.tooltip("boolean.yes"), "Yes");
        add(LangUtil.tooltip("boolean.no"), "No");
        add(LangUtil.tooltip("hold_shift"), "Hold [SHIFT] to expand.");

        // keys and locks
        add(LangUtil.tooltip("key_lock.rarity"), "Rarity: %s");
        add(LangUtil.tooltip("key_lock.category"), "Category: %s");
        add(LangUtil.tooltip("key_lock.craftable"), "Craftable: %s");
        add(LangUtil.tooltip("key_lock.breakable"), "Breakable: %s");
        add(LangUtil.tooltip("key_lock.damageable"), "Damageable: %s");
        add(LangUtil.tooltip("key_lock.accepts_keys"), "Accepts Keys:");
        add(LangUtil.tooltip("key_lock.specials"), "Specials: %s");
        add(LangUtil.tooltip("key_lock.skeleton_key.specials"), "Opens COMMON, UNCOMMON, SCARCE~and RARE locks (excluding Wither)");
        add(LangUtil.tooltip("key_lock.ember_key.specials"), "Destroys Wood and Leaf Locks");
        add(LangUtil.tooltip("key_lock.ember_lock.specials"), "Destroys All Keys regardless of breakability (excluding Ember and Lightning)");
        add(LangUtil.tooltip("key_lock.lightning_key.specials"), "Opens any lock in ELEMENTAL category");
        add(LangUtil.tooltip("key_lock.metallurgists_key.specials"), "Opens any lock in METALS category");
        add(LangUtil.tooltip("key_lock.jewelled_key.specials"), "Opens any lock in GEMS category");
        add(LangUtil.tooltip("key_lock.pilferers_lock_pick.specials"), "Opens COMMON (%s%%) and UNCOMMON (%s%%) locks");
        add(LangUtil.tooltip("key_lock.thiefs_lock_pick.specials"), "Opens COMMON (%s%%), UNCOMMON (%s%%) and SCARCE (%s%%) locks");
        add(LangUtil.tooltip("key_lock.key_ring"), "Container for keys");
        
        // chests
        add(LangUtil.tooltip("chest.rarity"), "Rarity: %s");
        add(LangUtil.tooltip("chest.max_locks"), "Max Locks: %s");
        add(LangUtil.tooltip("chest.container_size"), "Inventory Size: %s");
        
        // capabilities
        add(LangUtil.tooltip("durability.infinite_uses"), "Uses Remaining: Infinite");
        add(LangUtil.tooltip("durability.uses"), "Uses Remaining: [%s/%s]");
        add(LangUtil.tooltip("durability.max_uses"), "Max Uses: %s");
        
        /*
         * screens
         */
        // chests
        add(LangUtil.screen("wood_chest.name"), "Wood Chest");
        
        /*
         *  chat
         */
        // keys
        add(LangUtil.chat("key.key_break"), "Your key broke whilst attempting to unlock the lock!");
        add(LangUtil.chat("key.key_not_fit"), "Your key doesn't fit the lock!");
        add(LangUtil.chat("key.key_unable_unlock"), "Your key failed to unlock the lock!");

    }
}
