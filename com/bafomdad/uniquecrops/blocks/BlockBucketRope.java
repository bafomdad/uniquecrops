package com.bafomdad.uniquecrops.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

public class BlockBucketRope extends Block {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final AxisAlignedBB BUCKET_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.5D, 0.625D);

	public BlockBucketRope() {
		
		super(Material.ANVIL);
		setRegistryName("bucketrope");
		setTranslationKey(UniqueCrops.MOD_ID + ".bucketrope");
		setCreativeTab(UniqueCrops.TAB);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		UCBlocks.blocks.add(this);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, FACING);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		return getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		return state.getValue(FACING).getHorizontalIndex();
	}
	
	@Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
    	
    	 addCollisionBoxToList(pos, entityBox, collidingBoxes, BUCKET_AABB);
    }
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		
		return BUCKET_AABB;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		ItemStack bucket = player.getHeldItem(hand);
		if (bucket.getItem() == Items.BUCKET) {
			if (!world.isRemote) {
				BlockPos searchPos = pos.down();
				while (searchPos.getY() > 0) {
					searchPos = searchPos.down();
					if (!world.isAirBlock(searchPos)) break;
				}
				IBlockState loopState = world.getBlockState(searchPos);
				if (player.canPlayerEdit(searchPos, facing, bucket) && loopState.getMaterial().isLiquid()) {
					int l = loopState.getBlock().getMetaFromState(loopState);
					Fluid fluid = FluidRegistry.lookupFluidForBlock(loopState.getBlock());
					if (l == 0) {
						ItemStack filled = FluidUtil.getFilledBucket(new FluidStack(fluid, Fluid.BUCKET_VOLUME));
						if (!filled.isEmpty()) {
							if (!player.capabilities.isCreativeMode)
								player.setHeldItem(hand, filled);
							world.setBlockToAir(searchPos);
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase elb, ItemStack stack) {
		
		world.setBlockState(pos, state.withProperty(FACING, elb.getHorizontalFacing().getOpposite()), 2);
	}
	
	@Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
    	
		super.neighborChanged(state, world, pos, block, fromPos);
	}
	
	@Override
    public boolean isOpaqueCube(IBlockState state) {
		
        return false;
    }
	
	@Override
    public boolean isFullCube(IBlockState state) {
		
        return false;
    }
}
