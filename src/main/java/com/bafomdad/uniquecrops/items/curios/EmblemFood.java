package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class EmblemFood extends ItemCurioUC {

    public EmblemFood() {

        super(UCItems.unstackable().durability(50));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void curioTick(String identifier, int index, LivingEntity entity, ItemStack stack) {

        if (!entity.level.isClientSide && entity instanceof ServerPlayer playerMP) {
            int diff = 20 - playerMP.getFoodData().getFoodLevel();
            if (playerMP.getFoodData().needsFood() && diff >= 3) {
                playerMP.getFoodData().eat(6, 0.6F);
                stack.hurtAndBreak(1, playerMP, (player) -> {});
            }
        }
    }
}
