package com.bafomdad.uniquecrops.render.entity;

import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Supplier;

public class TEISR extends BlockEntityWithoutLevelRenderer {

    private final Supplier<BlockEntity> dummy;

    public TEISR() {

        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.dummy = Suppliers.memoize(() -> ((EntityBlock)UCBlocks.FASCINO.get()).newBlockEntity(BlockPos.ZERO, UCBlocks.FASCINO.get().defaultBlockState()));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType mode, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {

        if (stack.getItem() == UCItems.DUMMY_FASCINO.get()) {
            BlockEntityRenderer<?> r = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(dummy.get());
            if (r != null)
                r.render(null, 0, ms, buffers, light, overlay);
        }
    }
}
