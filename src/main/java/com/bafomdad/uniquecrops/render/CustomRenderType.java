package com.bafomdad.uniquecrops.render;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class CustomRenderType extends RenderType {

    private static final Map<RenderType, RenderType> remappedTypes = new IdentityHashMap<>();

    public CustomRenderType(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setup, Runnable clear) {

        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setup, clear);
    }

    public static final BiFunction<ResourceLocation, Boolean, RenderType> CUSTOM_BEAM = Util.memoize((res, bool) -> {
        RenderType.CompositeState composite = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_BEACON_BEAM_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(res, false, false))
                .setTransparencyState(bool ? TRANSLUCENT_TRANSPARENCY : NO_TRANSPARENCY)
                .setWriteMaskState(bool ? COLOR_WRITE : COLOR_DEPTH_WRITE)
                .createCompositeState(false);
        return RenderType.create("custom_beam", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 256, false, true, composite);
    });

    private CustomRenderType(RenderType original) {

        super(String.format("%s_%s_custom", original.toString(), UniqueCrops.MOD_ID), original.format(), original.mode(), original.bufferSize(), original.affectsCrumbling(), true, () -> {
            original.setupRenderState();

            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
        }, () -> {
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();

            original.clearRenderState();
        });
    }

    public static RenderType remap(RenderType in) {

        if (in instanceof CustomRenderType) return in;

        return remappedTypes.computeIfAbsent(in, CustomRenderType::new);
    }
}
