package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.UUID;

public class VampiricOintmentItem extends ItemBaseUC {

    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {

        if (!(target instanceof PlayerEntity)) {
            if (!hasTaglock(stack) && !player.world.isRemote) {
                ItemStack newStack = new ItemStack(this);
                setTaglock(newStack, target);
                ItemHandlerHelper.giveItemToPlayer(player, newStack);
                stack.shrink(1);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    public boolean hasTaglock(ItemStack stack) {

        return stack.hasTag() && stack.getTag().contains(UCStrings.TAG_LOCK);
    }

    public void setTaglock(ItemStack stack, LivingEntity target) {

        UUID id = target.getUniqueID();
        NBTUtils.setString(stack, UCStrings.TAG_LOCK, id.toString());
    }
}
