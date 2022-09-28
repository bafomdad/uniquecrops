package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.blocks.tiles.TileHarvestTrap;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class HarvestTrapBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape HARVEST_AABB = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 18.0D, 11.0D);

    public HarvestTrapBlock() {

        super(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(0.85F, 15.0F).randomTicks());
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        return HARVEST_AABB;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileHarvestTrap) {
            TileHarvestTrap trap = (TileHarvestTrap)tile;
            if (!trap.hasSpirit() && !trap.isCollected()) {
                if (player.getItemInHand(hand).getItem() == UCItems.SPIRITBAIT.get()) {
                    trap.setBaitPower(3);
                    player.getItemInHand(hand).shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }
            if (trap.hasSpirit() && !trap.isCollected()) {
                trap.setSpiritTime(100);
                trap.setCollected();
                trap.setBaitPower(0);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        if (world.isClientSide) return;

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileHarvestTrap) {
            if (UCUtils.getClosestTile(TileHarvestTrap.class, world, pos, 10.0D) != null) {
                UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.BARRIER, pos.getX(), pos.getY() + 0.75, pos.getZ(), 0));
                return;
            }
            TileHarvestTrap trap = (TileHarvestTrap)tile;
            if (trap.hasSpirit()) return;

            if (rand.nextInt(5 - trap.getBaitPower()) == 0)
                trap.setSpiritTime(200);
            else
                trap.tickCropGrowth();
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return new TileHarvestTrap(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {

        if (!level.isClientSide()) {
            return (lvl, pos, st, te) -> {
                if (te instanceof TileHarvestTrap harvestTrap) harvestTrap.tickServer();
            };
        }
        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileHarvestTrap trap && trap.hasSpirit()) {
            for (int i = 0; i < 5; i++) {
                double d0 = (double)pos.getX() + rand.nextFloat();
                double d1 = (double)pos.getY() + 0.55F;
                double d2 = (double)pos.getZ() + rand.nextFloat();

                float[] color = trap.getSpiritColor();

                if (world.isClientSide()) {
                    world.addParticle(EnumParticle.SPARK.getType(), d0, d1, d2, color[0], color[1], color[2]);
                }
            }
        }
    }
}
