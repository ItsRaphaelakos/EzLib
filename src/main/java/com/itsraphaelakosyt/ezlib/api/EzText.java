package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Method;

/**
 * EzText - Fluent builder for rendering text in GUI screens and HUD overlays.
 *
 * This version avoids directly depending on GuiGraphics/DrawContext class names,
 * so it is safer for Minecraft 26.1.x non-obfuscated builds.
 */
public final class EzText {

    private int x = 0;
    private int y = 0;
    private Component component = Component.empty();
    private int color = EzColor.WHITE;
    private boolean shadow = true;

    private EzText() {}

    public static EzText create() {
        return new EzText();
    }

    public EzText x(int x) {
        this.x = x;
        return this;
    }

    public EzText y(int y) {
        this.y = y;
        return this;
    }

    public EzText text(String text) {
        this.component = Component.literal(text);
        return this;
    }

    public EzText component(Component component) {
        this.component = component == null ? Component.empty() : component;
        return this;
    }

    public EzText color(int color) {
        this.color = color;
        return this;
    }

    public EzText shadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    /**
     * Draws text using the provided GUI graphics/context object.
     *
     * Accepts Object on purpose, because Minecraft 26.1.x changed/removed
     * some old mapped GUI class names.
     */
    public void draw(Object guiGraphics) {
        if (guiGraphics == null || Minecraft.getInstance() == null || Minecraft.getInstance().font == null) {
            return;
        }

        try {
            // Try method with shadow boolean:
            // drawString(font, component, x, y, color, shadow)
            Method method = guiGraphics.getClass().getMethod(
                    "drawString",
                    Minecraft.getInstance().font.getClass(),
                    Component.class,
                    int.class,
                    int.class,
                    int.class,
                    boolean.class
            );

            method.invoke(
                    guiGraphics,
                    Minecraft.getInstance().font,
                    component,
                    x,
                    y,
                    color,
                    shadow
            );
            return;
        } catch (Throwable ignored) {
            // Try fallback below.
        }

        try {
            // Try method without shadow boolean:
            // drawString(font, component, x, y, color)
            Method method = guiGraphics.getClass().getMethod(
                    "drawString",
                    Minecraft.getInstance().font.getClass(),
                    Component.class,
                    int.class,
                    int.class,
                    int.class
            );

            method.invoke(
                    guiGraphics,
                    Minecraft.getInstance().font,
                    component,
                    x,
                    y,
                    color
            );
        } catch (Throwable ignored) {
            // Do nothing instead of crashing the game.
        }
    }
}