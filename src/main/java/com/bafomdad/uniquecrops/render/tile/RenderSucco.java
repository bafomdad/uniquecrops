package com.bafomdad.uniquecrops.render.tile;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileSucco;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;
import java.util.Map;

public class RenderSucco extends TileEntityRenderer<TileSucco> {

    private static IRenderTypeBuffer.Impl buffers = null;

    public RenderSucco(TileEntityRendererDispatcher manager) {

        super(manager);
    }

    @Override
    public void render(TileSucco te, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffer, int light, int overlay) {

        int age = te.getBlockState().getValue(BaseCropsBlock.AGE);

        if (buffers  == null)
            buffers = initBuffers(Minecraft.getInstance().renderBuffers().bufferSource());

        BlockState renderState = UCBlocks.DUMMY_CROP.get().defaultBlockState().setValue(BaseCropsBlock.AGE, age);
        BlockRendererDispatcher brd = Minecraft.getInstance().getBlockRenderer();
        if (buffer instanceof IRenderTypeBuffer.Impl) {
            ((IRenderTypeBuffer.Impl)buffer).endBatch();
        }
        brd.renderBlock(renderState, ms, buffers, light, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);

        buffers.endBatch();
    }

    private static IRenderTypeBuffer.Impl initBuffers(IRenderTypeBuffer.Impl original) {

        BufferBuilder fallback = ObfuscationReflectionHelper.getPrivateValue(IRenderTypeBuffer.Impl.class, original, "field_228457_a_");
        Map<RenderType, BufferBuilder> layerBuffers = ObfuscationReflectionHelper.getPrivateValue(IRenderTypeBuffer.Impl.class, original, "field_228458_b_");
        Map<RenderType, BufferBuilder> remapped = new Object2ObjectLinkedOpenHashMap<>();
        for (Map.Entry<RenderType, BufferBuilder> e : layerBuffers.entrySet())
            remapped.put(GhostRenderType.remap(e.getKey()), e.getValue());

        return new GhostBuffers(fallback, remapped);
    }

    private static class GhostBuffers extends IRenderTypeBuffer.Impl {

        protected GhostBuffers(BufferBuilder fallback, Map<RenderType, BufferBuilder> layerBuffers) {

            super(fallback, layerBuffers);
        }

        @Override
        public IVertexBuilder getBuffer(RenderType type) {

            return super.getBuffer(GhostRenderType.remap(type));
        }
    }

    private static class GhostRenderType extends RenderType {
        private static Map<RenderType, RenderType> remappedTypes = new IdentityHashMap<>();

        private GhostRenderType(RenderType original) {
            super(String.format("%s_%s_ghost", original.toString(), UniqueCrops.MOD_ID), original.format(), original.mode(), original.bufferSize(), original.affectsCrumbling(), true, () -> {
                original.setupRenderState();

                // Alter GL state
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
                float moon = Minecraft.getInstance().level.getMoonPhase();
                RenderSystem.blendColor(1, 1, 1, moon);
            }, () -> {
                RenderSystem.blendColor(1, 1, 1, 1);
                RenderSystem.defaultBlendFunc();
                RenderSystem.disableBlend();

                original.clearRenderState();
            });
        }

        @Override
        public boolean equals(@Nullable Object other) {
            return this == other;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this);
        }

        public static RenderType remap(RenderType in) {

            if (in instanceof GhostRenderType) {
                return in;
            } else {
                return remappedTypes.computeIfAbsent(in, GhostRenderType::new);
            }
        }
    }
}
