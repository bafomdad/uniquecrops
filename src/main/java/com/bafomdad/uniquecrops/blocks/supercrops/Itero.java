package com.bafomdad.uniquecrops.blocks.supercrops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.BaseSuperCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileItero;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Random;

public class Itero extends BaseSuperCropsBlock implements EntityBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public Itero() {

        registerDefaultState(defaultBlockState().setValue(AGE, 0));
        MinecraftForge.EVENT_BUS.addListener(this::onPressurePlateTrigger);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(AGE);
    }

    private void onPressurePlateTrigger(BlockEvent.NeighborNotifyEvent event) {

        if (event.getState().getBlock() == Blocks.STONE_PRESSURE_PLATE) {
            if (event.getState().getValue(PressurePlateBlock.POWERED)) {
                for (BlockPos loopPos : TileItero.PLATES) {
                    BlockEntity tile = event.getWorld().getBlockEntity(event.getPos().subtract(loopPos));
                    if (tile instanceof TileItero) {
                        ((TileItero)tile).matchCombo(event.getPos());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {

        return BaseCropsBlock.SHAPE_BY_AGE[state.getValue(AGE)];
    }

    public boolean isMaxAge(BlockState state) {

        return state.getValue(AGE) >= 7;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (isMaxAge(state)) {
            if (!world.isClientSide) {
                int num = 1 + world.random.nextInt(2);
                Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, new ItemStack(UCItems.CUBEYTHINGY.get(), num));
                world.setBlock(pos, this.defaultBlockState(), 2);
            }
            return InteractionResult.SUCCESS;
        }
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TileItero && !world.isClientSide) {
            TileItero itero = (TileItero)tile;
            itero.tryShowDemo();
            itero.createCombos(state.getValue(AGE));
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return new TileItero(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {

        if (!level.isClientSide()) {
            return (lvl, pos, st, te) -> {
                if (te instanceof TileItero itero) itero.tickServer();
            };
        }
        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {

        if (rand.nextInt(2) == 0) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileItero && ((TileItero)tile).showingDemo())
                world.addParticle(DustParticleOptions.REDSTONE, pos.getX() + rand.nextFloat(), pos.getY() + 0.25, pos.getZ() + rand.nextFloat(), 0, 0,0);
        }
    }
}
