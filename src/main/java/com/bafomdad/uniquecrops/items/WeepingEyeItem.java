package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.entities.WeepingEyeEntity;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class WeepingEyeItem extends ItemBaseUC {

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == this) {
            if (!player.isCreative())
                stack.shrink(1);
            if (!world.isClientSide) {
                WeepingEyeEntity eye = new WeepingEyeEntity(player);
                eye.shootFromRotation(player, player.xRot, player.yRot, 0F, 1.5F, 1F);
                eye.setDeltaMovement(eye.getDeltaMovement().scale(1.6));
                world.addFreshEntity(eye);
            }
            return ActionResult.success(stack);
        }
        return ActionResult.pass(stack);
    }
}
