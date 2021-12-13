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
package com.someguyssoftware.treasure2.capability;

import java.util.List;

import com.someguyssoftware.treasure2.capability.CharmableCapability.InventoryType;
import com.someguyssoftware.treasure2.charm.ICharm;
import com.someguyssoftware.treasure2.charm.ICharmEntity;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

/**
 * CharmableCapability is a capability that has an ICharmEntity inventory
 * 
 * @author Mark Gottschling on Apr 27, 2020
 *
 */
public interface ICharmableCapability {
	public List<ICharmEntity>[] getCharmEntities();
	public void setCharmEntities(List<ICharmEntity>[] entities);
	
	/**
	 * Convenience method.
	 * @param type
	 * @param entity
	 */	
	void add(InventoryType type, ICharmEntity entity);
	
	public boolean isCharmed();
	int getMaxCharmLevel();
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag);	
	
	public boolean isSource();
	public void setSource(boolean source);
	
	public boolean isBindable();
	public void setBindable(boolean bindable);
	
	public boolean isInnate();
	public void setInnate(boolean innate);
	public int getMaxInnateSize();
	
	public boolean isImbuable();
	public void setImbuable(boolean imbue);
	public int getMaxImbueSize();
	
	public boolean isImbuing();
	public void setImbuing(boolean imbuing);
	
	public boolean isSocketable();
	public void setSocketable(boolean socketable);
	public int getMaxSocketsSize();
	
	public int getMaxSize(InventoryType type);
	
	void setMaxSocketsSize(int maxSocketsSize);
	void setMaxImbueSize(int maxImbueSize);
	void setMaxInnateSize(int maxInnateSize);
	
	ResourceLocation getBaseMaterial();
	void setBaseMaterial(ResourceLocation baseMaterial);
	
	ResourceLocation getSourceItem();
	void setSourceItem(ResourceLocation sourceItem);
	
	public boolean contains(ICharm charm);
	public List<ICharmEntity> getAllCharmEntities();
	ICharmEntity getHighestLevel();
	void setHighestLevel(ICharmEntity highestLevel);
	
	void remove(InventoryType type, int index);
	boolean isExecuting();
	public void setExecuting(boolean executing);
	boolean isNamedByMaterial();
	void setNamedByMaterial(boolean namedByMaterial);
	boolean isNamedByCharm();
	void setNamedByCharm(boolean namedByCharm);


}