package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.enums.EnumArmorMaterial;
import com.bafomdad.uniquecrops.items.base.ItemArmorUC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class Glasses3DItem extends ItemArmorUC implements IBookUpgradeable {

    public Glasses3DItem() {

        super(EnumArmorMaterial.GLASSES_3D, EquipmentSlotType.HEAD);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {

        if (world.isClientSide) return;
        if (!isMaxLevel(stack)) return;

        int sunlight = world.getBrightness(LightType.SKY, player.blockPosition().offset(0, player.getEyeHeight(), 0));
        if (sunlight <= 3)
            player.addEffect(new EffectInstance(Effects.NIGHT_VISION, 30));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {

        return false;
    }
}
