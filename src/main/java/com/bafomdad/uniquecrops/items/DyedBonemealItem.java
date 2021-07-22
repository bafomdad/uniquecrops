package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class DyedBonemealItem extends ItemBaseUC {

    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {

        ItemStack stack = ctx.getPlayer().getHeldItem(ctx.getHand());
        if (!ctx.getPlayer().canPlayerEdit(ctx.getPos(), ctx.getFace(), stack)) return ActionResultType.FAIL;

        if (BoneMealItem.applyBonemeal(stack, ctx.getWorld(), ctx.getPos(), ctx.getPlayer())) {
            if (!ctx.getWorld().isRemote)
                ctx.getWorld().playEvent(2005, ctx.getPos(), 0);
        }
        return ActionResultType.SUCCESS;
    }
}
