package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCOreHandler;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
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
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Random;

public class GlassesPixelItem extends ItemArmorUC implements IBookUpgradeable {


    public GlassesPixelItem() {

        super(EnumArmorMaterial.GLASSES_PIXELS, EquipmentSlotType.HEAD);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(this::onBlockBreak);
    }

    private void onPlayerTick(TickEvent.PlayerTickEvent event) {

        PlayerEntity player = event.player;
        ItemStack pixelGlasses = player.inventory.armor.get(3);
        if (pixelGlasses.getItem() == this) {
            boolean flag = NBTUtils.getBoolean(pixelGlasses, "isActive", false);
            boolean flag2 = isMaxLevel(pixelGlasses);
            if (flag && flag2) {
                if (event.phase == TickEvent.Phase.START && player.level.getGameTime() % 20 == 0) {
                    ChunkPos cPos = new ChunkPos(player.blockPosition());
                    if (UCOreHandler.getInstance().getSaveInfo().containsKey(cPos)) {
                        BlockPos pos = UCOreHandler.getInstance().getSaveInfo().get(cPos);
                        NBTUtils.setLong(pixelGlasses, "orePos", pos.asLong());
                    }
                    if (!event.side.isClient() && !UCOreHandler.getInstance().getSaveInfo().containsKey(cPos))
                        NBTUtils.setLong(pixelGlasses, "orePos", BlockPos.ZERO.asLong());
                }
            }
        }
        if (player.getPersistentData().contains(UCStrings.TAG_ABSTRACT)) {
            if (event.phase == TickEvent.Phase.START && player.level.random.nextInt(1000) == 0) {
                Random rand = new Random();
                if (rand.nextInt(10) != 0) {
                    ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(UCItems.ABSTRACT.get()));
                    if (!event.player.level.isClientSide)
                        UCUtils.setAbstractCropGrowth(event.player, -1);
                }
            }
        }
    }

    private void onBlockBreak(BlockEvent.BreakEvent event) {

        if (event.getPlayer() == null) return;

        PlayerEntity player = event.getPlayer();

        if (player.getMainHandItem().isCorrectToolForDrops(event.getState()) && player.inventory.armor.get(3).getItem() == this) {
            boolean flag = NBTUtils.getBoolean(player.inventory.armor.get(3), "isActive", false);
            boolean flag2 = isMaxLevel(player.inventory.armor.get(3));
            if (flag && flag2 && event.getState().is(BlockTags.BASE_STONE_OVERWORLD)) {
                if (UCOreHandler.getInstance().getSaveInfo().containsValue(event.getPos())) {
                    if (!event.getWorld().isClientSide())
                        InventoryHelper.dropItemStack(event.getPlayer().level, event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, new ItemStack(UCItems.DIAMONDS.get()));
                    UCOreHandler.getInstance().removeChunk(event.getPos(), true);
                    if (!player.isCreative())
                        player.inventory.armor.get(3).hurtAndBreak(10, player, (entity) -> {});
                }
            }
        }
    }
}
