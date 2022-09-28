package com.bafomdad.uniquecrops.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ObtusePlatformBlock extends Block {

    public ObtusePlatformBlock() {

        super(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission());
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {

        if (!(entity instanceof LivingEntity)) return;

        float prevFallDistance = entity.fallDistance;
        if (!world.isClientSide && !entity.isOnGround() && (prevFallDistance > 1.0F || prevFallDistance > 20.0F))
            entity.fallDistance = 20.0F;
    }
}
