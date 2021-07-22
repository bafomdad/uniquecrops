package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCOreHandler;
import com.bafomdad.uniquecrops.core.enums.EnumArmorMaterial;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemArmorUC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;

public class GlassesPixelItem extends ItemArmorUC implements IBookUpgradeable {


    public GlassesPixelItem() {

        super(EnumArmorMaterial.GLASSES_PIXELS, EquipmentSlotType.HEAD);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(this::onBlockBreak);
    }

    private void onPlayerTick(TickEvent.PlayerTickEvent event) {

        PlayerEntity player = event.player;
        ItemStack pixelGlasses = player.inventory.armorInventory.get(3);
        if (pixelGlasses.getItem() == this) {
            boolean flag = NBTUtils.getBoolean(pixelGlasses, "isActive", false);
            boolean flag2 = isMaxLevel(pixelGlasses);
            if (flag && flag2) {
                if (event.phase == TickEvent.Phase.START && player.world.getGameTime() % 20 == 0) {
                    ChunkPos cPos = new ChunkPos(player.getPosition());
                    if (UCOreHandler.getInstance().getSaveInfo().containsKey(cPos)) {
                        BlockPos pos = UCOreHandler.getInstance().getSaveInfo().get(cPos);
//                        System.out.println("blockpos: " + pos + " / chunkpos: " + cPos);
                        NBTUtils.setLong(pixelGlasses, "orePos", pos.toLong());
                    }
                    if (!event.side.isClient() && !UCOreHandler.getInstance().getSaveInfo().containsKey(cPos))
                        NBTUtils.setLong(pixelGlasses, "orePos", BlockPos.ZERO.toLong());
                }
            }
        }
    }

    private void onBlockBreak(BlockEvent.BreakEvent event) {

        if (event.getPlayer() == null) return;

        PlayerEntity player = event.getPlayer();

        if (player.getHeldItemMainhand().canHarvestBlock(event.getState()) && player.inventory.armorInventory.get(3).getItem() == this) {
            boolean flag = NBTUtils.getBoolean(player.inventory.armorInventory.get(3), "isActive", false);
            boolean flag2 = isMaxLevel(player.inventory.armorInventory.get(3));
            if (flag && flag2 && event.getState().isIn(BlockTags.BASE_STONE_OVERWORLD)) {
                if (UCOreHandler.getInstance().getSaveInfo().containsValue(event.getPos())) {
                    if (!event.getWorld().isRemote())
                        InventoryHelper.spawnItemStack(event.getPlayer().world, event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, new ItemStack(UCItems.DIAMONDS.get()));
                    UCOreHandler.getInstance().removeChunk(event.getPos(), true);
                    if (!player.isCreative())
                        player.inventory.armorInventory.get(3).damageItem(10, player, (entity) -> {});
                }
            }
        }
    }
}
