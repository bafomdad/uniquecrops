package com.bafomdad.uniquecrops.core.enums;

import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;

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
    BARRIER(ParticleTypes.BARRIER);

    final IParticleData type;

    EnumParticle(IParticleData type) {

        this.type = type;
    }

    public IParticleData getType() {

        return type;
    }
}
