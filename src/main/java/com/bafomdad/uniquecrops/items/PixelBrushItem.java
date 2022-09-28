package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class PixelBrushItem extends ItemBaseUC {

    public PixelBrushItem() {

        super(UCItems.defaultBuilder().durability(131));
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {

        if (allowdedIn(tab)) {
            ItemStack brush = new ItemStack(this);
            items.add(brush.copy());
            brush.setDamageValue(brush.getMaxDamage());
            items.add(brush);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag whatisthis) {

        if (stack.hasTag() && stack.getTag().contains(UCStrings.TAG_BIOME)) {
            ResourceLocation biomeId = new ResourceLocation(stack.getTag().getString(UCStrings.TAG_BIOME));
            Biome biome = world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).get(biomeId);
            list.add(new TextComponent(ChatFormatting.GREEN + "Biome: " + ChatFormatting.RESET + biome.getRegistryName().getPath()));
        } else {
            list.add(new TextComponent(ChatFormatting.GREEN + "Biome: " + ChatFormatting.RESET + "<NONE>"));
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {

        if (ctx.getItemInHand().getDamageValue() == ctx.getItemInHand().getMaxDamage()) return InteractionResult.PASS;
        if (!ctx.getItemInHand().hasTag() || (ctx.getItemInHand().hasTag() && !ctx.getItemInHand().getTag().contains(UCStrings.TAG_BIOME))) return InteractionResult.PASS;

        ResourceLocation biomeId = new ResourceLocation(ctx.getItemInHand().getTag().getString(UCStrings.TAG_BIOME));
        boolean flag = UCUtils.setBiome(biomeId, ctx.getLevel(), ctx.getClickedPos());
        if (!flag) return InteractionResult.PASS;
        if (!ctx.getLevel().isClientSide()) {
            ctx.getItemInHand().hurtAndBreak(1, ctx.getPlayer(), (player) -> {});
            BlockPos offset = ctx.getClickedPos().relative(ctx.getClickedFace());
            if (ctx.getLevel().isEmptyBlock(offset)) {
                // make a temporary block change so the biome changes stay changed
                ctx.getLevel().setBlockAndUpdate(offset, Blocks.LILY_PAD.defaultBlockState());
                ctx.getLevel().setBlockAndUpdate(offset, Blocks.AIR.defaultBlockState());
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {

        stack.setDamageValue(stack.getMaxDamage());
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {

        return false;
    }
}
