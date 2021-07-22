package com.bafomdad.uniquecrops.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class TotemheadBlock extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final VoxelShape TOTEM_AABB = VoxelShapes.create(0.25F, 0.0F, 0.25F, 0.75F, 1.75F, 0.75F);
    private static final int RANGE = 12;

    public TotemheadBlock() {

        super(Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 20.0F).tickRandomly());
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {

        super.fillStateContainer(builder);
        builder.add(FACING);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {


        if (findMobs(world, pos))
            world.getPendingBlockTicks().scheduleTick(pos, this, 100);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (findMobs(world, pos))
            world.getPendingBlockTicks().scheduleTick(pos, this, 100);
    }

    private boolean findMobs(ServerWorld world, BlockPos pos) {

        boolean foundEntity = false;
        List<LivingEntity> elb = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.add(-RANGE, -1, -RANGE), pos.add(RANGE, 1, RANGE)));
        for (LivingEntity entity : elb) {
            if (entity.isAlive() && (entity instanceof MonsterEntity || entity instanceof SlimeEntity)) {
                entity.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, 500, 1));
                foundEntity = true;
            }
        }
        return foundEntity;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return TOTEM_AABB;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {

        return this.getDefaultState().with(FACING, ctx.getPlacementHorizontalFacing());
    }
}
