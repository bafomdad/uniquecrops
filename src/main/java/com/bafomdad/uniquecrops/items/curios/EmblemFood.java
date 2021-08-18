package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

public class EmblemFood extends ItemCurioUC {

    public EmblemFood() {

        super(UCItems.unstackable().durability(50));
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {

        if (!entity.level.isClientSide && entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity playerMP = (ServerPlayerEntity) entity;
            int diff = 20 - playerMP.getFoodData().getFoodLevel();
            if (playerMP.getFoodData().needsFood() && diff >= 5) {
                playerMP.getFoodData().eat(6, 0.6F);
                stack.hurtAndBreak(1, playerMP, (player) -> {});
            }
        }
    }
}
