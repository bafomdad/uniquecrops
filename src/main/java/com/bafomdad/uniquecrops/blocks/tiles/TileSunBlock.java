package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.init.UCTiles;
import com.bafomdad.uniquecrops.network.UCPacketDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileSunBlock extends BaseTileUC {

    public static final int MAX_POWER = 10;
    public int powerlevel;
    public boolean powered;

    public TileSunBlock(BlockPos pos, BlockState state) {

        super(UCTiles.SUNTILE.get(), pos, state);
    }

    public void tickServer() {

        if (level != null && level.getBestNeighborSignal(getBlockPos()) > 0) {
            this.powerlevel = Math.min(this.powerlevel + 1, MAX_POWER);
            this.powered = true;
        } else {
            this.powerlevel = Math.max(this.powerlevel - 1, 0);
            this.powered = false;
        }
        if (powerlevel != 0)
            UCPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }

    @OnlyIn(Dist.CLIENT)
    public AABB getRenderBoundingBox() {

        return BlockEntity.INFINITE_EXTENT_AABB;
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

        tag.putInt("UC_powerlevel", powerlevel);
        tag.putBoolean("UC_powered", powered);
    }

    @Override
    public void readCustomNBT(CompoundTag tag) {

        this.powerlevel = tag.getInt("UC_powerlevel");
        this.powered = tag.getBoolean("UC_powered");
    }
}
