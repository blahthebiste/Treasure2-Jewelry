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
import mod.gottsch.forge.treasure2.core.entity.monster.WitherwoodGolem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

/**
 * @author Mark Gottschling on AUg 27, 2024
 *
 */
public class WitherwoodGolemModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Treasure.MODID, "witherwood_golem"), "main");

	private final ModelPart head;
	private final ModelPart neck;
	private final ModelPart torso;
	private final ModelPart upperBody;
	private final ModelPart leftArm;
	private final ModelPart elbow1;
	private final ModelPart rightArm;
	private final ModelPart elbow0;
	private final ModelPart lowerBody;
	private final ModelPart legs;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public WitherwoodGolemModel(ModelPart root) {
		this.head = root.getChild("head");
		this.neck = head.getChild("neck");
		this.torso = root.getChild("torso");
		this.upperBody = torso.getChild("upperBody");
		this.leftArm = upperBody.getChild("arm1");
		this.elbow1 = leftArm.getChild("elbow1");
		this.rightArm = upperBody.getChild("arm0");
		this.elbow0 = rightArm.getChild("elbow0");
		this.lowerBody = torso.getChild("lowerBody");
		this.legs = root.getChild("legs");
		this.rightLeg = legs.getChild("leg0");
		this.leftLeg = legs.getChild("leg1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(17, 51).addBox(0.0F, -15.0F, 3.0F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -13.0F, 3.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(17, 43).addBox(3.0F, -13.0F, -1.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(46, 55).addBox(-1.0F, -12.0F, 0.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -10.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition neck = head.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 63).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 2.5F, 0.4363F, 0.0F, 0.0F));
		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(46, 39).mirror().addBox(4.0F, -16.0F, -1.0F, 2.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(46, 39).addBox(-6.0F, -16.0F, -1.0F, 2.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.3054F, 0.0F, 0.0F));
		PartDefinition upperBody = torso.addOrReplaceChild("upperBody", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -7.0F, -5.0F, 14.0F, 14.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(29, 51).addBox(3.0F, -8.0F, 4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
		PartDefinition cube_r1 = upperBody.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(29, 51).addBox(0.0F, -8.0F, 0.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 6.0F, 4.0F, -0.2618F, 0.0F, 0.0F));
		PartDefinition arm1 = upperBody.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(0, 43).mirror().addBox(-1.0F, -2.5F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(49, 61).addBox(3.0F, -2.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -4.0F, -1.0F, -0.3927F, 0.0F, -0.1745F));
		PartDefinition elbow1 = arm1.addOrReplaceChild("elbow1", CubeListBuilder.create().texOffs(29, 39).mirror().addBox(-2.0F, -0.5F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 13.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
		PartDefinition arm0 = upperBody.addOrReplaceChild("arm0", CubeListBuilder.create().texOffs(0, 43).addBox(-4.0F, -2.5F, -3.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -4.0F, 0.0F, -0.3927F, 0.0F, 0.1745F));
		PartDefinition elbow0 = arm0.addOrReplaceChild("elbow0", CubeListBuilder.create().texOffs(29, 39).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 13.0F, -1.0F, -0.3491F, 0.0F, 0.0F));
		PartDefinition cube_r2 = elbow0.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(49, 61).mirror().addBox(-8.0F, -6.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
		PartDefinition lowerBody = torso.addOrReplaceChild("lowerBody", CubeListBuilder.create().texOffs(33, 24).addBox(-3.5F, 0.0F, -3.0F, 7.0F, 8.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, -0.0873F, 0.0F, 0.0F));
		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition leg0 = legs.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(47, 0).addBox(-1.5F, -5.0F, -3.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(52, 14).addBox(-2.0F, 4.0F, -3.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(41, 31).addBox(-1.0F, 11.0F, -5.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(41, 30).addBox(-4.0F, 11.0F, -2.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -13.0F, 1.0F));
		PartDefinition leg1 = legs.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(47, 0).mirror().addBox(-3.5F, -5.0F, -3.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(52, 14).mirror().addBox(-4.0F, 4.0F, -3.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, -13.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		this.rightLeg.xRot = -1.5F * Mth.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
		this.leftLeg.xRot = 1.5F * Mth.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
		this.rightLeg.yRot = 0.0F;
		this.leftLeg.yRot = 0.0F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void prepareMobModel(T entity, float p_102958_, float p_102959_, float p_102960_) {
		int attackAnimationTick = ((WitherwoodGolem)entity).getAttackAnimationTick();
		this.rightArm.xRot = -2.0F + 1.5F * Mth.triangleWave((float)attackAnimationTick - p_102960_, 10.0F);
		this.leftArm.xRot = -2.0F + 1.5F * Mth.triangleWave((float)attackAnimationTick - p_102960_, 10.0F);
	}
}