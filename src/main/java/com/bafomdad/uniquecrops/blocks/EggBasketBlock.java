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

public class EggBasketBlock extends Block {

    public static final VoxelShape EGGBASKET = VoxelShapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

    public EggBasketBlock() {

        super(Properties.of(Material.SPONGE).sound(SoundType.WOOD).strength(0.09F, 3.0F));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        return EGGBASKET;
    }
}
