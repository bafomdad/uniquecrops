package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Random;

public class EmblemTransformation extends ItemCurioUC {

    public EmblemTransformation() {

        MinecraftForge.EVENT_BUS.addListener(this::onHitEntity);
    }

    private void onHitEntity(LivingHurtEvent event) {

        if (event.getAmount() <= 0 || event.getEntityLiving() instanceof PlayerEntity) return;
        if (!(event.getSource().getDirectEntity() instanceof PlayerEntity)) return;
        if (!hasCurio((LivingEntity)event.getSource().getDirectEntity())) return;

        Random rand = new Random();
        if (rand.nextInt(100) == 0) {
            LivingEntity elb = event.getEntityLiving();
            EntityType type = Registry.ENTITY_TYPE.byId(rand.nextInt(Registry.ENTITY_TYPE.entrySet().size()));
            Entity entity = type.create(elb.getCommandSenderWorld());
            if (!(entity instanceof LivingEntity)) return;

            entity.absMoveTo(elb.getX(), elb.getY(), elb.getZ(), elb.yRot, elb.xRot);
            elb.getCommandSenderWorld().addFreshEntity(entity);
            elb.remove();
        }
    }
}
