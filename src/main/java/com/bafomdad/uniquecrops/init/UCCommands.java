package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class UCCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(Commands.literal("uniquecrops")
                .requires(s -> s.hasPermission(2))
                .then(Commands.literal("setlevel")
                        .then(Commands.argument("level", IntegerArgumentType.integer(0, 10))
                        .executes((ctx -> setLevel(ctx, IntegerArgumentType.getInteger(ctx, "level"))))))
        );
    }

    private static int setLevel(CommandContext<CommandSourceStack> ctx, int level) throws CommandSyntaxException {

        ServerPlayer player = ctx.getSource().getPlayerOrException();
        ItemStack heldItem = player.getMainHandItem();

        if (heldItem.getItem() instanceof IBookUpgradeable) {
            IBookUpgradeable upgradeable = (IBookUpgradeable)heldItem.getItem();
            level = Math.min(level, 10);
            upgradeable.setLevel(heldItem, level);
        }
        return Command.SINGLE_SUCCESS;
    }
}
