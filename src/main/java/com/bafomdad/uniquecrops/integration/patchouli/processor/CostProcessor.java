package com.bafomdad.uniquecrops.integration.patchouli.processor;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class CostProcessor implements IComponentProcessor {

    private Recipe<?> recipe;

    @Override
    public void setup(IVariableProvider var) {

        if (!var.has("multiblock")) return;

        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        recipe = manager.byKey(new ResourceLocation(var.get("multiblock").asString())).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public IVariable process(String key) {

        if (key.equals("multiblock"))
            return IVariable.wrap(recipe.getId().toString());

        return null;
    }
}
