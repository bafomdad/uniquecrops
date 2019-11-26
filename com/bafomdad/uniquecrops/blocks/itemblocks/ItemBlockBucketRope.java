package com.bafomdad.uniquecrops.blocks.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockBucketRope extends ItemBlock {

	public ItemBlockBucketRope(Block block) {
		
		super(block);
	}
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		boolean flag = (facing == player.getHorizontalFacing().rotateY()) || (facing == player.getHorizontalFacing().rotateY().getOpposite());
		
		if (flag && world.isSideSolid(pos, facing) && world.isSideSolid(pos.offset(facing, 2), facing.getOpposite())) {
			if (world.isAirBlock(pos.offset(facing)) && world.isAirBlock(pos.offset(facing).down())) {
				return super.onItemUse(player, world, pos.down(), hand, facing, hitX, hitY, hitZ);
			}
		}
		return EnumActionResult.PASS;
	}
}
