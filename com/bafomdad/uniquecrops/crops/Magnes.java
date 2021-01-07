package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.entities.EntityMovingCrop;
import com.bafomdad.uniquecrops.init.UCItems;

public class Magnes extends BlockCropsBase {
	
	public static PropertyBool POLARITY = PropertyBool.create("polarity");
	private static final int RANGE = 7;

	public Magnes() {
		
		super(EnumCrops.MAGNETS);
		setDefaultState(blockState.getBaseState().withProperty(getAgeProperty(), 0).withProperty(POLARITY, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, AGE, POLARITY);
	}
	
	public boolean isBlue(IBlockState state) {
		
		return state.getValue(POLARITY);
	}
	
	public boolean isOpposite(IBlockState state1, IBlockState state2) {
		
		return state1.getValue(POLARITY) != state2.getValue(POLARITY);
	}
	
	@Override
    public Item getSeed() {
       
		return UCItems.seedsMagnets;
    }

    @Override
    public Item getCrop() {
        
    	return Items.AIR;
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {

		return this.getDefaultState().withProperty(AGE, meta & 7).withProperty(POLARITY, (meta & 8) > 0);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {

		int i = state.getValue(AGE);
		if (isBlue(state))
			i |= 8;
		return i;
	}
	
	@Override
	public int getAge(IBlockState state) {
		
		return state.getValue(AGE);
	}
	
	@Override
    public boolean isMaxAge(IBlockState state) {
		
        return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
    }
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		ItemStack stack = player.getHeldItem(hand);
		if (!stack.isEmpty() && this.isMaxAge(state)) {
			int[] oreIDs = OreDictionary.getOreIDs(stack);
			for (int id : oreIDs) {
				if (OreDictionary.getOreName(id).equals("dyeBlue")) {
					if (isBlue(state)) continue;
					
					if (!player.capabilities.isCreativeMode)
						stack.shrink(1);
					world.setBlockState(pos, state.withProperty(POLARITY, true), 2);
					return true;
				}
				if (OreDictionary.getOreName(id).equals("dyeRed")) {
					if (!isBlue(state)) continue;
					
					if (!player.capabilities.isCreativeMode)
						stack.shrink(1);
					world.setBlockState(pos, state.withProperty(POLARITY, false), 2);
					return true;
				}
			}
		}
//		if (this.isMaxAge(state)) {
//			magnetize(world, pos, state);
//			return true;
//		}
		return false;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		super.updateTick(world, pos, state, rand);
		if (this.isMaxAge(state))
			magnetize(world, pos, state);
	}
	
	private void magnetize(World world, BlockPos pos, IBlockState state) {
		
		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
			for (int i = 0; i < RANGE; i++) {
				BlockPos loopPos = pos.offset(facing, i);
				IBlockState loopState = world.getBlockState(loopPos);
				if (loopState.getMaterial() != Material.AIR) {
					if (loopState.getBlock() == this && isOpposite(state, loopState) && this.isMaxAge(loopState) && i > 1) {
						spawnMovingCrop(world, pos, state, facing, i);
						spawnMovingCrop(world, loopPos, loopState, facing.getOpposite(), i);
						break;
					}
					else if (loopState.getBlock() != this)
						break;
				}
			}
		}
	}
	
	private void spawnMovingCrop(World world, BlockPos pos, IBlockState state, EnumFacing facing, int distance) {
		
		IBlockState savedState = state;
		EntityFallingBlock fallingBlock = new EntityFallingBlock(world, pos.getX(), pos.getY(), pos.getZ(), savedState);
		fallingBlock.setNoGravity(true);
		fallingBlock.fallTime = 10;
		fallingBlock.fallDistance = 10;
		EntityMovingCrop entity = new EntityMovingCrop(world, pos, facing, distance);
		if (!world.isRemote) {
			world.setBlockToAir(pos);
			world.spawnEntity(entity);
			world.spawnEntity(fallingBlock);
			fallingBlock.startRiding(entity);
		}
	}
}
