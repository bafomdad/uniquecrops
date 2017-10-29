package com.bafomdad.uniquecrops.blocks.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileBarrel extends TileBaseUC implements IInventory {

	ItemStackHandler inv = new ItemStackHandler(100);
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		inv.deserializeNBT(tag.getCompoundTag("inventory"));
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		tag.setTag("inventory", inv.serializeNBT());
	}

	@Override
	public String getName() {

		return "TileAbstractBarrel";
	}

	@Override
	public boolean hasCustomName() {

		return false;
	}

	@Override
	public int getSizeInventory() {

		return inv.getSlots();
	}

	@Override
	public boolean isEmpty() {

		for (int i = 0; i < inv.getSlots(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) return false;
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {

		return inv.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {

		ItemStack stack = inv.getStackInSlot(index);
		if (!stack.isEmpty()) {
			if (stack.getCount() <= count)
				inv.setStackInSlot(index, ItemStack.EMPTY);
			else {
				stack = stack.splitStack(count);
				if (stack.getCount() == 0)
					inv.setStackInSlot(index, ItemStack.EMPTY);
			}
		}
		return stack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {

		ItemStack stack = inv.getStackInSlot(index);
		inv.setStackInSlot(index, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

		inv.setStackInSlot(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {

		return !this.isInvalid() && this.getDistanceSq(player.posX, player.posY, player.posZ) < 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {

		return inv.getStackInSlot(index).isEmpty();
	}

	@Override
	public int getField(int id) {

		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {

		return 0;
	}

	@Override
	public void clear() {
		
		for (int i = 0; i < inv.getSlots(); i++)
			inv.setStackInSlot(i, ItemStack.EMPTY);
	}
}
