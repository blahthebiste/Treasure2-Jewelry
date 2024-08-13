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
package mod.gottsch.forge.treasure2.core.item.weapon;


import net.minecraft.client.settings.CreativeSettings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

/**
 * Extends AxeItem so it behaves like a vanilla axe elsewhere in the codebase. ex. breaks shields etc.
 * @author Mark Gottschling May 24, 2023
 *
 */
public class Axe extends AxeItem implements IWeapon {
	private double criticalChance;
	private float criticalDamage;
	
	public Axe(IItemTier tier, float damageModifier, float speedModifier, Item.Properties properties) {
		this(tier, damageModifier, speedModifier, 0, 0f, properties);
	}

	/**
	 * 
	 * @param tier
	 * @param damageModifier
	 * @param speedModifier
	 * @param criticalChance
	 * @param criticalDamage
	 * @param properties
	 */
	public Axe(IItemTier tier, float damageModifier, float speedModifier, double criticalChance, float criticalDamage, Item.Properties properties) {
		super(tier, damageModifier, speedModifier, properties);
		this.criticalChance = criticalChance;
		this.criticalDamage = criticalDamage;
	}
	
	@Override
	public ITextComponent getName(ItemStack itemStack) {
		if (isUnique()) {
			return new TranslationTextComponent(this.getDescriptionId(itemStack)).withStyle(TextFormatting.YELLOW);
		} else {
			return new TranslationTextComponent(this.getDescriptionId(itemStack));
		}
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flag) {
		appendStats(stack, worldIn, tooltip, flag);
		appendHoverExtras(stack, worldIn, tooltip, flag);
	}

	public double getCriticalChance() {
		return criticalChance;
	}

	public void setCriticalChance(double criticalChance) {
		this.criticalChance = criticalChance;
	}

	public float getCriticalDamage() {
		return criticalDamage;
	}

	public void setCriticalDamage(float criticalDamage) {
		this.criticalDamage = criticalDamage;
	}
}
