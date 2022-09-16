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
package com.someguyssoftware.treasure2.rune;

import com.someguyssoftware.treasure2.Treasure;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Jan 23, 2022
 *
 */
public class PersistenceRune extends Rune {

	protected PersistenceRune(Builder builder) {
		super(builder);
	}

	@Override
	public boolean isValid(ItemStack itemStack) {		
		return 	(EnchantmentHelper.hasVanishingCurse(itemStack));
	}

	@Override
	public void apply(ItemStack itemStack, IRuneEntity entity) {
		Treasure.LOGGER.debug("applying persistence rune, isvalid -> {}, isapplied -> {}", isValid(itemStack), entity.isApplied());
		if (!isValid(itemStack) || entity.isApplied()) {
			return;
		}
		Treasure.LOGGER.debug("do we get here?");
        ListNBT enchantmentTagList = itemStack.getEnchantmentTags();
        int indexOfVanishing = -1;
        for (int i = 0; i < enchantmentTagList.size(); ++i) {
            CompoundNBT nbt = enchantmentTagList.getCompound(i);
            Enchantment enchantment = Enchantment.byId(nbt.getShort("id"));
            if (enchantment == Enchantments.VANISHING_CURSE) {
                indexOfVanishing = i;
                break;
            }
        }
        if (indexOfVanishing > -1) {
        	enchantmentTagList.remove(indexOfVanishing);
        }
        entity.setApplied(true);
	}

	@Override
	public void undo(ItemStack itemStack, IRuneEntity entity) {
		itemStack.enchant(Enchantments.VANISHING_CURSE, 1);
	}

	/*
	 * 
	 */
	public static class Builder extends Rune.Builder {
		public Builder(ResourceLocation name) {
			super(name);
		}
		@Override
		public IRune build() {
			return new PersistenceRune(this);
		}
	}
}
