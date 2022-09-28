package com.bafomdad.uniquecrops.render.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SparkFX extends TextureSheetParticle {

    private SparkFX(ClientLevel world, double xPos, double yPos, double zPos, float red, float green, float blue, TextureAtlasSprite sprite) {

        super(world, xPos, yPos, zPos);
        float size = 0.75F;
        this.setSize(size, size);
        this.setColor(red, green, blue);
        this.quadSize *= (this.random.nextFloat() * 0.5F + 0.5F);
        this.hasPhysics = false;
        this.age = (int)(28D / (this.random.nextFloat() * 0.3D + 0.7D) * 1);
        this.alpha = 0.5F;
        this.sprite = sprite;
    }

    @Override
    public void tick() {

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age-- <= 0)
            this.remove();
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {

        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet sprites;

        public Factory(SpriteSet sprite) {

            this.sprites = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel world, double xPos, double yPos, double zPos, double r, double g, double b) {

            return new SparkFX(world, xPos, yPos, zPos, (float)r, (float)g, (float)b, sprites.get(0, 1));
        }
    }
}
