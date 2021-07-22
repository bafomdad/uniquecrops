package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.gui.ContainerBarrel;
import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileBarrel extends BaseTileUC implements INamedContainerProvider {

    final ItemStackHandler inv = new ItemStackHandler(100);

    public TileBarrel() {

        super(UCTiles.BARREL.get());
    }

    @Override
    public void writeCustomNBT(CompoundNBT tag) {

        tag.put("inventory", inv.serializeNBT());
    }

    @Override
    public void readCustomNBT(CompoundNBT tag) {

        inv.deserializeNBT(tag.getCompound("inventory"));
    }

    public IItemHandler getInventory() {

        return this.inv;
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        return (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) ? LazyOptional.of(() -> this.inv).cast() : super.getCapability(cap, side);
    }

    @Override
    public ITextComponent getDisplayName() {

        return new TranslationTextComponent("container.uniquecrops.abstractbarrel");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerinv, PlayerEntity player) {

        return new ContainerBarrel(windowId, playerinv, this);
    }
}
