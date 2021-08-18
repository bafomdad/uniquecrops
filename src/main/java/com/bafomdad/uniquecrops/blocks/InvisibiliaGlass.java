package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class InvisibiliaGlass extends AbstractGlassBlock {

    public InvisibiliaGlass() {

        super(Properties.copy(Blocks.GLASS).isViewBlocking((state, reader, pos) -> false).isSuffocating((state, reader, pos) -> false));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        Entity entity = ctx.getEntity();
        if (!(entity instanceof PlayerEntity))
            return super.getCollisionShape(state, world, pos, ctx);

        PlayerEntity player = (PlayerEntity)entity;
        if (!player.isCreative()) {
            if (player.inventory.armor.get(3).getItem() != UCItems.GLASSES_3D.get())
                return VoxelShapes.empty();
        }
        return super.getCollisionShape(state, world, pos, ctx);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext ctx) {

        return this.getCollisionShape(state, reader, pos, ctx);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {

        Entity entity = ctx.getEntity();
        if (!(entity instanceof PlayerEntity))
            return super.getShape(state, world, pos, ctx);

        PlayerEntity player = (PlayerEntity)entity;
        if (!player.isCreative()) {
            if (player.inventory.armor.get(3).getItem() != UCItems.GLASSES_3D.get())
                return VoxelShapes.empty();
        }
        return super.getShape(state, world, pos, ctx);
    }
}
