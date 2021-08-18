package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class DyedBonemealItem extends ItemBaseUC {

    @Override
    public ActionResultType useOn(ItemUseContext ctx) {

        ItemStack stack = ctx.getPlayer().getItemInHand(ctx.getHand());
        if (!ctx.getPlayer().mayUseItemAt(ctx.getClickedPos(), ctx.getClickedFace(), stack)) return ActionResultType.FAIL;

        if (BoneMealItem.applyBonemeal(stack, ctx.getLevel(), ctx.getClickedPos(), ctx.getPlayer())) {
            if (!ctx.getLevel().isClientSide)
                ctx.getLevel().levelEvent(2005, ctx.getClickedPos(), 0);
        }
        return ActionResultType.SUCCESS;
    }
}
