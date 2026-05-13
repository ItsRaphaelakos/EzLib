package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Method;

/**
 * EzItem - Convenience checks and helper utilities for {@link ItemStack} instances.
 *
 * <pre>{@code
 * boolean match = EzItem.is(stack, Items.STICK);
 * boolean empty = EzItem.isEmpty(stack);
 * boolean named = EzItem.hasName(stack);
 *
 * int durability = EzItem.durability(stack);
 * int max = EzItem.maxDurability(stack);
 * int percent = EzItem.durabilityPercent(stack);
 * }</pre>
 */
public final class EzItem {

    private EzItem() {}

    /** Returns true if the stack contains the given item type. */
    public static boolean is(ItemStack stack, Item item) {
        return stack != null && !stack.isEmpty() && stack.is(item);
    }

    /** Returns true if the stack is null or empty. */
    public static boolean isEmpty(ItemStack stack) {
        return stack == null || stack.isEmpty();
    }

    /** Returns true if the stack has a custom name component. */
    public static boolean hasName(ItemStack stack) {
        if (isEmpty(stack)) return false;

        try {
            Method method = stack.getClass().getMethod("hasCustomHoverName");
            Object result = method.invoke(stack);
            return result instanceof Boolean && (Boolean) result;
        } catch (Throwable ignored) {
            // Method may not exist in Minecraft 26.1.x.
        }

        try {
            Method method = stack.getClass().getMethod("hasCustomName");
            Object result = method.invoke(stack);
            return result instanceof Boolean && (Boolean) result;
        } catch (Throwable ignored) {
            // Method may not exist in Minecraft 26.1.x.
        }

        return false;
    }

    /**
     * Returns the display name string of the stack,
     * or an empty string if the stack is empty.
     */
    public static String getName(ItemStack stack) {
        if (isEmpty(stack)) return "";

        try {
            return stack.getHoverName().getString();
        } catch (Throwable ignored) {
            return "";
        }
    }

    /** Returns true if this stack can take durability damage. */
    public static boolean isDamageable(ItemStack stack) {
        return stack != null && !stack.isEmpty() && stack.isDamageableItem();
    }

    /** Returns the max durability of this stack, or 0 if it is not damageable. */
    public static int maxDurability(ItemStack stack) {
        if (!isDamageable(stack)) return 0;
        return stack.getMaxDamage();
    }

    /** Returns how much durability is left, or 0 if it is not damageable. */
    public static int durability(ItemStack stack) {
        if (!isDamageable(stack)) return 0;
        return stack.getMaxDamage() - stack.getDamageValue();
    }

    /** Returns how much durability damage the item has taken. */
    public static int damage(ItemStack stack) {
        if (!isDamageable(stack)) return 0;
        return stack.getDamageValue();
    }

    /** Returns durability percentage from 0 to 100. */
    public static int durabilityPercent(ItemStack stack) {
        if (!isDamageable(stack)) return 0;

        int max = stack.getMaxDamage();
        if (max <= 0) return 0;

        int left = durability(stack);
        return Math.max(0, Math.min(100, Math.round((left * 100.0f) / max)));
    }

    /** Returns true if the item looks like armor. */
    public static boolean isArmor(ItemStack stack) {
        if (isEmpty(stack)) return false;

        String className = stack.getItem().getClass().getName().toLowerCase();
        return className.contains("armor");
    }

    /** Returns true if the item is armor and can take durability damage. */
    public static boolean isDamageableArmor(ItemStack stack) {
        return isArmor(stack) && isDamageable(stack);
    }

    /** Returns true if the item has durability lower than the given percent. */
    public static boolean isDurabilityBelow(ItemStack stack, int percent) {
        return isDamageable(stack) && durabilityPercent(stack) < percent;
    }

    /** Returns a text like 315/429 for durability HUDs. */
    public static String durabilityText(ItemStack stack) {
        if (!isDamageable(stack)) return "";
        return durability(stack) + "/" + maxDurability(stack);
    }

    /** Returns a text like 73% for durability HUDs. */
    public static String durabilityPercentText(ItemStack stack) {
        if (!isDamageable(stack)) return "";
        return durabilityPercent(stack) + "%";
    }
}