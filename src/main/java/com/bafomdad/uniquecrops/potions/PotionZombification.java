package com.bafomdad.uniquecrops.potions;

import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.PotionEvent;

public class PotionZombification extends MobEffect {

    static final DamageSource ZOMBIFICATION = new DamageSource("zombification").bypassArmor().bypassInvul().bypassMagic();

    public PotionZombification() {

        super(MobEffectCategory.HARMFUL, 0x93C47D);
        MinecraftForge.EVENT_BUS.addListener(this::onPotionExpire);
        MinecraftForge.EVENT_BUS.addListener(this::onPotionRemove);
    }

    private void onPotionExpire(PotionEvent.PotionExpiryEvent event) {

        if (event.getPotionEffect() == null) return;
        if (event.getPotionEffect().getEffect() == this && event.getEntityLiving() instanceof Player player) {
            if (!player.getLevel().isClientSide()) {
                ZombieVillager zombie = EntityType.ZOMBIE_VILLAGER.create(player.getLevel());
                zombie.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
                player.getLevel().addFreshEntity(zombie);
                if (player.getLevel().getDifficulty() != Difficulty.PEACEFUL)
                    player.hurt(ZOMBIFICATION, Float.MAX_VALUE);
            }
        }
    }

    private void onPotionRemove(PotionEvent.PotionRemoveEvent event) {

        if (event.getPotionEffect() == null) return;

        if (event.getPotionEffect().getEffect() == this)
            event.setCanceled(true);
    }
}
