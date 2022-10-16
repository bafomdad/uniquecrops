package com.bafomdad.uniquecrops.items.base;

import com.bafomdad.uniquecrops.blocks.HourglassBlock;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ItemBlockUC extends BlockItem {

    public ItemBlockUC(Block block) {

        super(block, UCItems.defaultBuilder());
    }

    @Override
    public int getEntityLifespan(ItemStack stack, Level level) {

        return Integer.MAX_VALUE;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {

        if (stack.is(UCBlocks.HOURGLASS.get().asItem())) {
            if (!entity.getLevel().isClientSide() && entity.getLevel().getGameTime() % 200 == 0)
                HourglassBlock.searchAroundBlocks(entity.getLevel(), entity.blockPosition(), entity.getLevel().random);
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
