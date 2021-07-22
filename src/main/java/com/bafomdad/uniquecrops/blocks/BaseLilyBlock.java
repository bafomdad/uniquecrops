package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.core.enums.EnumLily;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BaseLilyBlock extends LilyPadBlock {

    final EnumLily lily;

    public BaseLilyBlock(EnumLily lily) {

        super(Properties.from(Blocks.LILY_PAD));
        this.lily = lily;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {

        if (world.isRemote) return;
        lily.collide(state, world, pos, entity);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader reader, BlockPos pos) {

        return lily.isValidGround(state, reader, pos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {

        if (rand.nextInt(2) == 0)
            world.addParticle(lily.getParticle(), pos.getX() + rand.nextFloat(), pos.getY(), pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
    }
}
