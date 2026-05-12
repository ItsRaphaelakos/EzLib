package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.network.chat.Component;

/**
 * EzText – small fluent text helper.
 *
 * This version accepts Object for the graphics argument so the public API keeps
 * working while Minecraft 26.1.x rendering names settle.
 */
public final class EzText {
    private int x = 0;
    private int y = 0;
    private Component component = Component.empty();
    private int color = EzColor.WHITE;
    private boolean shadow = true;

    private EzText() {}

    public static EzText create() { return new EzText(); }

    public EzText x(int x) { this.x = x; return this; }
    public EzText y(int y) { this.y = y; return this; }
    public EzText text(String text) { this.component = Component.literal(text); return this; }
    public EzText text(Component text) { this.component = text == null ? Component.empty() : text; return this; }
    public EzText color(int color) { this.color = color; return this; }
    public EzText color(long color) { this.color = (int) color; return this; }
    public EzText colour(int color) { return color(color); }
    public EzText shadow(boolean shadow) { this.shadow = shadow; return this; }

    public int x() { return x; }
    public int y() { return y; }
    public Component component() { return component; }
    public int color() { return color; }
    public boolean shadow() { return shadow; }

    /**
     * Draw hook. For now it is a safe no-op so EzLib compiles on 26.1.2.
     * Later we can wire this to GuiGraphicsExtractor once the exact methods are confirmed.
     */
    public void draw(Object guiGraphics) {
        // TODO 26.1.2: render through GuiGraphicsExtractor.
    }
}
