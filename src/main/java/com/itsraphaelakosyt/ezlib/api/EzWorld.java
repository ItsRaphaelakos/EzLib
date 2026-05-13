package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.lang.reflect.Method;

/**
 * EzWorld - Convenient queries about the current client-side world.
 *
 * <pre>{@code
 * boolean day     = EzWorld.isDay();
 * boolean raining = EzWorld.isRaining();
 * long    time    = EzWorld.getTime();
 * String  biome   = EzWorld.getBiome();
 * }</pre>
 */
public final class EzWorld {

    private EzWorld() {}

    /** Returns true if it is currently daytime. */
    public static boolean isDay() {
        long t = getTime() % 24000L;
        return t >= 0 && t < 12000L;
    }

    /** Returns true if it is currently night-time. */
    public static boolean isNight() {
        return !isDay();
    }

    /** Returns the current day-time tick, or 0 if unavailable. */
    public static long getTime() {
        Level world = EzClient.world();
        if (world == null) return 0L;

        Long time = callLong(world, "getDayTime");
        if (time != null) return time % 24000L;

        time = callLong(world, "getTimeOfDay");
        if (time != null) return time % 24000L;

        time = callLong(world, "dayTime");
        if (time != null) return time % 24000L;

        return 0L;
    }

    /** Returns true if it is currently raining or snowing in the world. */
    public static boolean isRaining() {
        Level world = EzClient.world();
        if (world == null) return false;

        Boolean raining = callBoolean(world, "isRaining");
        return raining != null && raining;
    }

    /**
     * Returns a human-readable name for the biome the player is currently standing in,
     * or "unknown" if unavailable.
     */
    public static String getBiome() {
        Level world = EzClient.world();
        var player = EzClient.player();

        if (world == null || player == null) return "unknown";

        try {
            BlockPos pos = player.blockPosition();
            Object biomeHolder = world.getBiome(pos);
            if (biomeHolder == null) return "unknown";

            Object unwrapKey = biomeHolder.getClass().getMethod("unwrapKey").invoke(biomeHolder);
            if (unwrapKey == null) return "unknown";

            // unwrapKey is usually Optional<ResourceKey<Biome>>
            if (unwrapKey instanceof java.util.Optional<?> optional && optional.isPresent()) {
                Object key = optional.get();
                Object location = key.getClass().getMethod("location").invoke(key);
                Object path = location.getClass().getMethod("getPath").invoke(location);

                if (path != null) {
                    return path.toString();
                }
            }
        } catch (Throwable ignored) {
            // Biome API changed or unavailable.
        }

        return "unknown";
    }

    private static Long callLong(Object target, String methodName) {
        try {
            Method method = target.getClass().getMethod(methodName);
            Object result = method.invoke(target);

            if (result instanceof Number number) {
                return number.longValue();
            }
        } catch (Throwable ignored) {
            // Method does not exist in this MC version.
        }

        return null;
    }

    private static Boolean callBoolean(Object target, String methodName) {
        try {
            Method method = target.getClass().getMethod(methodName);
            Object result = method.invoke(target);

            if (result instanceof Boolean bool) {
                return bool;
            }
        } catch (Throwable ignored) {
            // Method does not exist in this MC version.
        }

        return null;
    }
}