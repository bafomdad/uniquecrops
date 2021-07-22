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

        super(UCItems.defaultBuilder().maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        if (hand == Hand.MAIN_HAND) {
            boolean canFill = false;
            for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
                ItemStack loopStack = player.inventory.mainInventory.get(i);
                if (loopStack.isEmpty()) {
                    player.inventory.mainInventory.set(i, new ItemStack(UCItems.DOGRESIDUE.get()));
                    canFill = true;
                }
            }
            if (!world.isRemote) {
                if (canFill)
                    player.sendMessage(new StringTextComponent("The rest of your inventory filled up with dog residue."), player.getUniqueID());
                else
                    player.sendMessage(new StringTextComponent("You finished using it. An uneasy silence fills the room."), player.getUniqueID());
            }
            return ActionResult.resultSuccess(player.getHeldItemMainhand());
        }
        return ActionResult.resultPass(player.getHeldItem(hand));
    }
}
