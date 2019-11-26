package com.bafomdad.uniquecrops.core;

import java.awt.Point;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import com.google.common.collect.Sets;
import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;

@JsonAdapter(MultiblockPattern.Deserializer.class)
public class MultiblockPattern {
	
	public static final MultiblockPattern DEFAULT = new MultiblockPattern(
			new ItemStack(Items.DIAMOND),
			0,
			new String[] {
				"OQO",
				"QGQ",
				"OQO"
			},
			new String[] {
				"RRR",
				"RAR",
				"RRR"
			},
			new Point(1, 1),
			new HashMap<Character, Slot>() {{
				put('O', new Slot(Blocks.OBSIDIAN));
				put('Q', new Slot(Blocks.WHEAT.getBlockState().getBaseState().withProperty(BlockCrops.AGE, 7)));
				put('G', new Slot(Blocks.GLOWSTONE));
			}},
			new HashMap<Character, Slot>() {{
				put('R', new Slot(Blocks.RAIL));
				put('A', new Slot(Blocks.ACACIA_FENCE));
			}},
			"Example Pattern",
			"do.not.bother.translating.this"
	);

	private final ItemStack catalyst;
	private final String[] shape;
	private final Point origin;
	private final Map<Character, Slot> definition;
	
	// new definitions
	private final int power;
	private final String[] shapeResult;
	private final Map<Character, Slot> definitionResult;
	private String name, description;
	
	public MultiblockPattern(ItemStack catalyst, int power, String[] shape, String[] shapeResult, Point origin, Map<Character, Slot> definition, Map<Character, Slot> definitionResult, String patternName, String description) {
		
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
			throw new IllegalStateException("Origin point cannot be blank space");
		
		int lineLength = shape[0].length();
		for (String line : shape) {
			if (line.length() != lineLength)
				throw new IllegalStateException("All lines in the shape must be the same size");
			
			for (char letter : line.toCharArray())
				if (definition.get(letter) == null)
					throw new IllegalStateException(letter + " is not defined.");
		}
		for (String line2 : shapeResult) {
			if (line2.length() != lineLength)
				throw new IllegalStateException("All lines in the shape must be the same size");
			
			for (char letter : line2.toCharArray())
				if (definitionResult.get(letter) == null)
					throw new IllegalStateException(letter + " is not defined.");
		}
		this.name = patternName;
		this.description = description;
	}
	
	public String getName() {
		
		return name;
	}
	
	public String getDescription() {
		
		return description;
	}
	
	public ItemStack getCatalyst() {
		
		return catalyst;
	}
	
	public int getPower() {
		
		return power;
	}
	
	public String[] getShape() {
		
		return this.shape;
	}
	
	public String[] getShapeResult() {
		
		return this.shapeResult;
	}
	
	public Map<Character, Slot> getDefinition() {
		
		return this.definition;
	}
	
	public Map<Character, Slot> getDefinitionResult() {
		
		return this.definitionResult;
	}
	
	public ActionResult<Set<BlockPos>> match(World world, BlockPos originBlock) {
		
		Set<BlockPos> matched = Sets.newHashSet();
		for (int y = 0; y < shape.length; y++) {
			String line = shape[y];
			for (int x = 0; x < line.length(); x++) {
				BlockPos offset = originBlock.add(x - origin.x, 0, y - origin.y);
				IBlockState state = world.getBlockState(offset);
				if (!definition.get(line.charAt(x)).test(state))
					return ActionResult.newResult(EnumActionResult.FAIL, Collections.emptySet());

				matched.add(offset);
			}
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, matched);
	}
	
	public void setResult(World world, BlockPos originBlock) {
		
		for (int y = 0; y < shapeResult.length; y++) {
			String line = shapeResult[y];
			for (int x = 0; x < line.length(); x++) {
				BlockPos offset = originBlock.add(x - origin.x, 0, y - origin.y);
				IBlockState state = definitionResult.get(line.charAt(x)).getFirstState();
				world.playEvent(2001, offset, Block.getStateId(state));
				world.setBlockState(offset, state, 2);
			}
		}
	}
	
