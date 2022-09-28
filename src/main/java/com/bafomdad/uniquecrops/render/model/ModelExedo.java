package com.bafomdad.uniquecrops.render.model;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileExedo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.Random;

public class ModelExedo extends Model {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(UniqueCrops.MOD_ID, "modelexedo"), "main");
    private final ModelPart stalk;
    private final ModelPart stalk2;
    private final ModelPart stalk3;
    private final ModelPart leaf1;
    private final ModelPart leaf2;
    private final ModelPart stamen;
    private final ModelPart petal1;
    private final ModelPart petal2;
    private final ModelPart petal3;
    private final ModelPart petal4;

    public ModelExedo(ModelPart root) {

        super(RenderType::entityCutout);

        this.stalk = root.getChild("stalk");
        this.stalk2 = root.getChild("stalk2");
        this.stalk3 = root.getChild("stalk3");
        this.leaf1 = root.getChild("leaf1");
        this.leaf2 = root.getChild("leaf2");
        this.stamen = root.getChild("stamen");
        this.petal1 = root.getChild("petal1");
        this.petal2 = root.getChild("petal2");
        this.petal3 = root.getChild("petal3");
        this.petal4 = root.getChild("petal4");
    }

    public static MeshDefinition createBodyLayer() {

        var mesh = new MeshDefinition();
        var partdefinition = mesh.getRoot();

        PartDefinition stalk = partdefinition.addOrReplaceChild("stalk", CubeListBuilder.create().texOffs(16, 5).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));
        PartDefinition stalk2 = partdefinition.addOrReplaceChild("stalk2", CubeListBuilder.create().texOffs(16, 4).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.0F));
        PartDefinition stalk3 = partdefinition.addOrReplaceChild("stalk3", CubeListBuilder.create().texOffs(16, 5).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));
        PartDefinition leaf1 = partdefinition.addOrReplaceChild("leaf1", CubeListBuilder.create().texOffs(16, 6).addBox(0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2F, 22.0F, 1.2F, 0.7854F, 0.0F, -0.7854F));
        PartDefinition leaf2 = partdefinition.addOrReplaceChild("leaf2", CubeListBuilder.create().texOffs(16, 6).addBox(0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2F, 22.0F, -1.2F, 0.7854F, 3.1416F, 0.7854F));
        PartDefinition stamen = partdefinition.addOrReplaceChild("stamen", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.5F, 0.0F));
        PartDefinition petal1 = partdefinition.addOrReplaceChild("petal1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 16.0F, -0.5F, 0.7854F, -2.3562F, 0.0F));
        PartDefinition petal2 = partdefinition.addOrReplaceChild("petal2", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 16.0F, 0.5F, 0.7854F, -0.7854F, 0.0F));
        PartDefinition petal3 = partdefinition.addOrReplaceChild("petal3", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 16.0F, -0.5F, 0.7854F, 2.3562F, 0.0F));
        PartDefinition petal4 = partdefinition.addOrReplaceChild("petal4", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 16.0F, 0.5F, 0.7854F, 0.7854F, 0.0F));

        return mesh;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        stalk.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        stalk2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        stalk3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leaf1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leaf2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        stamen.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        petal1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        petal2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        petal3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        petal4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void renderWithWiggle(TileExedo te, PoseStack ms, VertexConsumer buffer, int light, int overlay) {

        Random rand = te.getLevel().random;
        boolean flag = te.isWiggling;

        ms.pushPose();
        ms.translate(flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0, 0, flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0);
        ms.scale(1.5F, 1.5F, 1.5F);
        this.stalk.render(ms, buffer, light, overlay);
        this.leaf1.render(ms, buffer, light, overlay);
        this.leaf2.render(ms, buffer, light, overlay);
        ms.popPose();

        ms.pushPose();
        ms.translate(flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0, 0, flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0);
        ms.scale(1.5F, 1.5F, 1.5F);
        this.stalk2.render(ms, buffer, light, overlay);
        ms.popPose();

        ms.pushPose();
        ms.translate(flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0, 0, flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0);
        ms.scale(1.5F, 1.5F, 1.5F);
        this.stalk3.render(ms, buffer, light, overlay);
        ms.popPose();

        ms.pushPose();
        ms.translate(flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0, 0.575, flag ? Math.sin(rand.nextInt(100)) * 0.0125F : 0);
        ms.scale(0.8F, 0.8F, 0.8F);
        this.petal4.render(ms, buffer, light, overlay);
        this.petal3.render(ms, buffer, light, overlay);
        this.petal2.render(ms, buffer, light, overlay);
        this.petal1.render(ms, buffer, light, overlay);
        ms.translate(0, 0.3, 0);
        ms.scale(0.5F, 0.5F, 0.5F);
        this.stamen.render(ms, buffer, light, overlay);
        ms.popPose();
    }
}