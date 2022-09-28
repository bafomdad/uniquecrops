package com.bafomdad.uniquecrops.render.model;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ModelSundial extends Model {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(UniqueCrops.MOD_ID, "modelsundial"), "main");
    public final ModelPart Dial;
    public final ModelPart DialRedstone;

    public ModelSundial(ModelPart root) {

        super(RenderType::entityCutout);
        this.Dial = root.getChild("Dial");
        this.DialRedstone = root.getChild("DialRedstone");
    }

    public static MeshDefinition createBodyLayer() {

        var mesh = new MeshDefinition();
        var partdefinition = mesh.getRoot();

        partdefinition.addOrReplaceChild("Dial", CubeListBuilder.create().texOffs(16, 16).mirror().addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -1.0F, 0.0F));
        partdefinition.addOrReplaceChild("DialRedstone", CubeListBuilder.create().texOffs(32, 16).mirror().addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -0.5F, 0.0F));

        return mesh;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        Dial.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        DialRedstone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}