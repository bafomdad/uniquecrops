package com.bafomdad.uniquecrops.core;

import com.bafomdad.uniquecrops.init.UCItems;
import com.google.common.collect.Maps;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Util;

import java.util.Map;

public class DyeUtils {

    // copied from SheepEntity class because WHY THE FUCK IS THAT FIELD PRIVATE
    public static final Map<DyeColor, IItemProvider> WOOL_BY_COLOR = Util.make(Maps.newEnumMap(DyeColor.class), (p_203402_0_) -> {
        p_203402_0_.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        p_203402_0_.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        p_203402_0_.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        p_203402_0_.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        p_203402_0_.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        p_203402_0_.put(DyeColor.LIME, Blocks.LIME_WOOL);
        p_203402_0_.put(DyeColor.PINK, Blocks.PINK_WOOL);
        p_203402_0_.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        p_203402_0_.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        p_203402_0_.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        p_203402_0_.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        p_203402_0_.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        p_203402_0_.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        p_203402_0_.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        p_203402_0_.put(DyeColor.RED, Blocks.RED_WOOL);
        p_203402_0_.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
    });
    public static final Map<DyeColor, IItemProvider> DYE_BY_COLOR = Util.make(Maps.newEnumMap(DyeColor.class), (p_203402_0_) -> {
        p_203402_0_.put(DyeColor.WHITE, Items.WHITE_DYE);
        p_203402_0_.put(DyeColor.ORANGE, Items.ORANGE_DYE);
        p_203402_0_.put(DyeColor.MAGENTA, Items.MAGENTA_DYE);
        p_203402_0_.put(DyeColor.LIGHT_BLUE, Items.LIGHT_BLUE_DYE);
        p_203402_0_.put(DyeColor.YELLOW, Items.YELLOW_DYE);
        p_203402_0_.put(DyeColor.LIME, Items.LIME_DYE);
        p_203402_0_.put(DyeColor.PINK, Items.PINK_DYE);
        p_203402_0_.put(DyeColor.GRAY, Items.GRAY_DYE);
        p_203402_0_.put(DyeColor.LIGHT_GRAY, Items.LIGHT_GRAY_DYE);
        p_203402_0_.put(DyeColor.CYAN, Items.CYAN_DYE);
        p_203402_0_.put(DyeColor.PURPLE, Items.PURPLE_DYE);
        p_203402_0_.put(DyeColor.BLUE, Items.BLUE_DYE);
        p_203402_0_.put(DyeColor.BROWN, Items.BROWN_DYE);
        p_203402_0_.put(DyeColor.GREEN, Items.GREEN_DYE);
        p_203402_0_.put(DyeColor.RED, Items.RED_DYE);
        p_203402_0_.put(DyeColor.BLACK, Items.BLACK_DYE);
    });
    public static final Map<DyeColor, IItemProvider> BONEMEAL_DYE = Util.make(Maps.newEnumMap(DyeColor.class), (p_203402_0_) -> {
        p_203402_0_.put(DyeColor.WHITE, UCItems.WHITE_BONEMEAL.get());
        p_203402_0_.put(DyeColor.ORANGE, UCItems.ORANGE_BONEMEAL.get());
        p_203402_0_.put(DyeColor.MAGENTA, UCItems.MAGENTA_BONEMEAL.get());
        p_203402_0_.put(DyeColor.LIGHT_BLUE, UCItems.LIGHTBLUE_BONEMEAL.get());
        p_203402_0_.put(DyeColor.YELLOW, UCItems.YELLOW_BONEMEAL.get());
        p_203402_0_.put(DyeColor.LIME, UCItems.LIME_BONEMEAL.get());
        p_203402_0_.put(DyeColor.PINK, UCItems.PINK_BONEMEAL.get());
        p_203402_0_.put(DyeColor.GRAY, UCItems.GRAY_BONEMEAL.get());
        p_203402_0_.put(DyeColor.LIGHT_GRAY, UCItems.SILVER_BONEMEAL.get());
        p_203402_0_.put(DyeColor.CYAN, UCItems.CYAN_BONEMEAL.get());
        p_203402_0_.put(DyeColor.PURPLE, UCItems.PURPLE_BONEMEAL.get());
        p_203402_0_.put(DyeColor.BLUE, UCItems.BLUE_BONEMEAL.get());
        p_203402_0_.put(DyeColor.BROWN, UCItems.BROWN_BONEMEAL.get());
        p_203402_0_.put(DyeColor.GREEN, UCItems.GREEN_BONEMEAL.get());
        p_203402_0_.put(DyeColor.RED, UCItems.RED_BONEMEAL.get());
        p_203402_0_.put(DyeColor.BLACK, UCItems.BLACK_BONEMEAL.get());
    });
}
