package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class HandMirrorItem extends ItemBaseUC {

    public HandMirrorItem() {

        super(UCItems.unstackable());
        MinecraftForge.EVENT_BUS.addListener(this::reflectLazers);
    }

    private void reflectLazers(LivingAttackEvent event) {

        if (event.getEntityLiving() instanceof Player && event.getSource().getEntity() instanceof Guardian) {
            ItemStack mirror = event.getEntityLiving().getOffhandItem();
            if ( mirror.getItem() == this) {
                float damage = event.getAmount();
                event.getSource().getEntity().hurt(EntityDamageSource.MAGIC, damage);
                event.setCanceled(true);
                if (event.getEntityLiving() instanceof ServerPlayer)
                mirror.hurt(1, event.getEntityLiving().level.random, (ServerPlayer)event.getEntityLiving());
            }
        }
    }
}
