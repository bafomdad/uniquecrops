package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.blocks.GobletBlock;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

public class TileGoblet extends BaseTileUC {

    public UUID entityId;

    public TileGoblet() {

        super(UCTiles.GOBLET.get());
    }

    public void writeCustomNBT(CompoundNBT tag) {

        if (entityId != null)
            tag.putString(UCStrings.TAG_LOCK, entityId.toString());
        else if (entityId == null && tag.contains(UCStrings.TAG_LOCK))
            tag.remove(UCStrings.TAG_LOCK);
    }

    public void readCustomNBT(CompoundNBT tag) {

        if (tag.contains(UCStrings.TAG_LOCK))
            entityId = UUID.fromString(tag.getString(UCStrings.TAG_LOCK));
    }

    public void setTaglock(UUID uuid) {

        this.entityId = uuid;
    }

    public void eraseTaglock() {

        if (entityId != null) {
            entityId = null;
            level.setBlock(worldPosition, UCBlocks.GOBLET.get().defaultBlockState().setValue(GobletBlock.FILLED, false), 3);
        }
    }
}
