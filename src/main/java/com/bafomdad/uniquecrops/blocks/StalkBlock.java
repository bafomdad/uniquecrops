package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.blocks.tiles.TileCraftyPlant;
import com.bafomdad.uniquecrops.core.enums.EnumDirectional;
import com.bafomdad.uniquecrops.init.UCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;

public class StalkBlock extends BaseStalkBlock {

    public static final EnumProperty STALKS = EnumProperty.<EnumDirectional>create("stalk", EnumDirectional.class);

    public StalkBlock() {

        super(Properties.of(Material.PLANT).strength(0.1F, 1.0F).noCollission().randomTicks().isSuffocating(StalkBlock::isntSolid));
        registerDefaultState(defaultBlockState().setValue(STALKS, EnumDirectional.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(STALKS);
    }

    private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {

        return false;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileCraftyPlant) {
            if (!world.isClientSide)
                NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)tile, pos);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (state.getValue(STALKS) == EnumDirectional.DOWN && world.isEmptyBlock(pos.above())) {
            if (rand.nextInt(5) == 0) {
                world.levelEvent(2001, pos.above(), Block.getId(state));
                world.setBlock(pos.above(), UCBlocks.STALK.get().defaultBlockState().setValue(STALKS, EnumDirectional.UP), 2);
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        if (state.getValue(STALKS) == EnumDirectional.UP)
            return VoxelShapes.block();

        return VoxelShapes.empty();
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {

        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileCraftyPlant) {
            TileCraftyPlant craft = (TileCraftyPlant)tile;
            for (int i = 0; i < craft.getCraftingInventory().getSlots(); i++) {
                ItemStack stack  = craft.getCraftingInventory().getStackInSlot(i);
                if (!stack.isEmpty() && !world.isClientSide)
                    InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
    }

    @Override
    public void checkAndDropBlock(World world, BlockPos pos, BlockState state) {

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

    private boolean isNeighborStalkMissing(IWorld world, BlockPos pos, BlockState state) {

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

    private boolean isStalk(IWorld world, BlockPos pos) {

        return !(world.getBlockState(pos).getBlock() instanceof BaseStalkBlock);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return state.getValue(STALKS) == EnumDirectional.UP;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileCraftyPlant();
    }
}
