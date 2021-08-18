package com.bafomdad.uniquecrops.potions;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.InstantEffect;

public class PotionReverse extends InstantEffect {

    public PotionReverse() {

        super(EffectType.NEUTRAL, 0x845c28);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        if (!entity.level.isClientSide && entity instanceof PlayerEntity)
            PotionBehavior.reverseEffects((PlayerEntity)entity);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {

        return true;
    }
}
