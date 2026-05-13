package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

/**
 * EzInventory - Convenience helpers for querying the local player's inventory.
 *
 * <pre>{@code
 * boolean hasStick   = EzInventory.hasItem(Items.STICK);
 * ItemStack torches  = EzInventory.findItem(Items.TORCH);
 * boolean noHotbar   = EzInventory.hasItemIgnoreHotbar(Items.DIAMOND);
 * }</pre>
 */
public final class EzInventory {

    private EzInventory() {}

    /** Returns true if the player's full inventory contains at least one of item. */
    public static boolean hasItem(Item item) {
        return hasItem(stack -> stack.is(item));
    }

    /** Predicate variant - returns true if any slot matches. */
    public static boolean hasItem(Predicate<ItemStack> predicate) {
        Inventory inv = getInventory();
        if (inv == null || predicate == null) return false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && predicate.test(stack)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the first ItemStack matching item, or ItemStack.EMPTY if absent.
     */
    public static ItemStack findItem(Item item) {
        return findItem(stack -> stack.is(item));
    }

    /** Predicate variant. */
    public static ItemStack findItem(Predicate<ItemStack> predicate) {
        Inventory inv = getInventory();
        if (inv == null || predicate == null) return ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && predicate.test(stack)) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }

    /**
     * Returns true if the item is present in the main inventory,
     * excluding hotbar slots 0-8.
     */
    public static boolean hasItemIgnoreHotbar(Item item) {
        Inventory inv = getInventory();
        if (inv == null) return false;

        int hotbarSize = Inventory.getSelectionSize();

        for (int i = hotbarSize; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.is(item)) {
                return true;
            }
        }

        return false;
    }

    /** Returns the local player's inventory, or null if no player exists. */
    private static Inventory getInventory() {
        var player = EzClient.player();
        return player != null ? player.getInventory() : null;
    }
}