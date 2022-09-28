package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.TierItem;
import com.bafomdad.uniquecrops.init.UCItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class PrecisionHammerItem extends TieredItem {

    public static final Set<ToolAction> MINE_ANYTHING = Set.of(ToolActions.AXE_DIG, ToolActions.HOE_DIG, ToolActions.SHOVEL_DIG, ToolActions.PICKAXE_DIG, ToolActions.SWORD_DIG);

    private final Multimap<Attribute, AttributeModifier> attributes;

    private static final Set<Material> EFFECTIVE_ON_MATERIALS = Sets.newHashSet(
            Material.WOOD,
            Material.NETHER_WOOD,
            Material.PLANT,
            Material.REPLACEABLE_PLANT,
            Material.BAMBOO,
            Material.VEGETABLE,
            Material.GRASS,
            Material.STONE,
            Material.METAL,
            Material.HEAVY_METAL,
            Material.SAND,
            Material.DIRT,
            Material.CACTUS,
            Material.GLASS,
            Material.CLAY,
            Material.ICE,
            Material.ICE_SOLID,
            Material.WATER_PLANT,
            Material.LEAVES,
            Material.PISTON,
            Material.SNOW,
            Material.SNOW,
            Material.WOOL,
            Material.WEB,
            Material.SPONGE,
            Material.CLOTH_DECORATION
    );

    public PrecisionHammerItem() {

        super(TierItem.PRECISION, UCItems.unstackable());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.4D, AttributeModifier.Operation.ADDITION));
        this.attributes = builder.build();
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {

        return Rarity.RARE;
    }

    @Override
    public boolean isCorrectToolForDrops(@NotNull BlockState state) {

        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction action) {

        return PrecisionHammerItem.MINE_ANYTHING.contains(action);
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {

        return 13;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {

        return slot == EquipmentSlot.MAINHAND ? attributes : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, List<Component> list, @NotNull TooltipFlag flag) {

        list.add(new TranslatableComponent(UCStrings.TOOLTIP + "precisionhammer"));
    }

    @Override
    public void onCraftedBy(ItemStack stack, @NotNull Level world, @NotNull Player player) {

        stack.enchant(Enchantments.SILK_TOUCH, 1);
    }
}
