package com.itsraphaelakosyt.ezlib.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** EzNotification – simple notification store. Rendering will be added later. */
public final class EzNotification {
    private static final List<Notification> ACTIVE = new ArrayList<>();
    private String title = "";
    private String message = "";
    private int durationSeconds = 3;

    private EzNotification() {}

    public static EzNotification create() { return new EzNotification(); }
    public static void register() { EzLogger.info("EzNotification placeholder registered."); }

    public EzNotification title(String title) { this.title = title == null ? "" : title; return this; }
    public EzNotification message(String message) { this.message = message == null ? "" : message; return this; }
    public EzNotification duration(int seconds) { this.durationSeconds = Math.max(1, seconds); return this; }

    public void show() {
        ACTIVE.add(new Notification(title, message, durationSeconds, System.currentTimeMillis()));
        EzLogger.info("Notification: " + title + " - " + message);
    }

    public static List<Notification> active() { return Collections.unmodifiableList(ACTIVE); }

    public record Notification(String title, String message, int durationSeconds, long createdAtMillis) {}
}
