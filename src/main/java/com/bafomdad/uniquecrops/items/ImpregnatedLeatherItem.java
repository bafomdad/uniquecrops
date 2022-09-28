package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.List;

public class ImpregnatedLeatherItem extends ItemBaseUC {

    private static final String TEMP_CAP = "cropCap";
    private static final int MAX_CAPACITY = 10;

    public ImpregnatedLeatherItem() {

        super(UCItems.unstackable().rarity(Rarity.UNCOMMON));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag whatisthis) {

        boolean flag = Screen.hasShiftDown();
        stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            if (flag)
                list.add(new TextComponent(ChatFormatting.GREEN + "Crop Power: " + crop.getPower() + "/" + crop.getCapacity()));
        });
        if (!flag)
            list.add(new TextComponent(ChatFormatting.LIGHT_PURPLE + "<Press shift>"));
    }

    @Override
    public CompoundTag getShareTag(ItemStack stack) {

        CompoundTag tag = stack.hasTag() ? stack.getTag().copy() : new CompoundTag();
        stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            tag.put(TEMP_CAP, crop.serializeNBT());
        });
        return tag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {

        if (nbt != null) {
            stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
                if (nbt.contains(TEMP_CAP, 10)) {
                    crop.deserializeNBT(nbt.getCompound(TEMP_CAP));
                    nbt.remove(TEMP_CAP);
                }
            });
        }
        super.readShareTag(stack, nbt);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {

        if (!(entity instanceof Player)) return;

        stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            if (!world.isClientSide && crop.getPower() >= crop.getCapacity()) {
                stack.shrink(1);
                ItemHandlerHelper.giveItemToPlayer((Player)entity, new ItemStack(UCItems.ENCHANTED_LEATHER.get()));
            }
        });
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return !ItemStack.isSame(oldStack, newStack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {

        if (CPProvider.CROP_POWER == null)
            return null;

        return new CPProvider(MAX_CAPACITY, true);
    }
}
