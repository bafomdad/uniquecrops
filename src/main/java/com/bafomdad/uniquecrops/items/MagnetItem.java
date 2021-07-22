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

        super(UCItems.defaultBuilder().maxStackSize(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {

        if (entity instanceof PlayerEntity && !world.isRemote && NBTUtils.getBoolean(stack, UCStrings.ITEM_ACTIVATED, false)) {
            PlayerEntity player = (PlayerEntity)entity;
            if (player.isCreative() || player.isSpectator()) return;

            if (!player.isSneaking()) {
                int range = 2 + Math.max(this.getLevel(stack), 0);
                List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(player.getPosX() - range, player.getPosY() - range, player.getPosZ() - range, player.getPosX() + range, player.getPosY() + range, player.getPosZ() + range));
                if (!items.isEmpty()) {
                    for (ItemEntity ei : items) {
                        if (ei.isAlive() && !ei.cannotPickup())
                            ei.onCollideWithPlayer(player);
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        if (hand == Hand.MAIN_HAND && !world.isRemote() && player.isSneaking()) {
            NBTUtils.setBoolean(player.getHeldItemMainhand(), UCStrings.ITEM_ACTIVATED, !NBTUtils.getBoolean(player.getHeldItemMainhand(), UCStrings.ITEM_ACTIVATED, false));
            return ActionResult.resultSuccess(player.getHeldItemMainhand());
        }
        return ActionResult.resultPass(player.getHeldItem(hand));
    }

    @Override
    public Rarity getRarity(ItemStack stack) {

        return Rarity.RARE;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {

        return NBTUtils.getBoolean(stack, UCStrings.ITEM_ACTIVATED, false);
    }
}
