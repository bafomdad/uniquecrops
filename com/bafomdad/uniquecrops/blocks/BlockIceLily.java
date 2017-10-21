package com.bafomdad.uniquecrops.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockIceLily extends BlockBush {

	protected static final AxisAlignedBB LILY_PAD_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.09375D, 0.9375D);
	
	public BlockIceLily() {
		
		super(Material.GRASS);
		setRegistryName("icelily");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".icelily");
		setSoundType(SoundType.SNOW);
		setCreativeTab(UniqueCrops.TAB);
		useNeighborBrightness = true;
		UCBlocks.blocks.add(this);
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
       
		return LILY_PAD_AABB;
    }

	@Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        
		return LILY_PAD_AABB;
    }
	
	@Override
    public boolean canSustainBush(IBlockState state) {
		
        return state.getBlock() == Blocks.WATER;
    }
	
	@Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
    	
		Material material = world.getBlockState(pos.down()).getMaterial();
		return (super.canPlaceBlockAt(world, pos)) && (material != null) && (material.isLiquid());
    }
	
	@Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
    	
		IBlockState watersource = world.getBlockState(pos.add(0, -1, 0));
		Material material = watersource.getMaterial();
		if (material != Material.WATER) return false;
		boolean flag = ((Integer)watersource.getValue(BlockLiquid.LEVEL)).intValue() == 0;
		
		return pos.getY() >= 0 && pos.getY() < 256 ? material == Material.WATER && flag : false;
    }
	
	@Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
    	
		if (world.isRemote)
			return;
		
		if (entity instanceof EntityItem) {
			EntityItem ei = (EntityItem)entity;
			if (!ei.isDead && ei.age >= 105)
				ei.age = 105;
		}
    }
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	if (rand.nextInt(2) == 0)
    		world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, pos.getX() + rand.nextFloat(), pos.getY(), pos.getZ() + rand.nextFloat(), 0.0D, 0.03D, 0.0D);
    }
    
    public static class ItemIceLily extends ItemBlock {
    	
		public ItemIceLily(Block block) {
			
			super(block);
		}
		
		@Override
	    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
			
	        ItemStack itemstack = player.getHeldItem(hand);
	        RayTraceResult raytraceresult = this.rayTrace(world, player, true);

	        if (raytraceresult == null)
	        {
	            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	        }
	        else
	        {
	            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
	            {
	                BlockPos blockpos = raytraceresult.getBlockPos();

	                if (!world.isBlockModifiable(player, blockpos) || !player.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack))
	                {
	                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
	                }
	                BlockPos blockpos1 = blockpos.up();
	                IBlockState iblockstate = world.getBlockState(blockpos);

	                if (iblockstate.getMaterial() == Material.WATER && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0 && world.isAirBlock(blockpos1))
	                {
	                    // special case for handling block placement with water lilies
	                    net.minecraftforge.common.util.BlockSnapshot blocksnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(world, blockpos1);
	                    world.setBlockState(blockpos1, UCBlocks.icelily.getDefaultState());
	                    if (net.minecraftforge.event.ForgeEventFactory.onPlayerBlockPlace(player, blocksnapshot, net.minecraft.util.EnumFacing.UP, hand).isCanceled())
	                    {
	                        blocksnapshot.restore(true, false);
	                        return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
	                    }

	                    world.setBlockState(blockpos1, UCBlocks.icelily.getDefaultState(), 11);

	                    if (player instanceof EntityPlayerMP)
	                    {
	                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, blockpos1, itemstack);
	                    }

	                    if (!player.capabilities.isCreativeMode)
	                    {
	                        itemstack.shrink(1);
	                    }
	                    player.addStat(StatList.getObjectUseStats(this));
	                    world.playSound(player, blockpos, SoundEvents.BLOCK_SNOW_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
	                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	                }
	            }
	            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
	        }
	    }
    }
}
