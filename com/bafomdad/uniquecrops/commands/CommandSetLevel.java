package com.bafomdad.uniquecrops.commands;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class CommandSetLevel extends CommandBase {

	@Override
	public String getName() {

		return "setlevel";
	}

	@Override
	public String getUsage(ICommandSender sender) {

		return "commands.uniquecrops.usage.setlevel";
	}
	
	@Override
    public int getRequiredPermissionLevel() {
		
        return 2;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		if (args.length != 1)
			throw new WrongUsageException(getUsage(sender));
		
		EntityPlayer player = (EntityPlayer)sender;
		ItemStack heldItem = player.getHeldItemMainhand();
		
		if (heldItem.getItem() instanceof IBookUpgradeable) {
			IBookUpgradeable upgradeable = (IBookUpgradeable)heldItem.getItem();
			if (!player.world.isRemote) {
				int level = Math.min(Integer.parseInt(args[0]), 10);
				upgradeable.setLevel(heldItem, level);
			}
			
		} else throw new CommandException("commands.uniquecrops.error.not_upgradeable");
	}
}
