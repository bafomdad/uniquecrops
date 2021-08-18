package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.blocks.tiles.TileHarvestTrap;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.RedstoneParticleData;
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
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class HarvestTrapBlock extends Block {

    private static final int RANGE = 4;

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final VoxelShape HARVEST_AABB = VoxelShapes.box(0.75D, 0.0D, 0.75D, 0.25D, 1.25D, 0.25D);

    public HarvestTrapBlock() {

        super(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(0.85F, 15.0F).randomTicks());
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return HARVEST_AABB;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {

        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileHarvestTrap) {
            TileHarvestTrap trap = (TileHarvestTrap)tile;
            if (!trap.hasSpirit() && !trap.isCollected()) {
                if (player.getItemInHand(hand).getItem() == UCItems.SPIRITBAIT.get()) {
                    trap.setBaitPower(3);
                    player.getItemInHand(hand).shrink(1);
                    return ActionResultType.SUCCESS;
                }
            }
            if (trap.hasSpirit() && !trap.isCollected()) {
                trap.setSpiritTime(100);
                trap.setCollected();
                trap.setBaitPower(0);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (world.isClientSide) return;

        TileEntity tile = world.getBlockEntity(pos);
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
    public boolean hasTileEntity(BlockState state) {

        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileHarvestTrap();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {

        TileEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileHarvestTrap && ((TileHarvestTrap)tile).hasSpirit()) {
            for (int i = 0; i < 5; i++) {
                double d0 = (double)pos.getX() + rand.nextFloat();
                double d1 = (double)pos.getY() + 0.55F;
                double d2 = (double)pos.getZ() + rand.nextFloat();
                float[] color = ((TileHarvestTrap)tile).getSpiritColor();
                world.addParticle(new RedstoneParticleData(color[0], color[1], color[2], 0.5F), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
