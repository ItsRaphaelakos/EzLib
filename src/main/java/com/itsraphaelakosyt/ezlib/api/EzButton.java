package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

/**
 * EzButton – Fluent builder for standard buttons and ON/OFF toggle buttons.
 *
 * <pre>{@code
 * // Simple button:
 * Button btn = EzButton.create()
 *     .x(20).y(50).width(120).height(20)
 *     .text("Click Me")
 *     .onPress(() -> doSomething())
 *     .build();
 *
 * // Toggle button bound to a config field:
 * Button toggle = EzButton.create()
 *     .x(20).y(80).width(120).height(20)
 *     .text("Show Compass")
 *     .toggle(config.showCompass)
 *     .onToggle(value -> config.showCompass = value)
 *     .color(EzColor.WHITE)
 *     .outline(EzOutline.BLACK)
 *     .build();
 * }</pre>
 */
public final class EzButton {

    private int x = 0;
    private int y = 0;
    private int width  = 120;
    private int height = 20;
    private String text = "Button";

    // Toggle support
    private boolean isToggle     = false;
    private boolean toggleState  = false;
    private Consumer<Boolean> onToggleCallback = null;

    // Simple press
    private Runnable onPressCallback = null;

    // Visual (stored for subclassers / custom renderers – vanilla Button ignores them)
    @SuppressWarnings("unused")
    private int color   = EzColor.WHITE;
    @SuppressWarnings("unused")
    private int outline = EzOutline.BLACK;

    private EzButton() {}

    public static EzButton create() {
        return new EzButton();
    }

    // ── Position / size ───────────────────────────────────────────────────────

    public EzButton x(int x)       { this.x = x;       return this; }
    public EzButton y(int y)       { this.y = y;       return this; }
    public EzButton width(int w)   { this.width = w;   return this; }
    public EzButton height(int h)  { this.height = h;  return this; }

    // ── Label ─────────────────────────────────────────────────────────────────

    public EzButton text(String label) { this.text = label; return this; }

    // ── Toggle ────────────────────────────────────────────────────────────────

    /** Marks this button as a toggle and sets its initial state. */
    public EzButton toggle(boolean initialState) {
        this.isToggle    = true;
        this.toggleState = initialState;
        return this;
    }

    /** Called with the new boolean value every time the toggle changes. */
    public EzButton onToggle(Consumer<Boolean> callback) {
        this.onToggleCallback = callback;
        return this;
    }

    // ── Press (non-toggle) ────────────────────────────────────────────────────

    public EzButton onPress(Runnable callback) {
        this.onPressCallback = callback;
        return this;
    }

    // ── Appearance ────────────────────────────────────────────────────────────

    public EzButton color(int color)     { this.color   = color;   return this; }
    public EzButton outline(int outline) { this.outline = outline; return this; }

    // ── Build ─────────────────────────────────────────────────────────────────

    /**
     * Constructs and returns the Minecraft {@link Button} widget.
     * For toggle buttons the label is automatically suffixed with the current state
     * (e.g. "Show Compass: ON").
     */
    public Button build() {
        if (isToggle) {
            // Capture mutable state in a single-element array so the lambda can update it.
            final boolean[] state = { toggleState };
            final Consumer<Boolean> callback = onToggleCallback;
            final String baseLabel = text;

            return Button.builder(toggleLabel(baseLabel, state[0]), btn -> {
                state[0] = !state[0];
                btn.setMessage(toggleLabel(baseLabel, state[0]));
                if (callback != null) callback.accept(state[0]);
            }).pos(x, y).size(width, height).build();
        } else {
            Runnable press = onPressCallback;
            return Button.builder(Component.literal(text), btn -> {
                if (press != null) press.run();
            }).pos(x, y).size(width, height).build();
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static Component toggleLabel(String base, boolean on) {
        return Component.literal(base + ": " + (on ? "ON" : "OFF"));
    }
}
