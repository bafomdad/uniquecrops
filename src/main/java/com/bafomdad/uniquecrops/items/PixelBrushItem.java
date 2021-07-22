package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class PixelBrushItem extends ItemBaseUC {

    public PixelBrushItem() {

        super(UCItems.defaultBuilder().maxDamage(131));
    }

    @Override
    public void fillItemGroup(ItemGroup tab, NonNullList<ItemStack> items) {

        if (isInGroup(tab)) {
            ItemStack brush = new ItemStack(this);
            items.add(brush.copy());
            brush.setDamage(brush.getMaxDamage());
            items.add(brush);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag whatisthis) {

        if (stack.hasTag() && stack.getTag().contains(UCStrings.TAG_BIOME)) {
            ResourceLocation biomeId = new ResourceLocation(stack.getTag().getString(UCStrings.TAG_BIOME));
            Biome biome = world.func_241828_r().getRegistry(Registry.BIOME_KEY).getOrDefault(biomeId);
            list.add(new StringTextComponent(TextFormatting.GREEN + "Biome: " + TextFormatting.RESET + biome.getRegistryName().getPath()));
        } else {
            list.add(new StringTextComponent(TextFormatting.GREEN + "Biome: " + TextFormatting.RESET + "<NONE>"));
        }
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {

        if (ctx.getItem().getDamage() == ctx.getItem().getMaxDamage()) return ActionResultType.PASS;
        if (!ctx.getItem().hasTag() || (ctx.getItem().hasTag() && !ctx.getItem().getTag().contains(UCStrings.TAG_BIOME))) return ActionResultType.PASS;

        ResourceLocation biomeId = new ResourceLocation(ctx.getItem().getTag().getString(UCStrings.TAG_BIOME));
        boolean flag = UCUtils.setBiome(biomeId, ctx.getWorld(), ctx.getPos());
        if (!flag) return ActionResultType.PASS;
        if (!ctx.getWorld().isRemote)
            ctx.getItem().damageItem(1, ctx.getPlayer(), (player) -> {});

        return ActionResultType.SUCCESS;
    }

    @Override
    public void onCreated(ItemStack stack, World world, PlayerEntity player) {

        stack.setDamage(stack.getMaxDamage());
    }
}
