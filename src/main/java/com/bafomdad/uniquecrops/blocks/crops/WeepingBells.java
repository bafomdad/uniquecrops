package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileWeepingBells;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class WeepingBells extends BaseCropsBlock {

    public WeepingBells() {

        super(UCItems.WEEPINGTEAR, UCItems.WEEPINGBELLS_SEED);
        setBonemealable(false);
        setIgnoreGrowthRestrictions(true);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        boolean flag = false;
        if (this.canIgnoreGrowthRestrictions(world, pos)) flag = true;

        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileWeepingBells && ((TileWeepingBells)tile).isLooking()) flag = true;

        if (flag) super.randomTick(state, world, pos, rand);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileWeepingBells();
    }
}
