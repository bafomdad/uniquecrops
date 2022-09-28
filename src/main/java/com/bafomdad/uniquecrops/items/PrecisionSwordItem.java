package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.enums.TierItem;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PrecisionSwordItem extends SwordItem implements IBookUpgradeable {

    public PrecisionSwordItem() {

        super(TierItem.PRECISION, 3, -2.4F, UCItems.unstackable());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flag) {

        if (stack.getItem() instanceof IBookUpgradeable) {
            if (((IBookUpgradeable)stack.getItem()).getLevel(stack) > -1)
                list.add(new TextComponent(ChatFormatting.GOLD + "+" + ((IBookUpgradeable)stack.getItem()).getLevel(stack)));
            else
                list.add(new TextComponent(ChatFormatting.GOLD + "Upgradeable"));
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        if (!target.level.isClientSide && target.invulnerableTime > 0) {
            if (isMaxLevel(stack))
                target.invulnerableTime = 0;
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {

        stack.enchant(Enchantments.MOB_LOOTING, 1);
    }
}
