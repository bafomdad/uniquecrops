package com.bafomdad.uniquecrops.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ObtusePlatformBlock extends Block {

    public ObtusePlatformBlock() {

        super(AbstractBlock.Properties.from(Blocks.OAK_PLANKS).notSolid().doesNotBlockMovement());
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {

        if (!(entity instanceof LivingEntity)) return;

        float prevFallDistance = entity.fallDistance;
        if (!world.isRemote && !entity.isOnGround() && (prevFallDistance > 1.0F || prevFallDistance > 20.0F))
            entity.fallDistance = 20.0F;
    }
}
