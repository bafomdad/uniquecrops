package com.bafomdad.uniquecrops.items.base;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class ItemSeedsUC extends BlockNamedItem {

    public ItemSeedsUC(BaseCropsBlock block) {

        super(block, UCItems.defaultBuilder());
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {

        return super.onItemUse(ctx);
    }
}
