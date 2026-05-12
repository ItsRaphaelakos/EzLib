package com.itsraphaelakosyt.ezlib.api;

/**
 * EzOutline – Colour values used to draw button/text outlines.
 *
 * <pre>{@code
 * int outline = EzOutline.BLACK;
 * int custom  = EzOutline.color(0x222222);
 * }</pre>
 */
public final class EzOutline {

    public static final int BLACK     = EzColor.BLACK;
    public static final int WHITE     = EzColor.WHITE;
    /** No outline – transparent. */
    public static final int NONE      = EzColor.INVISIBLE;
    /** Alias for {@link #NONE}. */
    public static final int INVISIBLE = EzColor.INVISIBLE;

    private EzOutline() {}

    /**
     * Returns a fully-opaque outline colour from a raw RGB hex value.
     * Example: {@code EzOutline.color(0x000000)} → black outline.
     */
    public static int color(long rgbHex) {
        return EzColor.fromHex(rgbHex);
    }
}
