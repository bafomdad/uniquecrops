package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileIndustria;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class Industria extends BaseCropsBlock {

    public Industria() {

        super(UCItems.BEAN_BATTERY, UCItems.INDUSTRIA_SEED);
        setBonemealable(false);
        setClickHarvest(false);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {

        // NO-OP
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileIndustria();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {

        for (int i = 0; i < this.getAge(state) + 1; i++) {
            double d0 = (double)pos.getX() + rand.nextFloat();
            double d1 = (double)pos.getY() + 0.1F;
            double d2 = (double)pos.getZ() + rand.nextFloat();
            world.addParticle(new RedstoneParticleData(1.0F, 0.0F, 0.0F, 0.5F), d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
