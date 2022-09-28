package com.bafomdad.uniquecrops.render.entity;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.entities.BattleCropEntity;
import com.bafomdad.uniquecrops.render.model.ModelBattleCrop;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderBattleCropEntity extends MobRenderer<BattleCropEntity, ModelBattleCrop> {

    private static final ResourceLocation CROP_TEXTURE = new ResourceLocation(UniqueCrops.MOD_ID, "textures/models/entity/model_original.png");

    public RenderBattleCropEntity(EntityRendererProvider.Context ctx) {

        super(ctx, new ModelBattleCrop(ctx.bakeLayer(ModelBattleCrop.LAYER_LOCATION)), 0.15F);
    }

    @Override
    public ResourceLocation getTextureLocation(BattleCropEntity entity) {

        return CROP_TEXTURE;
    }
}
