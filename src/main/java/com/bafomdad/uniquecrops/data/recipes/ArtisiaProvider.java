package com.bafomdad.uniquecrops.data.recipes;

import com.bafomdad.uniquecrops.core.JsonUtils;
import com.bafomdad.uniquecrops.core.DyeUtils;
import com.bafomdad.uniquecrops.core.enums.EnumBonemealDye;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ArtisiaProvider extends RecipeProvider {

    public ArtisiaProvider(DataGenerator gen) {

        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        for (DyeColor dye : DyeColor.values())
            consumer.accept(create(getDyeCraftingResult(dye.ordinal()), DyeUtils.DYE_BY_COLOR.get(DyeColor.byId(dye.ordinal())), UCItems.SAVAGEESSENCE.get(), UCItems.SAVAGEESSENCE.get()));

        DyeUtils.BONEMEAL_DYE.entrySet().forEach(dye -> {
            Item item = dye.getValue().asItem();
            Item dyeItem = DyeUtils.DYE_BY_COLOR.get(DyeColor.byId(dye.getKey().ordinal())).asItem();
            consumer.accept(create(new ItemStack(item, 8), dyeItem, Items.BONE_MEAL, Items.BONE_MEAL));
        });

        consumer.accept(create(UCItems.CINDERBELLA_SEED.get(), Items.SUGAR, Items.WHEAT_SEEDS, UCItems.NORMAL_SEED.get()));
        consumer.accept(create(UCItems.COLLIS_SEED.get(), Items.SUGAR, UCItems.NORMAL_SEED.get(), UCItems.CINDERBELLA_SEED.get()));
        consumer.accept(create(UCItems.DIRIGIBLE_SEED.get(), Items.SUGAR, Items.PUMPKIN_SEEDS, UCItems.COLLIS_SEED.get()));
        consumer.accept(create(UCItems.ENDERLILY_SEED.get(), Items.ENDER_EYE, Items.ENDER_PEARL, UCItems.DIRIGIBLE_SEED.get()));
        consumer.accept(create(UCItems.INVISIBILIA_SEED.get(), Items.SUGAR, Blocks.GLASS, UCItems.CINDERBELLA_SEED.get()));
        consumer.accept(create(UCItems.KNOWLEDGE_SEED.get(), Items.SUGAR, Items.ENCHANTED_BOOK, UCItems.INVISIBILIA_SEED.get()));
        consumer.accept(create(UCItems.MARYJANE_SEED.get(), Items.BLAZE_ROD, Items.BLAZE_POWDER, UCItems.COLLIS_SEED.get()));
        consumer.accept(create(UCItems.MERLINIA_SEED.get(), Items.PUMPKIN_SEEDS, UCItems.TIMEMEAL.get(), UCItems.ENDERLILY_SEED.get()));
        consumer.accept(create(UCItems.MILLENNIUM_SEED.get(), Items.CLOCK, Items.PUMPKIN_SEEDS, UCItems.MERLINIA_SEED.get()));
        consumer.accept(create(UCItems.MUSICA_SEED.get(), Blocks.JUKEBOX, UCItems.NORMAL_SEED.get(), UCItems.MARYJANE_SEED.get()));
        consumer.accept(create(UCItems.PRECISION_SEED.get(), Items.GOLD_NUGGET, UCItems.COLLIS_SEED.get(), UCItems.INVISIBILIA_SEED.get()));
        consumer.accept(create(UCItems.WEEPINGBELLS_SEED.get(), Items.GHAST_TEAR, Items.MELON_SEEDS, UCItems.ENDERLILY_SEED.get()));
        consumer.accept(create(UCItems.ABSTRACT_SEED.get(), Ingredient.of(Items.SUGAR_CANE), Ingredient.of(Blocks.TERRACOTTA), Ingredient.of(ItemTags.WOOL)));
        consumer.accept(create(UCItems.COBBLONIA_SEED.get(), Blocks.COBBLESTONE, Blocks.STONE_BRICKS, UCItems.NORMAL_SEED.get()));
        consumer.accept(create(UCItems.DYEIUS_SEED.get(), Ingredient.of(ItemTags.WOOL), Ingredient.of(Tags.Items.DYES), Ingredient.of(UCItems.ABSTRACT_SEED.get())));
        consumer.accept(create(UCItems.EULA_SEED.get(), Items.PAPER, Items.BOOK, UCItems.COBBLONIA_SEED.get()));
        consumer.accept(create(UCItems.FEROXIA_SEED.get(), Items.CLAY_BALL, UCItems.KNOWLEDGE_SEED.get(), UCItems.WEEPINGBELLS_SEED.get()));
        consumer.accept(create(UCItems.WAFFLONIA_SEED.get(), Items.WHEAT_SEEDS, Items.BREAD, Items.SUGAR));
        consumer.accept(create(UCItems.PIXELSIUS_SEED.get(), UCItems.WAFFLONIA_SEED.get(), Items.BLACK_DYE, Items.PAINTING));
        consumer.accept(create(UCItems.DEVILSNARE_SEED.get(), UCItems.PIXELSIUS_SEED.get(), Blocks.DEAD_BUSH, Items.SWEET_BERRIES));
        consumer.accept(create(UCItems.MALLEATORIS_SEED.get(), UCItems.PRECISION_SEED.get(), Blocks.ANVIL, Items.IRON_INGOT));
        consumer.accept(create(UCItems.PETRAMIA_SEED.get(), UCItems.COBBLONIA_SEED.get(), Blocks.OBSIDIAN, Blocks.COBBLESTONE));
        consumer.accept(create(UCItems.IMPERIA_SEED.get(), UCItems.PETRAMIA_SEED.get(), Blocks.END_ROD, Blocks.GLOWSTONE));
        consumer.accept(create(UCItems.LACUSIA_SEED.get(), Blocks.HOPPER, UCItems.NORMAL_SEED.get(), Items.REDSTONE));
        consumer.accept(create(UCItems.HEXIS_SEED.get(), UCItems.MALLEATORIS_SEED.get(), Items.WOODEN_SWORD, Items.LAPIS_LAZULI));
        consumer.accept(create(UCItems.QUARRY_SEED.get(), Items.DIAMOND_PICKAXE, UCItems.PETRAMIA_SEED.get(), UCItems.HEXIS_SEED.get()));
        consumer.accept(create(UCItems.INSTABILIS_SEED.get(), UCItems.PRECISION_SEED.get(), Blocks.TNT, Items.REDSTONE));
        consumer.accept(create(UCItems.INDUSTRIA_SEED.get(), UCItems.INSTABILIS_SEED.get(), Blocks.SUNFLOWER, Items.POTATO));
        consumer.accept(create(UCItems.SUCCO_SEED.get(), Items.POTION, Items.GHAST_TEAR, UCItems.INVISIBILIA_SEED.get()));
        consumer.accept(create(UCItems.DONUTSTEEL_SEED.get(), Items.CAKE, Items.PURPLE_DYE, Blocks.WHITE_GLAZED_TERRACOTTA));
        consumer.accept(create(UCItems.MAGNES_SEED.get(), UCItems.INDUSTRIA_SEED.get(), UCItems.BEAN_BATTERY.get(), Items.IRON_INGOT));
    }

    private static ItemStack getDyeCraftingResult(int meta) {

        switch (meta) {
            default: return new ItemStack(Items.GHAST_TEAR);
            case 1: return new ItemStack(Items.FIRE_CHARGE, 8);
            case 2: return new ItemStack(Items.DRAGON_BREATH);
            case 3: return new ItemStack(Items.DIAMOND);
            case 4: return new ItemStack(Items.GOLD_INGOT);
            case 5: return new ItemStack(Items.SLIME_BALL, 4);
            case 6: return new ItemStack(Items.SADDLE);
            case 7: return new ItemStack(Blocks.CLAY, 8);
            case 8: return new ItemStack(Items.IRON_INGOT, 2);
            case 9: return new ItemStack(Blocks.CYAN_TERRACOTTA, 16);
            case 10: return new ItemStack(Items.CHORUS_FRUIT);
            case 11: return new ItemStack(Blocks.PRISMARINE, 8);
            case 12: return new ItemStack(Blocks.DIRT, 3);
            case 13: return new ItemStack(Items.EMERALD);
            case 14: return new ItemStack(Items.NETHER_WART);
            case 15: return new ItemStack(Items.WITHER_SKELETON_SKULL);
        }
    }

    private static ResourceLocation idFor(ResourceLocation name) {

        return new ResourceLocation(name.getNamespace(), "artisia/" + name.getPath());
    }

    private static RecipeFinished create(ItemStack output, ItemLike center, ItemLike edge, ItemLike corner) {

        Ingredient fromCenter = Ingredient.of(center);
        Ingredient fromEdge = Ingredient.of(edge);
        Ingredient fromCorner = Ingredient.of(corner);

        return new RecipeFinished(idFor(Registry.ITEM.getKey(output.getItem())), output, fromCorner, fromEdge, fromCorner, fromEdge, fromCenter, fromEdge, fromCorner, fromEdge, fromCorner);
    }

    private static RecipeFinished create(ItemLike item, ItemLike center, ItemLike edge, ItemLike corner) {

        Ingredient fromCenter = Ingredient.of(center);
        Ingredient fromEdge = Ingredient.of(edge);
        Ingredient fromCorner = Ingredient.of(corner);

        return create(item, fromCenter, fromEdge, fromCorner);
    }

    private static RecipeFinished create(ItemLike item, Ingredient center, Ingredient edge, Ingredient corner) {

        return new RecipeFinished(idFor(Registry.ITEM.getKey(item.asItem())), new ItemStack(item), corner, edge, corner, edge, center, edge, corner, edge, corner);
    }

    private static class RecipeFinished implements FinishedRecipe {

        private final ResourceLocation id;
        private final ItemStack output;
        private final Ingredient[] inputs;

        private RecipeFinished(ResourceLocation id, ItemStack output, Ingredient... inputs) {

            this.id = id;
            this.output = output;
            this.inputs = inputs;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {

            json.add("output", JsonUtils.serializeStack(output));
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

            return UCRecipes.ARTISIA_SERIALIZER.get();
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
