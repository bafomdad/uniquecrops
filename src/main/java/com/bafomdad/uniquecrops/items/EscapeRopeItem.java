package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public class EscapeRopeItem extends ItemBaseUC {

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack held = player.getMainHandItem();
        BlockPos highestPos = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, player.blockPosition());
        if (world.dimension() == Level.NETHER && player.getY() >= 126) {
            int air = 0;
            for (int i = highestPos.getY(); i > 1; i--) {
                BlockPos.MutableBlockPos loopPos = new BlockPos.MutableBlockPos(highestPos.getX(), highestPos.getY(), highestPos.getZ());
                if (world.isEmptyBlock(loopPos)) air++;
                else air = 0;
                if (air >= 2 && !world.isEmptyBlock(loopPos.below()) && world.getFluidState(loopPos.below()).isEmpty()) {
                    if (!world.isClientSide) {
                        player.teleportTo(highestPos.getX() + 0.5, loopPos.immutable().getY(), highestPos.getZ() + 0.5);
                        if (!player.isCreative())
                            held.shrink(1);
                    }
                    return new InteractionResultHolder(InteractionResult.SUCCESS, held);
                }
            }
        }
        if (player.getY() == highestPos.getY()) return new InteractionResultHolder(InteractionResult.PASS, held);

        if (!world.isClientSide) {
            player.teleportTo(highestPos.getX() + 0.5, highestPos.getY(), highestPos.getZ() + 0.5);
            if (!player.isCreative())
                held.shrink(1);
        }
        return new InteractionResultHolder(InteractionResult.SUCCESS, held);
    }
}
