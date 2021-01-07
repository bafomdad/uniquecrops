package com.bafomdad.uniquecrops.gui;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.bafomdad.uniquecrops.blocks.tiles.TileBarrel;
import com.bafomdad.uniquecrops.blocks.tiles.TileCraftyPlant;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		switch (ID) {
			case 2: if (tile instanceof TileBarrel) return new ContainerBarrel(player.inventory, (TileBarrel)tile);
			case 4: if (tile instanceof TileCraftyPlant) return new ContainerCraftyPlant(player, (TileCraftyPlant)tile);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		
		switch (ID) {
			case 0: return new GuiBookGuide(player, player.getHeldItemMainhand());
			case 1: return new GuiBookEula(player);
			case 2: if (tile instanceof TileBarrel) return new GuiBarrel(player.inventory, (TileBarrel)tile);
			case 3: return new GuiBookMultiblocks(player, player.getHeldItemMainhand());
			case 4: if (tile instanceof TileCraftyPlant) return new GuiCraftyPlant(new ContainerCraftyPlant(player, (TileCraftyPlant)tile));
			case 5: return new GuiColorfulCubes();
		}	
		return null;
	}

}
