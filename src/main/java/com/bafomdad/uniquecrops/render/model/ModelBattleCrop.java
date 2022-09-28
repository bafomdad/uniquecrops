package com.bafomdad.uniquecrops.render.model;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.entities.BattleCropEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ModelBattleCrop extends EntityModel<BattleCropEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(UniqueCrops.MOD_ID, "modelbattlecrop"), "main");

    private final ModelPart crop1;
    private final ModelPart crop2;
    private final ModelPart bipedHead;
    private final ModelPart bipedRightLeg;
    private final ModelPart bipedLeftLeg;
    private final ModelPart bipedRightArm;
    private final ModelPart bipedLeftArm;

    public ModelBattleCrop(ModelPart root) {

        super(RenderType::entityCutoutNoCull);

        this.crop1 = root.getChild("crop1");
        this.crop2 = root.getChild("crop2");
        this.bipedHead = root.getChild("bipedHead");
        this.bipedRightLeg = root.getChild("bipedRightLeg");
        this.bipedLeftLeg = root.getChild("bipedLeftLeg");
        this.bipedRightArm = root.getChild("bipedRightArm");
        this.bipedLeftArm = root.getChild("bipedLeftArm");
    }

    public static MeshDefinition createBodyLayer() {

        var mesh = new MeshDefinition();
        var root = mesh.getRoot();

        root.addOrReplaceChild("crop1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        root.addOrReplaceChild("crop2", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        root.addOrReplaceChild("bipedHead", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, -2.9F));

        root.addOrReplaceChild("bipedRightLeg", CubeListBuilder.create().texOffs(16, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 18.0F, 0.0F));

        root.addOrReplaceChild("bipedLeftLeg", CubeListBuilder.create().texOffs(16, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 18.0F, 0.0F));

        root.addOrReplaceChild("bipedRightArm", CubeListBuilder.create().texOffs(16, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 14.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        root.addOrReplaceChild("bipedLeftArm", CubeListBuilder.create().texOffs(16, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 14.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        return mesh;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        crop1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        crop2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bipedHead.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bipedRightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bipedLeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bipedRightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bipedLeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(BattleCropEntity entity, float limbSwing, float limbSwingAmount, float age, float yaw, float pitch) {

        Vec3 vec3 = new Vec3((float)entity.getDeltaMovement().x, 0, (float)entity.getDeltaMovement().z);
        float speed = (float)(vec3.length() * 0.25F);
        float rad = (float)Math.sin(Math.toRadians(age % 360) * 24F);

        this.bipedLeftLeg.xRot = speed * 60F * rad;
        this.bipedLeftArm.xRot = speed * 60F * rad;
        this.bipedRightLeg.xRot = speed * -120F * rad;
        this.bipedRightArm.xRot = speed * -120F * rad;
    }
}