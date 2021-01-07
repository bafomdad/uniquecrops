package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.SeedBehavior;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;

public class Abstract extends BlockCropsBase {

	public Abstract() {
		
		super(EnumCrops.ABSTRACT);
	}
	
	@Override
	public boolean canPlantCrop(World world, EntityPlayer player, EnumFacing side, BlockPos pos, ItemStack stack) {
		
		player.renderBrokenItemStack(stack);
		if (!player.world.isRemote)
			SeedBehavior.setAbstractCropGrowth(player, world.rand.nextInt(2) + 1);
		
		return false;
	}
}
