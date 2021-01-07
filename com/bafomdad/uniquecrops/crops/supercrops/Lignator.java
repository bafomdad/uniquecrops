package com.bafomdad.uniquecrops.crops.supercrops;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockSuperCropsBase;
import com.bafomdad.uniquecrops.core.enums.EnumSuperCrops;

public class Lignator extends BlockSuperCropsBase {
	
	public static final PropertyBool DIAGONAL = PropertyBool.create("diagonal");
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);

	public Lignator() {
		
		super(EnumSuperCrops.LIGNATOR);
		setTickRandomly(true);
		setDefaultState(blockState.getBaseState().withProperty(DIAGONAL, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, DIAGONAL);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		return getDefaultState().withProperty(DIAGONAL, meta == 1 ? true : false);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		return state.getValue(DIAGONAL) ? 1 : 0;
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		
		return AABB;
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		
		return AABB;
	}
	
	@Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
		
		if (!(entity instanceof EntityItem))
			entity.attackEntityFrom(DamageSource.CACTUS, 2.0F);
    }
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (world.isRemote) return;
		
		if (world.getBlockState(pos.down()).getBlock() != this) {
			for (MutableBlockPos loopPos : MutableBlockPos.getAllInBoxMutable(pos.add(-5, 0, -5), pos.add(5, 0, 5))) {
				if (world.getBlockState(loopPos).getBlock() instanceof BlockLog) {
					world.destroyBlock(loopPos.toImmutable(), true);
				}
				if (!pos.equals(loopPos) && world.getBlockState(loopPos).getBlock() == this)
					world.destroyBlock(loopPos.toImmutable(), false);
			}
		}
		if (world.getBlockState(pos.up()).getBlock() != this && (world.isAirBlock(pos.up()) || world.getBlockState(pos.up()).getMaterial() == Material.LEAVES)) {
			growAndHarvest(world, pos.up());
			world.scheduleUpdate(pos.up(), this, 200);
		}
	}
	
	private void growAndHarvest(World world, BlockPos pos) {
		
		boolean grow = false;
		for (MutableBlockPos loopPos : MutableBlockPos.getAllInBoxMutable(pos.add(-5, 0, -5), pos.add(5, 0, 5))) {
			if (world.getBlockState(loopPos).getBlock() instanceof BlockLog) {
				if (!grow) grow = true;
				world.destroyBlock(loopPos.toImmutable(), true);
			}
			if (!pos.equals(loopPos) && world.getBlockState(loopPos).getBlock() == this)
				world.destroyBlock(loopPos.toImmutable(), false);
		}
		if (grow) {
			world.setBlockState(pos, this.getDefaultState(), 3);
		}
		else if (!grow) {
			int i;
			for (i = 1; world.getBlockState(pos.down(i)).getBlock() == this; ++i) {
				;
			}
			world.destroyBlock(pos.down(i - 2), false);
			world.scheduleUpdate(pos.down(i - 1), this, 200);
		}
	}
	
	@Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		
		super.onBlockAdded(world, pos, state);
		if (!world.isRemote) {
			boolean modulo = (pos.getY() % 2 == 0) ? true : false;
			world.setBlockState(pos, getDefaultState().withProperty(DIAGONAL, modulo), 2);
		}
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		
		super.neighborChanged(state, world, pos, block, fromPos);
		this.checkAndDropBlock(world, fromPos, state);
	}
	
	@Override
	protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
		
		if (world.isAirBlock(pos.down())) {
			world.destroyBlock(pos, false);
		}
	}
}
