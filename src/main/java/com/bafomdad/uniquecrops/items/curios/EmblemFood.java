package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

public class EmblemFood extends ItemCurioUC {

    public EmblemFood() {

        super(UCItems.unstackable().maxDamage(50));
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {

        if (!entity.world.isRemote && entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity playerMP = (ServerPlayerEntity) entity;
            int diff = 20 - playerMP.getFoodStats().getFoodLevel();
            if (playerMP.getFoodStats().needFood() && diff >= 5) {
                playerMP.getFoodStats().addStats(6, 0.6F);
                stack.damageItem(1, playerMP, (player) -> { });
            }
        }
    }
}
