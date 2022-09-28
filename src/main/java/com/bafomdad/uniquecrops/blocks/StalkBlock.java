package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.blocks.tiles.TileCraftyPlant;
import com.bafomdad.uniquecrops.core.enums.EnumDirectional;
import com.bafomdad.uniquecrops.init.UCBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class StalkBlock extends BaseStalkBlock implements EntityBlock {

    public static final EnumProperty STALKS = EnumProperty.<EnumDirectional>create("stalk", EnumDirectional.class);

    public StalkBlock() {

        super(Properties.of(Material.PLANT).strength(0.1F, 1.0F).noCollission().randomTicks().isSuffocating(StalkBlock::isntSolid));
        registerDefaultState(defaultBlockState().setValue(STALKS, EnumDirectional.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(STALKS);
    }

    private static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {

        return false;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileCraftyPlant) {
            if (!world.isClientSide)
                NetworkHooks.openGui((ServerPlayer)player, (MenuProvider)tile, pos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        if (state.getValue(STALKS) == EnumDirectional.DOWN && world.isEmptyBlock(pos.above())) {
            if (rand.nextInt(5) == 0) {
                world.levelEvent(2001, pos.above(), Block.getId(state));
                world.setBlock(pos.above(), UCBlocks.STALK.get().defaultBlockState().setValue(STALKS, EnumDirectional.UP), 2);
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        if (state.getValue(STALKS) == EnumDirectional.UP)
            return Shapes.block();

        return Shapes.empty();
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileCraftyPlant) {
            TileCraftyPlant craft = (TileCraftyPlant)tile;
            for (int i = 0; i < craft.getCraftingInventory().getSlots(); i++) {
                ItemStack stack  = craft.getCraftingInventory().getStackInSlot(i);
                if (!stack.isEmpty() && !world.isClientSide)
                    Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
    }

    @Override
    public void checkAndDropBlock(Level world, BlockPos pos, BlockState state) {

        if (state.getValue(STALKS) == EnumDirectional.DOWN) {
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos loopPos = pos.relative(dir);
                if (world.isEmptyBlock(loopPos)) {
                    world.destroyBlock(pos, false);
                }
            }
            return;
        }
        if (state.getValue(STALKS) == EnumDirectional.UP) {
            if (world.isEmptyBlock(pos.below())) {
                world.destroyBlock(pos, false);
            }
            return;
        }
        if (isNeighborStalkMissing(world, pos, state)) {
            world.destroyBlock(pos, false);
        }
    }

    private boolean isNeighborStalkMissing(LevelAccessor world, BlockPos pos, BlockState state) {

        if (!(state.getBlock() instanceof BaseStalkBlock)) return false;

        EnumDirectional prop = (EnumDirectional)state.getValue(STALKS);
        switch (prop) {
            case NORTH:
            case SOUTH:
                return isStalk(world, pos.east()) || isStalk(world, pos.west());
            case WEST:
            case EAST:
                return isStalk(world, pos.north()) || isStalk(world, pos.south());
            case NORTHEAST: return isStalk(world, pos.south()) || isStalk(world, pos.west());
            case NORTHWEST: return isStalk(world, pos.south()) || isStalk(world, pos.east());
            case SOUTHEAST: return isStalk(world, pos.north()) || isStalk(world, pos.west());
            case SOUTHWEST: return isStalk(world, pos.north()) || isStalk(world, pos.east());
            default: return false;
        }
    }

    private boolean isStalk(LevelAccessor world, BlockPos pos) {

        return !(world.getBlockState(pos).getBlock() instanceof BaseStalkBlock);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return state.getValue(STALKS) == EnumDirectional.UP ? new TileCraftyPlant(pos, state) : null;
    }
}
