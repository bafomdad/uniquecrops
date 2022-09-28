package com.bafomdad.uniquecrops.proxies;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.UUID;

public class CommonProxy {

    public Player getPlayer() { return null; }

    public Player getPlayerFromUUID(String uuid) {

        return ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(UUID.fromString(uuid));
    }

    public Item.Properties propertiesWithTEISR(Item.Properties props) {

        return props;
    }

    public void openCube() {}

    public void openBook() {}
}
