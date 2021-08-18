package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.enums.TierItem;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import javax.annotation.Nullable;
import java.util.List;

public class PrecisionShovelItem extends ShovelItem implements IBookUpgradeable {

    private static final int RANGE = 5;

    public PrecisionShovelItem() {

        super(TierItem.PRECISION, 1.5F, -3.0F, UCItems.unstackable().addToolType(ToolType.SHOVEL, TierItem.PRECISION.getLevel()));
        MinecraftForge.EVENT_BUS.addListener(this::onBlockFall);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {

        if (stack.getItem() instanceof IBookUpgradeable) {
            if (((IBookUpgradeable)stack.getItem()).getLevel(stack) > -1)
                list.add(new StringTextComponent(TextFormatting.GOLD + "+" + ((IBookUpgradeable)stack.getItem()).getLevel(stack)));
            else
                list.add(new StringTextComponent(TextFormatting.GOLD + "Upgradeable"));
        }
    }

    public void onBlockFall(EntityJoinWorldEvent event) {

        if (event.getEntity() instanceof FallingBlockEntity) {
            PlayerEntity player = event.getEntity().level.getNearestPlayer(event.getEntity(), RANGE);
            if (player != null && player.getMainHandItem().getItem() == this) {
                if (isMaxLevel(player.getMainHandItem()))
                    event.setCanceled(true);
            }
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, World world, PlayerEntity player) {

        stack.enchant(Enchantments.SILK_TOUCH, 1);
    }
}
