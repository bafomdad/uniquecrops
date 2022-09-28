package com.bafomdad.uniquecrops.mixin;

import com.bafomdad.uniquecrops.integration.patchouli.PatchouliUtils;
import com.bafomdad.uniquecrops.items.curios.EmblemIronStomach;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeManager.class)
public class MixinRecipeLoad {

    @Inject(method = "replaceRecipes", at = @At("RETURN"))
    public void uniquecrops_onSync(Iterable<Recipe<?>> recipes, CallbackInfo info) {

        PatchouliUtils.registerMultiblocks();
        EmblemIronStomach.init();
    }
}
