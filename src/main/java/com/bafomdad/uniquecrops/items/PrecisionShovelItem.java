package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.enums.TierItem;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import javax.annotation.Nullable;
import java.util.List;

public class PrecisionShovelItem extends ShovelItem implements IBookUpgradeable {

    private static final int RANGE = 5;

    public PrecisionShovelItem() {

        super(TierItem.PRECISION, 1.5F, -3.0F, UCItems.unstackable());
        MinecraftForge.EVENT_BUS.addListener(this::onBlockFall);
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

    public void onBlockFall(EntityJoinWorldEvent event) {

        if (event.getEntity() instanceof FallingBlockEntity) {
            Player player = event.getEntity().level.getNearestPlayer(event.getEntity(), RANGE);
            if (player != null && player.getMainHandItem().getItem() == this) {
                if (isMaxLevel(player.getMainHandItem()))
                    event.setCanceled(true);
            }
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {

        stack.enchant(Enchantments.SILK_TOUCH, 1);
    }
}
