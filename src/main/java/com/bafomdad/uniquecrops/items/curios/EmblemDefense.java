package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class EmblemDefense extends ItemCurioUC {

    public EmblemDefense() {

        MinecraftForge.EVENT_BUS.addListener(this::autoShield);
    }

    private void autoShield(LivingAttackEvent event) {

        if (!(event.getEntityLiving() instanceof ServerPlayerEntity)) return;
        if (!(event.getSource().getDirectEntity() instanceof ArrowEntity)) return;
        if (!this.hasCurio(event.getEntityLiving())) return;

        ItemStack shield = event.getEntityLiving().getOffhandItem();
        if (!(shield.getItem() instanceof ShieldItem)) return;

        shield.hurt(1, event.getEntityLiving().level.random, (ServerPlayerEntity)event.getEntityLiving());
        event.setCanceled(true);
    }
}
