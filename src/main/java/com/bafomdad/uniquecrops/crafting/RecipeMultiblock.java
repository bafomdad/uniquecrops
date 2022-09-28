package com.bafomdad.uniquecrops.crafting;

import com.bafomdad.uniquecrops.api.IMultiblockRecipe;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.data.recipes.MultiblockProvider;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.gson.reflect.TypeToken;
import com.google.common.collect.Sets;
import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.resources.ResourceLocation;

public class RecipeMultiblock implements IMultiblockRecipe {

    private final ResourceLocation id;
    private final ItemStack catalyst;
    private final int power;
    private final String[] shape;
    private final String[] shapeResult;
    private final Point origin;
    private final Map<Character, Slot> definition;
    private final Map<Character, Slot> definitionResult;

    public RecipeMultiblock(ResourceLocation id, ItemStack catalyst, int power, String[] shape, String[] shapeResult, Point origin, Map<Character, Slot> definition, Map<Character, Slot> definitionResult) {

        this.id = id;
        this.catalyst = catalyst;
        this.power = power;
        this.shape = shape;
        this.shapeResult = shapeResult;
        this.origin = origin;
        this.definition = definition;
        this.definition.put(' ', new Slot(Blocks.AIR.defaultBlockState()));
        this.definitionResult = definitionResult;
        this.definitionResult.put(' ', new Slot(Blocks.AIR.defaultBlockState()));

        char originChar = shape[origin.y].charAt(origin.x);
        if (originChar == ' ' || definition.get(originChar).test(Blocks.AIR.defaultBlockState()))
            throw new IllegalStateException(id + ": Origin point cannot be blank space");

        int lineLength = shape[0].length();
        for (String line : shape) {
            if (line.length() != lineLength)
                throw new IllegalStateException(id + ": All lines in the shape must be the same size");
            for (char letter : line.toCharArray())
                if (definition.get(letter) == null)
                    throw new IllegalStateException(id + ": " + letter + " is not defined");
        }
        for (String line2 : shapeResult) {
            if (line2.length() != lineLength)
                throw new IllegalStateException(id + ": All lines in the shape must be the same size");
            for (char letter : line2.toCharArray())
                if (definitionResult.get(letter) == null)
                    throw new IllegalStateException(id + ": " + letter + " is not defined");
        }
    }

    @Override
    public ResourceLocation getId() {

        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return UCRecipes.MULTIBLOCK_SERIALIZER.get();
    }

    @Override
    public boolean match(Level world, BlockPos originBlock) {

        Set<BlockPos> matched = Sets.newHashSet();
        for (int y = 0; y < shape.length; y++) {
            String line = shape[y];
            for (int x = 0; x < line.length(); x++) {
                BlockPos offset = originBlock.offset(x - origin.x, 0, y - origin.y);
                BlockState state = world.getBlockState(offset);
                if (!definition.get(line.charAt(x)).test(state))
                    return false;

                matched.add(offset);
            }
        }
        return true;
    }

    @Override
    public ItemStack getCatalyst() {

        return this.catalyst;
    }

    @Override
    public int getPower() {

        return this.power;
    }

    @Override
    public String[] getShape() {

        return this.shape;
    }

    @Override
    public Map<Character, Slot> getDefinition() {

        return this.definition;
    }

    @Override
    public void setResult(Level world, BlockPos originBlock) {

        for (int y = 0; y < shapeResult.length; y++) {
            String line = shapeResult[y];
            for (int x = 0; x < line.length(); x++) {
                BlockPos offset = originBlock.offset(x - origin.x, 0, y - origin.y);
                BlockState state = definitionResult.get(line.charAt(x)).getFirstState();
                world.levelEvent(2001, offset, Block.getId(state));
                world.setBlock(offset, state, 2);
            }
        }
    }

    @Override
    public boolean isOriginBlock(BlockState state) {

        Slot slot = definition.get(shape[origin.y].charAt(origin.x));
        return slot.test(state);
    }

    public static class Slot implements Predicate<BlockState> {

        @JsonAdapter(MultiblockProvider.SerializerBlockState.class)
        public final Set<BlockState> states;

        public Slot(BlockState... states) {

            this.states = Sets.newHashSet(states);
        }

        public Slot(Block block) {

            this(block.defaultBlockState());
//            this(block.getStateContainer().getValidStates().toArray(new BlockState[0]));
        }

        @Override
        public boolean test(BlockState state) {

            if (getFirstState().equals(state.getBlock().defaultBlockState()))
                return true;

            return states.contains(state);
        }

        public BlockState getFirstState() {

            Iterator iter = states.iterator();
            return (BlockState)iter.next();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<RecipeMultiblock> {

        @Override
        public RecipeMultiblock fromJson(ResourceLocation id, JsonObject obj) {

            ResourceLocation itemId = new ResourceLocation(obj.getAsJsonObject("catalyst").getAsJsonPrimitive("item").getAsString());
            int power = obj.getAsJsonObject("catalyst").getAsJsonPrimitive("power").getAsInt();
            ItemStack catalyst = new ItemStack(Registry.ITEM.get(itemId));

            String[] shape = UCUtils.convertJson(obj.getAsJsonArray("shape"));
            String[] shapeResult = UCUtils.convertJson(obj.getAsJsonArray("shaperesult"));
            JsonObject point = GsonHelper.getAsJsonObject(obj, "origin");
            Point origin = new Point(point.get("x").getAsInt(), point.get("y").getAsInt());
            JsonObject definition = obj.getAsJsonObject("definition");
            Map<Character, Slot> mapDefinition = new GsonBuilder().create().fromJson(definition, new TypeToken<Map<Character, Slot>>(){}.getType());
            JsonObject definitionResult = obj.getAsJsonObject("definitionresult");
            Map<Character, Slot> mapDefinitionResult = new GsonBuilder().create().fromJson(definitionResult, new TypeToken<Map<Character, Slot>>(){}.getType());

            return new RecipeMultiblock(id, catalyst, power, shape, shapeResult, origin, mapDefinition, mapDefinitionResult);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, RecipeMultiblock recipe) {

            buf.writeItem(recipe.catalyst);
            buf.writeVarInt(recipe.power);
            UCUtils.serializeArray(buf, recipe.shape);
            UCUtils.serializeArray(buf, recipe.shapeResult);
            buf.writeVarIntArray(new int[] { recipe.origin.x, recipe.origin.y });
            buf.writeNbt(UCUtils.serializeMap("definition", recipe.definition));
            buf.writeNbt(UCUtils.serializeMap("definitionresult", recipe.definitionResult));
        }

        @Nullable
        @Override
        public RecipeMultiblock fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {

            ItemStack catalyst = buf.readItem();
            int power = buf.readVarInt();
            String[] shape = UCUtils.deserializeString(buf);
            String[] shapeResult = UCUtils.deserializeString(buf);
            int[] origin = buf.readVarIntArray();
            Point point = new Point(origin[0], origin[1]);
            Map<Character, Slot> definition = UCUtils.deserializeMap("definition", buf.readNbt());
            Map<Character, Slot> definitionResult = UCUtils.deserializeMap("definitionresult", buf.readNbt());

            return new RecipeMultiblock(id, catalyst, power, shape, shapeResult, point, definition, definitionResult);
        }
    }
}
