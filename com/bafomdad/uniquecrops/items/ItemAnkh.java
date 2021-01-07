package com.bafomdad.uniquecrops.items;

import java.util.ArrayList;
import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class ItemAnkh extends Item {
	
	private static final int[][] INV = new int[][] {
		{ 9, 10, 11, 12, 13, 14, 15, 16, 17 },
		{ 18, 19, 20, 21, 22, 23, 24, 25, 26 },
		{ 27, 28, 29, 30, 31, 32, 33, 34, 35 },
		{ 0, 1, 2, 3, 4, 5, 6, 7, 8 }
	};

	public ItemAnkh() {
		
		setRegistryName("ankh");
		setTranslationKey(UniqueCrops.MOD_ID + ".ankh");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(6);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
	
	public static List<Integer> getSurroundingSlots(int slotIndex) {
		
		List<Integer> slotList = new ArrayList();
		if (slotIndex > 35 || slotIndex < 0) return slotList;
		
		for (int i = 0; i < INV.length; i++) {
			for (int j = 0; j < INV[i].length; j++) {
				if (INV[i][j] == slotIndex) {
					for (int dx = (i > 0 ? -1 : 0); dx <= (i < (INV.length - 1) ? 1 : 0); ++dx) {
						for (int dy = (j > 0 ? -1 : 0); dy <= (j < (INV[i].length - 1) ? 1 : 0); ++dy) {
							if (dx != 0 || dy != 0)
								slotList.add(INV[i + dx][j + dy]);
						}
					}
				}
			}
		}
		slotList.add(slotIndex);
		return slotList;
	}
	
	public static void saveAnkhItems(EntityPlayer player) {
		
		NBTTagCompound playerTag = player.getEntityData();
		if (playerTag.hasKey(UCStrings.SAVED_ITEMS))
			playerTag.removeTag(UCStrings.SAVED_ITEMS);
		
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
			ItemStack ankh = player.inventory.mainInventory.get(i);
			if (!ankh.isEmpty() && ankh.getItem() == UCItems.ankh) {
				List<Integer> slots = getSurroundingSlots(i);
				if (!slots.isEmpty()) {
					for (int j = 0; j < slots.size(); j++) {
						int slot = slots.get(j);
						ItemStack stack = player.inventory.mainInventory.get(slot);
						if (!stack.isEmpty()) {
							NBTTagCompound tag = new NBTTagCompound();
							tag.setInteger("Slot", slot);
							stack.writeToNBT(tag);
							tagList.appendTag(tag);
							player.inventory.mainInventory.set(slot, ItemStack.EMPTY);
						}
					}
				}
				break;
			}
		}
		playerTag.setTag(UCStrings.SAVED_ITEMS, tagList);
	}
	
	public static void putAnkhItems(EntityPlayer oldPlayer, EntityPlayer newPlayer) {
		
		NBTTagCompound playerTag = oldPlayer.getEntityData();
		NBTTagList tagList = playerTag.getTagList(UCStrings.SAVED_ITEMS, Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			int slot = tag.getInteger("Slot");
			ItemStack newStack = new ItemStack(tag);
			if (newStack.getItem() == UCItems.ankh)
				newStack.damageItem(1, newPlayer);
			newPlayer.inventory.mainInventory.set(slot, newStack);
		}
	}
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    	
        return repair.getItem() == Items.FLINT;
    }
}
