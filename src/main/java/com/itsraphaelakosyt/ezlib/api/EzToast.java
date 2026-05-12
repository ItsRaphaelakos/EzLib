package com.itsraphaelakosyt.ezlib.api;

/** EzToast – placeholder toast helper. */
public final class EzToast {
    private String title = "";
    private String message = "";

    private EzToast() {}

    public static EzToast show() { return new EzToast(); }

    public EzToast title(String title) { this.title = title == null ? "" : title; return this; }
    public EzToast message(String message) { this.message = message == null ? "" : message; return this; }

    public void build() {
        EzLogger.info("Toast: " + title + " - " + message);
    }
}
