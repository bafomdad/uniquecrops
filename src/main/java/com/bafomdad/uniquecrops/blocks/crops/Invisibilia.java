package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class Invisibilia extends BaseCropsBlock {

    public Invisibilia() {

        super(UCItems.INVISITWINE, UCItems.INVISIBILIA_SEED);
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
