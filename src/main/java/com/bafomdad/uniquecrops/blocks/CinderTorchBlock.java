package com.bafomdad.uniquecrops.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class CinderTorchBlock extends Block {

    public static final VoxelShape TORCH_AABB = VoxelShapes.create(0.275D, 0.0D, 0.275D, 0.725D, 0.45D, 0.725D);

    public CinderTorchBlock() {

        super(Properties.create(Material.SAND).hardnessAndResistance(0.01F, 0.1F).sound(SoundType.STONE).setLightLevel(s -> 15));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return TORCH_AABB;
    }
}
