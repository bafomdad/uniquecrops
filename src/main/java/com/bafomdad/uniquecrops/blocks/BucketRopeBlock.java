package com.bafomdad.uniquecrops.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
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

public class BucketRopeBlock extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape BUCKET_AABB = Shapes.box(0.375D, 0.0D, 0.375D, 0.625D, 1.5D, 0.625D);

    public BucketRopeBlock() {

        super(Properties.of(Material.HEAVY_METAL).strength(1.0F));
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        ItemStack bucket = player.getItemInHand(hand);
        if (bucket.getItem() == Items.BUCKET) {
            if (!world.isClientSide) {
                BlockPos searchPos = pos.below();
                while (searchPos.getY() > world.getMinBuildHeight()) {
                    searchPos = searchPos.below();
                    if (!world.isEmptyBlock(searchPos)) break;
                }
                BlockState loopState = world.getBlockState(searchPos);
                System.out.println(loopState);
                if (player.mayUseItemAt(searchPos, hit.getDirection(), bucket) && loopState.getBlock() instanceof BucketPickup) {
                    ItemStack filled = ((BucketPickup)loopState.getBlock()).pickupBlock(world, searchPos, loopState);
                    System.out.println(filled);
                    if (!filled.isEmpty() && !player.isCreative())
                        player.setItemInHand(hand, filled);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        return BUCKET_AABB;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }
}
