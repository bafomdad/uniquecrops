package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;

public class Invisibilia extends BaseCropsBlock {

    public Invisibilia() {

        super(UCItems.INVISITWINE, UCItems.INVISIBILIA_SEED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        if (!(ctx instanceof EntityCollisionContext))
            return super.getShape(state, world, pos, ctx);

        Entity entity = ((EntityCollisionContext)ctx).getEntity();
        if (!(entity instanceof Player))
            return super.getShape(state, world, pos, ctx);

        Player player = (Player)entity;
        if (!player.isCreative()) {
            if (player.getInventory().armor.get(3).getItem() != UCItems.GLASSES_3D.get())
                return Shapes.empty();
        }
        return super.getShape(state, world, pos, ctx);
    }
}
