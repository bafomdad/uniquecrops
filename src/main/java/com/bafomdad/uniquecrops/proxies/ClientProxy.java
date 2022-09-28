package com.bafomdad.uniquecrops.proxies;

import com.bafomdad.uniquecrops.gui.GuiColorfulCube;
import com.bafomdad.uniquecrops.gui.GuiEulaBook;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClientProxy extends CommonProxy {

    @OnlyIn(Dist.CLIENT)
    @Override
    public Player getPlayer() {

        return Minecraft.getInstance().player;
    }

    @Override
    public Item.Properties propertiesWithTEISR(Item.Properties props) {

        return props;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void openCube() {

        Minecraft.getInstance().setScreen(new GuiColorfulCube());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void openBook() {

        Minecraft.getInstance().setScreen(new GuiEulaBook());
    }
}
