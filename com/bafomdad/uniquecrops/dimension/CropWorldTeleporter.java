package com.bafomdad.uniquecrops.dimension;

import java.util.Iterator;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCDimension;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class CropWorldTeleporter implements ITeleporter {

	@Override
	public void placeEntity(World world, Entity entity, float yaw) {

		double dist = -1.0;
		int mX = 0, mY = 0, mZ = 0;
		int sX = MathHelper.floor(entity.posX);
		int sZ = MathHelper.floor(entity.posZ);
		BlockPos playerPos = new BlockPos(sX, 36, sZ);
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		boolean flag = world.provider.getDimension() == UCDimension.dimID;
		int range = 4;
		
		if (flag) {
			Iterator<BlockPos.MutableBlockPos> iterator = BlockPos.MutableBlockPos.getAllInBoxMutable(playerPos.add(-range, -range, -range), playerPos.add(range, range, range)).iterator();
			while (iterator.hasNext()) {
				if (!world.isAirBlock(pos.setPos(iterator.next()))) {
					mX = pos.getX();
					mY = pos.getY() + 1;
					mZ = pos.getZ();
					break;
				}
			}
			if (mX > 0 && mY > 0 && mZ > 0) {
				entity.motionY = 0;
				entity.fallDistance = 0;
				if (entity instanceof EntityPlayer)
					((EntityPlayer)entity).getActivePotionEffects().removeIf(p -> "potion.awkward".equals(p.getEffectName()));

				if (entity instanceof EntityPlayerMP) {
					((EntityPlayerMP)entity).connection.setPlayerLocation((double)mX + 0.5, (double)mY + 0.5, (double)mZ + 0.5, entity.rotationYaw, entity.rotationPitch);
				} else {
					entity.setLocationAndAngles((double)mX + 0.5, (double)mY + 0.5, (double)mZ + 0.5, entity.rotationYaw, entity.rotationPitch);
				}
			}
		}
		if (!flag) {
			BlockPos telePos = getLastTeleportPosition(entity);
			if (telePos.equals(BlockPos.ORIGIN)) return;
			
			mX = telePos.getX();
			mY = telePos.getY();
			mZ = telePos.getZ();
			
			entity.motionY = 0;
			entity.fallDistance = 0;
			if (entity instanceof EntityPlayerMP) {
				((EntityPlayerMP)entity).connection.setPlayerLocation((double)mX + 0.5, (double)mY + 0.5, (double)mZ + 0.5, entity.rotationYaw, entity.rotationPitch);
			} else {
				entity.setLocationAndAngles((double)mX + 0.5, (double)mY + 0.5, (double)mZ + 0.5, entity.rotationYaw, entity.rotationPitch);
			}
		}
	}
	
	private BlockPos getLastTeleportPosition(Entity entity) {
		
		NBTTagCompound data = entity.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if (data != null) {
			if (data.hasKey(UCStrings.LAST_POSITION)) {
				return BlockPos.fromLong(data.getLong(UCStrings.LAST_POSITION));
			}
		}
		return BlockPos.ORIGIN;
	}
	
	private boolean shouldTeleportInChunk(int chunkX, int chunkZ) {
		
		int xOffset = chunkX;
		int zOffset = chunkZ;
		
		int spacing = 4;
		int clusterSize = 1;
		int width = spacing + clusterSize;
		
		if (xOffset < 0)
			xOffset = -xOffset + clusterSize;
		if (zOffset < 0)
			zOffset = -zOffset + clusterSize;
		
		xOffset %= width;
		zOffset %= width;
		
		return xOffset < clusterSize && zOffset < clusterSize;
	}
}
