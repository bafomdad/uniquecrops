package com.bafomdad.uniquecrops.crafting;

import com.bafomdad.uniquecrops.api.IHourglassRecipe;
import com.bafomdad.uniquecrops.core.JsonUtils;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
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
    public IRecipeSerializer<?> getSerializer() {

        return UCRecipes.HOURGLASS_SERIALIZER.get();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeHourglass> {

        @Override
        public RecipeHourglass fromJson(ResourceLocation id, JsonObject obj) {

            BlockState input = JsonUtils.readBlockState(JSONUtils.getAsJsonObject(obj, "input"));
            BlockState output = JsonUtils.readBlockState(JSONUtils.getAsJsonObject(obj, "output"));

            return new RecipeHourglass(id, input, output);
        }

        @Override
        public void toNetwork(PacketBuffer buff, RecipeHourglass recipe) {

            buff.writeVarInt(Block.getId(recipe.input));
            buff.writeVarInt(Block.getId(recipe.output));
        }

        @Nullable
        @Override
        public RecipeHourglass fromNetwork(ResourceLocation id, PacketBuffer buff) {

            BlockState input = Block.stateById(buff.readVarInt());
            BlockState output = Block.stateById(buff.readVarInt());
            return new RecipeHourglass(id, input, output);
        }
    }
}
