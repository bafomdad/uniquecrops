package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nullable;
import java.util.List;

public class MultiblockBookItem extends ItemBaseUC {

    public MultiblockBookItem() {

        super(UCItems.unstackable().rarity(Rarity.RARE));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag whatisthis) {

        list.add(getEdition().copy().withStyle(TextFormatting.GOLD));
    }

    private ITextComponent getEdition() {

        return PatchouliAPI.get().getSubtitle(UCItems.BOOK_MULTIBLOCK.getId());
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getMainHandItem();

        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity sPlayer = (ServerPlayerEntity)player;
            PatchouliAPI.get().openBookGUI(sPlayer, UCItems.BOOK_MULTIBLOCK.getId());
        }
        return ActionResult.success(stack);
    }
}
