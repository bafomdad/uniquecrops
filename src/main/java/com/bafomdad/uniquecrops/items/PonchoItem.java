package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.enums.EnumArmorMaterial;
import com.bafomdad.uniquecrops.init.UCPotions;
import com.bafomdad.uniquecrops.items.base.ItemArmorUC;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

public class PonchoItem extends ItemArmorUC implements IBookUpgradeable {

    public PonchoItem() {

        super(EnumArmorMaterial.PONCHO, EquipmentSlot.CHEST);
        MinecraftForge.EVENT_BUS.addListener(this::checkSetTarget);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {

        player.flyingSpeed = (0.025F + 1 * 0.02F);
        if (player.getDeltaMovement().y < -0.175F && !player.isOnGround() && !player.getAbilities().flying && !player.isCrouching()) {
            float fallVel = -0.175F;
            player.setDeltaMovement(player.getDeltaMovement().x, fallVel, player.getDeltaMovement().z);
            player.fallDistance = 0;
        }
    }

    private void checkSetTarget(LivingSetAttackTargetEvent event) {

        if (event.getTarget() == null) return;
        if (!(event.getTarget() instanceof Player) || event.getTarget() instanceof FakePlayer) return;
        if (!(event.getEntity() instanceof Mob)) return;

        Player player = (Player)event.getTarget();
        Mob ent = (Mob)event.getEntity();
        if (player.getEffect(UCPotions.IGNORANCE.get()) != null) {
            ent.setTarget(null);
            ent.setLastHurtByMob(null);
            return;
        }
        boolean flag = player.getInventory().armor.get(2).getItem() == this && this.isMaxLevel(player.getInventory().armor.get(2));
        if (flag && ent.isPickable() && !(ent instanceof Guardian || ent instanceof Shulker)) {
            ent.setTarget(null);
            ent.setLastHurtByMob(null);
        }
    }
}
