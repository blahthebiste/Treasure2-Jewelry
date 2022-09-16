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

import java.util.Random;

import com.someguyssoftware.gottschcore.spatial.ICoords;
import com.someguyssoftware.treasure2.Treasure;
import com.someguyssoftware.treasure2.charm.cost.ICostEvaluator;
import com.someguyssoftware.treasure2.util.ModUtils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 * @author Mark Gottschling on Apr 26, 2020
 *
 */
public class LifeStrikeCharm extends Charm {
	private static final float LIFE_AMOUNT = 2.0F;
	public static String LIFE_STRIKE_TYPE = "life_strike";
	private static final Class<?> REGISTERED_EVENT = LivingHurtEvent.class;
	
	// the amount of health it cost in addition to mana
	private double lifeCost;
	
	/**
	 * 
	 * @param builder
	 */
	LifeStrikeCharm(Builder builder) {
		super(builder);
	}

	@Override
	public Class<?> getRegisteredEvent() {
		return REGISTERED_EVENT;
	}

	@Override
	public ICharmEntity createEntity() {
		ICharmEntity entity = new LifeStrikeCharmEntity(this);
		return entity;
	}
	
	@Override
	public ICharmEntity createEntity(ICharmEntity entity) {
		ICharmEntity newEntity = new LifeStrikeCharmEntity((LifeStrikeCharmEntity)entity);
		return newEntity;
	}
	
	/**
	 * NOTE: it is assumed that only the allowable events are calling this action.
	 */
	@Override
	public boolean update(World world, Random random, ICoords coords, PlayerEntity player, Event event, final ICharmEntity entity) {
		boolean result = false;
		if (entity.getMana() > 0 && player.isAlive()) {
			DamageSource source = ((LivingHurtEvent) event).getSource();
			if (source.getDirectEntity() instanceof PlayerEntity) {

				if (player.getHealth() > 5.0F) {
					// get the source and amount
					double sourceAmount = ((LivingHurtEvent)event).getAmount();
					double lifeStrikeAmount = sourceAmount + (sourceAmount * entity.getAmount());
					// increase damage amount
					((LivingHurtEvent)event).setAmount((float) lifeStrikeAmount);

					applyCost(world, random, coords, player, event, entity, Math.max(1.0, Math.min(5.0, lifeStrikeAmount - sourceAmount)));
					result = true;
					Treasure.LOGGER.debug("life strike damage {} onto mob -> {} ", (sourceAmount * entity.getAmount()), source.getDirectEntity().getName());
				}
			}
		}
		return result;
	}
	
	@Override
	public ITextComponent getCharmDesc(ICharmEntity entity) {
		return new TranslationTextComponent("tooltip.charm.rate.life_strike", Math.round((entity.getAmount())*100));
	}
	
	public static class Builder extends Charm.Builder {

		public double lifeCost = LIFE_AMOUNT;
		
		public Builder(Integer level) {
			super(ModUtils.asLocation(makeName(LIFE_STRIKE_TYPE, level)), LIFE_STRIKE_TYPE, level);
		}
		
		public Builder withLifeCost(double lifeCost)  {
			this.lifeCost = lifeCost;
			return this;
		}
		
		@Override
		public ICharm build() {
			return  new LifeStrikeCharm(this);
		}
	}
	
	public static class CostEvaluator implements ICostEvaluator {
		/**
		 * @param amount the cost amount requested
		 * @return the actual cost incurred
		 */
		@Override
		public double apply(World world, Random random, ICoords coords, PlayerEntity player, Event event, final ICharmEntity entity, double amount) {
			double cost = amount;
			LifeStrikeCharmEntity lifeEntity = (LifeStrikeCharmEntity)entity;
			if (entity instanceof LifeStrikeCharmEntity) {
				// reduce player's health by life cost
				player.setHealth(MathHelper.clamp(player.getHealth() - (float)lifeEntity.getLifeCost(), 0.0F, player.getMaxHealth()));		

				// reduce mana
				if (entity.getMana() < amount) {
					cost = entity.getMana();
				}
				double remaining = entity.getMana() - cost;
				entity.setMana(MathHelper.clamp(remaining,  0D, entity.getMana()));
			}
			return cost;
		}
	}

	public double getLifeCost() {
		return lifeCost;
	}

	public void setLifeCost(double lifeCost) {
		this.lifeCost = lifeCost;
	}
}
