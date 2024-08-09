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
package mod.gottsch.forge.treasure2.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.gottsch.forge.treasure2.Treasure;
import mod.gottsch.forge.treasure2.core.entity.monster.BarrelMimic;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import oshi.hardware.platform.unix.solaris.SolarisUsbDevice;

/**
 *
 * @author Mark Gottschling on Aug 6, 2024
 *
 * @param <T>
 */
public class BarrelMimicModel<T extends Entity> extends EntityModel<T> {

	private float SIDE_EYE_64 = -1.11701F;

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Treasure.MODID, "barrel_mimic"), "main");

	private final ModelPart body;
	private final ModelPart lid;
	private final ModelPart middleLid;
	private final ModelPart front;
	private final ModelPart tongue;
	private final ModelPart eye1;
	private final ModelPart eye2;
	private final ModelPart eye3;

	private float bodyY;

	/**
	 *
	 * @param root
	 */
	public BarrelMimicModel(ModelPart root) {
		this.body = root.getChild("body");
		this.lid = body.getChild("lid");
		this.middleLid = lid.getChild("middle_lid");
		this.eye1 = lid.getChild("eye1");
		this.eye2 = lid.getChild("eye2");
		this.eye3 = middleLid.getChild("eye3");
		this.tongue = body.getChild("base").getChild("tongue");
		this.front = body.getChild("base").getChild("front");

		bodyY = body.y;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition lid = body.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(49, 11).addBox(4.0F, -6.0F, -16.0F, 4.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(33, 34).addBox(-8.0F, -6.0F, -16.0F, 4.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 8.0F));
		PartDefinition eye1 = lid.addOrReplaceChild("eye1", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, 0.1F, 0.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, -3.1F, -16.1F));
		PartDefinition eye2 = lid.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, 0.1F, 0.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, -3.1F, -16.1F));
		PartDefinition smallTooth1 = lid.addOrReplaceChild("smallTooth1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -0.5F, -15.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition smallTooth2 = lid.addOrReplaceChild("smallTooth2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.25F, -0.5F, -15.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition middle_lid = lid.addOrReplaceChild("middle_lid", CubeListBuilder.create().texOffs(0, 27).addBox(-4.0F, -6.0F, -16.0F, 8.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition eye3 = middle_lid.addOrReplaceChild("eye3", CubeListBuilder.create().texOffs(35, 57).addBox(-3.5F, 0.0F, 0.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -5.1F, -16.1F));
		PartDefinition topTooth1 = middle_lid.addOrReplaceChild("topTooth1", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -15.5F));
		PartDefinition t_r1 = topTooth1.addOrReplaceChild("t_r1", CubeListBuilder.create().texOffs(7, 32).addBox(-2.5F, 3.5F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -1.0F, -0.5F, 0.0F, 0.0F, 0.7854F));
		PartDefinition topTooth8 = middle_lid.addOrReplaceChild("topTooth8", CubeListBuilder.create(), PartPose.offset(2.0F, -2.0F, -15.75F));
		PartDefinition t_r2 = topTooth8.addOrReplaceChild("t_r2", CubeListBuilder.create().texOffs(7, 32).addBox(-2.5F, 3.5F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -1.0F, -0.5F, 0.0F, 0.0F, 0.7854F));
		PartDefinition topTooth2 = middle_lid.addOrReplaceChild("topTooth2", CubeListBuilder.create(), PartPose.offset(4.0F, -2.0F, -15.5F));
		PartDefinition t_r3 = topTooth2.addOrReplaceChild("t_r3", CubeListBuilder.create().texOffs(7, 32).addBox(-2.5F, 3.5F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -1.0F, -0.5F, 0.0F, 0.0F, 0.7854F));
		PartDefinition topTooth3 = middle_lid.addOrReplaceChild("topTooth3", CubeListBuilder.create(), PartPose.offset(7.0F, 0.0F, -15.5F));
		PartDefinition skin1 = lid.addOrReplaceChild("skin1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -4.0F, -2.5F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 2.0F, -4.5F, 0.5236F, 0.0F, 0.0F));
		PartDefinition skin2 = lid.addOrReplaceChild("skin2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -4.0F, -2.5F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 2.0F, -4.5F, 0.5236F, 0.0F, 0.0F));
		PartDefinition topTooth6 = lid.addOrReplaceChild("topTooth6", CubeListBuilder.create(), PartPose.offsetAndRotation(7.1109F, 0.0178F, -13.4F, 0.0F, -1.5708F, 0.0F));
		PartDefinition t_r4 = topTooth6.addOrReplaceChild("t_r4", CubeListBuilder.create().texOffs(7, 32).addBox(-2.5F, 2.0F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8891F, -1.7678F, -0.6F, 0.0F, 0.0F, 0.7854F));
		PartDefinition topTooth5 = lid.addOrReplaceChild("topTooth5", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.3891F, 0.0178F, -13.4F, 0.0F, -1.5708F, 0.0F));
		PartDefinition t_r5 = topTooth5.addOrReplaceChild("t_r5", CubeListBuilder.create().texOffs(7, 32).addBox(-2.5F, 2.0F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8891F, -1.7678F, -0.6F, 0.0F, 0.0F, 0.7854F));
		PartDefinition topTooth4 = lid.addOrReplaceChild("topTooth4", CubeListBuilder.create(), PartPose.offsetAndRotation(7.1109F, 0.0178F, -10.4F, 0.0F, -1.5708F, 0.0F));
		PartDefinition t_r6 = topTooth4.addOrReplaceChild("t_r6", CubeListBuilder.create().texOffs(7, 32).addBox(-2.5F, 2.0F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8891F, -1.7678F, -0.6F, 0.0F, 0.0F, 0.7854F));
		PartDefinition topTooth7 = lid.addOrReplaceChild("topTooth7", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.6391F, 0.0178F, -10.4F, 0.0F, -1.5708F, 0.0F));
		PartDefinition t_r7 = topTooth7.addOrReplaceChild("t_r7", CubeListBuilder.create().texOffs(7, 32).addBox(-2.5F, 2.0F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8891F, -1.7678F, -0.6F, 0.0F, 0.0F, 0.7854F));
		PartDefinition base = body.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -10.0F, -8.0F, 16.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition front = base.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 54).addBox(-7.0F, -10.0F, -3.0F, 14.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.0F));
		PartDefinition bottomTooth5 = front.addOrReplaceChild("bottomTooth5", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, -9.75F, -1.25F, -0.2182F, -0.4363F, 0.0F));
		PartDefinition t_r8 = bottomTooth5.addOrReplaceChild("t_r8", CubeListBuilder.create().texOffs(7, 0).addBox(-8.0F, 5.5F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, -1.0F, -0.5F, 0.0F, 0.0F, 0.7854F));
		PartDefinition bottomTooth2 = front.addOrReplaceChild("bottomTooth2", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, -9.75F, -1.25F, -0.2182F, 0.4363F, 0.0F));
		PartDefinition t_r9 = bottomTooth2.addOrReplaceChild("t_r9", CubeListBuilder.create().texOffs(7, 0).addBox(-8.0F, 5.5F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, -1.0F, -0.5F, 0.0F, 0.0F, 0.7854F));
		PartDefinition tongue = base.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(49, 0).addBox(-3.0F, 0.9F, -12.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, 7.0F, -0.1745F, 0.0F, 0.0F));
		PartDefinition bottomTooth6 = base.addOrReplaceChild("bottomTooth6", CubeListBuilder.create(), PartPose.offsetAndRotation(7.1109F, -9.9822F, -5.4F, 0.0F, -1.5708F, 0.0F));
		PartDefinition t_r10 = bottomTooth6.addOrReplaceChild("t_r10", CubeListBuilder.create().texOffs(0, 32).addBox(-2.5F, 2.0F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8891F, -1.7678F, -0.6F, 0.0F, 0.0F, 0.7854F));
		PartDefinition bottomTooth4 = base.addOrReplaceChild("bottomTooth4", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.3891F, -9.9822F, -5.4F, 0.0F, -1.5708F, 0.0F));
		PartDefinition t_r11 = bottomTooth4.addOrReplaceChild("t_r11", CubeListBuilder.create().texOffs(0, 32).addBox(-2.5F, 2.0F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8891F, -1.7678F, -0.6F, 0.0F, 0.0F, 0.7854F));
		PartDefinition bottomTooth3 = base.addOrReplaceChild("bottomTooth3", CubeListBuilder.create(), PartPose.offsetAndRotation(7.6109F, -9.9822F, -2.4F, 0.0F, -1.5708F, 0.0F));
		PartDefinition t_r12 = bottomTooth3.addOrReplaceChild("t_r12", CubeListBuilder.create().texOffs(0, 32).addBox(-2.5F, 2.0F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8891F, -1.7678F, -0.6F, 0.0F, 0.0F, 0.7854F));
		PartDefinition bottomTooth7 = base.addOrReplaceChild("bottomTooth7", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.8891F, -9.9822F, -2.4F, 0.0F, -1.5708F, 0.0F));
		PartDefinition t_r13 = bottomTooth7.addOrReplaceChild("t_r13", CubeListBuilder.create().texOffs(0, 32).addBox(-2.5F, 2.0F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8891F, -1.7678F, -0.6F, 0.0F, 0.0F, 0.7854F));
		PartDefinition bottomTooth8 = base.addOrReplaceChild("bottomTooth8", CubeListBuilder.create(), PartPose.offsetAndRotation(2.2F, -10.25F, -7.5F, -0.0873F, 0.0F, 0.0F));
		PartDefinition t2_r1 = bottomTooth8.addOrReplaceChild("t2_r1", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -1.0F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7F, -1.5F, -0.5F, 0.0F, 0.0F, 0.7854F));
		PartDefinition bottomTooth9 = base.addOrReplaceChild("bottomTooth9", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.8F, -10.25F, -7.5F, -0.0873F, 0.0F, 0.0F));
		PartDefinition t3_r1 = bottomTooth9.addOrReplaceChild("t3_r1", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -1.0F, 0.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7F, -1.5F, -0.5F, 0.0F, 0.0F, 0.7854F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		BarrelMimic mimic = (BarrelMimic)entity;
		if (mimic.isActive()) {
			body.xRot = 0.2618F; // 15 degrees

			// chomp lid
			if (mimic.hasTarget()) {
				bobMouth(lid,22.5f, 22.5f, ageInTicks);
			} else {
//				lid.xRot = -degToRad(22.5f);
				bobMouth(lid, 22.5f, 3f, ageInTicks);
			}
			middleLid.xRot = -0.174533F;
			front.xRot = 0.174533F;
			eye3.xRot = -1.003564F;
			eye1.xRot = SIDE_EYE_64;
			eye2.xRot = eye1.xRot;
			tongue.xRot = -0.174533F; // 10

			bob(body, bodyY, ageInTicks);
		} else {
			if (mimic.getAmount() < 1F) {
				body.xRot = mimic.getAmount() * 0.2618F;
				lid.xRot = mimic.getAmount() * -0.7854F;
				middleLid.xRot = mimic.getAmount() * -0.174533F;
				front.xRot = mimic.getAmount() * 0.174533F;
				eye3.xRot = mimic.getAmount() * -1.003564F;
				eye1.xRot = SIDE_EYE_64;
				eye2.xRot = eye1.xRot;
				tongue.xRot = mimic.getAmount() * -0.174533F;
			}
		}
	}

	public static void bob(ModelPart part, float originY, float age) {
		part.y = originY + (Mth.cos(age * 0.25F) * 0.5F + 0.05F);
	}

	public static void bobMouth(ModelPart mouth, float originRot, float maxRot, float age) {
		mouth.xRot = -(degToRad(originRot + Mth.cos(age * 0.25f) * maxRot));
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	protected static float degToRad(float degrees) {
		return degrees * (float)Math.PI / 180 ;
	}

}