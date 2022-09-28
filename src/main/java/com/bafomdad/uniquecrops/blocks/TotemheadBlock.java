package com.bafomdad.uniquecrops.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.server.level.ServerLevel;

import java.util.List;
import java.util.Random;

public class TotemheadBlock extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape TOTEM_AABB = Shapes.box(0.25F, 0.0F, 0.25F, 0.75F, 1.75F, 0.75F);
    private static final int RANGE = 12;

    public TotemheadBlock() {

        super(Properties.of(Material.WOOD).strength(2.0F, 20.0F).randomTicks());
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        if (findMobs(world, pos))
            world.scheduleTick(pos, this, 100);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        if (findMobs(world, pos))
            world.scheduleTick(pos, this, 100);
    }

    private boolean findMobs(ServerLevel world, BlockPos pos) {

        boolean foundEntity = false;
        List<LivingEntity> elb = world.getEntitiesOfClass(LivingEntity.class, new AABB(pos.offset(-RANGE, -1, -RANGE), pos.offset(RANGE, 1, RANGE)));
        for (LivingEntity entity : elb) {
            if (entity.isAlive() && (entity instanceof Monster || entity instanceof Slime)) {
                entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 500, 1));
                foundEntity = true;
            }
        }
        return foundEntity;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        return TOTEM_AABB;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }
}
