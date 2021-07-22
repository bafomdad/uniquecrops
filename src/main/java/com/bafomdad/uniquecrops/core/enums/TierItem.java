package com.bafomdad.uniquecrops.core.enums;

import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum TierItem implements IItemTier {

    PRECISION(3, 1751, 8.0F, 3.0F, 14, () -> Ingredient.fromItems(UCItems.PREGEM.get()));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    TierItem(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {

        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyValue<>(repairMaterialIn);
    }

    @Override
    public int getMaxUses() {

        return this.maxUses;
    }

    @Override
    public float getEfficiency() {

        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {

        return attackDamage;
    }

    @Override
    public int getHarvestLevel() {

        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {

        return this.enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {

        return this.repairMaterial.getValue();
    }
}
