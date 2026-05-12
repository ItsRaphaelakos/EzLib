package com.itsraphaelakosyt.ezlib.api;

/**
 * EzColor – Common colour constants (ARGB int) and a factory for custom colours.
 *
 * <pre>{@code
 * int white  = EzColor.WHITE;
 * int custom = EzColor.of(0xFF, 0x33, 0x99, 0xFF); // RGBA
 * int hex    = EzColor.fromHex(0xFF3399FF);
 * }</pre>
 */
public final class EzColor {

    // ── Named colours (ARGB format) ───────────────────────────────────────────
    public static final int WHITE     = 0xFFFFFFFF;
    public static final int BLACK     = 0xFF000000;
    public static final int RED       = 0xFFFF0000;
    public static final int GREEN     = 0xFF00FF00;
    public static final int BLUE      = 0xFF0000FF;
    public static final int YELLOW    = 0xFFFFFF00;
    public static final int GRAY      = 0xFF808080;
    public static final int GOLD      = 0xFFFFAA00;
    public static final int AQUA      = 0xFF00FFFF;
    /** Fully transparent – invisible. */
    public static final int INVISIBLE = 0x00000000;
    /** Alias for {@link #INVISIBLE}. */
    public static final int INV       = INVISIBLE;

    private EzColor() {}

    /**
     * Builds an ARGB colour from individual alpha, red, green, blue components (0–255 each).
     */
    public static int of(int a, int r, int g, int b) {
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }

    /**
     * Accepts a raw ARGB or RGB int (e.g. {@code 0xFFAA33}).
     * If the value has no alpha component (< 0x01000000) full opacity is assumed.
     */
    public static int fromHex(long hex) {
        int v = (int) hex;
        if ((v >>> 24) == 0) v |= 0xFF000000; // inject full alpha
        return v;
    }
}
