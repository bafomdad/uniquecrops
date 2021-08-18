package com.bafomdad.uniquecrops.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class CropPortalBlock extends Block {

    public static final VoxelShape PORTAL_AABB = VoxelShapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

    public CropPortalBlock() {

        super(Properties.copy(Blocks.NETHER_PORTAL));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return PORTAL_AABB;
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {

        if (!(entity instanceof PlayerEntity)) return;

        Vector3d vec3d = entity.getDeltaMovement();
        entity.setDeltaMovement(vec3d.x, Math.abs(vec3d.y * 1.05), vec3d.z);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {

        if (world.isEmptyBlock(facingPos))
            world.destroyBlock(pos, false);

        return super.updateShape(state, facing, facingState, world, pos, facingPos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {

        for (int i = 0; i < 2; ++i) {
            double d0 = (float)pos.getX() + rand.nextFloat();
            double d1 = (float)pos.getY() + rand.nextFloat();
            double d2 = (float)pos.getZ() + rand.nextFloat();

            int k = 3369483;
            double d3 = (double)(k >> 16 & 255) / 255.0D;
            double d4 = (double)(k >> 8 & 255) / 255.0D;
            double d5 = (double)(k >> 0 & 255) / 255.0D;
            world.addParticle(ParticleTypes.ENTITY_EFFECT, d0, d1, d2, d3, d4, d5);
        }
    }
}
