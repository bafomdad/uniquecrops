package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import com.google.common.collect.Lists;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.InterModComms;

import java.util.List;

public class EmblemScarab extends ItemCurioUC {

    private static final List<String> BLACKLIST = Lists.newArrayList();

    public EmblemScarab() {

        InterModComms.sendTo(UniqueCrops.MOD_ID, UCStrings.BLACKLIST_EFFECT, () -> "minecraft.effect.awkward");
        InterModComms.sendTo(UniqueCrops.MOD_ID, UCStrings.BLACKLIST_EFFECT, () -> "effect.uniquecrops.zombification" );
    }

    @SuppressWarnings("deprecation")
    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {

        if (entity instanceof Player player) {
            if (!player.getActiveEffects().isEmpty())
                player.getActiveEffects().removeIf(effect -> !BLACKLIST.contains(effect.getDescriptionId()));
        }
    }

    public static void blacklistPotionEffect(String effect) {

        BLACKLIST.add(effect);
    }
}
