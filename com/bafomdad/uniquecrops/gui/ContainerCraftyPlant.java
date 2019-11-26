package com.bafomdad.uniquecrops.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerCraftyPlant extends ContainerWorkbench {

	public ContainerCraftyPlant(InventoryPlayer player, World world) {
		
		super(player, world, BlockPos.ORIGIN);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		
		return true;
	}
}
