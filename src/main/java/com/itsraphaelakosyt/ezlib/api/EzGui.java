package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** EzGui – simple fluent builder for basic screens. */
public final class EzGui {
    private String name = "";
    private int xSize = 176;
    private int ySize = 166;
    private int z = 0;
    private String background = null;

    private EzGui() {}

    public static EzGui create() { return new EzGui(); }

    public EzGui name(String name) { this.name = name == null ? "" : name; return this; }
    public EzGui size(int width, int height) { this.xSize = width; this.ySize = height; return this; }
    public EzGui xSize(int xSize) { this.xSize = xSize; return this; }
    public EzGui ySize(int ySize) { this.ySize = ySize; return this; }
    public EzGui z(int z) { this.z = z; return this; }
    public EzGui background(String path) { this.background = path; return this; }
    public EzGui texture(String modId, String path) { this.background = modId + ":" + path; return this; }

    public Screen build() {
        return new EzBuiltScreen(Component.literal(name), xSize, ySize, z, background);
    }

    public static class EzBuiltScreen extends Screen {
        protected final int xSize;
        protected final int ySize;
        protected final int z;
        protected final String background;

        public EzBuiltScreen(Component title, int xSize, int ySize, int z, String background) {
            super(title);
            this.xSize = xSize;
            this.ySize = ySize;
            this.z = z;
            this.background = background;
        }
    }
}
