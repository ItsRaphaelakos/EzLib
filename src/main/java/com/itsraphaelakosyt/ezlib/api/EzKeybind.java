package com.itsraphaelakosyt.ezlib.api;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Method;

/**
 * EzKeybind - Fluent builder for client-side key mappings.
 */
public final class EzKeybind {

    private String name = "key.ezlib.unnamed";
    private String categoryId = "ezlib:misc";
    private int glfwKey = GLFW.GLFW_KEY_UNKNOWN;
    private Runnable onPress = null;

    private EzKeybind() {}

    public static EzKeybind create() {
        return new EzKeybind();
    }

    public EzKeybind name(String name) {
        if (name == null || name.isBlank()) {
            this.name = "key.ezlib.unnamed";
            return this;
        }

        this.name = name.contains(".")
                ? name
                : "key." + name.toLowerCase().replace(" ", "_");

        return this;
    }

    public EzKeybind key(int glfwKey) {
        this.glfwKey = glfwKey;
        return this;
    }

    public EzKeybind category(String category) {
        if (category == null || category.isBlank()) {
            this.categoryId = "ezlib:misc";
            return this;
        }

        if (category.contains(":")) {
            this.categoryId = category;
        } else {
            this.categoryId = "ezlib:" + category.toLowerCase().replace(" ", "_");
        }

        return this;
    }

    public EzKeybind onPress(Runnable onPress) {
        this.onPress = onPress;
        return this;
    }

    public KeyMapping register() {
        KeyMapping.Category category = KeyMapping.Category.register(parseIdentifier(categoryId));

        KeyMapping binding = new KeyMapping(
                name,
                InputConstants.Type.KEYSYM,
                glfwKey,
                category
        );

        registerWithFabricIfAvailable(binding);

        if (onPress != null) {
            Runnable callback = onPress;

            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                while (binding.consumeClick()) {
                    callback.run();
                }
            });
        }

        return binding;
    }

    private static void registerWithFabricIfAvailable(KeyMapping binding) {
        String[] helperClasses = {
                "net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper",
                "net.fabricmc.fabric.api.client.keybinding.v1.KeyMappingHelper",
                "net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper"
        };

        String[] methodNames = {
                "registerKeyMapping",
                "registerKeyBinding"
        };

        for (String helperClass : helperClasses) {
            try {
                Class<?> clazz = Class.forName(helperClass);

                for (String methodName : methodNames) {
                    try {
                        Method method = clazz.getMethod(methodName, KeyMapping.class);
                        method.invoke(null, binding);
                        return;
                    } catch (Throwable ignored) {
                    }
                }
            } catch (Throwable ignored) {
            }
        }
    }

    private static Identifier parseIdentifier(String id) {
        if (id.contains(":")) {
            String[] parts = id.split(":", 2);
            return Identifier.fromNamespaceAndPath(parts[0], parts[1]);
        }

        return Identifier.fromNamespaceAndPath("ezlib", id);
    }
}