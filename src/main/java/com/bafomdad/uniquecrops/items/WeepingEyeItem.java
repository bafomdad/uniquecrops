package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.entities.WeepingEyeEntity;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public class WeepingEyeItem extends ItemBaseUC {

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == this) {
            if (!player.isCreative())
                stack.shrink(1);
            if (!world.isClientSide) {
                WeepingEyeEntity eye = new WeepingEyeEntity(player);
                eye.shootFromRotation(player, player.xRotO, player.yRotO, 0F, 1.5F, 1F);
                eye.setDeltaMovement(eye.getDeltaMovement().scale(1.6));
                world.addFreshEntity(eye);
            }
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.pass(stack);
    }
}
