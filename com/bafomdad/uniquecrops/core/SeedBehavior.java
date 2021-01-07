package com.bafomdad.uniquecrops.core;

import java.util.function.BooleanSupplier;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.IPlantable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class SeedBehavior {

	public static boolean canPlantCrop(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, Block cropBlock) {
		
		if (side != EnumFacing.UP || stack.isEmpty())
			return false;

		if (!world.isRemote && cropBlock instanceof BlockCropsBase && !((BlockCropsBase)cropBlock).getType().configCanPlant()) {
			UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.BARRIER, pos.getX(), pos.getY() + 2, pos.getZ(), 0));
			return false;
		}
		else if (player.canPlayerEdit(pos, side, stack) && player.canPlayerEdit(pos.offset(EnumFacing.UP), side, stack)) {
			IBlockState state = world.getBlockState(pos);
			if (state.getBlock().canSustainPlant(state, world, pos, EnumFacing.UP, (IPlantable)stack.getItem()) && world.isAirBlock(pos.offset(EnumFacing.UP))) {
				
				BooleanSupplier bs = () -> ((BlockCropsBase)cropBlock).canPlantCrop(world, player, side, pos, stack);
				if (bs.getAsBoolean()) {
					world.setBlockState(pos.offset(side), cropBlock.getDefaultState(), 3);
					return true;
				}
			}
		}
		return false;
	}
	
	public static void setAbstractCropGrowth(EntityPlayer player, int num) {
		
		NBTTagCompound tag = player.getEntityData();
		if (!tag.hasKey(UCStrings.TAG_ABSTRACT)) {
			tag.setInteger(UCStrings.TAG_ABSTRACT, num);
			return;
		}
		tag.setInteger(UCStrings.TAG_ABSTRACT, tag.getInteger(UCStrings.TAG_ABSTRACT) + num);

		if (tag.getInteger(UCStrings.TAG_ABSTRACT) <= 0)
			tag.removeTag(UCStrings.TAG_ABSTRACT);
	}
	
	public static boolean canIgnoreGrowthRestrictions(World world) {
		
		return world.getSaveHandler().getWorldDirectory().getName().equals(UniqueCrops.MOD_NAME);
	}
}
