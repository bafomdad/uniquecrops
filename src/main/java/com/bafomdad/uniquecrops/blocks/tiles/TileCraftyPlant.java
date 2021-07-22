package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.gui.ContainerCraftyPlant;
import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileCraftyPlant extends BaseTileUC implements INamedContainerProvider {

    final ItemStackHandler inv = new ItemStackHandler(11);

    public TileCraftyPlant() {

        super(UCTiles.CRAFTYPLANT.get());
    }

    public IItemHandlerModifiable getCraftingInventory() {

        return this.inv;
    }

    public int getCraftingSize() {

        return 9;
    }

    public ItemStack getStaff() {

        return this.inv.getStackInSlot(10);
    }

    public ItemStack getResult() {

        return this.inv.getStackInSlot(9);
    }

    public void setResult(ItemStack toSet) {

        this.inv.setStackInSlot(9, toSet);
    }

    @Override
    public void writeCustomNBT(CompoundNBT tag) {

        tag.put("inventory", inv.serializeNBT());
    }

    @Override
    public void readCustomNBT(CompoundNBT tag) {

        inv.deserializeNBT(tag.getCompound("inventory"));
    }

    @Override
    public ITextComponent getDisplayName() {

        return new TranslationTextComponent("container.uniquecrops.craftyplant");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerinv, PlayerEntity player) {

        return new ContainerCraftyPlant(windowId, playerinv, this);
    }
}
