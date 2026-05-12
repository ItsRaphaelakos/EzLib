package com.itsraphaelakosyt.ezlib.api;

/**
 * EzMath – Common mathematical utilities.
 *
 * <pre>{@code
 * float clamped = EzMath.clamp(value, 0f, 1f);
 * float mixed   = EzMath.lerp(0f, 100f, 0.5f);  // → 50f
 * double dist   = EzMath.distance(0, 0, 3, 4);   // → 5.0
 * }</pre>
 */
public final class EzMath {

    private EzMath() {}

    // ── Clamp ─────────────────────────────────────────────────────────────────

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    // ── Linear interpolation ──────────────────────────────────────────────────

    /** Linear interpolation between {@code a} and {@code b} by factor {@code t} (0–1). */
    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    // ── Distance ──────────────────────────────────────────────────────────────

    /** 2-D Euclidean distance. */
    public static double distance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /** 3-D Euclidean distance. */
    public static double distance(double x1, double y1, double z1,
                                  double x2, double y2, double z2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
