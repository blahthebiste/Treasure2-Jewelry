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
package com.someguyssoftware.treasure2.particle;

import com.someguyssoftware.gottschcore.spatial.ICoords;
import com.someguyssoftware.gottschcore.world.WorldInfo;
import com.someguyssoftware.treasure2.network.PoisonMistMessageToServer;
import com.someguyssoftware.treasure2.network.TreasureNetworking;
import com.someguyssoftware.treasure2.particle.data.CollidingParticleType;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PoisonMistParticle extends AbstractCollidingMistParticle {

	/**
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param velocityX
	 * @param velocityY
	 * @param velocityZ
	 * @param parentCoords
	 */
	public PoisonMistParticle(ClientLevel world, double x, double y, double z, ICoords coords) {
		super(world, x, y, z, coords);
		init();
	}

	@Override
	public float getMistAlpha() {
		return 0.4F;
	}
	
	/**
	 * 
	 */
	@Override
	public void inflictEffectOnPlayer(Player player) {
		if (WorldInfo.isServerSide(player.level)) {
			return;
		}

		// check all player effects for poison
		boolean isAffected = false;
		for (EffectInstance effectInstance : player.getActiveEffects()) {
			if (effectInstance.getEffect() == Effects.POISON) {
				isAffected = true;
			}
		}

		// if player does not have poison effect, add it
		if (!isAffected) {
			PoisonMistMessageToServer messageToServer = new PoisonMistMessageToServer(player.getStringUUID());
			TreasureNetworking.simpleChannel.sendToServer(messageToServer);

		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<CollidingParticleType> {
		/*
		 * contains a list of textures; choose one using either
		 * newParticle.selectSpriteRandomly(spriteSet); or newParticle.selectSpriteWithAge(spriteSet);
		 */
		private final IAnimatedSprite spriteSet;
		
		/**
		 * 
		 * @param sprite
		 */
		public Factory(IAnimatedSprite sprite) {
			this.spriteSet = sprite;
		}
		
		/**
		 * 
		 */
		@Override
		public Particle createParticle(CollidingParticleType data, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			PoisonMistParticle particle = new PoisonMistParticle(world, x, y, z, data.getSourceCoords());
			particle.pickSprite(spriteSet);
			return particle;
		}
	}
}
