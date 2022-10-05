package com.bafomdad.uniquecrops.mixin;

import com.bafomdad.uniquecrops.events.UCEventHandlerClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class MixinWorldRenderer {

    @Inject(at = @At("RETURN"), method = "renderLevel")
    public void onRender(PoseStack ms, float dickTelta, long time, boolean outline, Camera camera, GameRenderer render, LightTexture light, Matrix4f mat, CallbackInfo info) {

        UCEventHandlerClient.renderWorldLast(ms);
    }
}
