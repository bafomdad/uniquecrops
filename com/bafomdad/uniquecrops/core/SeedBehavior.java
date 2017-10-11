package com.bafomdad.uniquecrops.core;

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
		
		if (!world.isRemote && cropBlock instanceof BlockCropsBase && !((BlockCropsBase)cropBlock).canPlantCrop()) {
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
				if (cropBlock == UCBlocks.cropEula) {
					player.openGui(UniqueCrops.instance, 1, world, (int)player.posX, (int)player.posY, (int)player.posZ);
				}
				if (cropBlock == UCBlocks.cropAbstract) {
					player.renderBrokenItemStack(stack);
					if (!player.world.isRemote)
						setAbstractCropGrowth(player, true);
					return true;
				}
				if (cropBlock == UCBlocks.cropPetramia && pos.offset(side).getY() >= 10) {
					return false;
				}
				world.setBlockState(pos.offset(side), cropBlock.getDefaultState(), 3);
				return true;
			}
		}
		return false;
	}
	
	public static final String TAG_ABSTRACT = "UC_tagAbstractGrowth";
	
	public static void setAbstractCropGrowth(EntityPlayer player, boolean add) {
		
		NBTTagCompound tag = player.getEntityData();
		if (!tag.hasKey(TAG_ABSTRACT) && add) {
			tag.setInteger(TAG_ABSTRACT, 1);
			return;
		}
		if (add) {
			tag.setInteger(TAG_ABSTRACT, tag.getInteger(TAG_ABSTRACT) + 1);
		}
		else if (!add) {
			int value = tag.getInteger(TAG_ABSTRACT);
			if (player.world.rand.nextInt(5) == 0)
				tag.setInteger(TAG_ABSTRACT, value - 1);
		}
		if (tag.getInteger(TAG_ABSTRACT) <= 0)
			tag.removeTag(TAG_ABSTRACT);
	}
}
