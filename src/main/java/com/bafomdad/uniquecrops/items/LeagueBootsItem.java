package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumArmorMaterial;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemArmorUC;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.ArrayList;
import java.util.List;

public class LeagueBootsItem extends ItemArmorUC implements IBookUpgradeable {

    private static final float DEFAULT_SPEED = 0.055F;
    private static final float JUMP_FACTOR = 0.2F;
    private static final float FALL_BUFFER = 2F;

    private static final List<String> CMONSTEPITUP = new ArrayList<>();

    public LeagueBootsItem() {

        super(EnumArmorMaterial.BOOTS_LEAGUE, EquipmentSlotType.FEET);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerJump);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerFall);
        MinecraftForge.EVENT_BUS.addListener(this::playerTick);
        MinecraftForge.EVENT_BUS.addListener(this::playerLoggedOut);
    }

    private void onPlayerJump(LivingEvent.LivingJumpEvent event) {

        if (event.getEntityLiving() instanceof PlayerEntity) {
            ItemStack boots = event.getEntityLiving().getItemBySlot(EquipmentSlotType.FEET);
            if (boots.getItem() == UCItems.SEVEN_LEAGUE_BOOTS.get()) {
                event.getEntityLiving().setDeltaMovement(event.getEntityLiving().getDeltaMovement().add(0, JUMP_FACTOR, 0));
                event.getEntityLiving().fallDistance -= FALL_BUFFER;
            }
        }
    }

    private void onPlayerFall(LivingFallEvent event) {

        LivingEntity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity) {
            ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
            if (boots.getItem() == this) {
                event.setDistance(Math.max(0, event.getDistance() - FALL_BUFFER));
            }
        }
    }

    private void playerTick(LivingEvent.LivingUpdateEvent event) {

        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)event.getEntityLiving();
            String name = getPlayerStr(player);
            if (CMONSTEPITUP.contains(name)) {
                ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
                if (boots.getItem() != this) {
                    player.maxUpStep = 0.6F;
                    CMONSTEPITUP.remove(name);
                }
            }
        }
    }

    private void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {

        String username = event.getPlayer().getGameProfile().getName();
        CMONSTEPITUP.remove(username + ":false");
        CMONSTEPITUP.remove(username + ":true");
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {

        String name = getPlayerStr(player);
        if (CMONSTEPITUP.contains(name)) {
            if (world.isClientSide) {
                float SPEED = NBTUtils.getFloat(stack, UCStrings.SPEED_MODIFIER, DEFAULT_SPEED);
                if ((player.isOnGround() || player.abilities.flying) && player.zza > 0F && !player.isInWaterOrBubble()) {
                    player.moveRelative(SPEED, new Vector3d(0F, 0F, 1F));
                }
                if (player.isCrouching())
                    player.maxUpStep = 0.60001F; // Botania's ItemTravelBelt uses this value to avoid setting the default value of 0.6F, so I'm not gonna step on any toes here
                else player.maxUpStep = 1.0625F;

                snapForward(player, stack);
            }
        } else {
            CMONSTEPITUP.add(name);
            player.maxUpStep = 1.0625F;
        }
    }

    private void snapForward(PlayerEntity player, ItemStack stack) {

//        if (player.world.provider.getDimension() == UCDimension.dimID) return;

        float speedMod = 0.95F;
        int sprintTicks = NBTUtils.getInt(stack, UCStrings.SPRINTING_TICKS, 0);
        if (sprintTicks > 0) {
            NBTUtils.setInt(stack, UCStrings.SPRINTING_TICKS, sprintTicks - 1);
            return;
        }
        if (player.isSprinting() && !player.abilities.flying) {
            if (NBTUtils.getFloat(stack, UCStrings.SPEED_MODIFIER, DEFAULT_SPEED) == DEFAULT_SPEED) {
                NBTUtils.setFloat(stack, UCStrings.SPEED_MODIFIER, speedMod * Math.max(getLevel(stack), 1));
                return;
            }
            else {
                player.setSprinting(false);
                NBTUtils.setInt(stack, UCStrings.SPRINTING_TICKS, 20);
            }
        }
        if (!player.isSprinting()) {
            NBTUtils.setFloat(stack, UCStrings.SPEED_MODIFIER, DEFAULT_SPEED);
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {

        return false;
    }

    private String getPlayerStr(PlayerEntity player) {

        return player.getGameProfile().getName() + ":" + player.level.isClientSide;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {

        return Rarity.EPIC;
    }
}
