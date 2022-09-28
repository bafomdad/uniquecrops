package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.enums.TierItem;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import javax.annotation.Nullable;
import java.util.List;

public class PrecisionPickaxeItem extends PickaxeItem implements IBookUpgradeable {

    public PrecisionPickaxeItem() {

        super(TierItem.PRECISION, 1, -2.8F, UCItems.unstackable());
        MinecraftForge.EVENT_BUS.addListener(this::breakSpawner);
        MinecraftForge.EVENT_BUS.addListener(this::placeSpawner);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flag) {

        if (stack.getItem() instanceof IBookUpgradeable) {
            if (((IBookUpgradeable)stack.getItem()).getLevel(stack) > -1)
                list.add(new TextComponent(ChatFormatting.GOLD + "+" + ((IBookUpgradeable)stack.getItem()).getLevel(stack)));
            else
                list.add(new TextComponent(ChatFormatting.GOLD + "Upgradeable"));
        }
    }

    private void breakSpawner(BlockEvent.BreakEvent event) {

        if (event.getPlayer() == null) return;
        boolean flag = event.getPlayer().getMainHandItem().getItem() == this;
        if (!flag) return;

        ItemStack pick = event.getPlayer().getMainHandItem();
        if (!this.isMaxLevel(pick)) return;

        if (event.getState().getBlock() == Blocks.SPAWNER) {
            event.setCanceled(true);
            BlockEntity tile = event.getWorld().getBlockEntity(event.getPos());
            if (tile instanceof SpawnerBlockEntity) {
                ItemStack stack = new ItemStack(event.getState().getBlock());
                if (!event.getWorld().isClientSide() && event.getWorld() instanceof Level) {
                    NBTUtils.setCompound(stack, "Spawner", tile.serializeNBT());
                    Containers.dropItemStack((Level)event.getWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, stack);
                }
            }
            event.getWorld().removeBlock(event.getPos(), false);
            if (event.getPlayer() instanceof ServerPlayer)
                event.getPlayer().getMainHandItem().hurt(1, event.getWorld().getRandom(), (ServerPlayer)event.getPlayer());
        }
    }

    private void placeSpawner(PlayerInteractEvent.RightClickBlock event) {

        if (event.getItemStack().getItem()!= Blocks.SPAWNER.asItem()) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getWorld() instanceof Level)) return;

        ItemStack stack = ((Player)event.getEntity()).getItemInHand(event.getHand());
        if (stack.getItem() == Blocks.SPAWNER.asItem() && stack.hasTag() && stack.getTag().contains("Spawner")) {
            BlockState spawner = Blocks.SPAWNER.defaultBlockState();
            BlockPos pos = event.getPos().relative(event.getFace());
            event.getWorld().setBlockAndUpdate(pos, spawner);
            BlockEntity tile = event.getWorld().getBlockEntity(pos);
            CompoundTag tag = stack.getTag().getCompound("Spawner");
            tag.putInt("x", pos.getX());
            tag.putInt("y", pos.getY());
            tag.putInt("z", pos.getZ());
            tile.load(tag);
            event.getPlayer().swing(event.getHand());
            event.setCanceled(true);
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {

        stack.enchant(Enchantments.SILK_TOUCH, 1);
    }
}
