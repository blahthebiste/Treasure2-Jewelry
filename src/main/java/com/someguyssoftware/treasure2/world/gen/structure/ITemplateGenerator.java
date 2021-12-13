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
package com.someguyssoftware.treasure2.world.gen.structure;

import java.util.Random;

import com.someguyssoftware.gottschcore.spatial.ICoords;
import com.someguyssoftware.gottschcore.world.gen.structure.IDecayProcessor;
import com.someguyssoftware.gottschcore.world.gen.structure.PlacementSettings;
import com.someguyssoftware.treasure2.generator.GeneratorResult;
import com.someguyssoftware.treasure2.generator.IGeneratorResult;
import com.someguyssoftware.treasure2.generator.TemplateGeneratorData;

import net.minecraft.world.level.block.Block;
import net.minecraft.core.BlockPos;
import net.minecraft.world.IServerLevel;

/**
 * 
 * @author Mark Gottschling on Jul 6, 2019
 *
 */
public interface ITemplateGenerator<RESULT extends IGeneratorResult<?>> {

	public GeneratorResult<TemplateGeneratorData> generate(IServerLevel world, Random random, TemplateHolder templateHolder, PlacementSettings settings, ICoords spawnCoords);

	public GeneratorResult<TemplateGeneratorData> generate(IServerLevel world, Random random, IDecayProcessor decayProcessor,
			TemplateHolder templateHolder, PlacementSettings placement, ICoords coords);
	
	/**
	 * NOTE not 100% sure that this  belongs here
	 * @param coords
	 * @param entranceCoords
	 * @param size
	 * @param placement
	 * @return
	 */
	public static ICoords alignEntranceToCoords(ICoords coords, ICoords entranceCoords, BlockPos size, PlacementSettings placement) {
		ICoords startCoords = null;
		// NOTE work with rotations only for now
		
		// first offset coords by entrance
		startCoords = coords.add(-entranceCoords.getX(), 0, -entranceCoords.getZ());
		
		// make adjustments for the rotation. REMEMBER that pits are 2x2
		switch (placement.getRotation()) {
		case CLOCKWISE_90:
			startCoords = startCoords.add(1, 0, 0);
			break;
		case CLOCKWISE_180:
			startCoords = startCoords.add(1, 0, 1);
			break;
		case COUNTERCLOCKWISE_90:
			startCoords = startCoords.add(0, 0, 1);
			break;
		default:
			break;
		}
		return startCoords;
	}
	
	public Block getNullBlock();
	public void setNullBlock(Block nullBlock);

}