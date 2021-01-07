package com.bafomdad.uniquecrops.crops;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Eula extends BlockCropsBase {

	public Eula() {
		
		super(EnumCrops.EULA);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsEula;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public boolean canPlantCrop(World world, EntityPlayer player, EnumFacing side, BlockPos pos, ItemStack stack) {
		
		NBTTagCompound data = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if (data != null && !data.getBoolean(UCStrings.TAG_EULA)) {
			player.openGui(UniqueCrops.instance, 1, world, (int)player.posX, (int)player.posY, (int)player.posZ);
			if (!world.isRemote) {
				data.setBoolean(UCStrings.TAG_EULA, true);
				player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
			}
		}
		return super.canPlantCrop(world, player, side, pos, stack);
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return 23;
	}
}
