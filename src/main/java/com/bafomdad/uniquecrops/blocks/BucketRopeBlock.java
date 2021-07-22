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

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final VoxelShape BUCKET_AABB = VoxelShapes.create(0.375D, 0.0D, 0.375D, 0.625D, 1.5D, 0.625D);

    public BucketRopeBlock() {

        super(Properties.create(Material.ANVIL).hardnessAndResistance(1.0F));
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {

        super.fillStateContainer(builder);
        builder.add(FACING);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        ItemStack bucket = player.getHeldItem(hand);
        if (bucket.getItem() == Items.BUCKET) {
            if (!world.isRemote) {
                BlockPos searchPos = pos.down();
                while (searchPos.getY() > 0) {
                    searchPos = searchPos.down();
                    if (!world.isAirBlock(searchPos)) break;
                }
                BlockState loopState = world.getBlockState(searchPos);
                if (player.canPlayerEdit(searchPos, hit.getFace(), bucket) && loopState.getBlock() instanceof IBucketPickupHandler) {
                    Fluid fluid = ((IBucketPickupHandler)loopState.getBlock()).pickupFluid(world, searchPos, loopState);
                    if (fluid != Fluids.EMPTY) {
                        ItemStack filled = FluidUtil.getFilledBucket(new FluidStack(fluid, FluidAttributes.BUCKET_VOLUME));
                        if (!player.isCreative())
                            player.setHeldItem(hand, filled);
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

        return this.getDefaultState().with(FACING, ctx.getPlacementHorizontalFacing());
    }
}
