package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

/**
 * EzGui - Fluent builder for simple GUI screens.
 *
 * <pre>{@code
 * Screen screen = EzGui.create()
 *     .name("EzCompass Settings")
 *     .size(176, 166)
 *     .background("textures/gui/settings.png")
 *     .build();
 *
 * EzClient.openScreen(screen);
 * }</pre>
 *
 * Note: uses .name() instead of .title().
 */
public final class EzGui {

    private String name = "";
    private int xSize = 176;
    private int ySize = 166;
    private Identifier bg = null;

    private EzGui() {}

    public static EzGui create() {
        return new EzGui();
    }

    /** Sets the screen title/name. */
    public EzGui name(String name) {
        this.name = name == null ? "" : name;
        return this;
    }

    /** Sets both width and height in one call. */
    public EzGui size(int width, int height) {
        this.xSize = width;
        this.ySize = height;
        return this;
    }

    public EzGui xSize(int width) {
        this.xSize = width;
        return this;
    }

    public EzGui ySize(int height) {
        this.ySize = height;
        return this;
    }

    /**
     * Sets the background texture path, e.g. "textures/gui/settings.png".
     * The namespace defaults to minecraft if omitted.
     */
    public EzGui background(String path) {
        if (path == null || path.isBlank()) {
            this.bg = null;
            return this;
        }

        if (path.contains(":")) {
            this.bg = Identifier.parse(path);
        } else {
            this.bg = Identifier.withDefaultNamespace(path);
        }

        return this;
    }

    /** Builds and returns a Screen with the configured properties. */
    public Screen build() {
        return new EzBuiltScreen(Component.literal(name), xSize, ySize, bg);
    }

    /**
     * A lightweight Screen produced by EzGui.build().
     */
    public static class EzBuiltScreen extends Screen {

        protected final int xSize;
        protected final int ySize;
        protected final Identifier background;

        protected int guiLeft;
        protected int guiTop;

        public EzBuiltScreen(Component title, int xSize, int ySize, Identifier background) {
            super(title);
            this.xSize = xSize;
            this.ySize = ySize;
            this.background = background;
        }

        @Override
        protected void init() {
            super.init();
            guiLeft = (width - xSize) / 2;
            guiTop = (height - ySize) / 2;
        }

        @Override
        public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
            extractBackground(guiGraphics, mouseX, mouseY, partialTick);

            if (background != null) {
                guiGraphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        background,
                        guiLeft,
                        guiTop,
                        0.0F,
                        0.0F,
                        xSize,
                        ySize,
                        xSize,
                        ySize
                );
            }

            super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        }

        public int getXSize() {
            return xSize;
        }

        public int getYSize() {
            return ySize;
        }

        public int getGuiLeft() {
            return guiLeft;
        }

        public int getGuiTop() {
            return guiTop;
        }

        public Identifier getBackground() {
            return background;
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }
    }
}