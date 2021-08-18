package com.bafomdad.uniquecrops.proxies;

import com.bafomdad.uniquecrops.gui.GuiColorfulCube;
import com.bafomdad.uniquecrops.gui.GuiEulaBook;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.render.entity.TEISR;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy {

    @Override
    public PlayerEntity getPlayer() {

        return Minecraft.getInstance().player;
    }

    @Override
    public Item.Properties propertiesWithTEISR(Item.Properties props) {

        props.setISTER(() -> () -> new TEISR(UCBlocks.FASCINO.get()));
        return props;
    }

    @Override
    public void openCube() {

        Minecraft.getInstance().setScreen(new GuiColorfulCube());
    }

    @Override
    public void openBook() {

        Minecraft.getInstance().setScreen(new GuiEulaBook());
    }
}
