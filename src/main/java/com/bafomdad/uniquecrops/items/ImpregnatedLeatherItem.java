package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
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
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag whatisthis) {

        boolean flag = Screen.hasShiftDown();
        stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            if (flag)
                list.add(new StringTextComponent(TextFormatting.GREEN + "Crop Power: " + crop.getPower() + "/" + crop.getCapacity()));
        });
        if (!flag)
            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "<Press shift>"));
    }

    @Override
    public CompoundNBT getShareTag(ItemStack stack) {

        CompoundNBT tag = stack.hasTag() ? stack.getTag().copy() : new CompoundNBT();
        stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            tag.put(TEMP_CAP, CPProvider.CROP_POWER.writeNBT(crop, null));
        });
        return tag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {

        if (nbt != null) {
            stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
                if (nbt.contains(TEMP_CAP, 10)) {
                    CPProvider.CROP_POWER.readNBT(crop, null, nbt.getCompound(TEMP_CAP));
                    nbt.remove(TEMP_CAP);
                }
            });
        }
        super.readShareTag(stack, nbt);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {

        if (!(entity instanceof PlayerEntity)) return;

        stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            if (!world.isRemote && crop.getPower() >= crop.getCapacity()) {
                stack.shrink(1);
                ItemHandlerHelper.giveItemToPlayer((PlayerEntity)entity, new ItemStack(UCItems.ENCHANTED_LEATHER.get()));
            }
        });
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return !ItemStack.areItemsEqual(oldStack, newStack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {

        if (CPProvider.CROP_POWER == null)
            return null;

        return new CPProvider(MAX_CAPACITY, true);
    }
}
