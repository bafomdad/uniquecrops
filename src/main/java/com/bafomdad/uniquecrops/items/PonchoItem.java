package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.enums.EnumArmorMaterial;
import com.bafomdad.uniquecrops.init.UCPotions;
import com.bafomdad.uniquecrops.items.base.ItemArmorUC;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

public class PonchoItem extends ItemArmorUC implements IBookUpgradeable {

    public PonchoItem() {

        super(EnumArmorMaterial.PONCHO, EquipmentSlotType.CHEST);
        MinecraftForge.EVENT_BUS.addListener(this::checkSetTarget);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {

        player.jumpMovementFactor = (0.025F + 1 * 0.02F);
        if (player.getMotion().y < -0.175F && !player.isOnGround() && !player.abilities.isFlying && !player.isSneaking()) {
            float fallVel = -0.175F;
            player.setMotion(player.getMotion().x, fallVel, player.getMotion().z);
            player.fallDistance = 0;
        }
    }

    private void checkSetTarget(LivingSetAttackTargetEvent event) {

        if (event.getTarget() == null) return;
        if (!(event.getTarget() instanceof PlayerEntity) || event.getTarget() instanceof FakePlayer) return;
        if (!(event.getEntity() instanceof MobEntity)) return;

        PlayerEntity player = (PlayerEntity)event.getTarget();
        MobEntity ent = (MobEntity)event.getEntity();
        if (player.getActivePotionEffect(UCPotions.IGNORANCE.get()) != null) {
            ent.setAttackTarget(null);
            ent.setRevengeTarget(null);
            return;
        }
        boolean flag = player.inventory.armorInventory.get(2).getItem() == this && this.isMaxLevel(player.inventory.armorInventory.get(2));
        if (flag && ent.isNonBoss() && !(ent instanceof GuardianEntity || ent instanceof ShulkerEntity)) {
            ent.setAttackTarget(null);
            ent.setRevengeTarget(null);
        }
    }
}
