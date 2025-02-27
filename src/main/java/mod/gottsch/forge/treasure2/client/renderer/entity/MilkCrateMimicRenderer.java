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
package mod.gottsch.forge.treasure2.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.gottsch.forge.treasure2.Treasure;
import mod.gottsch.forge.treasure2.client.model.entity.MilkCrateMimicModel;
import mod.gottsch.forge.treasure2.client.renderer.entity.layer.MilkCrateMimicLayer;
import mod.gottsch.forge.treasure2.core.entity.monster.MilkCrateMimic;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 *
 * @author Mark Gottschling on Apr 23, 2024
 *
 */
public class MilkCrateMimicRenderer extends MobRenderer<MilkCrateMimic, MilkCrateMimicModel<MilkCrateMimic>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Treasure.MODID, "textures/entity/mob/milk_crate_mimic.png");
	private final float scale;

	/**
	 *
	 * @param context
	 */
	public MilkCrateMimicRenderer(EntityRendererProvider.Context context) {
        super(context, new MilkCrateMimicModel<>(context.bakeLayer(MilkCrateMimicModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new MilkCrateMimicLayer<>(this));
        this.scale = 1.0F;
	}

	@Override
	protected void scale(MilkCrateMimic mimic, PoseStack pose, float scale) {
		pose.scale(this.scale, this.scale, this.scale);
	}
	
     @Override
    public ResourceLocation getTextureLocation(MilkCrateMimic entity) {
        return TEXTURE;
    }
}
