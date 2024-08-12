/*
 * This file is part of  Treasure2.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Mark Gottschling on 8/12/2024
 */
public class UniqueSword extends Sword {
    public UniqueSword(String id, String name, Item.ToolMaterial material, Item repairItem, float damageModifier, float speedModifier, CreativeTabs tab) {
        super(id, name, material, repairItem, damageModifier, speedModifier, tab);
    }

    public UniqueSword(String id, String name, Item.ToolMaterial material, Item repairItem, float damageModifier, float speedModifier, double criticalChance, float criticalDamage, CreativeTabs tab) {
        super(id, name, material, repairItem, damageModifier, speedModifier, criticalChance, criticalDamage, tab);
    }

    @Override
    public  void appendHoverExtras(ItemStack stack, World level, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(LangUtil.NEWLINE);
        tooltip.add(TextFormatting.LIGHT_PURPLE.toString() + TextFormatting.ITALIC + LangUtil.INDENT4 +
                I18n.translateToLocal(LangUtil.tooltip("weapons." + getRegistryName().getResourcePath() + ".lore")));
        tooltip.add(LangUtil.NEWLINE);
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemStack, ItemStack repairStack) {
        return false;
    }
}
