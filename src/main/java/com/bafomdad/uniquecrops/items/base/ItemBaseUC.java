package com.bafomdad.uniquecrops.items.base;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBaseUC extends Item {

    public ItemBaseUC() {

        super(UCItems.defaultBuilder());
    }

    public ItemBaseUC(Properties prop) {

        super(prop);
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

    @Override
    public boolean isFoil(ItemStack stack) {

        if (stack.getItem() == UCItems.POTION_REVERSE.get() || stack.getItem() == UCItems.POTION_IGNORANCE.get() || stack.getItem() == UCItems.POTION_ENNUI.get())
            return true;

        return super.isFoil(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {

        if (entity instanceof PlayerEntity) {
            if (stack.hasContainerItem()) {
                ItemStack container = stack.getContainerItem();
                if (!((PlayerEntity)entity).isCreative()) {
                    ((PlayerEntity)entity).inventory.add(container);
                }
                return container;
            }
        }
        return super.finishUsingItem(stack, world, entity);
    }
}
