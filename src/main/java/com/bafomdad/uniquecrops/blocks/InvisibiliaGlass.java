package com.bafomdad.uniquecrops.blocks;

import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;

public class InvisibiliaGlass extends AbstractGlassBlock {

    public InvisibiliaGlass() {

        super(Properties.copy(Blocks.GLASS).isViewBlocking((state, reader, pos) -> false).isSuffocating((state, reader, pos) -> false));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        if (!(ctx instanceof EntityCollisionContext))
            return super.getCollisionShape(state, world, pos, ctx);

        Entity entity = ((EntityCollisionContext)ctx).getEntity();
        if (!(entity instanceof Player))
            return super.getCollisionShape(state, world, pos, ctx);

        Player player = (Player)entity;
        if (!player.isCreative()) {
            if (player.getInventory().armor.get(3).getItem() != UCItems.GLASSES_3D.get())
                return Shapes.empty();
        }
        return super.getCollisionShape(state, world, pos, ctx);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext ctx) {

        return this.getCollisionShape(state, reader, pos, ctx);
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
