package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.gui.ContainerBarrel;
import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileBarrel extends BaseTileUC implements MenuProvider {

    final ItemStackHandler inv = new ItemStackHandler(100);

    public TileBarrel(BlockPos pos, BlockState state) {

        super(UCTiles.BARREL.get(), pos, state);
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

        tag.put("inventory", inv.serializeNBT());
    }

    @Override
    public void readCustomNBT(CompoundTag tag) {

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
    public Component getDisplayName() {

        return new TranslatableComponent("container.uniquecrops.abstractbarrel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerinv, Player player) {

        return new ContainerBarrel(windowId, playerinv, this);
    }
}
