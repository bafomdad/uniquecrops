package com.bafomdad.uniquecrops.mixin;

import com.bafomdad.uniquecrops.integration.patchouli.PatchouliUtils;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.network.play.server.SAdvancementInfoPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeManager.class)
public class MixinRecipeLoad {

    @Inject(at = @At("RETURN"), method = "deserializeRecipes")
    public void uniquecrops_onSync(Iterable<IRecipe<?>> recipes, CallbackInfo info) {

        PatchouliUtils.registerMultiblocks();
    }
}
