package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class MagnetItem extends ItemBaseUC implements IBookUpgradeable {

    public MagnetItem() {

        super(UCItems.defaultBuilder().stacksTo(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {

        if (entity instanceof PlayerEntity && !world.isClientSide && NBTUtils.getBoolean(stack, UCStrings.ITEM_ACTIVATED, false)) {
            PlayerEntity player = (PlayerEntity)entity;
            if (player.isCreative() || player.isSpectator()) return;

            if (!player.isCrouching()) {
                int range = 2 + Math.max(this.getLevel(stack), 0);
                List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(player.getX() - range, player.getY() - range, player.getZ() - range, player.getX() + range, player.getY() + range, player.getZ() + range));
                if (!items.isEmpty()) {
                    for (ItemEntity ei : items) {
                        if (ei.isAlive() && !ei.hasPickUpDelay())
                            ei.playerTouch(player);
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (hand == Hand.MAIN_HAND && !world.isClientSide() && player.isCrouching()) {
            NBTUtils.setBoolean(player.getMainHandItem(), UCStrings.ITEM_ACTIVATED, !NBTUtils.getBoolean(player.getMainHandItem(), UCStrings.ITEM_ACTIVATED, false));
            return ActionResult.success(player.getMainHandItem());
        }
        return ActionResult.pass(player.getItemInHand(hand));
    }

    @Override
    public Rarity getRarity(ItemStack stack) {

        return Rarity.RARE;
    }

    @Override
    public boolean isFoil(ItemStack stack) {

        return NBTUtils.getBoolean(stack, UCStrings.ITEM_ACTIVATED, false);
    }
}
