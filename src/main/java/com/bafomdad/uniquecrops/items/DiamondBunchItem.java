package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.items.ItemHandlerHelper;

public class DiamondBunchItem extends ItemBaseUC {

    private static final int MAX_DAMAGE = 5;

    public DiamondBunchItem() {

        super(UCItems.defaultBuilder().stacksTo(1));
        MinecraftForge.EVENT_BUS.addListener(this::onItemToss);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {

        return false;
    }

    private void onItemToss(ItemTossEvent event) {

        ItemEntity ei = event.getEntityItem();
        if (ei.getItem().getItem() == UCItems.DIAMONDS.get() && !event.getPlayer().isCreative()) {
            event.setCanceled(true);
            ItemStack eventStack = ei.getItem().copy();
            int damage = NBTUtils.getInt(eventStack, UCStrings.TAG_DIAMONDS, 0);
            NBTUtils.setInt(eventStack, UCStrings.TAG_DIAMONDS, damage + 1);
            if (!event.getPlayer().level.isClientSide) {
                int num = 1;
                if (NBTUtils.getInt(eventStack, UCStrings.TAG_DIAMONDS, 0) <= MAX_DAMAGE - 1)
                    ItemHandlerHelper.giveItemToPlayer(event.getPlayer(), eventStack);
                else
                    num = 2;

                ItemEntity diamonds = new ItemEntity(event.getPlayer().level, ei.getX() + 0.5, ei.getY() + 0.5, ei.getZ() + 0.5, new ItemStack(Items.DIAMOND, num));
                diamonds.setDefaultPickUpDelay();
                event.getPlayer().level.addFreshEntity(diamonds);
            }
        }
    }
}
