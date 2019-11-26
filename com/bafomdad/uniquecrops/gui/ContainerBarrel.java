package com.bafomdad.uniquecrops.gui;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.items.SlotItemHandler;

import com.bafomdad.uniquecrops.blocks.tiles.TileBarrel;

public class ContainerBarrel extends Container {
	
	TileBarrel tile;
	
	public ContainerBarrel(InventoryPlayer playerinv, TileBarrel tile) {
		
		this.tile = tile;
		int i;
		int j;

    	Random rand = new Random();
        for (i = 0; i < 2; ++i) {
            for (j = 0; j < 2; ++j) {
            	int z = rand.nextInt(tile.getInventory().getSlots());
                this.addSlotToContainer(new SlotItemHandler(tile.getInventory(), z, 72 + j * 18, 27 + i * 18));
            }
        }
        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j){
                this.addSlotToContainer(new Slot(playerinv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i)
            this.addSlotToContainer(new Slot(playerinv, i, 8 + i * 18, 142));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		
		return !(player instanceof FakePlayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		
		ItemStack stack = ItemStack.EMPTY;
		Slot slotto = inventorySlots.get(slot);
		
		if (slotto != null && slotto.getHasStack()) {
			ItemStack stackInSlot = slotto.getStack();
			stack = stackInSlot.copy();
			
			if (slot < 4) {
				if (!this.mergeItemStack(stackInSlot, 4, inventorySlots.size(), true))
					return ItemStack.EMPTY;
			} else {
				boolean b = false;
				for (int i = 0; i < tile.getInventory().getSlots(); i++) {
					if (this.getSlot(i).isItemValid(stackInSlot)) {
						if (this.mergeItemStack(stackInSlot, i, i + 1, false)) {
							b = true;
							break;
						}
					}
				}
				if (!b)
					return ItemStack.EMPTY;
			}
			if (stackInSlot.getCount() == 0)
				slotto.putStack(ItemStack.EMPTY);
			else
				slotto.onSlotChanged();
			
			if (stackInSlot.getCount() == stack.getCount())
				return ItemStack.EMPTY;
			
			slotto.onTake(player, stackInSlot);
		}
		return stack;
	}
}
