package com.bafomdad.uniquecrops.render.entity;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.entities.BattleCropEntity;
import com.bafomdad.uniquecrops.render.model.ModelBattleCrop;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class RenderBattleCropEntity extends BipedRenderer<BattleCropEntity, BipedModel<BattleCropEntity>> {

    private static final ResourceLocation CROP_TEXTURE = new ResourceLocation(UniqueCrops.MOD_ID, "textures/models/entity/model_original.png");

    public RenderBattleCropEntity(EntityRendererManager manager) {

        super(manager, new ModelBattleCrop(), 0.15F);
    }

    @Override
    public ResourceLocation getEntityTexture(BattleCropEntity entity) {

        return CROP_TEXTURE;
    }
}
