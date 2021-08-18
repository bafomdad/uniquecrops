package com.bafomdad.uniquecrops.items.base;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSeedsUC extends BlockNamedItem {

    public ItemSeedsUC(BaseCropsBlock block) {

        super(block, UCItems.defaultBuilder());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {

        list.add(new StringTextComponent("Bonemealable: ").withStyle(TextFormatting.GRAY).append(tf(getCrop().isBonemealable())));
        list.add(new StringTextComponent("Click Harvest: ").withStyle(TextFormatting.GRAY).append(tf(getCrop().isClickHarvest())));
        list.add(new StringTextComponent("Ignores Growth Restrictions: ").withStyle(TextFormatting.GRAY).append(tf(getCrop().isIgnoreGrowthRestrictions())));
    }

    private BaseCropsBlock getCrop() {

        return (BaseCropsBlock)this.getBlock();
    }

    private ITextComponent tf(boolean flag) {

        return new StringTextComponent(String.valueOf(flag)).withStyle(flag ? TextFormatting.GREEN : TextFormatting.RED);
    }

    @Override
    public ActionResultType useOn(ItemUseContext ctx) {

        return super.useOn(ctx);
    }
}
