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
import net.minecraft.block.CropsBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;
import java.util.List;

public class WildwoodStaffItem extends ItemBaseUC {

    private static final String TEMP_CAP = "cropCap";

    public WildwoodStaffItem() {

        super(UCItems.unstackable().rarity(Rarity.EPIC));
        MinecraftForge.EVENT_BUS.addListener(this::onCropGrowth);
    }

    private void onCropGrowth(BlockEvent.CropGrowEvent.Pre event) {

        if (event.getWorld().isRemote()) return;

        BlockPos pos = event.getPos();
        List<PlayerEntity> players = event.getWorld().getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos.add(-7, -3, -7), pos.add(7, 3, 7)));
        for (PlayerEntity player : players) {
            ItemStack itemCap = player.getHeldItemMainhand();
            ItemStack offhand = player.getHeldItemOffhand();
            int distance = (int)player.getDistanceSq(pos.getX(), pos.getY(), pos.getZ());
            int range = (offhand.getItem() instanceof IItemBooster) ? 4 + ((IItemBooster)offhand.getItem()).getRange(offhand) : 3;

            if (distance <= range) {
                itemCap.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
                    if (crop.canAdd()) {
                        int extra = (offhand.getItem() instanceof IItemBooster) ? ((IItemBooster)offhand.getItem()).getRange(offhand) : 0;
                        crop.add(1 + extra);
                        if (player instanceof ServerPlayerEntity)
                            UCPacketHandler.sendTo((ServerPlayerEntity)player, new PacketSyncCap(crop.serializeNBT()));
                        event.setResult(Event.Result.DENY);
                        return;
                    }
                });
            }
        }
        if (!(event.getState().getBlock() instanceof CropsBlock)) return;

        if (!(event.getWorld() instanceof World)) return;
        TileEntity tile = UCUtils.getClosestTile(TileDigger.class, (World)event.getWorld(), pos, 8.0D);
        if (tile instanceof TileDigger) {
            if (((TileDigger)tile).isJobDone()) {
                event.setResult(Event.Result.DEFAULT);
                return;
            }
            boolean flag = ((TileDigger)tile).digBlock((World)event.getWorld());
            if (flag)
                event.setResult(Event.Result.DENY);
            else
                event.setResult(Event.Result.DEFAULT);
        }
    }

    @Override
    public void fillItemGroup(ItemGroup tab, NonNullList<ItemStack> items) {

        if (isInGroup(tab)) {
            items.add(new ItemStack(this));

            ItemStack fullStaff = new ItemStack(this);
            fullStaff.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> crop.setPower(crop.getCapacity()));
            items.add(fullStaff);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag whatisthis) {

        boolean flag = Screen.hasShiftDown();
        stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            if (flag)
                list.add(new StringTextComponent(TextFormatting.GREEN + "Crop Power: " + crop.getPower() + "/" + crop.getCapacity()));
        });
        if (!flag)
            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "<Press shift>"));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {

        stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            if (crop.hasCooldown() && !world.isRemote) {
                crop.setCooldown(crop.getCooldown() - 1);
        }
        });
    }

    @Override
    public CompoundNBT getShareTag(ItemStack stack) {

        CompoundNBT tag = stack.getOrCreateTag();
        stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            tag.put(TEMP_CAP, CPProvider.CROP_POWER.writeNBT(crop, null));
        });
        return tag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {

        if (nbt != null) {
            stack.getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
                if (nbt.contains(TEMP_CAP, 10)) {
                    CPProvider.CROP_POWER.readNBT(crop, null, nbt.getCompound(TEMP_CAP));
                    nbt.remove(TEMP_CAP);
                }
            });
        }
        super.readShareTag(stack, nbt);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return !ItemStack.areItemsEqual(oldStack, newStack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {

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
