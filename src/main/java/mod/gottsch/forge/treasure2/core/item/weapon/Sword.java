/*
 * This file is part of  Treasure2.
 * Copyright (c) 2023 Mark Gottschling (gottsch)
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
package mod.gottsch.forge.treasure2.core.item.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.List;

/**
 * This wrapper class allows float values for damage.
 * @author Mark Gottschling May 8, 2023
 *
 */
public class Sword extends SwordItem implements IWeapon {
	private double criticalChance;
	private float criticalDamage;
	
	/*
		MC 1.16.5: net/minecraft/world/item/SwordItem.defaultModifiers
		Name: b => field_234810_b__ => defaultModifiers
		Side: BOTH
	 */
	private static final String DEFAULT_MODIFIERS_SRG_NAME = "field_234810_b_";

	/**
	 * 
	 * @param tier
	 * @param damageModifier
	 * @param speedModifier
	 * @param properties
	 */
	public Sword(IItemTier tier, float damageModifier, float speedModifier, Item.Properties properties) {
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
	public Sword(IItemTier tier, float damageModifier, float speedModifier, double criticalChance, float criticalDamage, Item.Properties properties) {
		super(tier, (int)Math.floor(damageModifier), speedModifier, properties);

		this.criticalChance = criticalChance;
		this.criticalDamage = criticalDamage;
		
		// override the parent class modifiers		
		Object reflectDefaultModifiers = ObfuscationReflectionHelper.getPrivateValue(SwordItem.class, this, DEFAULT_MODIFIERS_SRG_NAME);
		if (reflectDefaultModifiers instanceof Multimap) {
			float attackDamage = damageModifier + tier.getAttackDamageBonus();
			Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)attackDamage, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)speedModifier, AttributeModifier.Operation.ADDITION));
			reflectDefaultModifiers = builder.build();
		}
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
