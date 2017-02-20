package com.bafomdad.uniquecrops.core;

import java.lang.reflect.Field;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class SeedBehavior {

	public static boolean canPlantCrop(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, Block cropBlock) {
		
		if (side != EnumFacing.UP)
			return false;
		
		if (!world.isRemote && !checkCropConfig(cropBlock)) {
			UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.BARRIER, pos.getX(), pos.getY() + 2, pos.getZ(), 0));
			return false;
		}
		else if (player.canPlayerEdit(pos, side, stack) && player.canPlayerEdit(pos.offset(EnumFacing.UP), side, stack)) {
			IBlockState state = world.getBlockState(pos);
			if (state.getBlock().canSustainPlant(state, world, pos, EnumFacing.UP, (IPlantable)stack.getItem()) && world.isAirBlock(pos.offset(EnumFacing.UP))) {
				if (cropBlock == UCBlocks.cropCollis) {
					if (!world.provider.isSurfaceWorld() || pos.getY() <= 100)
						return false;
				}
				if (cropBlock == UCBlocks.cropCinderbella) {
					long time = world.getWorldTime() % 24000L;
					if (time >= 18000)
						return false;
					
					BlockPos pos1 = pos.add(0, 1, 0);
					int pumpkins = 0;
					for (EnumFacing facing : EnumFacing.HORIZONTALS) {
						IBlockState pumpkin = world.getBlockState(pos1.offset(facing));
						if (pumpkin.getBlock() != null && pumpkin.getBlock() == Blocks.PUMPKIN && !world.isRemote) {
							pumpkins++;
						}
					}
					if (pumpkins >= 4) {
						cropBlock.onBlockPlacedBy(world, pos.offset(side), cropBlock.getDefaultState(), player, stack);
						return true;
					}
				}
				if (cropBlock == UCBlocks.cropFeroxia) {
					cropBlock.onBlockPlacedBy(world, pos.offset(side), cropBlock.getDefaultState(), player, stack);
					return true;
				}
				world.setBlockState(pos.offset(side), cropBlock.getDefaultState(), 3);
				return true;
			}
		}
		return false;
	}
	
	private static boolean checkCropConfig(Block crop) {
		
		Field[] fields = UCConfig.class.getDeclaredFields();
		for (Field f : fields) {
			if (f.getName().equals(crop.getRegistryName().toString().substring(UniqueCrops.MOD_ID.length() + 1))) {
				try {
					if (!f.getBoolean(f))
						return false;
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return true;
	}
}
