package com.bafomdad.uniquecrops.mixin;

import com.bafomdad.uniquecrops.integration.patchouli.PatchouliUtils;
import com.bafomdad.uniquecrops.items.curios.EmblemIronStomach;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeManager.class)
public class MixinRecipeLoad {

    @Inject(at = @At("RETURN"), method = "replaceRecipes")
    public void uniquecrops_onSync(Iterable<IRecipe<?>> recipes, CallbackInfo info) {

        PatchouliUtils.registerMultiblocks();
        EmblemIronStomach.init();
    }
}
