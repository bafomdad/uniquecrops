package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.blocks.tiles.TileBarrel;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
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
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class AbstractBarrelBlock extends Block implements IWaterLoggable {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final VoxelShape BARREL_SHAPE = VoxelShapes.box(0.75D, 0.0D, 0.75D, 0.25D, 0.8D, 0.25D);

    public AbstractBarrelBlock() {

        super(Properties.of(Material.WOOD).strength(3.0F, 5.0F).sound(SoundType.WOOD));
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {

        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return BARREL_SHAPE;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        if (!world.isClientSide) {
            TileEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileBarrel)
                NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)tile, pos);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {

        if (state.getBlock() == this) return;

        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileBarrel) {
            tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
                    .ifPresent(inventory -> {
                        for (int i = 0; i < inventory.getSlots(); i++) {
                            ItemStack stack = inventory.getStackInSlot(i);
                            if (!stack.isEmpty() && !world.isClientSide)
                                InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                        }
                    });
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {

        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction side, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {

        if (state.getValue(WATERLOGGED))
            world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));

        return state;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileBarrel();
    }
}
