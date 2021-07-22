package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import com.google.common.collect.Lists;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.InterModComms;

import java.util.List;

public class EmblemScarab extends ItemCurioUC {

    private static final List<String> BLACKLIST = Lists.newArrayList();

    public EmblemScarab() {

        InterModComms.sendTo(UniqueCrops.MOD_ID, UCStrings.BLACKLIST_EFFECT, () -> "minecraft.effect.awkward");
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {

        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;
            if (!player.getActivePotionEffects().isEmpty())
                player.getActivePotionEffects().removeIf(effect -> !BLACKLIST.contains(effect.getEffectName()));
        }
    }

    public static void blacklistPotionEffect(String effect) {

        BLACKLIST.add(effect);
    }
}
