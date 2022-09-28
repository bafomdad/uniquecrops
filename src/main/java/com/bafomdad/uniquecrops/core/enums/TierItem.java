package com.bafomdad.uniquecrops.core.enums;

import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadedValue;

import java.util.function.Supplier;

public enum TierItem implements Tier {

    PRECISION(3, 1751, 8.0F, 3.0F, 14, () -> Ingredient.of(UCItems.PREGEM.get()));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyLoadedValue<Ingredient> repairMaterial;

    TierItem(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {

        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterialIn);
    }

    @Override
    public int getUses() {

        return this.maxUses;
    }

    @Override
    public float getSpeed() {

        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {

        return attackDamage;
    }

    @Override
    public int getLevel() {

        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {

        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {

        return this.repairMaterial.get();
    }
}
