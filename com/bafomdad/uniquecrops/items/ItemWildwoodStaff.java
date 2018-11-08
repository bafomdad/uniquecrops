package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockStalk;
import com.bafomdad.uniquecrops.blocks.tiles.TileArtisia;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWildwoodStaff extends Item {

	public ItemWildwoodStaff() {
		
		setRegistryName("wildwood_staff");
		setTranslationKey(UniqueCrops.MOD_ID + ".wildwoodstaff");
		setCreativeTab(UniqueCrops.TAB);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if (player.isSneaking()) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileArtisia) {
				ItemStack stack = ((TileArtisia)tile).getItem();
				if (!stack.isEmpty() && stack.getItem() == Item.getItemFromBlock(Blocks.CRAFTING_TABLE)) {
					boolean foundGrid = true;
					for (int i = 0; i < TileArtisia.GRIDPOS.length; i++) {
						BlockPos loopPos = pos.add(TileArtisia.GRIDPOS[i]);
						Block block = world.getBlockState(loopPos).getBlock();
						if (block == UCBlocks.cropArtisia) continue;
						
						else {
							foundGrid = false;
							break;
						}
					}
					if (foundGrid && !world.isRemote) {
						world.removeTileEntity(pos);
						for (int j = 0; j < TileArtisia.GRIDPOS.length; j++) {
							BlockPos loopPos = pos.add(TileArtisia.GRIDPOS[j]);
							world.removeTileEntity(loopPos);
						}
						world.setBlockState(pos, UCBlocks.centerstalk.getDefaultState(), 2);
						world.setBlockState(pos.up(), UCBlocks.topstalk.getDefaultState(), 2);
						for (BlockStalk.EnumStalk stalk : BlockStalk.EnumStalk.values()) {
							BlockPos offset = stalk.getOffset(pos);
							world.setBlockState(offset, UCBlocks.stalk.getDefaultState().withProperty(BlockStalk.STALKS, BlockStalk.EnumStalk.values()[stalk.ordinal()]), 2);
						}
					}
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.PASS;
	}
}
