package com.bafomdad.uniquecrops.blocks.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileSunBlock extends TileEntity implements ITickable {
	
	public int powerlevel;
	public static int MAX_POWER = 10;
	public boolean powered;
	
	@Override
	public double getMaxRenderDistanceSquared() {
		
		return 65536.0D;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	public boolean shouldRenderInPass(int pass) {
	
		return pass == 1;
	}

	@Override
	public void update() {
		
		if (this.world.getRedstonePowerFromNeighbors(getPos()) > 0) {
			this.powerlevel = Math.min(this.powerlevel + 1, MAX_POWER);
			this.powered = true;
		} else {
			this.powerlevel = Math.max(this.powerlevel - 1, 0);
			this.powered = false;
		}
	}
}
