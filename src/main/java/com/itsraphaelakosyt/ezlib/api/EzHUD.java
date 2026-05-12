package com.itsraphaelakosyt.ezlib.api;

import java.util.function.Consumer;

/**
 * EzHUD – placeholder-safe HUD helper for Minecraft 26.1.2.
 * Public API is kept; render registration can be wired to HudElementRegistry later.
 */
public final class EzHUD {
    private EzHUD() {}

    public static void register(String id, Consumer<Object> renderFunc) {
        EzLogger.info("Registered HUD element placeholder: " + id);
    }

    public static void register(String id, Object anchorId, Consumer<Object> renderFunc) {
        EzLogger.info("Registered HUD element placeholder: " + id);
    }
}
