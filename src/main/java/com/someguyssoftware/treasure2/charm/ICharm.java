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
package com.someguyssoftware.treasure2.charm;

import java.util.List;
import java.util.Random;

import com.someguyssoftware.gottschcore.spatial.ICoords;
import com.someguyssoftware.treasure2.enums.Rarity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;

/**
 * 
 * @author Mark Gottschling on Jan 16, 2021
 *
 */
public interface ICharm {

	public ResourceLocation getName();
	public String getType();
	public int getLevel();
	public double getMaxValue();
	public int getMaxDuration();
	public double getMaxPercent();
	public Rarity getRarity();
	public int getPriority();
	boolean isEffectStackable();
	
	/**
	 * 
	 */
	ICharmEntity createEntity();
	
    public boolean update(Level world, Random random, ICoords coords, Player player, Event event, final ICharmEntity entity);

	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn, ICharmEntity entity);

	/**
	 * 
	 * @param nbt
	 * @return
	 */
	CompoundTag save(CompoundTag nbt);
	
	public Class<?> getRegisteredEvent();
	
	public boolean isCurse();
}
