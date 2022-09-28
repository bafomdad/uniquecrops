package com.bafomdad.uniquecrops.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiFunction;

public class CustomRenderType extends RenderType {

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
}
