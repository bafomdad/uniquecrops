package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.blocks.tiles.TileSundial;
import com.bafomdad.uniquecrops.network.UCPacketDispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SundialBlock extends Block {

    public static final VoxelShape SUNDIAL_SHAPE = VoxelShapes.create(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.300000011920929D, 0.8999999761581421D);

    public SundialBlock() {

        super(Properties.from(Blocks.COBBLESTONE).notSolid().doesNotBlockMovement());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return SUNDIAL_SHAPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileSundial) {
                long time = world.getDayTime() % 24000L;
                ((TileSundial)tile).savedTime = (int)time;
                ((TileSundial)tile).savedRotation = world.getCelestialAngleRadians(1.0F);
                UCPacketDispatcher.dispatchTEToNearbyPlayers(tile);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public int getWeakPower(BlockState state, IBlockReader reader, BlockPos pos, Direction side) {

        if (reader.getTileEntity(pos) instanceof TileSundial) {
            if (((TileSundial)reader.getTileEntity(pos)).hasPower)
                return 15;
        }
        return 0;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileSundial();
    }
}
