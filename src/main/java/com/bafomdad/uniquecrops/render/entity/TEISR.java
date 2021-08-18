package com.bafomdad.uniquecrops.render.entity;

import com.bafomdad.uniquecrops.init.UCItems;
import com.google.common.base.Preconditions;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.LazyValue;
import net.minecraft.util.registry.Registry;

public class TEISR extends ItemStackTileEntityRenderer {

    private final Block block;
    private final LazyValue<TileEntity> dummy;

    public TEISR(Block block) {
        this.block = Preconditions.checkNotNull(block);
        this.dummy = new LazyValue<>(() -> {
            TileEntityType<?> type = Registry.BLOCK_ENTITY_TYPE.getOptional(Registry.BLOCK.getKey(block)).get();
            return type.create();
        });
    }

    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transform, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {

        if (stack.getItem() == UCItems.DUMMY_FASCINO.get()) {
            TileEntityRenderer<?> r = TileEntityRendererDispatcher.instance.getRenderer(dummy.get());
            if (r != null)
                r.render(null, 0, ms, buffers, light, overlay);
        }
    }
}
