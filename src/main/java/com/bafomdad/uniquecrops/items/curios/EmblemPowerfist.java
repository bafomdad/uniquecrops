package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EmblemPowerfist extends ItemCurioUC {

    public EmblemPowerfist() {

        MinecraftForge.EVENT_BUS.addListener(this::fistingSpeed);
    }

    private void fistingSpeed(PlayerEvent.BreakSpeed event) {

        if (hasCurio(event.getPlayer())) {
            ItemStack miningHand = event.getPlayer().getHeldItemMainhand();
            if (!miningHand.isEmpty()) return;

            if (event.getNewSpeed() < 8.0F)
                event.setNewSpeed(8.0F);
        }
    }
}
