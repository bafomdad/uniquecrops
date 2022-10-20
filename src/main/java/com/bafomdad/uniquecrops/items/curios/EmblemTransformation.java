package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Random;

public class EmblemTransformation extends ItemCurioUC {

    public EmblemTransformation() {

        MinecraftForge.EVENT_BUS.addListener(this::onHitEntity);
    }

    @SuppressWarnings("deprecation")
    private void onHitEntity(LivingHurtEvent event) {

        if (event.getAmount() <= 0 || event.getEntityLiving() instanceof Player) return;
        if (!(event.getSource().getDirectEntity() instanceof Player)) return;
        if (!hasCurio((LivingEntity)event.getSource().getDirectEntity())) return;

        Random rand = new Random();
        if (rand.nextInt(100) == 0) {
            LivingEntity elb = event.getEntityLiving();
            EntityType<?> type = Registry.ENTITY_TYPE.byId(rand.nextInt(Registry.ENTITY_TYPE.entrySet().size()));
            Entity entity = type.create(elb.getCommandSenderWorld());
            if (!(entity instanceof LivingEntity)) return;
            if (entity instanceof WitherBoss || entity instanceof EnderDragon) return;

            entity.absMoveTo(elb.getX(), elb.getY(), elb.getZ(), elb.yRotO, elb.xRotO);
            elb.getCommandSenderWorld().addFreshEntity(entity);
            elb.discard();
        }
    }
}
