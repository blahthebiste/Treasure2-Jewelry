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
package com.someguyssoftware.treasure2.tileentity;

import com.someguyssoftware.gottschcore.tileentity.ProximitySpawnerBlockEntity;
import com.someguyssoftware.treasure2.Treasure;

import net.minecraft.tileentity.ITickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Wrapper on PromixitySpawnerTileEntity, providing a no-arg constructor that provides the tile entity type to the super.
 * Researching why I couldn't pass the TET with reflection....
 * @author Mark Gottschling on Jan 29, 2021
 *
 */
public class TreasureProximitySpawnerTileEntity extends ProximitySpawnerTileEntity {

	public TreasureProximitySpawnerTileEntity() {
		super(TreasureTileEntities.PROXIMITY_TILE_ENTITY_TYPE);
	}
}
