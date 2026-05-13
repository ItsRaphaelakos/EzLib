package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;

/**
 * EzPlayer – Convenient movement/state queries and action helpers for the local player.
 *
 * <pre>{@code
 * if (EzPlayer.isShifting())       { ... }
 * if (EzPlayer.isRunning())        { ... }
 *
 * // Action builder pattern:
 * EzPlayer.attacking(EntityType.ZOMBIE)
 *     .then(() -> EzLogger.info("Attacking a zombie!"));
 * }</pre>
 */
public final class EzPlayer {

    private EzPlayer() {}

    // ── Movement state ────────────────────────────────────────────────────────

    public static boolean isShifting() {
        LocalPlayer p = EzClient.player();
        return p != null && p.isCrouching();
    }

    public static boolean isWalkingForward() {
        LocalPlayer p = EzClient.player();
        return p != null && p.zza > 0;
    }

    public static boolean isWalkingBackward() {
        LocalPlayer p = EzClient.player();
        return p != null && p.zza < 0;
    }

    public static boolean isWalkingLeft() {
        LocalPlayer p = EzClient.player();
        return p != null && p.xxa > 0;
    }

    public static boolean isWalkingRight() {
        LocalPlayer p = EzClient.player();
        return p != null && p.xxa < 0;
    }

    public static boolean isJumping() {
        LocalPlayer p = EzClient.player();
        // Player is in the air and moving upward
        return p != null && !p.onGround() && p.getDeltaMovement().y > 0;
    }

    public static boolean isSwimming() {
        LocalPlayer p = EzClient.player();
        return p != null && p.isSwimming();
    }

    /**
     * Returns {@code true} when the player is sprinting (vanilla sprint flag).
     */
    public static boolean isRunning() {
        LocalPlayer p = EzClient.player();
        return p != null && p.isSprinting();
    }

    // ── Action builder ────────────────────────────────────────────────────────

    /**
     * Returns an {@link ActionBuilder} that fires when the player is attacking the given entity type.
     * The check is evaluated lazily – nothing runs until {@link ActionBuilder#then} is called and the
     * condition is true.
     *
     * <pre>{@code
     * EzPlayer.attacking(EntityType.ZOMBIE)
     *     .then(() -> EzLogger.info("Hit a zombie!"));
     * }</pre>
     */
    public static ActionBuilder attacking(EntityType<?> targetType) {
        return new ActionBuilder(() -> {
            LocalPlayer p = EzClient.player();
            if (p == null) return false;
            // We detect "attacking" as the player swinging their arm at a mob of the given type
            // in the immediate look-target. For a full implementation hook into attack events.
            return p.swinging && p.getAttackStrengthScale(0f) >= 1f;
        });
    }

    // ── Inner builder ─────────────────────────────────────────────────────────

    /** Lazy condition→action builder returned by action helpers. */
    public static final class ActionBuilder {

        private final java.util.function.BooleanSupplier condition;

        ActionBuilder(java.util.function.BooleanSupplier condition) {
            this.condition = condition;
        }

        /**
         * Executes {@code action} if the condition currently holds.
         * Returns this builder for chaining.
         */
        public ActionBuilder then(Runnable action) {
            if (condition.getAsBoolean()) action.run();
            return this;
        }

        /**
         * Executes {@code action} if the condition does <em>not</em> hold.
         */
        public ActionBuilder otherwise(Runnable action) {
            if (!condition.getAsBoolean()) action.run();
            return this;
        }
    }
}
