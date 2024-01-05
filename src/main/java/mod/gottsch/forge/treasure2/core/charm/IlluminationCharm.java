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

import java.util.List;
import java.util.Random;

import com.someguyssoftware.gottschcore.block.BlockContext;
import com.someguyssoftware.gottschcore.spatial.Coords;
import com.someguyssoftware.gottschcore.spatial.ICoords;

import mod.gottsch.forge.treasure2.core.Treasure;
import mod.gottsch.forge.treasure2.core.util.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 * Adds light level to a blocks as the player moves every y seconds for a simutaneous maximum x blocks.
 * Ex. level 1, would a light level to a single block every 5 seconds for a max. of 3 block simutaneously lit.
 * After another 5 seconds, the first block would unlight and the newest block would light.
 * @author Mark Gottschling on May 4, 2020
 *
 */
public class IlluminationCharm extends Charm {
	public static String ILLUMINATION_TYPE = "illumination";
	private static final Class<?> REGISTERED_EVENT = LivingUpdateEvent.class;

	/**
	 * 
	 * @param builder
	 */
	IlluminationCharm(Builder builder) {
		super(builder);
	}

	public Class<?> getRegisteredEvent() {
		return REGISTERED_EVENT;
	}

	@Override
	public ICharmEntity createEntity() {
//		ICharmEntity entity = new IlluminationCharmEntity(this, this.getMana(),this.getDuration(), this.getPercent());
		ICharmEntity entity = new IlluminationCharmEntity(this);

		return entity;
	}

	@Override
	public ICharmEntity createEntity(ICharmEntity entity) {
		ICharmEntity newEntity = new IlluminationCharmEntity((IlluminationCharmEntity)entity);
		return newEntity;
	}
	
	/**
	 * NOTE: it is assumed that only the allowable events are calling this action.
	 */
	@Override
	public boolean update(World world, Random random, ICoords coords, PlayerEntity player, Event event, final ICharmEntity entity) {
		boolean result = false;

		if (world.getGameTime() % 100 == 0) {
			if ( player.isAlive() && entity.getMana() > 0) {
				ICoords currentCoords = new Coords((int)Math.floor(player.getX()), (int)Math.floor(player.getY()), (int)Math.floor(player.getZ()));

				/*
				 * validation checks
				 */
				// check that the block at current position is air or replaceable
				BlockContext cube = new BlockContext(world, currentCoords);
				if (!cube.isAir() && !cube.isReplaceable()) {
					return false;
				}
				// check that the block underneath is solid
				cube = new BlockContext(world, currentCoords.down(1));
				if (!cube.isSolid()) {
					Treasure.LOGGER.debug("not solid at -> {}", currentCoords.down(1));
					return false;
				}
				if (!(entity instanceof IlluminationCharmEntity)) {
					Treasure.LOGGER.debug("data are not instance of IlluminationCharmEntity -> {}.{}", this.getClass().getSimpleName(), entity.getClass().getSimpleName());
					return false;
				}

				IlluminationCharmEntity charmData = (IlluminationCharmEntity)entity;
				// cast as linked list
				List<ICoords> list = (List<ICoords>)charmData.getCoordsList();
				Treasure.LOGGER.debug("charm coords list size -> {}", list.size());
				double value = entity.getMana();

				boolean isUpdated = false;
				// check if the coordsList is empty or not
				if (list.isEmpty()) {
					// add current position
					list.add(0, currentCoords);
					isUpdated = true;
				}
				else {
					// determine if new position is different than last position - ie first element in data.coordsList
					ICoords firstCoords = list.get(0);
					if (!currentCoords.equals(firstCoords) && firstCoords.getDistanceSq(currentCoords) >= 25) {
						// add current coords to coords list
						list.add(0, currentCoords);
						// check if coords list is greater than max (data.value)
						if (list.size() > (int)charmData.getMana()) {
							// get difference in size
							int diff = (int) (list.size() - charmData.getMana());
							//															Treasure.logger.debug("diff -> {}", diff);
							for (int index = 0; index < diff; index++) {
								ICoords lastCoords = list.get(list.size()-1);
								Block block = world.getBlockState(lastCoords.toPos()).getBlock();
								if (block == Blocks.TORCH) {
									//	Treasure.logger.debug("set torch to air at -> {}", lastCoords.toShortString());
									world.setBlockAndUpdate(lastCoords.toPos(), Blocks.AIR.defaultBlockState());
								}
								else {
									//	Treasure.logger.debug("torch no longer found at -> {}", currentCoords.toShortString());
									// decrement value since torch was harvested
									value -= 1;
								}
								list.remove(lastCoords);
								//	Treasure.logger.debug("remove torch from list at -> {}; new size ->{}", lastCoords.toShortString(), list.size());								
							}	
						}
						isUpdated = true;
					}
				}
				if (isUpdated == true ) {
					world.setBlockAndUpdate(currentCoords.toPos(), Blocks.TORCH.defaultBlockState());
					//	Treasure.logger.debug("set torch at -> {}", currentCoords.toShortString());
					if (value < 0) {
						value = 0;
					}
					entity.setMana(value);
					//	Treasure.logger.debug("new data -> {}", data);
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public ITextComponent getCharmDesc(ICharmEntity entity) {
		return new TranslationTextComponent("tooltip.charm.rate.illumination");
	}
	
	public static class Builder extends Charm.Builder {

		public Builder(Integer level) {
			super(ModUtils.asLocation(makeName(ILLUMINATION_TYPE, level)), ILLUMINATION_TYPE, level);
		}

		@Override
		public ICharm build() {
			return  new IlluminationCharm(this);
		}
	}
}
