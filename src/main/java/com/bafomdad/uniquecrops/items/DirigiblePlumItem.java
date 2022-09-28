package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class DirigiblePlumItem extends ItemBaseUC {

    @Override
    public boolean hasCustomEntity(ItemStack stack) {

        return stack.getItem() == this;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {

        if (entity.getItem().getItem() == this) {
            double velY = 0;
            if (entity.tickCount > 40) {
                velY = 0.0625D;
                if (entity.getY() >= 256)
                    entity.discard();
            }
            entity.push(0, velY, 0);
            if (entity.tickCount % 10 == 0 && (!entity.isOnGround() && entity.verticalCollision))
                entity.discard();
        }
        return false;
    }
}
