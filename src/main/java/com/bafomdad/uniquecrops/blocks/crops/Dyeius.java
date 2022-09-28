package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.DyeUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.Containers;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class Dyeius extends BaseCropsBlock {

    public Dyeius() {

        super(() -> Items.BLUE_DYE, UCItems.DYEIUS_SEED);
    }

    @Override
    public void harvestItems(Level world, BlockPos pos, BlockState state, int fortune) {

        Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, getDyeTime(world));
        Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, new ItemStack(this.getSeed()));
    }

    private ItemStack getDyeTime(Level world) {

        long time = world.getDayTime() % 24000L;
        int meta = (int)(time / 1500);
        Item dye = DyeUtils.DYE_BY_COLOR.get(DyeColor.byId(meta)).asItem();

        return new ItemStack(dye);
    }
}
