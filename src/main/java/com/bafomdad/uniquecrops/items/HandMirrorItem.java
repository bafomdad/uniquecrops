package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class HandMirrorItem extends ItemBaseUC {

    public HandMirrorItem() {

        super(UCItems.unstackable());
        MinecraftForge.EVENT_BUS.addListener(this::reflectLazers);
    }

    private void reflectLazers(LivingAttackEvent event) {

        if (event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() instanceof GuardianEntity) {
            ItemStack mirror = event.getEntityLiving().getHeldItemOffhand();
            if ( mirror.getItem() == this) {
                float damage = event.getAmount();
                event.getSource().getTrueSource().attackEntityFrom(EntityDamageSource.MAGIC, damage);
                event.setCanceled(true);
                if (event.getEntityLiving() instanceof ServerPlayerEntity)
                mirror.attemptDamageItem(1, event.getEntityLiving().world.rand, (ServerPlayerEntity)event.getEntityLiving());
            }
        }
    }
}
