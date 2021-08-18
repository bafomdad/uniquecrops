package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Cobblonia extends BaseCropsBlock {

    public Cobblonia() {

        super(() -> Item.byBlock(Blocks.COBBLESTONE), UCItems.COBBLONIA_SEED);
        setIgnoreGrowthRestrictions(true);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        super.randomTick(state, world, pos, rand);
        if (!this.isMaxAge(state)) {
            return;
        }
        boolean flag = this.canIgnoreGrowthRestrictions(world, pos);
        cobbleGen(world, pos, flag);
    }

    private void cobbleGen(ServerWorld world, BlockPos pos, boolean boost) {

        BlockPos north = pos.below().north();
        BlockPos south = pos.below().south();
        BlockPos east = pos.below().east();
        BlockPos west = pos.below().west();

        int cobblegen = 0;

        if (isFluidSource(world, north, FluidTags.WATER) && isFluidSource(world, south, FluidTags.LAVA)) cobblegen++;
        if (isFluidSource(world, west, FluidTags.WATER) && isFluidSource(world, east, FluidTags.LAVA)) cobblegen++;
        if (isFluidSource(world, north, FluidTags.LAVA) && isFluidSource(world, south, FluidTags.WATER)) cobblegen++;
        if (isFluidSource(world, west, FluidTags.WATER) && isFluidSource(world, east, FluidTags.LAVA)) cobblegen++;

        if (cobblegen > 0) {
            cobblegen = boost ? 64 : cobblegen;
            ItemStack toDrop = new ItemStack(Blocks.COBBLESTONE, cobblegen);
            InventoryHelper.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, toDrop);
        }
    }

    private boolean isFluidSource(World world, BlockPos pos, ITag<Fluid> tag) {

        return world.getFluidState(pos).is(tag) && world.getFluidState(pos).isSource();
    }
}
