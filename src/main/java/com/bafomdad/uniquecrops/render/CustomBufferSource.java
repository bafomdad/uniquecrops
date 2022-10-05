package com.bafomdad.uniquecrops.render;

import com.bafomdad.uniquecrops.mixin.AccessorBS;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

import java.util.Map;

public class CustomBufferSource extends MultiBufferSource.BufferSource {

    protected CustomBufferSource(BufferBuilder fallback, Map<RenderType, BufferBuilder> layerbuffers) {

        super(fallback, layerbuffers);
    }

    public VertexConsumer getBuffer(RenderType type) {

        return super.getBuffer(CustomRenderType.remap(type));
    }

    public static MultiBufferSource.BufferSource initBuffers(MultiBufferSource.BufferSource original) {

        BufferBuilder fallback = ((AccessorBS)original).getFallbackBuffer();
        Map<RenderType, BufferBuilder> layerBuffers = ((AccessorBS)original).getFixedBuffers();
        Map<RenderType, BufferBuilder> remapped = new Object2ObjectLinkedOpenHashMap<>();
        for (Map.Entry<RenderType, BufferBuilder> e : layerBuffers.entrySet())
            remapped.put(CustomRenderType.remap(e.getKey()), e.getValue());

        return new CustomBufferSource(fallback, remapped);
    }
}
