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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        ItemStack book = player.getHeldItemMainhand();
        if (!world.isRemote) {
            EulaBookEntity bookEntity = new EulaBookEntity(player);
            bookEntity.func_234612_a_(player, player.rotationPitch, player.rotationYaw, -20.0F, 0.5F, 1.0F);
            world.addEntity(bookEntity);
        }
        if (!player.isCreative())
            book.shrink(1);

        return ActionResult.func_233538_a_(book, world.isRemote());
    }
}
