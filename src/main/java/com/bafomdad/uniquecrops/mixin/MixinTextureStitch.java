package com.bafomdad.uniquecrops.mixin;

import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureAtlasSprite.class)
public class MixinTextureStitch {

    @Shadow
    int frameCounter;

    @Inject(method = "updateAnimation", at = @At("HEAD"), cancellable = true)
    private void updateTextureAnimations(CallbackInfo ci) {

        updateTextureDyeius();
        updateTextureInvisible();
    }

    private void updateTextureInvisible() {

        if (!isUniqueTexture("invisibilia")) return;
        ClientPlayerEntity p = Minecraft.getInstance().player;
        if (p != null) {
            if ((p.inventory.armorInventory.get(3).getItem() == UCItems.GLASSES_3D.get()) || p.isCreative())
                this.frameCounter = 1;
            else
                this.frameCounter = 0;
        }
        this.uploadAnimationFrames(this.frameCounter);
    }

    private void updateTextureDyeius() {

        if (!isUniqueTexture("dyeplant5")) return;

        World world = Minecraft.getInstance().world;
        if (world != null) {
            long time = world.getDayTime() % 24000L;
            this.frameCounter = (int)(time / 1500);
        }
        this.uploadAnimationFrames(this.frameCounter);
    }

    private boolean isUniqueTexture(String texName) {

        return ((TextureAtlasSprite)(Object)this).getName().toString().contains(texName);
    }

    private void uploadAnimationFrames(int frame) {

        ((TextureAtlasSprite)(Object)this).uploadFrames(frame);
    }
}
