package com.bafomdad.uniquecrops.potions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PotionEnnui extends MobEffect {

    public PotionEnnui() {

        super(MobEffectCategory.NEUTRAL, 0xeef442);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerClickItem);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerJump);
    }

    private void onPlayerJump(LivingEvent.LivingJumpEvent event) {

        if (event.getEntityLiving().getEffect(this) != null)
            event.getEntityLiving().setDeltaMovement(event.getEntityLiving().getDeltaMovement().x, 0, event.getEntityLiving().getDeltaMovement().z);
    }

    private void onPlayerClickBlock(PlayerInteractEvent.RightClickBlock event) {

        if (event.getPlayer().getEffect(this) != null)
            event.setCanceled(true);
    }

    private void onPlayerClickItem(PlayerInteractEvent.RightClickItem event) {

        if (event.getPlayer().getEffect(this) != null)
            event.setCanceled(true);
    }
}
