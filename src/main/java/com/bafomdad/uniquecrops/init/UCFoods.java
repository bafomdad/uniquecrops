package com.bafomdad.uniquecrops.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class UCFoods {

    public static final Food LARGE_PLUM = new Food.Builder().nutrition(1).saturationMod(0.6F).alwaysEat().effect(() -> new EffectInstance(Effects.LEVITATION, 40), 1F).build();
    public static final Food TERIYAKI = new Food.Builder().nutrition(16).saturationMod(1.0F).effect(() -> new EffectInstance(Effects.SATURATION, 400), 1F).build();
    public static final Food STEVE_HEART = new Food.Builder().nutrition(0).saturationMod(0F).alwaysEat().effect(() -> new EffectInstance(Effects.ABSORPTION, 1200, 4), 1F).build();
    public static final Food GOLDEN_BREAD = new Food.Builder().nutrition(4).saturationMod(0.3F).effect(() -> new EffectInstance(Effects.LUCK, 2400), 1F).build();
    public static final Food DIET_PILLS = new Food.Builder().nutrition(-4).saturationMod(0F).alwaysEat().build();
    public static final Food WAFFLE = new Food.Builder().nutrition(8).saturationMod(1.0F).alwaysEat().build();
    public static final Food YOGURT = new Food.Builder().nutrition(3).saturationMod(0.6F).build();
    public static final Food EGGNOG = new Food.Builder().nutrition(4).saturationMod(1.2F).alwaysEat().build();
    public static final Food IGNORANCE_POTION = new Food.Builder().nutrition(0).saturationMod(0F).alwaysEat().effect(() -> new EffectInstance(UCPotions.IGNORANCE.get(), 6000), 1F).build();
    public static final Food ENNUI_POTION = new Food.Builder().nutrition(0).saturationMod(0F).alwaysEat().effect(() -> new EffectInstance(UCPotions.ENNUI.get(), 600), 1F).build();
    public static final Food REVERSE_POTION = new Food.Builder().nutrition(0).saturationMod(0F).alwaysEat().effect(() -> new EffectInstance(UCPotions.REVERSE.get(), 1), 1F).build();

    public static final Food EDIBLE_NUGGET = new Food.Builder().nutrition(2).saturationMod(1.2F).build();
    public static final Food EDIBLE_INGOT = new Food.Builder().nutrition(10).saturationMod(0.65F).build();
    public static final Food EDIBLE_GEM = new Food.Builder().nutrition(14).saturationMod(0.65F).build();
    public static final Food EDIBLE_DIAMOND = new Food.Builder().nutrition(14).saturationMod(0.65F).effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE, 5000, 1), 1F).build();
    public static final Food EDIBLE_GOLD = new Food.Builder().nutrition(10).saturationMod(0.65F).effect(() -> new EffectInstance(Effects.LUCK, 5000, 1), 1F).build();
    public static final Food EDIBLE_EMERALD = new Food.Builder().nutrition(14).saturationMod(0.65F).effect(() -> new EffectInstance(Effects.NIGHT_VISION, 5000, 1), 1F).build();
}