	public boolean isOriginBlock(IBlockState state) {
		
		Slot slot = definition.get(shape[origin.y].charAt(origin.x));
		return slot.test(state);
	}
	
	public Block getOriginBlock(IBlockState state, Map<Character, Slot> definition, String[] shape) {
		
		Slot slot = definition.get(shape[origin.y].charAt(origin.x));
		return slot.test(state) ? slot.getFirstState().getBlock() : Blocks.AIR;
	}
	
	public static class Slot implements Predicate<IBlockState> {
		
		@JsonAdapter(SerializerBlockState.class)
		private final Set<IBlockState> states;
		
		public Slot(IBlockState... states) {
			
			this.states = Sets.newHashSet(states);
		}
		
		public Slot(Block block) {
			
			this(block.getBlockState().getValidStates().toArray(new IBlockState[0]));
		}
		
		@Override
		public boolean test(IBlockState state) {

			return states.contains(state);
		}
		
		public IBlockState getFirstState() {
			
			Iterator iter = states.iterator();
			return (IBlockState)iter.next();
		}
	}
	
	public static class Deserializer implements JsonDeserializer<MultiblockPattern> {
		
		@Override
		public MultiblockPattern deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		
			JsonObject json = element.getAsJsonObject();
			
			ResourceLocation itemId = new ResourceLocation(json.getAsJsonObject("catalyst").getAsJsonPrimitive("item").getAsString());
			int meta = json.getAsJsonObject("catalyst").getAsJsonPrimitive("data").getAsInt();
			int power = json.getAsJsonObject("catalyst").getAsJsonPrimitive("power").getAsInt();
			ItemStack catalyst = new ItemStack(ForgeRegistries.ITEMS.getValue(itemId), 1, meta);
			
			String[] shape = ctx.deserialize(json.getAsJsonArray("shape"), String[].class);
			String[] shapeResult = ctx.deserialize(json.getAsJsonArray("shaperesult"), String[].class);
			Point origin = ctx.deserialize(json.getAsJsonObject("origin"), Point.class);
			Map<Character, Slot> definition = ctx.deserialize(json.getAsJsonObject("definition"), new TypeToken<Map<Character, Slot>>(){}.getType());
			Map<Character, Slot> definitionResult = ctx.deserialize(json.getAsJsonObject("definitionresult"), new TypeToken<Map<Character, Slot>>(){}.getType());
			
			String patternName = json.getAsJsonPrimitive("name").getAsString();
			String description = json.getAsJsonPrimitive("description").getAsString();
			
			return new MultiblockPattern(catalyst, power, shape, shapeResult, origin, definition, definitionResult, patternName, description);
		}
	}
	
	public static class SerializerBlockState implements JsonDeserializer<Set<IBlockState>> {
		
        @Override
        public Set<IBlockState> deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
           
        	Set<IBlockState> states = Sets.newHashSet();
            for (JsonElement entry : element.getAsJsonArray()) {
                String state = entry.getAsJsonPrimitive().getAsString();
                if (state.contains("[")) {
                    String[] split = state.split("\\[");
                    split[1] = split[1].substring(0, split[1].lastIndexOf("]")); // Make sure brackets are removed from state

                    Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(split[0]));
                    if (block == Blocks.AIR)
                        return Collections.singleton(block.getDefaultState());

                    BlockStateContainer blockState = block.getBlockState();
                    IBlockState returnState = blockState.getBaseState();

                    // Force our values into the state
                    String[] stateValues = split[1].split(","); // Splits up each value
                    for (String value : stateValues) {
                        String[] valueSplit = value.split("=");
                        IProperty property = blockState.getProperty(valueSplit[0]);
                        if (property != null)
                            returnState = returnState.withProperty(property, (Comparable) property.parseValue(valueSplit[1]).get());
                    }
                    states.add(returnState);
                } else {
                    states.addAll(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(state)).getBlockState().getValidStates());
                }
            }
            return states;
        }
	}
}
