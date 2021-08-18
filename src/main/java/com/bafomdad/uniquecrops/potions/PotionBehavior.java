package com.bafomdad.uniquecrops.potions;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.IdentityHashMap;
import java.util.Map;

public class PotionBehavior {

    private static final Map<Effect, Effect> REVERSE_MAP = new IdentityHashMap<>();

    static {
        REVERSE_MAP.put(Effects.NIGHT_VISION, Effects.BLINDNESS);
        REVERSE_MAP.put(Effects.MOVEMENT_SPEED, Effects.MOVEMENT_SLOWDOWN);
        REVERSE_MAP.put(Effects.REGENERATION, Effects.POISON);
        REVERSE_MAP.put(Effects.SATURATION, Effects.HUNGER);
        REVERSE_MAP.put(Effects.DIG_SPEED, Effects.DIG_SLOWDOWN);
        REVERSE_MAP.put(Effects.DAMAGE_BOOST, Effects.WEAKNESS);
        REVERSE_MAP.put(Effects.ABSORPTION, Effects.WITHER);
        REVERSE_MAP.put(Effects.LUCK, Effects.UNLUCK);
        REVERSE_MAP.put(Effects.JUMP, Effects.LEVITATION);
        REVERSE_MAP.put(Effects.FIRE_RESISTANCE, Effects.WATER_BREATHING);
        REVERSE_MAP.put(Effects.INVISIBILITY, Effects.GLOWING);
        REVERSE_MAP.put(Effects.HERO_OF_THE_VILLAGE, Effects.BAD_OMEN);
    }

    public static void reverseEffects(PlayerEntity player) {

        if (!player.getActiveEffects().isEmpty()) {
            for (EffectInstance eff : player.getActiveEffects()) {
                setReverseEffects(eff, player);
            }
        }
    }

    private static void setReverseEffects(EffectInstance eff, PlayerEntity player) {

        REVERSE_MAP.forEach((key, value) -> {
            if (key == eff.getEffect()) {
                player.addEffect(new EffectInstance(value, eff.getDuration(), eff.getAmplifier()));
                player.removeEffect(eff.getEffect());
                return;
            }
            if (value == eff.getEffect()) {
                player.addEffect(new EffectInstance(key, eff.getDuration(), eff.getAmplifier()));
                player.removeEffect(eff.getEffect());
            }
        });
    }
}
