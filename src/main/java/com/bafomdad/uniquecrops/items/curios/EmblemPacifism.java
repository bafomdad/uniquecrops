package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class EmblemPacifism extends ItemCurioUC {

    public EmblemPacifism() {

        MinecraftForge.EVENT_BUS.addListener(this::noDamage);
    }

    private void noDamage(LivingAttackEvent event) {

        PlayerEntity player = null;
        if (event.getEntityLiving() instanceof PlayerEntity) player = (PlayerEntity)event.getEntityLiving();
        if (event.getSource().getDirectEntity() instanceof PlayerEntity) player = (PlayerEntity)event.getSource().getDirectEntity();
        if (player == null) return;

        if (!hasCurio(player)) return;

        if (event.getEntityLiving() instanceof PlayerEntity && event.getSource().getDirectEntity() != null || event.getSource().getDirectEntity() == player)
            event.setCanceled(true);
    }
}
