package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;

import java.util.List;

public class MagnetItem extends ItemBaseUC implements IBookUpgradeable {

    public MagnetItem() {

        super(UCItems.unstackable());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {

        if (entity instanceof Player && !world.isClientSide && NBTUtils.getBoolean(stack, UCStrings.ITEM_ACTIVATED, false)) {
            Player player = (Player)entity;
            if (player.isCreative() || player.isSpectator()) return;

            if (!player.isCrouching()) {
                int range = 2 + Math.max(this.getLevel(stack), 0);
                List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, new AABB(player.getX() - range, player.getY() - range, player.getZ() - range, player.getX() + range, player.getY() + range, player.getZ() + range));
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
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        if (hand == InteractionHand.MAIN_HAND && !world.isClientSide() && player.isCrouching()) {
            NBTUtils.setBoolean(player.getMainHandItem(), UCStrings.ITEM_ACTIVATED, !NBTUtils.getBoolean(player.getMainHandItem(), UCStrings.ITEM_ACTIVATED, false));
            return InteractionResultHolder.success(player.getMainHandItem());
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
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
