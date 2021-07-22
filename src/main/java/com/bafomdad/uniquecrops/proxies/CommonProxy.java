package com.bafomdad.uniquecrops.proxies;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.UUID;

public class CommonProxy {

    public PlayerEntity getPlayer() { return null; }

    public PlayerEntity getPlayerFromUUID(String uuid) {

        return ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUUID(UUID.fromString(uuid));
    }

    public Item.Properties propertiesWithTEISR(Item.Properties props) {

        return props;
    }

    public void openCube() {}

    public void openBook() {}
}
