package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.core.DyeUtils;
import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.List;
import java.util.Random;

public class EmblemRainbow extends ItemCurioUC {

    public EmblemRainbow() {

        MinecraftForge.EVENT_BUS.addListener(this::onSheared);
    }

    private void onSheared(PlayerInteractEvent.EntityInteractSpecific event) {

        if (!hasCurio(event.getPlayer())) return;

        if (!(event.getTarget() instanceof Shearable)) return;
        if (!(event.getTarget() instanceof Sheep)) return;
        if (!(event.getItemStack().getItem() instanceof ShearsItem) || ((Sheep)event.getTarget()).isSheared()) return;

        int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, event.getItemStack());
        if (!event.getWorld().isClientSide) {
            List<ItemStack> wools = ((Sheep)event.getTarget()).onSheared(event.getPlayer(), event.getItemStack(), event.getWorld(), event.getPos(), fortune);
            for (ItemStack is : wools) {
                Random rand = new Random();
                ItemStack stack = new ItemStack(DyeUtils.WOOL_BY_COLOR.get(DyeColor.byId(rand.nextInt(15))));
                event.getWorld().addFreshEntity(new ItemEntity(event.getWorld(), event.getTarget().getX(), event.getTarget().getY(), event.getTarget().getZ(), stack));
                if (event.getPlayer() instanceof ServerPlayer)
                    event.getItemStack().hurt(1, rand, (ServerPlayer)event.getPlayer());
            }
        }
    }
}
