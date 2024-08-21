/*
 * This file is part of  Treasure2.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
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
package mod.gottsch.forge.treasure2.core.generator.template;

import com.google.common.collect.Multimap;
import mod.gottsch.forge.gottschcore.world.gen.structure.BlockInfoContext;
import mod.gottsch.forge.gottschcore.world.gen.structure.StructureMarkers;
import mod.gottsch.forge.treasure2.Treasure;
import mod.gottsch.forge.treasure2.core.generator.GeneratorUtil;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TemplatePoiInspector {
	private List<BlockInfoContext> bossChests;
	private List<BlockInfoContext> chests;
	private List<BlockInfoContext> spawners;
	private List<BlockInfoContext> proximitySpawners;

	public void inspect(Multimap<Block, BlockInfoContext> map) {
		bossChests = (List<BlockInfoContext>) map.get(GeneratorUtil.getMarkerBlock(StructureMarkers.BOSS_CHEST));
		bossChests.forEach(chest -> {
			Treasure.LOGGER.debug("boss chest -> {}", chest);
		});
		chests = (List<BlockInfoContext>) map.get(GeneratorUtil.getMarkerBlock(StructureMarkers.CHEST));
		spawners = (List<BlockInfoContext>) map.get(GeneratorUtil.getMarkerBlock(StructureMarkers.SPAWNER));
		proximitySpawners = (List<BlockInfoContext>) map.get(GeneratorUtil.getMarkerBlock(StructureMarkers.PROXIMITY_SPAWNER));
	}

	public List<BlockInfoContext> getBossChests() {
		if (bossChests == null) {
			bossChests = new ArrayList<>();
		}
		return bossChests;
	}

	public List<BlockInfoContext> getChests() {
		if (chests == null) {
			chests = new ArrayList<>();
		}
		return chests;
	}

	public List<BlockInfoContext> getSpawners() {
		if (spawners == null) {
			spawners = new ArrayList<>();
		}
		return spawners;
	}

	public List<BlockInfoContext> getProximitySpawners() {
		if (proximitySpawners == null) {
			proximitySpawners = new ArrayList<>();
		}
		return proximitySpawners;
	}
}