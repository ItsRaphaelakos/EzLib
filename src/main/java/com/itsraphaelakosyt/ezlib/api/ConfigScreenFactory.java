package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * ConfigScreenFactory – Quickly assembles a config screen from toggle entries,
 * without needing a custom {@link Screen} subclass.
 *
 * <pre>{@code
 * Screen configScreen = ConfigScreenFactory.builder("My Mod Settings")
 *     .toggle("Show HUD",     () -> cfg.showHud,     v -> cfg.showHud = v)
 *     .toggle("Show Compass", () -> cfg.showCompass, v -> cfg.showCompass = v)
 *     .onClose(() -> EzConfig.save("mymod", cfg))
 *     .build(parentScreen);
 * }</pre>
 */
public final class ConfigScreenFactory {

    private final String title;
    private final List<ToggleEntry> entries = new ArrayList<>();
    private Runnable onClose = null;

    private ConfigScreenFactory(String title) {
        this.title = title;
    }

    public static ConfigScreenFactory builder(String title) {
        return new ConfigScreenFactory(title);
    }

    // ── Entry types ───────────────────────────────────────────────────────────

    public ConfigScreenFactory toggle(String label,
                                      Supplier<Boolean> getter,
                                      Consumer<Boolean> setter) {
        entries.add(new ToggleEntry(label, getter, setter));
        return this;
    }

    /** Called when the screen is closed (use for saving config). */
    public ConfigScreenFactory onClose(Runnable onClose) {
        this.onClose = onClose;
        return this;
    }

    // ── Build ─────────────────────────────────────────────────────────────────

    public Screen build(Screen parent) {
        final String screenTitle    = title;
        final List<ToggleEntry> cfg = List.copyOf(entries);
        final Runnable close        = onClose;

        return new EzConfigScreen(Component.literal(screenTitle), parent, cfg, close);
    }

    // ── Inner Screen ──────────────────────────────────────────────────────────

    static final class ToggleEntry {
        final String label;
        final Supplier<Boolean> getter;
        final Consumer<Boolean> setter;

        ToggleEntry(String label, Supplier<Boolean> getter, Consumer<Boolean> setter) {
            this.label  = label;
            this.getter = getter;
            this.setter = setter;
        }
    }

    static final class EzConfigScreen extends EzGui.EzBuiltScreen {

        private final Screen parent;
        private final List<ToggleEntry> entries;
        private final Runnable onClose;

        EzConfigScreen(net.minecraft.network.chat.Component title,
                       Screen parent,
                       List<ToggleEntry> entries,
                       Runnable onClose) {
            super(title, 220, 20 + entries.size() * 24 + 10, null);
            this.parent  = parent;
            this.entries = entries;
            this.onClose = onClose;
        }

        @Override
        protected void init() {
            super.init();
            int startX = guiLeft + 10;
            int y      = guiTop  + 20;
            for (ToggleEntry entry : entries) {
                addRenderableWidget(
                    EzButton.create()
                        .x(startX).y(y).width(200).height(20)
                        .text(entry.label)
                        .toggle(entry.getter.get())
                        .onToggle(entry.setter::accept)
                        .build()
                );
                y += 24;
            }
        }

        @Override
        public void onClose() {
            if (onClose != null) onClose.run();
            minecraft.setScreen(parent);
        }

           @Override
        public void extractRenderState(GuiGraphicsExtractor guiGraphics,
                                       int mouseX, int mouseY, float partialTick) {
            super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

            String titleText = title.getString();
            int titleX = guiLeft + (xSize / 2) - (font.width(titleText) / 2);
            int titleY = guiTop + 6;

            guiGraphics.text(
                    font,
                    titleText,
                    titleX,
                    titleY,
                    EzColor.WHITE,
                    true
            );
        }
    }
}
