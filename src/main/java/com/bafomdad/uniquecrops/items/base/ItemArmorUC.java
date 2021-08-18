package com.bafomdad.uniquecrops.items.base;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemArmorUC extends ArmorItem {

    public ItemArmorUC(IArmorMaterial material, EquipmentSlotType slot) {

        super(material, slot, UCItems.defaultBuilder().stacksTo(1));
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
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {

        return String.format("%s:textures/models/armor/%s_layer_%s.png", UniqueCrops.MOD_ID, this.material.getName(), isUpper(slot) ? "1" : "2");
    }

    private boolean isUpper(EquipmentSlotType slot) {

        return slot == EquipmentSlotType.CHEST || slot == EquipmentSlotType.HEAD;
    }

    @Override
    public void onCraftedBy(ItemStack stack, World world, PlayerEntity player) {

        if (stack.getItem() == UCItems.CACTUS_BOOTS.get() || stack.getItem() == UCItems.CACTUS_CHESTPLATE.get() || stack.getItem() == UCItems.CACTUS_HELM.get() || stack.getItem() == UCItems.CACTUS_LEGGINGS.get())
            stack.enchant(Enchantments.THORNS, 1);
    }
}
