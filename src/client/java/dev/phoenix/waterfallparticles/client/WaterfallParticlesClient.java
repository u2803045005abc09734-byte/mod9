package dev.phoenix.waterfallparticles.client;

import dev.phoenix.waterfallparticles.WaterfallParticles;
import dev.phoenix.waterfallparticles.client.particle.CascadeParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.FluidState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/** Client-only scanner which adds a little spray beside vertical water columns. */
public final class WaterfallParticlesClient implements ClientModInitializer {
    private static int tickCounter;

    @Override
    public void onInitializeClient() {
        ParticleProviderRegistry.getInstance().register(WaterfallParticles.CASCADE, CascadeParticle.Provider::new);
        ClientTickEvents.END_CLIENT_TICK.register(WaterfallParticlesClient::onClientTick);
    }

    private static void onClientTick(Minecraft client) {
        if (client.level == null || client.player == null) {
            return;
        }

        // Scan only every fourth tick to keep the visual effect inexpensive.
        if (++tickCounter % 4 != 0) {
            return;
        }

        ClientLevel level = client.level;
        BlockPos center = client.player.blockPosition();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int dx = -5; dx <= 5; dx++) {
            for (int dz = -5; dz <= 5; dz++) {
                for (int dy = -4; dy <= 5; dy++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    FluidState current = level.getFluidState(pos);

                    // Only target flowing water with more water directly above it;
                    // that filters out most ordinary lake and river surfaces.
                    if (!current.is(FluidTags.WATER) || current.isSource()) {
                        continue;
                    }
                    if (!level.getFluidState(pos.above()).is(FluidTags.WATER)) {
                        continue;
                    }
                    if (random.nextInt(9) != 0) {
                        continue;
                    }

                    List<Direction> openSides = new ArrayList<>(4);
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        BlockPos side = pos.relative(direction);
                        if (level.getFluidState(side).isEmpty() && level.getBlockState(side).isAir()) {
                            openSides.add(direction);
                        }
                    }
                    if (openSides.isEmpty()) {
                        continue;
                    }

                    Direction side = openSides.get(random.nextInt(openSides.size()));
                    double px = pos.getX() + 0.5 + side.getStepX() * 0.51 + (random.nextDouble() - 0.5) * 0.08;
                    double py = pos.getY() + 0.15 + random.nextDouble() * 0.7;
                    double pz = pos.getZ() + 0.5 + side.getStepZ() * 0.51 + (random.nextDouble() - 0.5) * 0.08;

                    level.addParticle(WaterfallParticles.CASCADE, px, py, pz, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
