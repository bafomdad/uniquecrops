package com.bafomdad.uniquecrops.core.enums;

import com.bafomdad.uniquecrops.init.UCParticles;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum EnumParticle {

    SMOKE(ParticleTypes.SMOKE),
    PORTAL(ParticleTypes.PORTAL),
    FLAME(ParticleTypes.FLAME),
    WITCH(ParticleTypes.WITCH),
    WATER_DROP(ParticleTypes.FALLING_WATER),
    CLOUD(ParticleTypes.CLOUD),
    EXPLOSION(ParticleTypes.EXPLOSION),
    HEART(ParticleTypes.HEART),
    CRIT(ParticleTypes.CRIT),
    END_ROD(ParticleTypes.END_ROD),
    BARRIER(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.BARRIER))),
    SPARK(UCParticles.SPARK.get());

    final ParticleOptions type;

    EnumParticle(ParticleOptions type) {

        this.type = type;
    }

    public ParticleOptions getType() {

        return type;
    }

    public void setColor(float r, float g, float b) {

        if (this.getType() instanceof Particle p) {
            p.setColor(r, g, b);
        }
    }
}
