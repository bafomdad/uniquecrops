package com.bafomdad.uniquecrops.core.enums;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.BlockDoublePlant.EnumPlantType;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

import com.bafomdad.uniquecrops.core.UCUtils;

public enum EnumBonemealDye {

    WHITE(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.OXEYE_DAISY)),
    ORANGE(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.ORANGE_TULIP)),
    MAGENTA(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.ALLIUM)) {
    	@Override
    	public void growFlower(World world, BlockPos pos) {
    		if (world.rand.nextBoolean())
    			Blocks.DOUBLE_PLANT.placeAt(world, pos, EnumPlantType.SYRINGA, 2);
    		else
    			super.growFlower(world, pos);
    	}
    },
    LIGHT_BLUE(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.BLUE_ORCHID)),
    YELLOW(Blocks.YELLOW_FLOWER.getDefaultState().withProperty(Blocks.YELLOW_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.DANDELION)) {
    	@Override
    	public void growFlower(World world, BlockPos pos) {
    		if (world.rand.nextBoolean())
    			Blocks.DOUBLE_PLANT.placeAt(world, pos, EnumPlantType.SUNFLOWER, 2);
    		else
    			super.growFlower(world, pos);
    	}
    },
    LIME(Blocks.WATERLILY.getDefaultState()),
    PINK(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.PINK_TULIP)) {
    	@Override
    	public void growFlower(World world, BlockPos pos) {
    		if (world.rand.nextBoolean()) 
    			Blocks.DOUBLE_PLANT.placeAt(world, pos, EnumPlantType.PAEONIA, 2);
    		else
    			super.growFlower(world, pos);
    	}
    },
    GRAY,
    SILVER(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.HOUSTONIA),
    		Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.WHITE_TULIP)),
    CYAN,
    PURPLE,
    BLUE,
    BROWN,
    GREEN(Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.FERN)) {
    	@Override
    	public void growFlower(World world, BlockPos pos) {
    		if (world.rand.nextBoolean())
    			Blocks.DOUBLE_PLANT.placeAt(world, pos, EnumPlantType.FERN, 2);
    		else
    			super.growFlower(world, pos);
    	}
    },
    RED(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.POPPY)) {
    	@Override
    	public void growFlower(World world, BlockPos pos) {
    		if (world.rand.nextBoolean())
    			Blocks.DOUBLE_PLANT.placeAt(world, pos, EnumPlantType.ROSE, 2);
    		else
    			super.growFlower(world, pos);
    	}
    },
    BLACK;
    
    final IBlockState[] states;
    
    EnumBonemealDye(IBlockState... states) {
    	
    	this.states = states;
    }
    
    public Result grow(World world, BlockPos pos) {
    	
    	if (states == null || states.length <= 0) return Result.DENY;
    	
    	final int range = 3;
    	List<BlockPos> growthSpots = new ArrayList();
    	Iterable<BlockPos> posList = BlockPos.getAllInBox(pos.add(-range, -1, -range), pos.add(range, 1, range));
    	Iterator<BlockPos> posit = posList.iterator();
    	while (posit.hasNext()) {
    		BlockPos loopPos = posit.next();
    		if (loopPos.getY() < 255 && world.isAirBlock(loopPos) && world.isAirBlock(loopPos.up()) && world.getBlockState(loopPos.down()).getBlock() instanceof BlockGrass) {
    			growthSpots.add(loopPos);
    		}
    	}
    	int count = Math.min(growthSpots.size(), world.rand.nextBoolean() ? 3 : 4);
    	for (int i = 0; i < count; i++) {
    		BlockPos flowerPos = growthSpots.get(world.rand.nextInt(growthSpots.size()));
    		growthSpots.remove(flowerPos);
			growFlower(world, flowerPos);
    	}
    	return Result.ALLOW;
    }
    
    public void growFlower(World world, BlockPos pos) {

    	world.setBlockState(pos, UCUtils.selectRandom(world.rand, this.states), 2);
    }
}
