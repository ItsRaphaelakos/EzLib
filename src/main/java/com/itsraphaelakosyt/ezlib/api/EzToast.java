package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Method;

/**
 * EzToast - Show small pop-up toast messages in the top-right corner of the screen.
 *
 * <pre>{@code
 * EzToast.show()
 *     .title("EzCompass")
 *     .message("Settings saved!")
 *     .build();
 * }</pre>
 */
public final class EzToast {

    private String title = "";
    private String message = "";

    private EzToast() {}

    public static EzToast show() {
        return new EzToast();
    }

    public EzToast title(String title) {
        this.title = title == null ? "" : title;
        return this;
    }

    public EzToast message(String message) {
        this.message = message == null ? "" : message;
        return this;
    }

    /**
     * Queues the toast for display.
     * Safe to call from the client thread.
     */
    public void build() {
        Minecraft mc = EzClient.mc();
        if (mc == null) return;

        Object toastManager = findToastManager(mc);
        if (toastManager == null) return;

        try {
            Method addOrUpdate = SystemToast.class.getMethod(
                    "addOrUpdate",
                    toastManager.getClass(),
                    SystemToast.SystemToastId.class,
                    Component.class,
                    Component.class
            );

            addOrUpdate.invoke(
                    null,
                    toastManager,
                    SystemToast.SystemToastId.PERIODIC_NOTIFICATION,
                    Component.literal(title),
                    Component.literal(message)
            );
        } catch (Throwable ignored) {
            // Toast API changed or unavailable. Do nothing instead of crashing.
        }
    }

    private static Object findToastManager(Minecraft mc) {
        String[] methodNames = {
                "getToasts",
                "getToastManager",
                "toastManager"
        };

        for (String methodName : methodNames) {
            try {
                Method method = mc.getClass().getMethod(methodName);
                Object result = method.invoke(mc);
                if (result != null) {
                    return result;
                }
            } catch (Throwable ignored) {
                // Try next method name.
            }
        }

        return null;
    }
}