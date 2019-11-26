package com.bafomdad.uniquecrops.gui;

import com.bafomdad.uniquecrops.blocks.tiles.TileBarrel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (ID == 2 && tile instanceof TileBarrel)
			return new ContainerBarrel(player.inventory, (TileBarrel)tile);
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		
		if (ID == 0)
			return new GuiBookGuide(player, player.getHeldItemMainhand());
		if (ID == 1)
			return new GuiBookEula(player);
		if (ID == 2 && tile instanceof TileBarrel)
			return new GuiBarrel(player.inventory, (TileBarrel)tile);
		
		return null;
	}

}
