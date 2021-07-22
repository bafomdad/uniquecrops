package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.entities.DonkItemEntity;
import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;

import java.util.Random;

public class EmblemWeight extends ItemCurioUC {

    public EmblemWeight() {

        MinecraftForge.EVENT_BUS.addListener(this::onItemToss);
    }

    private void onItemToss(ItemTossEvent event) {

        if (event.getPlayer() == null) return;

        if (this.hasCurio(event.getPlayer())) {
            PlayerEntity player = event.getPlayer();
            DonkItemEntity ei = new DonkItemEntity(player.world, event.getEntityItem(), event.getEntityItem().getItem());
            ei.getPersistentData().putBoolean("UC:canDonk", true);
            Random rand = new Random();
            event.setCanceled(true);

            float f2 = 1.3F;
            double motionX = -MathHelper.sin(player.rotationYaw * 0.017453292F) * MathHelper.cos(player.rotationPitch * 0.017453292F) * f2;
            double motionY = -MathHelper.sin(player.rotationPitch * 0.017453292F) * f2 + 0.1F;
            double motionZ  = MathHelper.cos(player.rotationYaw * 0.017453292F) * MathHelper.cos(player.rotationPitch * 0.017453292F) * f2;
            float f3 = rand.nextFloat() * ((float)Math.PI * 2F);
            f2 = 0.02F * rand.nextFloat();
            motionX += Math.cos((double)f3) * (double)f2;
            motionY += (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F);
            motionZ += Math.sin((double)f3) * (double)f2;
            ei.setMotion(motionX, motionY, motionZ);

            if (!player.world.isRemote)
                player.world.addEntity(ei);
        }
    }
}
