package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.ArrayList;
import java.util.List;

public class AnkhItem extends ItemBaseUC {

    private static final int[][] INV = new int[][] {
            { 9, 10, 11, 12, 13, 14, 15, 16, 17 },
            { 18, 19, 20, 21, 22, 23, 24, 25, 26 },
            { 27, 28, 29, 30, 31, 32, 33, 34, 35 },
            { 0, 1, 2, 3, 4, 5, 6, 7, 8 }
    };

    public AnkhItem() {

        super(UCItems.defaultBuilder().maxDamage(6));
        MinecraftForge.EVENT_BUS.addListener(this::checkPlayerDeath);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerClone);
    }

    private void checkPlayerDeath(LivingDeathEvent event) {

        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)event.getEntityLiving();
            this.saveAnkhItems(player);

            CompoundNBT tag = player.getPersistentData();
            if (tag.contains("hasSacrificed") && !tag.getBoolean("hasSacrificed")) {
                tag.putBoolean("hasSacrificed", true);
                if (!player.world.isRemote)
                    InventoryHelper.spawnItemStack(player.world, player.getPosX(), player.getPosY(), player.getPosZ(), new ItemStack(UCItems.STEVE_HEART.get()));
            }
        }
    }

    private void onPlayerClone(PlayerEvent.Clone event) {

        if (event.isWasDeath() && !event.getPlayer().world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            PlayerEntity oldPlayer = event.getOriginal();
            PlayerEntity newPlayer = event.getPlayer();

            this.putAnkhItems(oldPlayer, newPlayer);

            CompoundNBT oldTag = oldPlayer.getPersistentData();
            CompoundNBT tag = newPlayer.getPersistentData();
            if (oldTag.contains(UCStrings.TAG_GROWTHSTAGES))
                tag.put(UCStrings.TAG_GROWTHSTAGES, oldTag.getList(UCStrings.TAG_GROWTHSTAGES, 10).copy());
            if (oldTag.contains("hasSacrificed"))
                tag.putBoolean("hasSacrificed", oldTag.getBoolean("hasSacrificed"));
            if (oldTag.contains(UCStrings.TAG_ABSTRACT))
                tag.putInt(UCStrings.TAG_ABSTRACT, oldTag.getInt(UCStrings.TAG_ABSTRACT));
        }
    }

    private List<Integer> getSurroundingSlots(int slotIndex) {

        List<Integer> slotList = new ArrayList();
        if (slotIndex > 35 || slotIndex < 0) return slotList;

        for (int i = 0; i < INV.length; i++) {
            for (int j = 0; j < INV[i].length; j++) {
                if (INV[i][j] == slotIndex) {
                    for (int dx = (i > 0 ? -1 : 0); dx <= (i < (INV.length - 1) ? 1 : 0); ++dx) {
                        for (int dy = (j > 0 ? -1 : 0); dy <= (j < (INV[i].length - 1) ? 1 : 0); ++dy) {
                            if (dx != 0 || dy != 0)
                                slotList.add(INV[i + dx][j + dy]);
                        }
                    }
                }
            }
        }
        slotList.add(slotIndex);
        return slotList;
    }

    private void saveAnkhItems(PlayerEntity player) {

        CompoundNBT playerTag = player.getPersistentData();
        if (playerTag.contains(UCStrings.SAVED_ITEMS))
            playerTag.remove(UCStrings.SAVED_ITEMS);

        ListNBT tagList = new ListNBT();
        for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
            ItemStack ankh = player.inventory.mainInventory.get(i);
            if (!ankh.isEmpty() && ankh.getItem() == UCItems.ANKH.get()) {
                List<Integer> slots = getSurroundingSlots(i);
                if (!slots.isEmpty()) {
                    for (int j = 0; j < slots.size(); j++) {
                        int slot = slots.get(j);
                        ItemStack stack = player.inventory.mainInventory.get(slot);
                        if (!stack.isEmpty()) {
                            CompoundNBT tag = new CompoundNBT();
                            tag.putInt("Slot", slot);
                            stack.write(tag);
                            tagList.add(tag);
                            if (!player.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY))
                                player.inventory.mainInventory.set(slot, ItemStack.EMPTY);
                        }
                    }
                }
                break;
            }
        }
        playerTag.put(UCStrings.SAVED_ITEMS, tagList);
    }

    private void putAnkhItems(PlayerEntity oldPlayer, PlayerEntity newPlayer) {

        CompoundNBT playerTag = oldPlayer.getPersistentData();
        ListNBT tagList = playerTag.getList(UCStrings.SAVED_ITEMS, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundNBT tag = tagList.getCompound(i);
            int slot = tag.getInt("Slot");
            ItemStack newStack = ItemStack.read(tag);
            if (newStack.getItem() == UCItems.ANKH.get())
                newStack.damageItem(1, newPlayer, (entity) -> {});
            newPlayer.inventory.mainInventory.set(slot, newStack);
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {

        return false;
    }
}
