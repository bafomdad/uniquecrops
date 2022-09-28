package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class EmblemDefense extends ItemCurioUC {

    public EmblemDefense() {

        MinecraftForge.EVENT_BUS.addListener(this::autoShield);
    }

    private void autoShield(LivingAttackEvent event) {

        if (!(event.getEntityLiving() instanceof ServerPlayer)) return;
        if (!(event.getSource().getDirectEntity() instanceof Arrow)) return;
        if (!this.hasCurio(event.getEntityLiving())) return;

        ItemStack shield = event.getEntityLiving().getOffhandItem();
        if (!(shield.getItem() instanceof ShieldItem)) return;

        shield.hurt(1, event.getEntityLiving().level.random, (ServerPlayer)event.getEntityLiving());
        event.setCanceled(true);
    }
}
