package com.bafomdad.uniquecrops.items.curios;

import com.bafomdad.uniquecrops.items.base.ItemCurioUC;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class EmblemPacifism extends ItemCurioUC {

    public EmblemPacifism() {

        MinecraftForge.EVENT_BUS.addListener(this::noDamage);
    }

    private void noDamage(LivingAttackEvent event) {

        Player player = null;
        if (event.getEntityLiving() instanceof Player) player = (Player)event.getEntityLiving();
        if (event.getSource().getDirectEntity() instanceof Player) player = (Player)event.getSource().getDirectEntity();
        if (player == null) return;

        if (!hasCurio(player)) return;

        if (event.getEntityLiving() instanceof Player && event.getSource().getDirectEntity() != null || event.getSource().getDirectEntity() == player)
            event.setCanceled(true);
    }
}
