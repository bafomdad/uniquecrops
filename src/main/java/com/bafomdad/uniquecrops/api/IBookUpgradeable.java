package com.bafomdad.uniquecrops.api;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public interface IBookUpgradeable {

    default int getLevel(ItemStack stack) {

        if (stack.hasTag() && stack.getTag().contains(UCStrings.TAG_UPGRADE))
            return NBTUtils.getInt(stack, UCStrings.TAG_UPGRADE, -1);

        return -1;
    }

    default void setLevel(ItemStack stack, int level) {

        NBTUtils.setInt(stack, UCStrings.TAG_UPGRADE, level);
    }

    default boolean isMaxLevel(ItemStack stack) {

        return getLevel(stack) >= 10;
    }
}
