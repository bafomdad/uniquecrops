package com.bafomdad.uniquecrops.crafting;

import com.bafomdad.uniquecrops.api.IHourglassRecipe;
import com.bafomdad.uniquecrops.core.JsonUtils;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class RecipeHourglass implements IHourglassRecipe {

    private final ResourceLocation id;
    private final BlockState input;
    private final BlockState output;

    public RecipeHourglass(ResourceLocation id, BlockState input, BlockState output) {

        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(BlockState state) {

        return input.equals(state);
    }

    @Override
    public BlockState getInput() {

        return input;
    }

    @Override
    public BlockState getOutput() {

        return output;
    }

    @Override
    public ResourceLocation getId() {

        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return UCRecipes.HOURGLASS_SERIALIZER.get();
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<RecipeHourglass> {

        @Override
        public RecipeHourglass fromJson(ResourceLocation id, JsonObject obj) {

            BlockState input = JsonUtils.readBlockState(GsonHelper.getAsJsonObject(obj, "input"));
            BlockState output = JsonUtils.readBlockState(GsonHelper.getAsJsonObject(obj, "output"));

            return new RecipeHourglass(id, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buff, RecipeHourglass recipe) {

            buff.writeVarInt(Block.getId(recipe.input));
            buff.writeVarInt(Block.getId(recipe.output));
        }

        @Nullable
        @Override
        public RecipeHourglass fromNetwork(ResourceLocation id, FriendlyByteBuf buff) {

            BlockState input = Block.stateById(buff.readVarInt());
            BlockState output = Block.stateById(buff.readVarInt());
            return new RecipeHourglass(id, input, output);
        }
    }
}
