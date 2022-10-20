package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class CinderleafItem extends ItemBaseUC {

    public CinderleafItem() {

        super(UCItems.defaultBuilder().fireResistant());
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {

        return stack.is(this);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {

        if (entity.getItem().getItem() == this) {
            if (isWaterSource(entity.getCommandSenderWorld(), entity.blockPosition())) {
                if (entity.tickCount % 20 == 0) {
                    if (entity.getItem().getCount() == 4) {
                        entity.level.setBlock(entity.blockPosition(), Blocks.LAVA.defaultBlockState(), 3);
                        entity.discard();
                    }
                }
            }
        }
        return false;
    }

    private boolean isWaterSource(Level world, BlockPos pos) {

        return world.getFluidState(pos).is(FluidTags.WATER) && world.getFluidState(pos).isSource();
    }
}
