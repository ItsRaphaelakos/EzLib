package com.itsraphaelakosyt.ezlib.api;

/**
 * EzKeybind – fluent keybind helper.
 * This is a compile-safe placeholder for 26.1.2 until the new keybinding API is confirmed.
 */
public final class EzKeybind {
    private String name = "key.ezlib.unnamed";
    private String category = "key.categories.misc";
    private int glfwKey = -1;
    private Runnable onPress = null;

    private EzKeybind() {}

    public static EzKeybind create() { return new EzKeybind(); }

    public EzKeybind name(String name) { this.name = name == null ? this.name : name; return this; }
    public EzKeybind key(int glfwKey) { this.glfwKey = glfwKey; return this; }
    public EzKeybind category(String category) { this.category = category == null ? this.category : category; return this; }
    public EzKeybind onPress(Runnable onPress) { this.onPress = onPress; return this; }

    public EzKeybind register() {
        EzLogger.info("Keybind placeholder registered: " + name + " key=" + glfwKey + " category=" + category);
        return this;
    }

    public void run() { if (onPress != null) onPress.run(); }
}
