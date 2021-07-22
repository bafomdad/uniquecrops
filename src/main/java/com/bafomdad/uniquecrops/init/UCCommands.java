package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

public class UCCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(Commands.literal("uniquecrops")
                .requires(s -> s.hasPermissionLevel(2))
                .then(Commands.literal("setlevel")
                        .then(Commands.argument("level", IntegerArgumentType.integer(0, 10))
                        .executes((ctx -> setLevel(ctx, IntegerArgumentType.getInteger(ctx, "level"))))))
        );
    }

    private static int setLevel(CommandContext<CommandSource> ctx, int level) throws CommandSyntaxException {

        ServerPlayerEntity player = ctx.getSource().asPlayer();
        ItemStack heldItem = player.getHeldItemMainhand();

        if (heldItem.getItem() instanceof IBookUpgradeable) {
            IBookUpgradeable upgradeable = (IBookUpgradeable)heldItem.getItem();
            level = Math.min(level, 10);
            upgradeable.setLevel(heldItem, level);
        }
        return Command.SINGLE_SUCCESS;
    }
}
