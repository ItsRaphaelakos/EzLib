package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

/**
 * EzSound – Plays sound events on the client without boilerplate.
 *
 * <pre>{@code
 * EzSound.play(SoundEvents.UI_BUTTON_CLICK.value());
 * EzSound.play(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
 * }</pre>
 */
public final class EzSound {

    private EzSound() {}

    /**
     * Plays a {@link SoundEvent} at the player's position with default volume and pitch.
     */
    public static void play(SoundEvent sound) {
        play(sound, 1.0f, 1.0f);
    }

    /**
     * Plays a {@link SoundEvent} at the player's position with custom volume and pitch.
     *
     * @param sound  the sound to play
     * @param volume master volume multiplier (1.0 = 100 %)
     * @param pitch  pitch shift (1.0 = normal)
     */
    public static void play(SoundEvent sound, float volume, float pitch) {
        LocalPlayer player = EzClient.player();
        if (player == null) return;
        player.playSound(sound, volume, pitch);
    }

    /**
     * Plays a sound through the level (audible at a specific position).
     */
    public static void playAtPosition(SoundEvent sound, double x, double y, double z,
                                      float volume, float pitch) {
        var world = EzClient.world();
        if (world == null) return;
        world.playLocalSound(x, y, z, sound, SoundSource.MASTER, volume, pitch, false);
    }
}
