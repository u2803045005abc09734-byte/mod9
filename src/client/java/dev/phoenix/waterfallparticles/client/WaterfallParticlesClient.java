
package dev.phoenix.waterfallparticles.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

/**
 * Small, short-lived water-spray particle
 * using Minecraft 26.3's SingleQuadParticle API.
 */
public final class CascadeParticle extends SingleQuadParticle {
    private final SpriteSet sprites;

    private CascadeParticle(
            ClientLevel level,
            double x,
            double y,
            double z,
            SpriteSet sprites,
            double requestedScale
    ) {
        super(level, x, y, z, 0.0, 0.0, 0.0, sprites.first());

        this.sprites = sprites;
        this.lifetime = 9 + this.random.nextInt(4);
        this.quadSize = (float) Math.max(
                0.35,
                Math.min(0.95, requestedScale)
        );

        this.gravity = 0.4F;
        this.xd = this.random.nextDouble() * 0.18 - 0.09;
        this.yd = -0.02 - this.random.nextDouble() * 0.04;
        this.zd = this.random.nextDouble() * 0.18 - 0.09;
        this.hasPhysics = false;

        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.removed) {
            return;
        }

        this.setSpriteFromAge(this.sprites);

        BlockPos pos = BlockPos.containing(
                this.x,
                this.y,
                this.z
        );

        // Remove the particle when it enters a solid block.
        if (this.level.getBlockState(pos).isSolidRender()) {
            this.setAlpha(0.0F);
            this.remove();
            return;
        }

        // Fade out towards the end of the particle's lifetime.
        float progress = (float) this.age / (float) this.lifetime;

        if (progress > 0.55F) {
            this.setAlpha(
                    Math.max(
                            0.0F,
                            1.0F - (progress - 0.55F) / 0.45F
                    )
            );
        }
    }

    @Override
    protected SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    public static final class Provider
            implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(
                SimpleParticleType type,
                ClientLevel level,
                double x,
                double y,
                double z,
                double xAux,
                double yAux,
                double zAux,
                RandomSource random
        ) {
            double scale = 0.45 + random.nextDouble() * 0.55;

            return new CascadeParticle(
                    level,
                    x,
                    y,
                    z,
                    this.sprites,
                    scale
            );
        }
    }
}
