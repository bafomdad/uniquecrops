package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;

public class EmblemLeaf extends ItemCurioUC {

    private static final String ARMORCOUNT = "UC:armorCount";

    @Override
    public void onEquip(String identifier, int index, LivingEntity entity, ItemStack stack) {

        int armorCount = 0;
        for (ItemStack armor : ((Player)entity).getInventory().armor) {
            if (armor.getItem() instanceof ArmorItem) {
                if (((ArmorItem)armor.getItem()).getMaterial() != ArmorMaterials.LEATHER)
                    armorCount++;
            }
        }
        NBTUtils.setInt(stack, ARMORCOUNT, armorCount);
        entity.getAttributes().addTransientAttributeModifiers(getEquippedAttributeModifiers(stack));
    }

    @Override
    public void onUnequip(String identifier, int index, LivingEntity entity, ItemStack stack) {

        entity.getAttributes().removeAttributeModifiers(getEquippedAttributeModifiers(stack));
    }

    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {

        int armorCount = NBTUtils.getInt(stack, ARMORCOUNT, 0);
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(getCurioUUID(stack), "Leaf Emblem", armorCount, AttributeModifier.Operation.ADDITION));
        return attributes;
    }
}
