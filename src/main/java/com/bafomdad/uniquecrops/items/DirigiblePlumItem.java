package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;

public class DirigiblePlumItem extends ItemBaseUC {

    @Override
    public boolean hasCustomEntity(ItemStack stack) {

        return stack.getItem() == this;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {

        if (entity.getItem().getItem() == this) {
            double velY = 0;
            if (entity.ticksExisted > 40) {
                velY = 0.0625D;
                if (entity.getPosY() >= 256)
                    entity.remove();
            }
            entity.addVelocity(0, velY, 0);
            if (entity.ticksExisted % 10 == 0 && (!entity.isOnGround() && entity.collidedVertically))
                entity.remove();
        }
        return false;
    }
}
