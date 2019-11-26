package com.bafomdad.uniquecrops.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class GuiAbstractBook extends GuiScreen {
	
	public final EntityPlayer reader;
	public final ItemStack book;

	public final int WIDTH = 175;
	public final int HEIGHT = 228;
	
	public GuiAbstractBook(EntityPlayer player, ItemStack heldBook) {
		
		this.reader = player;
		this.book = heldBook;
	}
	
	public float getZLevel() {
		
		return zLevel;
	}
}
