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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == this) {
            if (!player.isCreative())
                stack.shrink(1);
            if (!world.isRemote) {
                WeepingEyeEntity eye = new WeepingEyeEntity(player);
                eye.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0F, 1.5F, 1F);
                eye.setMotion(eye.getMotion().scale(1.6));
                world.addEntity(eye);
            }
            return ActionResult.resultSuccess(stack);
        }
        return ActionResult.resultPass(stack);
    }
}
