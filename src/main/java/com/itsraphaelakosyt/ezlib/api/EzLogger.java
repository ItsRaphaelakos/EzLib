package com.itsraphaelakosyt.ezlib.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EzLogger – Thin wrapper around SLF4J for pretty, prefixed log output.
 *
 * <pre>{@code
 * EzLogger.info("Loaded config");
 * EzLogger.warn("Missing texture");
 * EzLogger.error("Something exploded", exception);
 * }</pre>
 */
public final class EzLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger("EzLib");

    private EzLogger() {}

    public static void info(String message, Object... args) {
        LOGGER.info("[EzLib] " + message, args);
    }

    public static void warn(String message, Object... args) {
        LOGGER.warn("[EzLib] " + message, args);
    }

    public static void error(String message, Object... args) {
        LOGGER.error("[EzLib] " + message, args);
    }

    public static void error(String message, Throwable throwable) {
        LOGGER.error("[EzLib] " + message, throwable);
    }

    public static void debug(String message, Object... args) {
        LOGGER.debug("[EzLib] " + message, args);
    }
}
