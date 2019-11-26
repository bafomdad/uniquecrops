package com.bafomdad.uniquecrops.blocks.tiles;

import java.util.UUID;

import com.bafomdad.uniquecrops.blocks.BlockGoblet;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileGoblet extends TileBaseUC {
	
	public UUID entityId;

	public void writeCustomNBT(NBTTagCompound tag) {
		
		if (entityId != null)
			tag.setString(UCStrings.TAG_LOCK, entityId.toString());
		else if (entityId == null && tag.hasKey(UCStrings.TAG_LOCK))
			tag.removeTag(UCStrings.TAG_LOCK);
	}
	
	public void readCustomNBT(NBTTagCompound tag) {
		
		if (tag.hasKey(UCStrings.TAG_LOCK))
			entityId = UUID.fromString(tag.getString(UCStrings.TAG_LOCK));
	}

	public void setTaglock(UUID uuid) {
		
		this.entityId = uuid;
	}
	
	public void eraseTaglock() {
		
		if (entityId != null) {
			entityId = null;
			world.setBlockState(pos, UCBlocks.goblet.getDefaultState().withProperty(BlockGoblet.FILLED, false), 3);
		}
	}
}
