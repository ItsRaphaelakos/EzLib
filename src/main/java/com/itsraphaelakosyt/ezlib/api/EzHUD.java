package com.itsraphaelakosyt.ezlib.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * EzHUD - Simple HUD helper for registering and rendering HUD elements.
 *
 * Minecraft 26.1.x changed some HUD/render class names, so this class avoids
 * direct imports like GuiGraphics, ResourceLocation and HudElementRegistry.
 *
 * Usage:
 * <pre>{@code
 * EzHUD.register("mymod:compass_hud", guiGraphics -> {
 *     EzText.create()
 *         .x(10).y(10)
 *         .text("N")
 *         .color(EzColor.GOLD)
 *         .draw(guiGraphics);
 * });
 *
 * // Call EzHUD.render(guiGraphics) from your mod HUD render code.
 * }</pre>
 */
public final class EzHUD {

    private static final List<HudElement> ELEMENTS = new ArrayList<>();

    private EzHUD() {}

    /**
     * Registers a HUD render function.
     *
     * @param id unique id for this HUD element, for example "mymod:my_hud"
     * @param renderFunc receives the current gui graphics/context object
     */
    public static void register(String id, Consumer<Object> renderFunc) {
        if (id == null || id.isBlank() || renderFunc == null) {
            return;
        }

        synchronized (ELEMENTS) {
            ELEMENTS.add(new HudElement(id, renderFunc));
        }
    }

    /**
     * Compatibility overload.
     *
     * The anchor object is ignored in this safe 26.1.x version, because the
     * old HudElementRegistry/ResourceLocation classes are not available here.
     */
    public static void register(String id, Object anchorId, Consumer<Object> renderFunc) {
        register(id, renderFunc);
    }

    /**
     * Renders all registered HUD elements.
     *
     * Pass the current GUI graphics/context object from your HUD render callback.
     */
    public static void render(Object guiGraphics) {
        if (guiGraphics == null) {
            return;
        }

        synchronized (ELEMENTS) {
            for (HudElement element : ELEMENTS) {
                try {
                    element.renderFunc.accept(guiGraphics);
                } catch (Throwable ignored) {
                    // Do not let one HUD element crash the whole HUD render.
                }
            }
        }
    }

    /** Removes all registered HUD elements. Useful for tests/reloads. */
    public static void clear() {
        synchronized (ELEMENTS) {
            ELEMENTS.clear();
        }
    }

    /** Returns how many HUD elements are registered. */
    public static int count() {
        synchronized (ELEMENTS) {
            return ELEMENTS.size();
        }
    }

    private static final class HudElement {
        final String id;
        final Consumer<Object> renderFunc;

        HudElement(String id, Consumer<Object> renderFunc) {
            this.id = id;
            this.renderFunc = renderFunc;
        }
    }
}