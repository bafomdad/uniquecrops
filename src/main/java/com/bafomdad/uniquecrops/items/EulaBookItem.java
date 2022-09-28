package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.entities.EulaBookEntity;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public class EulaBookItem extends ItemBaseUC {

    public EulaBookItem() {

        super(UCItems.unstackable());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack book = player.getMainHandItem();
        if (!world.isClientSide) {
            EulaBookEntity bookEntity = new EulaBookEntity(player);
            bookEntity.shootFromRotation(player, player.xRotO, player.yRotO, -20.0F, 0.5F, 1.0F);
            world.addFreshEntity(bookEntity);
        }
        if (!player.isCreative())
            book.shrink(1);

        return InteractionResultHolder.sidedSuccess(book, world.isClientSide());
    }
}
