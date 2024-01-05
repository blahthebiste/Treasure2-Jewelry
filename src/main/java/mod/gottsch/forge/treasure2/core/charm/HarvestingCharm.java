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
package mod.gottsch.forge.treasure2.core.charm;

import java.util.Random;

import com.someguyssoftware.gottschcore.spatial.ICoords;

import mod.gottsch.forge.treasure2.core.util.ModUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Event;

/**
 * 
 * @author Mark Gottschling on May 4, 2020
 *
 */
@Deprecated
public class HarvestingCharm extends Charm {
	public static String HARVESTING_TYPE = "harvesting";
//	private static final Class<?> REGISTERED_EVENT = BlockEvent.HarvestDropsEvent.class;

	/**
	 * 
	 * @param builder
	 */
	HarvestingCharm(Builder builder) {
		super(builder);
	}

	@Override
	public Class<?> getRegisteredEvent() {
//		return REGISTERED_EVENT;
		return null;
	}

	/**
	 * NOTE: it is assumed that only the allowable events are calling this action.
	 */
	@Override
	public boolean update(World world, Random random, ICoords coords, PlayerEntity player, Event event, final ICharmEntity entity) {
		boolean result = false;
//
//		if (entity.getMana() > 0 && player.isAlive()) {           
//			// process all the drops
//			for (ItemStack stack : ((BlockEvent.HarvestDropsEvent)event).getDrops()) {
//
//				// exclude all Charms, Pouches or Blocks with Tile Entities
//				Block block = Block.getBlockFromItem(stack.getItem());
//				if (block != Blocks.AIR) {
//					if (block.hasTileEntity(block.getDefaultState())) {
//						Treasure.LOGGER.debug("skipped item because it has a tile entity.");
//						continue;
//					}
//				} else {
//					// TODO update to check for capabilities
////					if (stack.getItem() instanceof ICharmed || stack.getItem() instanceof ICharmable || stack.getItem() instanceof IPouch) {
////						Treasure.logger.debug("skipped item because it is a charm or pouch.");
////						continue;
////					}
//				}
//
//				//					Treasure.logger.debug("current stack size is -> {}", stack.getCount());
//				int size = (int)(stack.getCount() * entity.getAmount());
//				stack.setCount(size);
//				//					Treasure.logger.debug("resulting stack size is -> {}", stack.getCount());
//			}
//			// all items drop
//			((BlockEvent.HarvestDropsEvent)event).setDropChance(1.0F);
////			entity.setMana(entity.getMana() - 1);
//			applyCost(world, random, coords, player, event, entity, getAmount());
//			result = true;
//		}
		return result;
	}

	@Override
	public ITextComponent getCharmDesc(ICharmEntity entity) {
		return new TranslationTextComponent("tooltip.charm.rate.harvest", Math.toIntExact((long) getAmount()));
	}
	
	public static class Builder extends Charm.Builder {

		public Builder(Integer level) {
			super(ModUtils.asLocation(makeName(HARVESTING_TYPE, level)), HARVESTING_TYPE, level);
		}

		@Override
		public ICharm build() {
			return  new HarvestingCharm(this);
		}
	}
}
