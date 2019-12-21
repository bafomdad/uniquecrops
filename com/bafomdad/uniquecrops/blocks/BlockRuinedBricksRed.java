package com.bafomdad.uniquecrops.blocks;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCDimension;

public class BlockRuinedBricksRed extends BlockBaseUC {

	public BlockRuinedBricksRed() {
		
		super("ruinedbricksred", Material.ROCK);
		setHardness(1.15F);
		setResistance(2000.0F);
	}
	
	@Override
    public EnumPushReaction getPushReaction(IBlockState state) {
		
        return EnumPushReaction.BLOCK;
    }
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {

		if (pos.getY() > 42 || pos.getY() < 35) return;
		
		System.out.println(pos);
		if (!world.isRemote && world.provider.getDimension() == 0 && entity instanceof EntityPlayer) {
			World cropworld = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(UCDimension.dimID);
			if (cropworld.getBlockState(pos).getBlock() == UCBlocks.ruinedBricksGhost) {
				world.playEvent(2004, pos, 0);
				cropworld.setBlockState(pos, state, 2);
			}
		}
	}
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
		
		if (pos.getY() > 42 || pos.getY() < 35) return;
		if (!world.isRemote && world.provider.getDimension() == 0) {
			World cropworld = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(UCDimension.dimID);
			if (cropworld.getBlockState(pos).getBlock() == this) {
				world.playEvent(2004, pos, 0);
				cropworld.setBlockState(pos, UCBlocks.ruinedBricksGhost.getDefaultState(), 2);
			}
		}
	}
}
