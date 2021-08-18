package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.TierItem;
import com.bafomdad.uniquecrops.init.UCItems;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.ToolItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class PrecisionHammerItem extends ToolItem {

    private static final Set<Block> EFFECTIVE_ON = ImmutableSet.of(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.NETHER_GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.PISTON_HEAD);

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
            Material.CORAL,
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

        super(1.0F, -2.6F, TierItem.PRECISION, EFFECTIVE_ON, UCItems.unstackable()
        .addToolType(ToolType.SHOVEL, TierItem.PRECISION.getLevel())
        .addToolType(ToolType.AXE, TierItem.PRECISION.getLevel())
        .addToolType(ToolType.PICKAXE, TierItem.PRECISION.getLevel())
        .addToolType(ToolType.HOE, TierItem.PRECISION.getLevel()));
    }

    @Override
    public Rarity getRarity(ItemStack stack) {

        return Rarity.RARE;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {

        Material material = state.getMaterial();
        return EFFECTIVE_ON_MATERIALS.contains(material) ? this.speed : super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState blockIn) {

        int i = this.getTier().getLevel();
        if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
            return i >= blockIn.getHarvestLevel();
        }
        Material material = blockIn.getMaterial();
        return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {

        list.add(new TranslationTextComponent(UCStrings.TOOLTIP + "precisionhammer"));
    }

    @Override
    public void onCraftedBy(ItemStack stack, World world, PlayerEntity player) {

        stack.enchant(Enchantments.SILK_TOUCH, 1);
    }
}
