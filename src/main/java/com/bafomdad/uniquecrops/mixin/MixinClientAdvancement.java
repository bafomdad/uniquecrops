package com.bafomdad.uniquecrops.mixin;

import com.bafomdad.uniquecrops.integration.patchouli.PatchouliUtils;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.network.play.server.SAdvancementInfoPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientAdvancementManager.class)
public class MixinClientAdvancement {

    /*
    Patchouli mixins into the end trail of this method, so I mixin into the head part, so it can call
    and register the multiblocks before the book populates its entries with said multiblocks, since
    unique crop's own multiblock recipes also only load in at around this time - calling it earlier than
    this only results in an empty multiblock recipe list for patchouli.
     */
    @Inject(at = @At("HEAD"), method = "read")
    public void uniquecrops_onSync(SAdvancementInfoPacket packet, CallbackInfo info) {

        PatchouliUtils.registerMultiblocks();
    }
}
