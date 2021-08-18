package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.entities.EulaBookEntity;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EulaBookItem extends ItemBaseUC {

    public EulaBookItem() {

        super(UCItems.unstackable());
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack book = player.getMainHandItem();
        if (!world.isClientSide) {
            EulaBookEntity bookEntity = new EulaBookEntity(player);
            bookEntity.shootFromRotation(player, player.xRot, player.yRot, -20.0F, 0.5F, 1.0F);
            world.addFreshEntity(bookEntity);
        }
        if (!player.isCreative())
            book.shrink(1);

        return ActionResult.sidedSuccess(book, world.isClientSide());
    }
}
