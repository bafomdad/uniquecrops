package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class DogResidueItem extends ItemBaseUC {

    public DogResidueItem() {

        super(UCItems.defaultBuilder().stacksTo(1));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (hand == Hand.MAIN_HAND) {
            boolean canFill = false;
            for (int i = 0; i < player.inventory.items.size(); i++) {
                ItemStack loopStack = player.inventory.items.get(i);
                if (loopStack.isEmpty()) {
                    player.inventory.items.set(i, new ItemStack(UCItems.DOGRESIDUE.get()));
                    canFill = true;
                }
            }
            if (!world.isClientSide) {
                if (canFill)
                    player.sendMessage(new StringTextComponent("The rest of your inventory filled up setValue dog residue."), player.getUUID());
                else
                    player.sendMessage(new StringTextComponent("You finished using it. An uneasy silence fills the room."), player.getUUID());
            }
            return ActionResult.success(player.getMainHandItem());
        }
        return ActionResult.pass(player.getItemInHand(hand));
    }
}
