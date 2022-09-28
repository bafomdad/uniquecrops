package com.bafomdad.uniquecrops.data.recipes;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.JsonUtils;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class HourglassProvider extends RecipeProvider {

    public HourglassProvider(DataGenerator gen) {

        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        consumer.accept(create("oldgrass", Blocks.GRASS_BLOCK, UCBlocks.OLDGRASS.get()));
        consumer.accept(create("oldcobble", Blocks.COBBLESTONE, UCBlocks.OLDCOBBLE.get()));
        consumer.accept(create("oldcobblemoss", Blocks.MOSSY_COBBLESTONE, UCBlocks.OLDCOBBLEMOSS.get()));
        consumer.accept(create("oldgravel", Blocks.GRAVEL, UCBlocks.OLDGRAVEL.get()));
        consumer.accept(create("oldbrick", Blocks.BRICKS, UCBlocks.OLDBRICK.get()));
        consumer.accept(create("olddiamond", Blocks.DIAMOND_BLOCK, UCBlocks.OLDDIAMOND.get()));
        consumer.accept(create("oldgold", Blocks.GOLD_BLOCK, UCBlocks.OLDGOLD.get()));
        consumer.accept(create("oldiron", Blocks.IRON_BLOCK, UCBlocks.OLDIRON.get()));
        consumer.accept(create("ruinedbricks", Blocks.STONE_BRICKS, UCBlocks.RUINEDBRICKS.get()));
        consumer.accept(create("ruinedbrickscarved", Blocks.CHISELED_STONE_BRICKS, UCBlocks.RUINEDBRICKSCARVED.get()));
        consumer.accept(create("flywood_sapling", Blocks.BIRCH_SAPLING, UCBlocks.FLYWOOD_SAPLING.get()));
    }

    private static RecipeFinished create(String name, BlockState input, BlockState output) {

        return new RecipeFinished(new ResourceLocation(UniqueCrops.MOD_ID, "hourglass/" + name), input, output);
    }

    private static RecipeFinished create(String name, Block input, Block output) {

        return create(name, input.defaultBlockState(), output.defaultBlockState());
    }

    private static class RecipeFinished implements FinishedRecipe {

        private final ResourceLocation id;
        private final BlockState input;
        private final BlockState output;

        private RecipeFinished(ResourceLocation id, BlockState input, BlockState output) {

            this.id = id;
            this.input = input;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {

            json.add("input", JsonUtils.writeBlockState(input));
            json.add("output", JsonUtils.writeBlockState(output));
        }

        @Override
        public ResourceLocation getId() {

            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {

            return UCRecipes.HOURGLASS_SERIALIZER.get();
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
