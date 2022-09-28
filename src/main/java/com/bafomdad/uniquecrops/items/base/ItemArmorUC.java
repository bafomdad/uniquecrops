package com.bafomdad.uniquecrops.items.base;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ItemArmorUC extends ArmorItem {

    public ItemArmorUC(ArmorMaterial material, EquipmentSlot slot) {

        super(material, slot, UCItems.defaultBuilder().stacksTo(1));
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
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {

        return String.format("%s:textures/models/armor/%s_layer_%s.png", UniqueCrops.MOD_ID, this.material.getName(), isUpper(slot) ? "1" : "2");
    }

    private boolean isUpper(EquipmentSlot slot) {

        return slot == EquipmentSlot.CHEST || slot == EquipmentSlot.HEAD;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {

        if (stack.getItem() == UCItems.CACTUS_BOOTS.get() || stack.getItem() == UCItems.CACTUS_CHESTPLATE.get() || stack.getItem() == UCItems.CACTUS_HELM.get() || stack.getItem() == UCItems.CACTUS_LEGGINGS.get())
            stack.enchant(Enchantments.THORNS, 1);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {

        if (stack.getItem() == UCItems.GLASSES_3D.get() || stack.getItem() == UCItems.GLASSES_PIXELS.get() || stack.getItem() == UCItems.THUNDERPANTZ.get())
            return true;

        return super.makesPiglinsNeutral(stack, wearer);
    }
}
