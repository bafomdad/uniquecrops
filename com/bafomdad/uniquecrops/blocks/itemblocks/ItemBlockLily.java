package com.bafomdad.uniquecrops.blocks.itemblocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemBlockLily extends ItemBlock {
	
	public ItemBlockLily(Block block) {
		
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
//                    world.setBlockState(blockpos1, UCBlocks.icelily.getDefaultState());
                    world.setBlockState(blockpos1, block.getDefaultState());
                    if (net.minecraftforge.event.ForgeEventFactory.onPlayerBlockPlace(player, blocksnapshot, net.minecraft.util.EnumFacing.UP, hand).isCanceled())
                    {
                        blocksnapshot.restore(true, false);
                        return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
                    }

//                    world.setBlockState(blockpos1, UCBlocks.icelily.getDefaultState(), 11);
                    world.setBlockState(blockpos1, block.getDefaultState(), 11);
                    
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
