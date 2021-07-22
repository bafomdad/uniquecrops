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
        REVERSE_MAP.put(Effects.SPEED, Effects.SLOWNESS);
        REVERSE_MAP.put(Effects.REGENERATION, Effects.POISON);
        REVERSE_MAP.put(Effects.SATURATION, Effects.HUNGER);
        REVERSE_MAP.put(Effects.HASTE, Effects.MINING_FATIGUE);
        REVERSE_MAP.put(Effects.STRENGTH, Effects.WEAKNESS);
        REVERSE_MAP.put(Effects.ABSORPTION, Effects.WITHER);
        REVERSE_MAP.put(Effects.LUCK, Effects.UNLUCK);
        REVERSE_MAP.put(Effects.JUMP_BOOST, Effects.LEVITATION);
        REVERSE_MAP.put(Effects.FIRE_RESISTANCE, Effects.WATER_BREATHING);
        REVERSE_MAP.put(Effects.INVISIBILITY, Effects.GLOWING);
        REVERSE_MAP.put(Effects.HERO_OF_THE_VILLAGE, Effects.BAD_OMEN);
    }

    public static void reverseEffects(PlayerEntity player) {

        if (!player.getActivePotionEffects().isEmpty()) {
            for (EffectInstance eff : player.getActivePotionEffects()) {
                setReverseEffects(eff, player);
            }
        }
    }

    private static void setReverseEffects(EffectInstance eff, PlayerEntity player) {

        REVERSE_MAP.forEach((key, value) -> {
            if (key == eff.getPotion()) {
                player.addPotionEffect(new EffectInstance(value, eff.getDuration(), eff.getAmplifier()));
                player.removePotionEffect(eff.getPotion());
                return;
            }
            if (value == eff.getPotion()) {
                player.addPotionEffect(new EffectInstance(key, eff.getDuration(), eff.getAmplifier()));
                player.removePotionEffect(eff.getPotion());
            }
        });
//        if (REVERSE_MAP.containsKey(eff.getPotion())) {
//            player.addPotionEffect(new EffectInstance(REVERSE_MAP.get(eff.getPotion()), eff.getDuration(), eff.getAmplifier()));
//            player.removePotionEffect(eff.getPotion());
//            return;
//        }
//        if (REVERSE_MAP.containsValue(eff.getPotion())) {
//            player.addPotionEffect((new EffectInstance(REVERSE_MAP.get(eff.getPotion()), eff.getDuration(), eff.getAmplifier())));
//            player.removePotionEffect(eff.getPotion());
//        }
    }
}
