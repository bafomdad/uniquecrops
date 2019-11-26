package com.bafomdad.uniquecrops.integration.crafttweaker;

import com.bafomdad.uniquecrops.UniqueCropsAPI;
import com.bafomdad.uniquecrops.integration.crafttweaker.mtlib.BaseUndoable;
import com.bafomdad.uniquecrops.integration.crafttweaker.mtlib.InputHelper;
import com.bafomdad.uniquecrops.integration.crafttweaker.mtlib.LogHelper;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Usage and syntax:
 * <p>
 * import mods.uniquecrops.SeedCrafting;
 * <p>
 * SeedCrafting.addRecipe(IItemStack output, IIngredient center, IIngredient corner, IIngredient edge);
 * SeedCrafting.removeRecipe(IItemStack output);
 */
@ZenRegister
@ZenClass(ZenSeedCrafting.NAME)
public class ZenSeedCrafting {

  public static final String NAME = "mods.uniquecrops.SeedCrafting";

  @ZenMethod
  public static void addRecipe(IItemStack output, IIngredient center, IIngredient corner, IIngredient edge) {

    CraftTweakerPlugin.LATE_ADDITIONS.add(new Add(
        InputHelper.toStack(output),
        IngredientHelper.toIngredient(center),
        IngredientHelper.toIngredient(corner),
        IngredientHelper.toIngredient(edge)
    ));
  }

  private static class Add extends BaseUndoable {

    private final ItemStack output;
    private final Ingredient center;
    private final Ingredient corner;
    private final Ingredient edge;

    protected Add(ItemStack output, Ingredient center, Ingredient corner, Ingredient edge) {

      super(NAME);
      this.output = output;
      this.center = center;
      this.corner = corner;
      this.edge = edge;
    }

    @Override
    public void apply() {

      UniqueCropsAPI.SEED_RECIPE_REGISTRY.addRecipe(this.output, this.center, this.corner, this.edge);
    }

    @Override
    protected String getRecipeInfo() {

      return LogHelper.getStackDescription(this.output);
    }
  }

  @ZenMethod
  public static void removeRecipe(IItemStack output) {

    CraftTweakerPlugin.LATE_REMOVALS.add(new Remove(
        InputHelper.toStack(output)
    ));
  }

  private static class Remove extends BaseUndoable {

    private ItemStack output;

    protected Remove(ItemStack output) {

      super(NAME);
      this.output = output;
    }

    @Override
    public void apply() {

      UniqueCropsAPI.SEED_RECIPE_REGISTRY.removeRecipesByOutput(this.output);
    }

    @Override
    protected String getRecipeInfo() {

      return LogHelper.getStackDescription(this.output);
    }
  }

}
