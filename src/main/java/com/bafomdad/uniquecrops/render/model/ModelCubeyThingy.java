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

public class ModelCubeyThingy extends Model {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(UniqueCrops.MOD_ID, "modelcubeythingy"), "main");
    private final ModelPart cube;

    public ModelCubeyThingy(ModelPart root) {

        super(RenderType::entityCutout);
        this.cube = root.getChild("cube");
    }

    public static MeshDefinition createBodyLayer() {

        var mesh = new MeshDefinition();
        var root = mesh.getRoot();

        root.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return mesh;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        cube.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}