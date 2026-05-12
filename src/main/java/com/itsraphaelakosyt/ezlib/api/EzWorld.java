package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.world.level.Level;

/** EzWorld – safe world info helpers. */
public final class EzWorld {
    private EzWorld() {}

    public static boolean isDay() { return false; }
    public static boolean isNight() { return !isDay(); }
    public static long getTime() { return 0L; }

    public static boolean isRaining() {
        Level world = EzClient.world();
        return world != null && world.isRaining();
    }

    public static String getBiome() { return "unknown"; }
}
