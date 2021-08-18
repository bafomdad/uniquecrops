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

        super(UCItems.defaultBuilder().durability(131));
    }

    @Override
    public void fillItemCategory(ItemGroup tab, NonNullList<ItemStack> items) {

        if (allowdedIn(tab)) {
            ItemStack brush = new ItemStack(this);
            items.add(brush.copy());
            brush.setDamageValue(brush.getMaxDamage());
            items.add(brush);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag whatisthis) {

        if (stack.hasTag() && stack.getTag().contains(UCStrings.TAG_BIOME)) {
            ResourceLocation biomeId = new ResourceLocation(stack.getTag().getString(UCStrings.TAG_BIOME));
            Biome biome = world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).get(biomeId);
            list.add(new StringTextComponent(TextFormatting.GREEN + "Biome: " + TextFormatting.RESET + biome.getRegistryName().getPath()));
        } else {
            list.add(new StringTextComponent(TextFormatting.GREEN + "Biome: " + TextFormatting.RESET + "<NONE>"));
        }
    }

    @Override
    public ActionResultType useOn(ItemUseContext ctx) {

        if (ctx.getItemInHand().getDamageValue() == ctx.getItemInHand().getMaxDamage()) return ActionResultType.PASS;
        if (!ctx.getItemInHand().hasTag() || (ctx.getItemInHand().hasTag() && !ctx.getItemInHand().getTag().contains(UCStrings.TAG_BIOME))) return ActionResultType.PASS;

        ResourceLocation biomeId = new ResourceLocation(ctx.getItemInHand().getTag().getString(UCStrings.TAG_BIOME));
        boolean flag = UCUtils.setBiome(biomeId, ctx.getLevel(), ctx.getClickedPos());
        if (!flag) return ActionResultType.PASS;
        if (!ctx.getLevel().isClientSide)
            ctx.getItemInHand().hurtAndBreak(1, ctx.getPlayer(), (player) -> {});

        return ActionResultType.SUCCESS;
    }

    @Override
    public void onCraftedBy(ItemStack stack, World world, PlayerEntity player) {

        stack.setDamageValue(stack.getMaxDamage());
    }
}
