package com.bafomdad.uniquecrops.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
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
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class BucketRopeBlock extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final VoxelShape BUCKET_AABB = VoxelShapes.box(0.375D, 0.0D, 0.375D, 0.625D, 1.5D, 0.625D);

    public BucketRopeBlock() {

        super(Properties.of(Material.HEAVY_METAL).strength(1.0F));
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        ItemStack bucket = player.getItemInHand(hand);
        if (bucket.getItem() == Items.BUCKET) {
            if (!world.isClientSide) {
                BlockPos searchPos = pos.below();
                while (searchPos.getY() > 0) {
                    searchPos = searchPos.below();
                    if (!world.isEmptyBlock(searchPos)) break;
                }
                BlockState loopState = world.getBlockState(searchPos);
                if (player.mayUseItemAt(searchPos, hit.getDirection(), bucket) && loopState.getBlock() instanceof IBucketPickupHandler) {
                    Fluid fluid = ((IBucketPickupHandler)loopState.getBlock()).takeLiquid(world, searchPos, loopState);
                    if (fluid != Fluids.EMPTY) {
                        ItemStack filled = FluidUtil.getFilledBucket(new FluidStack(fluid, FluidAttributes.BUCKET_VOLUME));
                        if (!player.isCreative())
                            player.setItemInHand(hand, filled);
                    }
                }
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return BUCKET_AABB;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {

        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }
}
