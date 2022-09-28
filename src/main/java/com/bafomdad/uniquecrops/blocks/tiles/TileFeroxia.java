package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class TileFeroxia extends BaseTileUC {

    private UUID owner;

    public TileFeroxia(BlockPos pos, BlockState state) {

        super(UCTiles.FEROXIA.get(), pos, state);
    }

    public void writeCustomNBT(CompoundTag tag) {

        if (owner != null)
            tag.putString("UC_Own", owner.toString());
    }

    public void readCustomNBT(CompoundTag tag) {

        this.owner = null;
        if (tag.contains("UC_Own"))
            owner = UUID.fromString(tag.getString("UC_Own"));
    }

    public UUID getOwner() {

        return owner;
    }

    public void setOwner(UUID owner) {

        if ((this.owner != null && !this.owner.equals(owner)) || (owner != null && !owner.equals(this.owner))) {

            this.owner = owner;
            if (level != null && !level.isClientSide)
                setChanged();
        }
    }
}
