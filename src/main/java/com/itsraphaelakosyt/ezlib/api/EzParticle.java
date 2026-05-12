package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;

/**
 * EzParticle – Fluent builder for spawning client-side particles.
 *
 * <pre>{@code
 * EzParticle.spawn(ParticleTypes.HAPPY_VILLAGER)
 *     .atPlayer()
 *     .count(10)
 *     .spread(0.5, 0.5, 0.5)
 *     .build();
 * }</pre>
 */
public final class EzParticle {

    private final ParticleOptions type;
    private double x, y, z;
    private double spreadX = 0.3, spreadY = 0.3, spreadZ = 0.3;
    private int count = 1;
    private boolean atPlayer = false;

    private EzParticle(ParticleOptions type) {
        this.type = type;
    }

    public static EzParticle spawn(ParticleOptions type) {
        return new EzParticle(type);
    }

    // ── Position ──────────────────────────────────────────────────────────────

    public EzParticle at(double x, double y, double z) {
        this.x = x; this.y = y; this.z = z;
        this.atPlayer = false;
        return this;
    }

    /** Spawn the particles at the local player's position. */
    public EzParticle atPlayer() {
        this.atPlayer = true;
        return this;
    }

    // ── Options ───────────────────────────────────────────────────────────────

    public EzParticle count(int count)  { this.count = count; return this; }

    public EzParticle spread(double sx, double sy, double sz) {
        this.spreadX = sx; this.spreadY = sy; this.spreadZ = sz;
        return this;
    }

    // ── Emit ──────────────────────────────────────────────────────────────────

    public void build() {
        Level world  = EzClient.world();
        LocalPlayer p = EzClient.player();
        if (world == null) return;

        double px = atPlayer && p != null ? p.getX() : x;
        double py = atPlayer && p != null ? p.getY() : y;
        double pz = atPlayer && p != null ? p.getZ() : z;

        for (int i = 0; i < count; i++) {
            world.addParticle(type, px, py, pz, spreadX, spreadY, spreadZ);
        }
    }
}
