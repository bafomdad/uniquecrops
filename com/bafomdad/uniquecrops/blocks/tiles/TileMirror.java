package com.bafomdad.uniquecrops.blocks.tiles;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.blocks.BlockMirror;
import com.bafomdad.uniquecrops.entities.EntityMirror;
import com.bafomdad.uniquecrops.init.UCBlocks;

public class TileMirror extends TileBaseUC {
	
	private EntityMirror mirror;

	@Nullable
	@SideOnly(Side.CLIENT)
	public EntityMirror getMirrorEntity() {
		
		if (this.mirror == null) {
			this.mirror = new EntityMirror(this.world, this.pos, getWorld().getBlockState(getPos()).getValue(BlockMirror.FACING));
			this.world.spawnEntity(this.mirror);
			if (!this.mirror.isEntityAlive())
				this.mirror = null;
		}
		return this.mirror;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		return this.getMirrorEntity() == null ? new AxisAlignedBB(this.getPos()) : new AxisAlignedBB(this.getPos()).expand(1, 2, 1);
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		
		this.handleUpdateTag(pkt.getNbtCompound());
	}
}
