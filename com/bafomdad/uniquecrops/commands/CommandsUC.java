package com.bafomdad.uniquecrops.commands;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandsUC extends CommandTreeBase {
	
	public CommandsUC() {
		
		addSubcommand(new CommandSetLevel());
	}

	@Override
	public String getName() {

		return "uniquecrops";
	}

	@Override
	public String getUsage(ICommandSender sender) {

		return "commands.uniquecrops.help";
	}
}
