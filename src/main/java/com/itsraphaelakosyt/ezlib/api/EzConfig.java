package com.itsraphaelakosyt.ezlib.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * EzConfig – Simple JSON config persistence backed by Gson.
 *
 * <pre>{@code
 * // Loading (creates default if absent):
 * MyConfig cfg = EzConfig.load("mymod", MyConfig.class, new MyConfig());
 *
 * // Saving:
 * EzConfig.save("mymod", cfg);
 * }</pre>
 *
 * Config files are stored in {@code .minecraft/config/<name>.json}.
 */
public final class EzConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private EzConfig() {}

    /**
     * Loads a config object from {@code config/<name>.json}.
     * If the file does not exist the {@code defaultValue} is returned as-is
     * (no file is written – call {@link #save} explicitly if you want to persist defaults).
     *
     * @param name         config file name (without .json)
     * @param type         config class
     * @param defaultValue fallback when the file is missing or corrupted
     * @param <T>          config type
     * @return loaded or default config
     */
    public static <T> T load(String name, Class<T> type, T defaultValue) {
        Path file = configPath(name);
        if (!Files.exists(file)) {
            EzLogger.info("Config '{}' not found, using defaults.", name);
            return defaultValue;
        }
        try (Reader reader = new InputStreamReader(
                new FileInputStream(file.toFile()), StandardCharsets.UTF_8)) {
            T loaded = GSON.fromJson(reader, type);
            return loaded != null ? loaded : defaultValue;
        } catch (Exception e) {
            EzLogger.error("Failed to load config '{}': {}", name, e.getMessage());
            return defaultValue;
        }
    }

    /**
     * Saves a config object to {@code config/<name>.json}.
     *
     * @param name   config file name (without .json)
     * @param config object to serialise
     */
    public static void save(String name, Object config) {
        Path file = configPath(name);
        try {
            Files.createDirectories(file.getParent());
            try (Writer writer = new OutputStreamWriter(
                    new FileOutputStream(file.toFile()), StandardCharsets.UTF_8)) {
                GSON.toJson(config, writer);
            }
            EzLogger.info("Config '{}' saved.", name);
        } catch (Exception e) {
            EzLogger.error("Failed to save config '{}': {}", name, e.getMessage());
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static Path configPath(String name) {
        return FabricLoader.getInstance().getConfigDir().resolve(name + ".json");
    }
}
