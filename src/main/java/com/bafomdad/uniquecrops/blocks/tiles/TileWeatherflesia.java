package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class TileWeatherflesia extends BaseTileUC {

    final ItemStackHandler inv = new ItemStackHandler(1);
    int biomeStrength = 0;

    public TileWeatherflesia() {

        super(UCTiles.WEATHERFLESIA.get());
    }

    @Override
    public void writeCustomNBT(CompoundNBT tag) {

        tag.putInt("UC:biomeStrength", this.biomeStrength);
        tag.put("inventory", inv.serializeNBT());
    }

    @Override
    public void readCustomNBT(CompoundNBT tag) {

        this.biomeStrength = tag.getInt("UC:biomeStrength");
        inv.deserializeNBT(tag.getCompound("inventory"));
    }

    public int getBiomeStrength() {

        return this.biomeStrength;
    }

    public void tickBiomeStrength() {

        if (this.biomeStrength < 100)
            this.biomeStrength++;
    }

    public ItemStack getBrush() {

        return inv.getStackInSlot(0);
    }

    public void setBrush(ItemStack stack) {

        inv.setStackInSlot(0, stack);
    }
}
