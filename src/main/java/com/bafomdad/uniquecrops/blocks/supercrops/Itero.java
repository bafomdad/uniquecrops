package com.bafomdad.uniquecrops.blocks.supercrops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.BaseSuperCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileItero;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Random;

public class Itero extends BaseSuperCropsBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;

    public Itero() {

        setDefaultState(getDefaultState().with(AGE, 0));
        MinecraftForge.EVENT_BUS.addListener(this::onPressurePlateTrigger);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {

        super.fillStateContainer(builder);
        builder.add(AGE);
    }

    private void onPressurePlateTrigger(BlockEvent.NeighborNotifyEvent event) {

        if (event.getState().getBlock() == Blocks.STONE_PRESSURE_PLATE) {
            if (event.getState().get(PressurePlateBlock.POWERED)) {
                for (BlockPos loopPos : TileItero.PLATES) {
                    TileEntity tile = event.getWorld().getTileEntity(event.getPos().subtract(loopPos));
                    if (tile instanceof TileItero) {
                        ((TileItero)tile).matchCombo(event.getPos());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

        return BaseCropsBlock.SHAPE_BY_AGE[state.get(AGE)];
    }

    public boolean isMaxAge(BlockState state) {

        return state.get(AGE) >= 7;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        if (isMaxAge(state)) {
            if (!world.isRemote) {
                int num = 1 + world.rand.nextInt(2);
                InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, new ItemStack(UCItems.CUBEYTHINGY.get(), num));
                world.setBlockState(pos, this.getDefaultState(), 2);
            }
            return ActionResultType.SUCCESS;
        }
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileItero && !world.isRemote) {
            TileItero itero = (TileItero)tile;
            itero.tryShowDemo();
            itero.createCombos(state.get(AGE));
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileItero();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {

        if (rand.nextInt(2) == 0) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileItero && ((TileItero)tile).showingDemo())
                world.addParticle(new RedstoneParticleData(1.0F, 0.0F, 0.0F, 0.5F), pos.getX() + rand.nextFloat(), pos.getY() + 0.25, pos.getZ() + rand.nextFloat(), 0, 0,0);
        }
    }
}
