package com.bafomdad.uniquecrops.potions;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.InstantenousMobEffect;

public class PotionReverse extends InstantenousMobEffect {

    public PotionReverse() {

        super(MobEffectCategory.NEUTRAL, 0x845c28);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        if (!entity.level.isClientSide && entity instanceof Player)
            PotionBehavior.reverseEffects((Player)entity);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {

        return true;
    }
}
