package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.blocks.tiles.TileSundial;
import com.bafomdad.uniquecrops.network.UCPacketDispatcher;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class SundialBlock extends Block implements EntityBlock {

    public static final VoxelShape SUNDIAL_SHAPE = Shapes.box(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.300000011920929D, 0.8999999761581421D);

    public SundialBlock() {

        super(Properties.copy(Blocks.COBBLESTONE).noOcclusion().noCollission());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        return SUNDIAL_SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (!world.isClientSide) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileSundial sundial) {
                long time = world.getDayTime() % 24000L;
                sundial.savedTime = (int)time;
                sundial.savedRotation = world.getSunAngle(1.0F);
                UCPacketDispatcher.dispatchTEToNearbyPlayers(sundial);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter reader, BlockPos pos, Direction side) {

        if (reader.getBlockEntity(pos) instanceof TileSundial) {
            if (((TileSundial)reader.getBlockEntity(pos)).hasPower)
                return 15;
        }
        return 0;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return new TileSundial(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {

        if (!level.isClientSide()) {
            return (lvl, pos, stt, te) -> {
              if (te instanceof TileSundial sundial) sundial.tickServer();
            };
        }
        return null;
    }
}
