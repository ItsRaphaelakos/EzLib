package com.itsraphaelakosyt.ezlib;

import com.itsraphaelakosyt.ezlib.api.EzLogger;
import net.fabricmc.api.ClientModInitializer;

/**
 * EzLib – A helper library for client-side QoL Fabric mods.
 * Entrypoint: runs once on client startup.
 */
public class EzLib implements ClientModInitializer {

    public static final String MOD_ID = "ezlib";
    public static final String MOD_NAME = "EzLib";

    @Override
    public void onInitializeClient() {
        EzLogger.info("EzLib initialised! Ready to make your mod life easier.");
    }
}
