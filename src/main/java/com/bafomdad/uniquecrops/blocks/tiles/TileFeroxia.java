package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

public class TileFeroxia extends BaseTileUC {

    private UUID owner;

    public TileFeroxia() {

        super(UCTiles.FEROXIA.get());
    }

    public void writeCustomNBT(CompoundNBT tag) {

        if (owner != null)
            tag.putString("UC_Own", owner.toString());
    }

    public void readCustomNBT(CompoundNBT tag) {

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
            if (world != null && !world.isRemote)
                markDirty();
        }
    }
}
