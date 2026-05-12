package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** ConfigScreenFactory – minimal config screen builder that compiles on 26.1.2. */
public final class ConfigScreenFactory {
    private String name = "Config";
    private int width = 176;
    private int height = 166;
    private final List<ToggleEntry> toggles = new ArrayList<>();
    private Runnable onClose = null;

    private ConfigScreenFactory() {}

    public static ConfigScreenFactory create() { return new ConfigScreenFactory(); }
    public static ConfigScreenFactory builder(String title) { return create().name(title); }

    public ConfigScreenFactory name(String name) { this.name = name == null ? "Config" : name; return this; }
    public ConfigScreenFactory size(int width, int height) { this.width = width; this.height = height; return this; }
    public ConfigScreenFactory xSize(int width) { this.width = width; return this; }
    public ConfigScreenFactory ySize(int height) { this.height = height; return this; }
    public ConfigScreenFactory button(EzButton button) { return this; }
    public ConfigScreenFactory text(EzText text) { return this; }
    public ConfigScreenFactory onClose(Runnable onClose) { this.onClose = onClose; return this; }

    public ConfigScreenFactory toggle(String label, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        toggles.add(new ToggleEntry(label, getter, setter));
        return this;
    }

    public Screen build() { return new BuiltConfigScreen(Component.literal(name), onClose); }
    public Screen build(Screen parent) { return build(); }

    private record ToggleEntry(String label, Supplier<Boolean> getter, Consumer<Boolean> setter) {}

    public static class BuiltConfigScreen extends Screen {
        private final Runnable onClose;
        protected BuiltConfigScreen(Component title, Runnable onClose) {
            super(title);
            this.onClose = onClose;
        }
        @Override
        public void onClose() {
            if (onClose != null) onClose.run();
            super.onClose();
        }
    }
}
