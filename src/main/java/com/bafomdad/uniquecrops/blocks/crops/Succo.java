package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileSucco;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Succo extends BaseCropsBlock {

    public Succo() {

        super(UCItems.VAMPIRIC_OINTMENT, UCItems.SUCCO_SEED);
        setIncludeSeed(false);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (world.dimensionType().moonPhase(world.dayTime()) == 1.0F)
            super.randomTick(state, world, pos, rand);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {

        return BlockRenderType.INVISIBLE;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        if (world instanceof World && ((World)world).getMoonPhase() != 1.0F)
            return VoxelShapes.empty();

        return super.getShape(state, world, pos, ctx);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileSucco();
    }
}
