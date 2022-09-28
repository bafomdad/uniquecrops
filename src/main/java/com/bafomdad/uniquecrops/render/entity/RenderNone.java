package com.bafomdad.uniquecrops.render.entity;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.resources.ResourceLocation;

public class RenderNone<T extends Entity> extends EntityRenderer<T> {

    public RenderNone(EntityRendererProvider.Context ctx) {

        super(ctx);
    }

    @Override
    public boolean shouldRender(T entity, Frustum clipping, double x, double y, double z) {

        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {

        return InventoryMenu.BLOCK_ATLAS;
    }
}
