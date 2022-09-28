package com.bafomdad.uniquecrops.data.recipes;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class EnchanterProvider extends RecipeProvider {

    public EnchanterProvider(DataGenerator gen) {

        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        consumer.accept(create(Enchantments.ALL_DAMAGE_PROTECTION, 80, UCBlocks.DARK_BLOCK.get(), UCBlocks.DARK_BLOCK.get(), Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK));
        consumer.accept(create(Enchantments.FIRE_PROTECTION, 40, UCItems.CINDERLEAF.get(), Items.LAVA_BUCKET, UCItems.CINDERLEAF.get(), Blocks.BRICKS));
        consumer.accept(create(Enchantments.FALL_PROTECTION, 40, UCItems.INVISIFEATHER.get(), Items.FEATHER, UCItems.CACTUS_BOOTS.get()));
        consumer.accept(create(Enchantments.BLAST_PROTECTION, 40, Blocks.TNT, Items.GUNPOWDER, Items.FLINT_AND_STEEL, Blocks.OBSIDIAN));
        consumer.accept(create(Enchantments.PROJECTILE_PROTECTION, 60, Items.SHIELD, Items.ARROW, Items.ARMOR_STAND));
        consumer.accept(create(Enchantments.RESPIRATION, 20, Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS, Blocks.PRISMARINE));
        consumer.accept(create(Enchantments.AQUA_AFFINITY, 30, Items.DIAMOND_PICKAXE, Items.WATER_BUCKET, UCItems.TIMEDUST.get(), UCItems.TIMEDUST.get()));
        consumer.accept(create(Enchantments.THORNS, 20, UCItems.CACTUS_BOOTS.get(), UCItems.CACTUS_CHESTPLATE.get(), UCItems.CACTUS_HELM.get(), UCItems.CACTUS_LEGGINGS.get()));
        consumer.accept(create(Enchantments.DEPTH_STRIDER, 40, Blocks.SPONGE, Items.PRISMARINE_SHARD, Items.WATER_BUCKET));
        consumer.accept(create(Enchantments.FROST_WALKER, 40, Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE));
        consumer.accept(create(Enchantments.SHARPNESS, 90, Items.WOODEN_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD));
        consumer.accept(create(Enchantments.SMITE, 30, Ingredient.of(UCItems.ENCHANTED_LEATHER.get()), Ingredient.of(Items.LEATHER_CHESTPLATE), Ingredient.of(Items.ROTTEN_FLESH), Ingredient.of(Items.WATER_BUCKET)));
        consumer.accept(create(Enchantments.BANE_OF_ARTHROPODS, 30, Items.IRON_SWORD, Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE, Items.FERMENTED_SPIDER_EYE));
        consumer.accept(create(Enchantments.KNOCKBACK, 40, Blocks.PISTON, Blocks.PISTON, Items.IRON_SWORD));
        consumer.accept(create(Enchantments.FIRE_ASPECT, 50, Items.IRON_SWORD, UCItems.MARYJANE_SEED.get(), Items.BLAZE_POWDER, Items.BLAZE_ROD));
        consumer.accept(create(Enchantments.MOB_LOOTING, 70, UCItems.EMERADIC_DIAMOND.get(), Blocks.BONE_BLOCK, Blocks.TNT, Blocks.COBWEB, Blocks.SOUL_SOIL));
        consumer.accept(create(Enchantments.SWEEPING_EDGE, 70, Items.IRON_SWORD, Items.IRON_SWORD, Items.DIAMOND_SWORD));
        consumer.accept(create(Enchantments.BLOCK_EFFICIENCY, 50, Blocks.REDSTONE_BLOCK, Items.REDSTONE, Blocks.REDSTONE_BLOCK, Blocks.REDSTONE_TORCH, Items.BEETROOT));
        consumer.accept(create(Enchantments.SILK_TOUCH, 40, Blocks.COBWEB, UCItems.PREGEM.get(), UCItems.PREGEM.get(), Items.SHEARS));
        consumer.accept(create(Enchantments.UNBREAKING, 50, Blocks.OBSIDIAN, Blocks.OBSIDIAN, UCItems.TIMEDUST.get(), Blocks.NETHER_BRICKS, Blocks.NETHER_BRICKS));
        consumer.accept(create(Enchantments.BLOCK_FORTUNE, 80, UCItems.SAVAGEESSENCE.get(), Items.DIAMOND_PICKAXE, UCItems.PREGEM.get()));
        consumer.accept(create(Enchantments.POWER_ARROWS, 60, Items.BOW, Items.ARROW, Items.CROSSBOW, Items.ARROW, UCItems.WEEPINGTEAR.get()));
        consumer.accept(create(Enchantments.PUNCH_ARROWS, 40, Blocks.PISTON, Blocks.STICKY_PISTON, Items.BOW));
        consumer.accept(create(Enchantments.FLAMING_ARROWS, 40, Items.BOW, UCItems.MARYJANE_SEED.get(), Items.BLAZE_POWDER, Items.BLAZE_ROD, UCItems.MARYJANE_SEED.get()));
        consumer.accept(create(Enchantments.INFINITY_ARROWS, 10, Items.BOW, UCItems.DOGRESIDUE.get(), UCItems.DOGRESIDUE.get(), UCItems.DOGRESIDUE.get(), UCItems.DOGRESIDUE.get()));
        consumer.accept(create(Enchantments.MENDING, 90, Items.EXPERIENCE_BOTTLE, UCItems.MALLEATORIS_SEED.get()));
    }

    private static RecipeFinished create(Enchantment ench, int cost, ItemLike... items) {

        ResourceLocation id = new ResourceLocation(UniqueCrops.MOD_ID, "enchanter/" + ench.getRegistryName().getPath());
        return new RecipeFinished(id, ench, cost, Ingredient.of(items));
    }

    private static RecipeFinished create(Enchantment ench, int cost, Ingredient... ingredients) {

        ResourceLocation id = new ResourceLocation(UniqueCrops.MOD_ID, "enchanter/" + ench.getRegistryName().getPath());
        return new RecipeFinished(id, ench, cost, ingredients);
    }

    private static class RecipeFinished implements FinishedRecipe {

        private final ResourceLocation id;
        private final Enchantment ench;
        private final int cost;
        private final Ingredient[] inputs;

        public RecipeFinished(ResourceLocation id, Enchantment ench, int cost, Ingredient... inputs) {

            this.id = id;
            this.ench = ench;
            this.cost = cost;
            this.inputs = inputs;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {

            json.addProperty("enchantment", ench.getRegistryName().toString());
            json.addProperty("cost", cost);
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingredient : inputs)
                ingredients.add(ingredient.toJson());
            json.add("ingredients", ingredients);
        }

        @Override
        public ResourceLocation getId() {

            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {

            return UCRecipes.ENCHANTER_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {

            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {

            return null;
        }
    }
}
