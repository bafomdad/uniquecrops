package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nullable;
import java.util.List;

public class GuideBookItem extends ItemBaseUC {

    public GuideBookItem() {

        super(UCItems.unstackable().rarity(Rarity.UNCOMMON));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag whatisthis) {

        list.add(getEdition().deepCopy().mergeStyle(TextFormatting.GOLD));
    }

    private ITextComponent getEdition() {

        return PatchouliAPI.get().getSubtitle(UCItems.BOOK_GUIDE.getId());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getHeldItemMainhand();

        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity sPlayer = (ServerPlayerEntity)player;
            PatchouliAPI.get().openBookGUI(sPlayer, UCItems.BOOK_GUIDE.getId());
        }
        return ActionResult.resultSuccess(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {

        if (!(entity instanceof PlayerEntity)) return;

        if (stack.getItem() == this && isSelected) {
            if (stack.hasTag() && stack.getTag().contains(UCStrings.TAG_GROWTHSTAGES)) return;
            if (world.isRemote) return;
            ListNBT tagList = UCUtils.getServerTaglist(entity.getUniqueID());
            if (tagList != null)
                stack.setTagInfo(UCStrings.TAG_GROWTHSTAGES, tagList);
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return !ItemStack.areItemsEqual(oldStack, newStack);
    }
}
