package com.bafomdad.uniquecrops.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;

public class CinderTorchBlock extends Block {

    public static final VoxelShape TORCH_AABB = Shapes.box(0.275D, 0.0D, 0.275D, 0.725D, 0.45D, 0.725D);

    public CinderTorchBlock() {

        super(Properties.of(Material.SAND).strength(0.01F, 0.1F).sound(SoundType.STONE).lightLevel(s -> 15));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        return TORCH_AABB;
    }
}
