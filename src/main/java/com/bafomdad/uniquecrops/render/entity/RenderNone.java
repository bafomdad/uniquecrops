package com.bafomdad.uniquecrops.render.entity;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;

public class RenderNone<T extends Entity> extends EntityRenderer<T> {

    public RenderNone(EntityRendererManager manager) {

        super(manager);
    }

    @Override
    public boolean shouldRender(T entity, ClippingHelper clipping, double x, double y, double z) {

        return false;
    }

    @Override
    public ResourceLocation getEntityTexture(T entity) {

        return PlayerContainer.LOCATION_BLOCKS_TEXTURE;
    }
}
