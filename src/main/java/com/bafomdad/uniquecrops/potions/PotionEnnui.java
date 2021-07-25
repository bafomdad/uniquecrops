package com.bafomdad.uniquecrops.potions;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PotionEnnui extends Effect {

    public PotionEnnui() {

        super(EffectType.NEUTRAL, 0xeef442);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerClickItem);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerJump);
    }

    private void onPlayerJump(LivingEvent.LivingJumpEvent event) {

        if (event.getEntityLiving().getActivePotionEffect(this) != null)
            event.getEntityLiving().setMotion(event.getEntityLiving().getMotion().x, 0, event.getEntityLiving().getMotion().z);
    }

    private void onPlayerClickBlock(PlayerInteractEvent.RightClickBlock event) {

        if (event.getPlayer().getActivePotionEffect(this) != null)
            event.setCanceled(true);
    }

    private void onPlayerClickItem(PlayerInteractEvent.RightClickItem event) {

        if (event.getPlayer().getActivePotionEffect(this) != null)
            event.setCanceled(true);
    }
}