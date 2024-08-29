package mod.gottsch.forge.treasure2.client.model.entity;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class WitherwoodGolem<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "witherwoodgolem"), "main");
	private final ModelPart head;
	private final ModelPart neck;
	private final ModelPart torso;
	private final ModelPart upperBody;
	private final ModelPart arm1;
	private final ModelPart elbow1;
	private final ModelPart arm0;
	private final ModelPart elbow0;
	private final ModelPart lowerBody;
	private final ModelPart legs;
	private final ModelPart leg0;
	private final ModelPart leg1;

	public WitherwoodGolem(ModelPart root) {
		this.head = root.getChild("head");
		this.neck = root.getChild("neck");
		this.torso = root.getChild("torso");
		this.upperBody = root.getChild("upperBody");
		this.arm1 = root.getChild("arm1");
		this.elbow1 = root.getChild("elbow1");
		this.arm0 = root.getChild("arm0");
		this.elbow0 = root.getChild("elbow0");
		this.lowerBody = root.getChild("lowerBody");
		this.legs = root.getChild("legs");
		this.leg0 = root.getChild("leg0");
		this.leg1 = root.getChild("leg1");
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
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}