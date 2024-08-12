/*
 * This file is part of  Treasure2.
 * Copyright (c) 2023 Mark Gottschling (gottsch)
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
package com.someguyssoftware.treasure2.item.weapon;

import com.someguyssoftware.treasure2.util.LangUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 
 * @author Mark Gottschling May 24, 2023
 *
 */
public interface IWeapon {
	static final DecimalFormat df = new DecimalFormat("###.##");
	
	/**
	 * Determines whether the weapon is "unique" or named. ex. The Black Sword, The
	 * Sword of Omens.
	 * 
	 * @return
	 */
	default public boolean isUnique() {
		return false;
	}

	/**
	 * 
	 * @param stack
	 * @param worldIn
	 * @param tooltip
	 * @param flag
	 */
	default public void appendStats(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flag) {
		if (getCriticalChance() > 0f) {
			tooltip.add(LangUtil.NEWLINE);
			tooltip.add(TextFormatting.AQUA + "" + I18n.translateToLocalFormatted(LangUtil.tooltip("weapons.power_attack_chance"), df.format(getCriticalChance())));
			tooltip.add(TextFormatting.AQUA + "" + I18n.translateToLocalFormatted(LangUtil.tooltip("weapons.power_attack_damage"), df.format(getCriticalDamage())));
			tooltip.add(LangUtil.NEWLINE);
		}
	}
	
	/**
	 * 
	 * @param stack
	 * @param level
	 * @param tooltip
	 * @param flag
	 */
	default public  void appendHoverExtras(ItemStack stack, World level, List<String> tooltip, ITooltipFlag flag) {
		// TODO add tooltip info for critical percentage and damage
	}

	double getCriticalChance();

	void setCriticalChance(double criticalChance);

	float getCriticalDamage();

	void setCriticalDamage(float criticalDamage);
}
