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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        ItemStack held = player.getHeldItemMainhand();
        BlockPos highestPos = world.getHeight(Heightmap.Type.WORLD_SURFACE, player.getPosition());
        if (world.getDimensionKey() == World.THE_NETHER && player.getPosY() >= 126) {
            int air = 0;
            for (int i = highestPos.getY(); i > 1; i--) {
                BlockPos.Mutable loopPos = new BlockPos.Mutable(highestPos.getX(), highestPos.getY(), highestPos.getZ());
                if (world.isAirBlock(loopPos)) air++;
                else air = 0;
                if (air >= 2 && !world.isAirBlock(loopPos.down()) && world.getFluidState(loopPos.down()).isEmpty()) {
                    if (!world.isRemote) {
                        player.setPositionAndUpdate(highestPos.getX() + 0.5, loopPos.toImmutable().getY(), highestPos.getZ() + 0.5);
                        if (!player.isCreative())
                            held.shrink(1);
                    }
                    return new ActionResult(ActionResultType.SUCCESS, held);
                }
            }
        }
        if (player.getPosY() == highestPos.getY()) return new ActionResult(ActionResultType.PASS, held);

        if (!world.isRemote) {
            player.setPositionAndUpdate(highestPos.getX() + 0.5, highestPos.getY(), highestPos.getZ() + 0.5);
            if (!player.isCreative())
                held.shrink(1);
        }
        return new ActionResult(ActionResultType.SUCCESS, held);
    }
}
