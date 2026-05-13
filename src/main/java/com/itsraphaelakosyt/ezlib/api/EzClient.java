package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.level.Level;

/**
 * EzClient – Central access point for the Minecraft client instance,
 * the local player, and the current world.
 *
 * <pre>{@code
 * Minecraft mc     = EzClient.mc();
 * LocalPlayer p    = EzClient.player();
 * Level world      = EzClient.world();
 * boolean inGame   = EzClient.isInGame();
 * EzClient.openScreen(myScreen);
 * }</pre>
 */
public final class EzClient {

    private EzClient() {}

    /** Returns the Minecraft client singleton. Never null. */
    public static Minecraft mc() {
        return Minecraft.getInstance();
    }

    /**
     * Returns the local player, or {@code null} when not in a world.
     */
    public static LocalPlayer player() {
        return mc().player;
    }

    /**
     * Returns the current client-side Level (world), or {@code null} when not in a world.
     */
    public static Level world() {
        return mc().level;
    }

    /** Returns {@code true} when a player and world are loaded (in-game). */
    public static boolean isInGame() {
        return player() != null && world() != null;
    }

    /**
     * Opens the given {@link Screen}.
     * Safe to call from any client thread.
     */
    public static void openScreen(Screen screen) {
        mc().setScreen(screen);
    }
}
