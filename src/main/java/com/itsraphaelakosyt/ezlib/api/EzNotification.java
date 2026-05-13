package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.client.Minecraft;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * EzNotification - Renders temporary on-screen notifications.
 *
 * In Minecraft 26.1.x, HUD callback/render class names changed, so this class
 * avoids direct imports like GuiGraphics and HudRenderCallback.
 */
public final class EzNotification {

    private static final List<ActiveNotification> ACTIVE = new ArrayList<>();
    private static boolean registered = false;

    private EzNotification() {}

    /**
     * Marks notifications as registered.
     *
     * For Minecraft 26.1.x, call EzNotification.render(guiGraphics)
     * from your HUD/render callback.
     */
    public static void register() {
        if (registered) return;
        registered = true;
    }

    private String title = "";
    private String message = "";
    private int duration = 3;

    public static EzNotification create() {
        return new EzNotification();
    }

    public EzNotification title(String title) {
        this.title = title == null ? "" : title;
        return this;
    }

    public EzNotification message(String message) {
        this.message = message == null ? "" : message;
        return this;
    }

    /** Display duration in seconds. Default: 3. */
    public EzNotification duration(int seconds) {
        this.duration = Math.max(1, seconds);
        return this;
    }

    /** Queues the notification for display. */
    public void show() {
        synchronized (ACTIVE) {
            ACTIVE.add(new ActiveNotification(title, message, duration * 20));
        }
    }

    /**
     * Renders all notifications.
     *
     * Pass the current GUI graphics/context object from your HUD render code.
     */
    public static void render(Object guiGraphics) {
        if (guiGraphics == null) return;

        Minecraft mc = EzClient.mc();
        if (mc == null || mc.font == null || mc.getWindow() == null) return;

        int screenW = mc.getWindow().getGuiScaledWidth();
        int y = 40;
        int padX = 6;
        int padY = 4;
        int notifWidth = 160;

        synchronized (ACTIVE) {
            Iterator<ActiveNotification> it = ACTIVE.iterator();

            while (it.hasNext()) {
                ActiveNotification n = it.next();
                n.ticksRemaining--;

                if (n.ticksRemaining <= 0) {
                    it.remove();
                    continue;
                }

                int x = screenW - notifWidth - 10;

                fill(guiGraphics, x - padX, y - padY, x + notifWidth + padX, y + 20 + padY, 0xCC000000);
                drawString(guiGraphics, mc, n.title, x, y, EzColor.GOLD);
                drawString(guiGraphics, mc, n.message, x, y + 10, EzColor.WHITE);

                y += 30;
            }
        }
    }

    private static void fill(Object guiGraphics, int x1, int y1, int x2, int y2, int color) {
        try {
            Method method = guiGraphics.getClass().getMethod(
                    "fill",
                    int.class,
                    int.class,
                    int.class,
                    int.class,
                    int.class
            );

            method.invoke(guiGraphics, x1, y1, x2, y2, color);
        } catch (Throwable ignored) {
            // Fill method changed or unavailable.
        }
    }

    private static void drawString(Object guiGraphics, Minecraft mc, String text, int x, int y, int color) {
        try {
            Method method = guiGraphics.getClass().getMethod(
                    "drawString",
                    mc.font.getClass(),
                    String.class,
                    int.class,
                    int.class,
                    int.class
            );

            method.invoke(guiGraphics, mc.font, text, x, y, color);
            return;
        } catch (Throwable ignored) {
            // Try method with shadow boolean.
        }

        try {
            Method method = guiGraphics.getClass().getMethod(
                    "drawString",
                    mc.font.getClass(),
                    String.class,
                    int.class,
                    int.class,
                    int.class,
                    boolean.class
            );

            method.invoke(guiGraphics, mc.font, text, x, y, color, true);
        } catch (Throwable ignored) {
            // Draw method changed or unavailable.
        }
    }

    private static final class ActiveNotification {
        final String title;
        final String message;
        int ticksRemaining;

        ActiveNotification(String title, String message, int ticks) {
            this.title = title;
            this.message = message;
            this.ticksRemaining = ticks;
        }
    }
}