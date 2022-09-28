package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.ICropPower;
import com.bafomdad.uniquecrops.api.IItemBooster;
import com.bafomdad.uniquecrops.blocks.tiles.TileDigger;
import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import com.bafomdad.uniquecrops.network.PacketSyncCap;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;
import java.util.List;

public class StaffWildwoodItem extends ItemBaseUC {

    private static final String TEMP_CAP = "cropCap";

    public StaffWildwoodItem() {

        super(UCItems.unstackable().rarity(Rarity.EPIC));
        MinecraftForge.EVENT_BUS.addListener(this::onCropGrowth);
    }

    private void onCropGrowth(BlockEvent.CropGrowEvent.Pre event) {

        if (event.getWorld().isClientSide()) return;

        BlockPos pos = event.getPos();
        List<Player> players = event.getWorld().getEntitiesOfClass(Player.class, new AABB(pos.offset(-7, -3, -7), pos.offset(7, 3, 7)));
        for (Player player : players) {
            ItemStack itemCap = player.getMainHandItem();
            ItemStack offhand = player.getOffhandItem();
            int distance = (int)player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
            int range = (offhand.getItem() instanceof IItemBooster) ? 4 + ((IItemBooster)offhand.getItem()).getRange(offhand) : 3;

            if (distance <= range) {
                itemCap.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
                    if (crop.canAdd()) {
                        int extra = (offhand.getItem() instanceof IItemBooster) ? ((IItemBooster)offhand.getItem()).getPower(offhand) : 0;
                        crop.add(1 + extra);
                        if (player instanceof ServerPlayer)
                            UCPacketHandler.sendTo((ServerPlayer)player, new PacketSyncCap(crop.serializeNBT()));
                        event.setResult(Event.Result.DENY);
                        return;
                    }
                });
            }
        }
        if (!(event.getState().getBlock() instanceof CropBlock)) return;

        if (!(event.getWorld() instanceof Level)) return;
        BlockEntity tile = UCUtils.getClosestTile(TileDigger.class, (Level)event.getWorld(), pos, 8.0D);
        if (tile instanceof TileDigger) {
            if (((TileDigger)tile).isJobDone()) {
                event.setResult(Event.Result.DEFAULT);
                return;
            }
            boolean flag = ((TileDigger)tile).digBlock((Level)event.getWorld());
            if (flag)
                event.setResult(Event.Result.DENY);
            else
                event.setResult(Event.Result.DEFAULT);
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {

        if (allowdedIn(tab)) {
            items.add(new ItemStack(this));

            ItemStack fullStaff = new ItemStack(this);
            fullStaff.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> crop.setPower(crop.getCapacity()));
            items.add(fullStaff);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag whatisthis) {

        boolean flag = Screen.hasShiftDown();
        stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            if (flag)
                list.add(new TextComponent(ChatFormatting.GREEN + "Crop Power: " + crop.getPower() + "/" + crop.getCapacity()));
        });
        if (!flag)
            list.add(new TextComponent(ChatFormatting.LIGHT_PURPLE + "<Press shift>"));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {

        stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            if (crop.hasCooldown() && !world.isClientSide) {
                crop.setCooldown(crop.getCooldown() - 1);
        }
        });
    }

    @Override
    public CompoundTag getShareTag(ItemStack stack) {

        CompoundTag tag = stack.getOrCreateTag();
        stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            tag.put(TEMP_CAP, crop.serializeNBT());
        });
        return tag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {

        if (nbt != null) {
            stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
                if (nbt.contains(TEMP_CAP, 10)) {
                    crop.deserializeNBT(nbt.getCompound(TEMP_CAP));
                    nbt.remove(TEMP_CAP);
                }
            });
        }
        super.readShareTag(stack, nbt);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return !ItemStack.isSame(oldStack, newStack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {

        if (CPProvider.CROP_POWER == null)
            return null;

        return new CPProvider();
    }

    public static boolean adjustPower(ItemStack stack, int amount) {

        LazyOptional<ICropPower> cap = stack.getCapability(CPProvider.CROP_POWER, null);
        if (cap.isPresent()) {
            if (cap.resolve().get().getPower() >= amount) {
                cap.resolve().get().remove(amount);
                return true;
            }
        }
        return false;
    }
}
