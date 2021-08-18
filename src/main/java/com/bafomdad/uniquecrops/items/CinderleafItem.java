package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CinderleafItem extends ItemBaseUC {

    @Override
    public boolean hasCustomEntity(ItemStack stack) {

        return stack.getItem() == this;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {

        if (entity.getItem().getItem() == this) {
            if (isWaterSource(entity.getCommandSenderWorld(), entity.blockPosition())) {
                if (entity.getAge() % 20 == 0) {
                    if (entity.getItem().getCount() == 4) {
                        entity.level.setBlock(entity.blockPosition(), Blocks.LAVA.defaultBlockState(), 3);
                        entity.remove();
                    }
                }
            }
        }
        return false;
    }

    private boolean isWaterSource(World world, BlockPos pos) {

        return world.getFluidState(pos).is(FluidTags.WATER) && world.getFluidState(pos).isSource();
    }
}
