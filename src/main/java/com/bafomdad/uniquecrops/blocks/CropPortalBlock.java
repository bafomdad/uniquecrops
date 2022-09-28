package com.bafomdad.uniquecrops.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class CropPortalBlock extends Block {

    public static final VoxelShape PORTAL_AABB = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

    public CropPortalBlock() {

        super(Properties.copy(Blocks.NETHER_PORTAL));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        return PORTAL_AABB;
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {

        if (!(entity instanceof Player)) return;

        Vec3 vec3d = entity.getDeltaMovement();
        entity.setDeltaMovement(vec3d.x, Math.abs(vec3d.y * 1.05), vec3d.z);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {

        if (world.isEmptyBlock(facingPos))
            world.destroyBlock(pos, false);

        return super.updateShape(state, facing, facingState, world, pos, facingPos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {

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
