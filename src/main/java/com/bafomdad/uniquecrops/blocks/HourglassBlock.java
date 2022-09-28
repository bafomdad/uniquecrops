package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.api.IHourglassRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HourglassBlock extends Block {

    private static final int RANGE = 3;

    public static final VoxelShape HOURGLASS = Shapes.box(0.25F, 0.0F, 0.25F, 0.75F, 1F, 0.75F);

    public HourglassBlock() {

        super(Properties.of(Material.METAL).sound(SoundType.GLASS).strength(1.0F).randomTicks().isRedstoneConductor(HourglassBlock::isntSolid));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        return HOURGLASS;
    }

    private static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {

        return false;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        if (world.getBestNeighborSignal(pos) > 0)
            searchAroundBlocks(world, pos, rand);
    }

    private void searchAroundBlocks(Level world, BlockPos pos, Random rand) {

        List<BlockPos> toConvert = new ArrayList<>();
        for (BlockPos loopPos : BlockPos.betweenClosed(pos.offset(-RANGE, -RANGE, -RANGE), pos.offset(RANGE, RANGE, RANGE))) {
            if (!world.isEmptyBlock(loopPos))
                toConvert.add(loopPos.immutable());
        }
        Collections.shuffle(toConvert, rand);
        for (BlockPos loopPos : toConvert) {
            boolean flag = rand.nextInt(10) == 0;
            if (flag) {
                BlockState loopState = world.getBlockState(loopPos);
                IHourglassRecipe recipe = findRecipe(world, loopState);
                if (recipe != null) {
                    convertBlock(world, loopPos, recipe.getOutput());
                }
            }
        }
    }

    private IHourglassRecipe findRecipe(Level world, BlockState state) {

        for (Recipe<?> recipe : world.getRecipeManager().getRecipes()) {
            if (recipe instanceof IHourglassRecipe && ((IHourglassRecipe)recipe).matches(state))
                return ((IHourglassRecipe)recipe);
        }
        return null;
    }

    private void convertBlock(Level world, BlockPos pos, BlockState output) {

        world.setBlock(pos, output, 2);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {

        if (rand.nextInt(2) == 0 && world.hasNeighborSignal(pos))
            world.addParticle(ParticleTypes.END_ROD, pos.getX() + rand.nextFloat(), pos.getY() + 0.5, pos.getZ() + rand.nextFloat(), 0, 0, 0);
    }
}
