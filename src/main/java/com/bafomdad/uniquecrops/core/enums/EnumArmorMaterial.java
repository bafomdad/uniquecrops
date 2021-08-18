package com.bafomdad.uniquecrops.core.enums;

import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.function.Supplier;

public enum EnumArmorMaterial implements IArmorMaterial {

    GLASSES_3D("3dglasses", 18, new int[] { 0, 0, 0, 0}, 10, () -> SoundEvents.ARMOR_EQUIP_IRON, () -> Items.AIR, 0F),
    GLASSES_PIXELS("pixelglasses", GLASSES_3D),
    PONCHO("poncho", 8, new int[] { 1, 2, 1, 1 }, 0, () -> SoundEvents.ARMOR_EQUIP_LEATHER, UCItems.INVISIFEATHER, 0F),
    SLIPPERS("slippers", 8, new int[] { 1, 1, 1, 1 }, 0, () -> SoundEvents.ARMOR_EQUIP_GOLD, UCItems.SLIPPERGLASS, 0F),
    CACTUS("cactus", 13, new int[] { 1, 4, 5, 2}, 8, () -> SoundEvents.ARMOR_EQUIP_LEATHER, () -> Item.byBlock(Blocks.CACTUS), 0F),
    THUNDERPANTZ("thunderpantz", 15, new int[] { 1, 4, 5, 2}, 6, () -> SoundEvents.ARMOR_EQUIP_LEATHER, () -> Items.AIR, 0F),
    BOOTS_LEAGUE("bootsleague", 18, new int[] { 0, 0, 0, 0}, 1, () -> SoundEvents.ARMOR_EQUIP_LEATHER, () -> Items.AIR, 0F);

    private final String name;
    private final int durabilityMultiplier;
    private final int[] damageReduction;
    private final int enchantability;
    private final Supplier<SoundEvent> equipSound;
    private final Supplier<Item> repairItem;
    private final float toughness;
    private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };

    EnumArmorMaterial(String name, int durabilityMultiplier, int[] damageReduction, int enchantability, Supplier<SoundEvent> equipSound, Supplier<Item> repairItem, float toughness) {

        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.damageReduction = damageReduction;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.repairItem = repairItem;
        this.toughness = toughness;
    }

    EnumArmorMaterial(String name, EnumArmorMaterial toCopy) {

        this.name = name;
        this.durabilityMultiplier = toCopy.durabilityMultiplier;
        this.damageReduction = toCopy.damageReduction;
        this.enchantability = toCopy.enchantability;
        this.equipSound = toCopy.equipSound;
        this.repairItem = toCopy.repairItem;
        this.toughness = toCopy.toughness;
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlotType slot) {

        return durabilityMultiplier * MAX_DAMAGE_ARRAY[slot.getIndex()];
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slot) {

        return damageReduction[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {

        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {

        return equipSound.get();
    }

    @Override
    public Ingredient getRepairIngredient() {

        return Ingredient.of(repairItem.get());
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public float getToughness() {

        return toughness;
    }

    @Override
    public float getKnockbackResistance() {

        return 0;
    }
}
