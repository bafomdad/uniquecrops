package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import com.google.common.collect.HashMultimap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import com.google.common.collect.Multimap;
import net.minecraftforge.common.ForgeMod;

public class EmblemMelee extends ItemCurioUC {

    @Override
    public void onEquip(String identifier, int index, LivingEntity entity, ItemStack stack) {

        entity.getAttributes().addTransientAttributeModifiers(getEquippedAttributeModifiers(stack));
    }

    @Override
    public void onUnequip(String identifier, int index, LivingEntity entity, ItemStack stack) {

        entity.getAttributes().removeAttributeModifiers(getEquippedAttributeModifiers(stack));
    }

    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {

        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(Attributes.ATTACK_SPEED, new AttributeModifier(getCurioUUID(stack), "Melee Emblem", 1, AttributeModifier.Operation.ADDITION));
        return attributes;
    }
}
