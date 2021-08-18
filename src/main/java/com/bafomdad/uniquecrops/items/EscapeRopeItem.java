package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

public class EscapeRopeItem extends ItemBaseUC {

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack held = player.getMainHandItem();
        BlockPos highestPos = world.getHeightmapPos(Heightmap.Type.WORLD_SURFACE, player.blockPosition());
        if (world.dimension() == World.NETHER && player.getY() >= 126) {
            int air = 0;
            for (int i = highestPos.getY(); i > 1; i--) {
                BlockPos.Mutable loopPos = new BlockPos.Mutable(highestPos.getX(), highestPos.getY(), highestPos.getZ());
                if (world.isEmptyBlock(loopPos)) air++;
                else air = 0;
                if (air >= 2 && !world.isEmptyBlock(loopPos.below()) && world.getFluidState(loopPos.below()).isEmpty()) {
                    if (!world.isClientSide) {
                        player.teleportTo(highestPos.getX() + 0.5, loopPos.immutable().getY(), highestPos.getZ() + 0.5);
                        if (!player.isCreative())
                            held.shrink(1);
                    }
                    return new ActionResult(ActionResultType.SUCCESS, held);
                }
            }
        }
        if (player.getY() == highestPos.getY()) return new ActionResult(ActionResultType.PASS, held);

        if (!world.isClientSide) {
            player.teleportTo(highestPos.getX() + 0.5, highestPos.getY(), highestPos.getZ() + 0.5);
            if (!player.isCreative())
                held.shrink(1);
        }
        return new ActionResult(ActionResultType.SUCCESS, held);
    }
}
