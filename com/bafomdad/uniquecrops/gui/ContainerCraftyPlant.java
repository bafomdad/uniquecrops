package com.bafomdad.uniquecrops.gui;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import akka.util.Collections;

import com.bafomdad.uniquecrops.UniqueCropsAPI;
import com.bafomdad.uniquecrops.blocks.tiles.TileCraftyPlant;
import com.bafomdad.uniquecrops.capabilities.CPCapability;
import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.bafomdad.uniquecrops.crafting.SeedRecipe;
import com.bafomdad.uniquecrops.init.UCItems;

public class ContainerCraftyPlant extends Container {
	
	private TileCraftyPlant tile;

	public ContainerCraftyPlant(EntityPlayer player, TileCraftyPlant tile) {
		
		this.tile = tile;
		
		addSlotToContainer(new SlotSeedCrafting(tile.getCraftingInventory(), 9, 124, 35));
		addSlotToContainer(new SlotSeedCrafting(tile.getCraftingInventory(), 10, 94, 17));
		
        for (int i = 0; i < 3; ++i) {
            for (int m = 0; m < 3; ++m)
                this.addSlotToContainer(new SlotSeedCrafting(tile.getCraftingInventory(), m + i * 3, 30 + m * 18, 17 + i * 18));
        }
		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 9; k++)
				addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
		}
		for (int l = 0; l < 9; l++)
			addSlotToContainer(new Slot(player.inventory, l, 8 + l * 18, 142));
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(i);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			int size = tile.getCraftingInventory().getSlots();

			if (i < size) {
				slot.onTake(player, stack1);
				if (!this.mergeItemStack(stack1, size, this.inventorySlots.size(), true))
					return ItemStack.EMPTY;
			} else {
				boolean b = false;
				for (int j = 0; j < size; j++) {
					if (this.getSlot(j).isItemValid(stack1)) {
						if (this.mergeItemStack(stack1, j, j + 1, false)) {
							b = true;
							break;
						}
					}
				}
				if (!b)
					return ItemStack.EMPTY;
			}
			if (stack1.getCount() == 0)
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			slot.onTake(player, stack1);
		}
		return stack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		
		return !(player instanceof FakePlayer);
	}
	
	private class SlotSeedCrafting extends SlotItemHandler {
		
		final int COST = 50;
		final int indexSlot;
		
		public SlotSeedCrafting(IItemHandler handler, int index, int xPosition, int yPosition) {
			
			super(handler, index, xPosition, yPosition);
			this.indexSlot = index;
		}
		
		@Override
		public void onSlotChanged() {
			
			IItemHandler handler = getItemHandler();
			List<ItemStack> stacks = IntStream.range(0, tile.getCraftingSize()).mapToObj(i -> handler.getStackInSlot(i)).collect(Collectors.toList());
			SeedRecipe recipe = UniqueCropsAPI.SEED_RECIPE_REGISTRY.findRecipe(stacks);
			ItemStack result = ItemStack.EMPTY;
			
			if (recipe != null) {
				result = recipe.getOutput().copy();
			}
			tile.setResult(result);
			tile.markDirty();
		}
		
		@Override
		public ItemStack onTake(EntityPlayer player, ItemStack stack) {
			
			if (indexSlot == tile.getCraftingSize()) {
				if (!stack.isEmpty()) {
					CPCapability cap = tile.getStaff().getCapability(CPProvider.CROP_POWER, null);
					if (cap != null && cap.getPower() >= COST) {
						cap.remove(COST);
					} else {
						IntStream.range(0, tile.getCraftingSize()).forEach(i -> {
							if (!getItemHandler().getStackInSlot(i).isEmpty())
								getItemHandler().getStackInSlot(i).shrink(1);
							});
					}
				}
			}
			return super.onTake(player, stack);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			
			if (indexSlot == 9)
				return false;
			if (indexSlot == 10)
				return stack.getItem() == UCItems.wildwoodStaff;
				
			return true;
		}
	}
}
