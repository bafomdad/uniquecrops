package com.bafomdad.uniquecrops.data.recipes;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.StalkBlock;
import com.bafomdad.uniquecrops.blocks.supercrops.Weatherflesia;
import com.bafomdad.uniquecrops.core.enums.EnumDirectional;
import com.bafomdad.uniquecrops.crafting.RecipeMultiblock.Slot;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.google.common.collect.Sets;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.awt.*;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class MultiblockProvider extends RecipeProvider {

    public MultiblockProvider(DataGenerator gen) {

        super(gen);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        consumer.accept(create("craftyplant",
                UCItems.WILDWOOD_STAFF.get(),
                20,
                new String[] {
                        "AAA",
                        "A0A",
                        "AAA"
                },
                new String[] {
                        "HNP",
                        "WCE",
                        "DSG"
                },
                new Point(1, 1),
                new HashMap<Character, Slot>() {{
                    put('A', new Slot(UCBlocks.ARTISIA_CROP.get()));
                    put('0', new Slot(UCBlocks.ARTISIA_CROP.get()));
                }},
                new HashMap<Character, Slot>() {{
                    put('C', new Slot(UCBlocks.STALK.get().getDefaultState().with(StalkBlock.STALKS, EnumDirectional.DOWN)));
                    put('N', new Slot(UCBlocks.STALK.get().getDefaultState().with(StalkBlock.STALKS, EnumDirectional.NORTH)));
                    put('S', new Slot(UCBlocks.STALK.get().getDefaultState().with(StalkBlock.STALKS, EnumDirectional.SOUTH)));
                    put('W', new Slot(UCBlocks.STALK.get().getDefaultState().with(StalkBlock.STALKS, EnumDirectional.WEST)));
                    put('E', new Slot(UCBlocks.STALK.get().getDefaultState().with(StalkBlock.STALKS, EnumDirectional.EAST)));
                    put('H', new Slot(UCBlocks.STALK.get().getDefaultState().with(StalkBlock.STALKS, EnumDirectional.NORTHWEST)));
                    put('P', new Slot(UCBlocks.STALK.get().getDefaultState().with(StalkBlock.STALKS, EnumDirectional.NORTHEAST)));
                    put('D', new Slot(UCBlocks.STALK.get().getDefaultState().with(StalkBlock.STALKS, EnumDirectional.SOUTHWEST)));
                    put('G', new Slot(UCBlocks.STALK.get().getDefaultState().with(StalkBlock.STALKS, EnumDirectional.SOUTHEAST)));
                }}
                ));
        consumer.accept(create("fascino",
                UCItems.WILDWOOD_STAFF.get(),
                75,
                new String[] {
                        "L L",
                        " 0 ",
                        "L L"
                },
                new String[] {
                        "   ",
                        " D ",
                        "   "
                },
                new Point(1, 1),
                new HashMap<Character, Slot>() {{
                    put('0', new Slot(Blocks.ENCHANTING_TABLE));
                    put('L', new Slot(Blocks.LAPIS_BLOCK));
                }},
                new HashMap<Character, Slot>() {{
                    put('D', new Slot(UCBlocks.FASCINO.get()));
                }}
                ));
        consumer.accept(create("weatherflesia",
                UCItems.WILDWOOD_STAFF.get(),
                45,
                new String[] {
                        "TKT",
                        "K0K",
                        "TKT"
                },
                new String[] {
                        "HNP",
                        "WCE",
                        "DSG"
                },
                new Point(1, 1),
                new HashMap<Character, Slot>() {{
                    put('T', new Slot(UCBlocks.INDUSTRIA_CROP.get()));
                    put('K', new Slot(UCBlocks.KNOWLEDGE_CROP.get()));
                    put('0', new Slot(Blocks.BEACON));
                }},
                new HashMap<Character, Slot>() {{
                    put('C', new Slot(UCBlocks.WEATHERFLESIA.get().getDefaultState().with(Weatherflesia.RAFFLESIA, EnumDirectional.UP)));
                    put('N', new Slot(UCBlocks.WEATHERFLESIA.get().getDefaultState().with(Weatherflesia.RAFFLESIA, EnumDirectional.NORTH)));
                    put('S', new Slot(UCBlocks.WEATHERFLESIA.get().getDefaultState().with(Weatherflesia.RAFFLESIA, EnumDirectional.SOUTH)));
                    put('W', new Slot(UCBlocks.WEATHERFLESIA.get().getDefaultState().with(Weatherflesia.RAFFLESIA, EnumDirectional.WEST)));
                    put('E', new Slot(UCBlocks.WEATHERFLESIA.get().getDefaultState().with(Weatherflesia.RAFFLESIA, EnumDirectional.EAST)));
                    put('H', new Slot(UCBlocks.WEATHERFLESIA.get().getDefaultState().with(Weatherflesia.RAFFLESIA, EnumDirectional.NORTHWEST)));
                    put('P', new Slot(UCBlocks.WEATHERFLESIA.get().getDefaultState().with(Weatherflesia.RAFFLESIA, EnumDirectional.NORTHEAST)));
                    put('D', new Slot(UCBlocks.WEATHERFLESIA.get().getDefaultState().with(Weatherflesia.RAFFLESIA, EnumDirectional.SOUTHWEST)));
                    put('G', new Slot(UCBlocks.WEATHERFLESIA.get().getDefaultState().with(Weatherflesia.RAFFLESIA, EnumDirectional.SOUTHEAST)));
                }}
        ));
        consumer.accept(create("cropworldportal",
                UCItems.WILDWOOD_STAFF.get(),
                50,
                new String[] {
                        "CRRRC",
                        "R   R",
                        "R 0 R",
                        "R   R",
                        "CRRRC"
                },
                new String[] {
                        "CRRRC",
                        "RPPPR",
                        "RPPPR",
                        "RPPPR",
                        "CRRRC"
                },
                new Point(2, 2),
                new HashMap<Character, Slot>() {{
                    put('R', new Slot(UCBlocks.RUINEDBRICKS.get()));
                    put('C', new Slot(UCBlocks.RUINEDBRICKSCARVED.get()));
                    put('0', new Slot(Blocks.EMERALD_BLOCK));
                }},
                new HashMap<Character, Slot>() {{
                    put('R', new Slot(UCBlocks.RUINEDBRICKS.get()));
                    put('C', new Slot(UCBlocks.RUINEDBRICKSCARVED.get()));
                    put('P', new Slot(UCBlocks.CROP_PORTAL.get()));
                }}
        ));
        consumer.accept(create("lignator",
                UCItems.EMERADIC_DIAMOND.get(),
                0,
                new String[] {
                        " I ",
                        "I0I",
                        " I "
                },
                new String[] {
                        "   ",
                        " L ",
                        "   "
                },
                new Point(1, 1),
                new HashMap<Character, Slot>() {{
                    put('0', new Slot(Blocks.MELON));
                    put('I', new Slot(Blocks.IRON_BARS));
                }},
                new HashMap<Character, Slot>() {{
                    put('L', new Slot(UCBlocks.LIGNATOR.get()));
                }}
        ));
        consumer.accept(create("exedo",
                UCItems.WILDWOOD_STAFF.get(),
                40,
                new String[] {
                        " D ",
                        "D0D",
                        " D "
                },
                new String[] {
                        "   ",
                        " E ",
                        "   "
                },
                new Point(1, 1),
                new HashMap<Character, Slot>() {{
                    put('D', new Slot(UCBlocks.DEVILSNARE_CROP.get()));
                    put('0', new Slot(UCBlocks.DEVILSNARE_CROP.get()));
                    put('W', new Slot(UCBlocks.WEEPINGBELLS_CROP.get()));
                }},
                new HashMap<Character, Slot>() {{
                    put('E', new Slot(UCBlocks.EXEDO.get()));
                }}
        ));
        consumer.accept(create("cocito",
                UCItems.WILDWOOD_STAFF.get(),
                40,
                new String[] {
                        "MDM",
                        "D0D",
                        "MDM"
                },
                new String[] {
                        "   ",
                        " C ",
                        "   "
                },
                new Point(1, 1),
                new HashMap<Character, Slot>() {{
                    put('D', new Slot(UCBlocks.INDUSTRIA_CROP.get()));
                    put('M', new Slot(UCBlocks.MARYJANE_CROP.get()));
                    put('0', new Slot(UCBlocks.MARYJANE_CROP.get()));
                }},
                new HashMap<Character, Slot>() {{
                    put('C', new Slot(UCBlocks.COCITO.get()));
                }}
        ));
        consumer.accept(create("itero",
                UCItems.WILDWOOD_STAFF.get(),
                80,
                new String[] {
                        "PRRRP",
                        "R   R",
                        "R 0 R",
                        "R   R",
                        "PRRRP"
                },
                new String[] {
                        "P   P",
                        "     ",
                        "  I  ",
                        "     ",
                        "P   P"
                },
                new Point(2, 2),
                new HashMap<Character, Slot>() {{
                    put('P', new Slot(Blocks.STONE_PRESSURE_PLATE));
                    put('0', new Slot(UCBlocks.PIXELSIUS_CROP.get()));
                    put('R', new Slot(Blocks.REDSTONE_WIRE));
                }},
                new HashMap<Character, Slot>() {{
                    put('P', new Slot(Blocks.STONE_PRESSURE_PLATE));
                    put('I', new Slot(UCBlocks.ITERO.get()));
                }}
        ));
    }

    private static FinishedRecipe create(String name, Item item, int power, String[] shape, String[] shapeResult, Point origin, Map<Character, Slot> definition, Map<Character, Slot> definitionResult) {

        ResourceLocation id = new ResourceLocation(UniqueCrops.MOD_ID, "multiblocks/" + name);
        return new FinishedRecipe(id, new ItemStack(item), power, shape, shapeResult, origin, definition, definitionResult);
    }

    private static class FinishedRecipe implements IFinishedRecipe {

        private final ResourceLocation id;
        private final ItemStack catalyst;
        private final int power;
        private final String[] shape;
        private final String[] shapeResult;
        private final Point origin;
        private final Map<Character, Slot> definition;
        private final Map<Character, Slot> definitionResult;

        private FinishedRecipe(ResourceLocation id, ItemStack catalyst, int power, String[] shape, String[] shapeResult, Point origin, Map<Character, Slot> definition, Map<Character, Slot> definitionResult) {

            this.id = id;
            this.catalyst = catalyst;
            this.power = power;
            this.shape = shape;
            this.shapeResult = shapeResult;
            this.origin = origin;
            this.definition = definition;
            this.definition.put(' ', new Slot(Blocks.AIR.getDefaultState()));
            this.definitionResult = definitionResult;
            this.definitionResult.put(' ', new Slot(Blocks.AIR.getDefaultState()));

            char originChar = shape[origin.y].charAt(origin.x);
            if (originChar == ' ' || definition.get(originChar).test(Blocks.AIR.getDefaultState()))
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
        public void serialize(JsonObject json) {

            JsonObject cata = new JsonObject();
            ResourceLocation item = catalyst.getItem().getRegistryName();
            cata.addProperty("item", item.toString());
            cata.addProperty("power", power);
            json.add("catalyst", cata);
            JsonArray shape1 = new JsonArray();
            for (String s : shape)
                shape1.add(s);
            json.add("shape", shape1);
            JsonArray shape2 = new JsonArray();
            for (String s : shapeResult)
                shape2.add(s);
            json.add("shaperesult", shape2);
            JsonObject point = new JsonObject();
            point.addProperty("x", origin.x);
            point.addProperty("y", origin.y);
            json.add("origin", point);
            JsonObject defjson = new JsonObject();
            for (Map.Entry<Character, Slot> map1 : definition.entrySet())
                defjson.add(map1.getKey().toString(), new GsonBuilder().create().toJsonTree(map1.getValue(), new TypeToken<Slot>() {}.getType()));

            json.add("definition", defjson);
            JsonObject resultjson = new JsonObject();
            for (Map.Entry<Character, Slot> map2 : definitionResult.entrySet())
                resultjson.add(map2.getKey().toString(), new GsonBuilder().create().toJsonTree(map2.getValue(), new TypeToken<Slot>() {}.getType()));

            json.add("definitionresult", resultjson);
        }

        @Override
        public ResourceLocation getID() {

            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {

            return UCRecipes.MULTIBLOCK_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {

            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {

            return null;
        }
    }

    public static class SerializerBlockState implements JsonDeserializer<Set<BlockState>>, JsonSerializer<Set<BlockState>> {

        @Override
        public Set<BlockState> deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {

            Set<BlockState> states = Sets.newHashSet();
            for (JsonElement entry : element.getAsJsonArray()) {
                String state = entry.getAsJsonPrimitive().getAsString();
                if (state.contains("[")) {
                    String[] split = state.split("\\[");
                    split[1] = split[1].substring(0, split[1].lastIndexOf("]")); // Make sure brackets are removed from state

                    Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(split[0]));
                    if (block == Blocks.AIR)
                        return Collections.singleton(block.getDefaultState());

                    StateContainer blockState = block.getStateContainer();
                    BlockState returnState = block.getDefaultState();

                    // Force our values into the state
                    String[] stateValues = split[1].split(","); // Splits up each value
                    for (String value : stateValues) {
//                        String[] valueSplit = value.split("=");
                        String[] valueSplit = value.split("-"); // split with - instead of = because minecraft's GSON escapes html characters
                        Property property = blockState.getProperty(valueSplit[0]);
                        if (property != null)
                            returnState = returnState.with(property, (Comparable) property.parseValue(valueSplit[1]).get());
                    }
                    states.add(returnState);
                } else {
                    states.addAll(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(state)).getStateContainer().getValidStates());
                }
            }
            return states;
        }

        @Override
        public JsonElement serialize(Set<BlockState> src, Type type, JsonSerializationContext ctx) {

            JsonArray arr = new JsonArray();
            for (BlockState state : src)
                arr.add(BlockStateParser.toString(state).replace("=", "-"));

            return ctx.serialize(arr);
        }
    }
}
