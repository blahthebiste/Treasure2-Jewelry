/**
 * 
 */
package com.someguyssoftware.treasure2.gui.model;

import java.util.function.Function;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.someguyssoftware.treasure2.Treasure;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

/**
 * @author Mark Gottschling on Aug 24, 2020
 *
 */
public abstract class AbstractTreasureChestModel extends Model implements ITreasureChestModel {

	public AbstractTreasureChestModel(Function<ResourceLocation, RenderType> renderTypeIn) {
		super(renderTypeIn);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		Treasure.LOGGER.info("THIS SHOULD NEVER BE CALLED");
	}
	
	protected void setRotation(ModelRenderer model, float x, float y, float z) {
		model.xRot = x;
		model.yRot = y;
		model.zRot = z;
	}
	
	@Override
	public RenderType getChestRenderType(ResourceLocation location) {
		return this.renderType.apply(location);
	}
}
