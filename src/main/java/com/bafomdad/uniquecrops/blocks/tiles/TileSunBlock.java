package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileSunBlock extends BaseTileUC implements ITickableTileEntity {

    public static final int MAX_POWER = 10;
    public int powerlevel;
    public boolean powered;

    public TileSunBlock() {

        super(UCTiles.SUNTILE.get());
    }

    @Override
    public void tick() {

        if (this.world.getRedstonePowerFromNeighbors(getPos()) > 0) {
            this.powerlevel = Math.min(this.powerlevel + 1, MAX_POWER);
            this.powered = true;
        } else {
            this.powerlevel = Math.max(this.powerlevel - 1, 0);
            this.powered = false;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {

        return TileEntity.INFINITE_EXTENT_AABB;
    }
}
