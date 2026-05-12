package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

/** EzInventory – convenience helpers for querying the local player's inventory. */
public final class EzInventory {
    private EzInventory() {}

    public static boolean hasItem(Item item) { return hasItem(stack -> stack.is(item)); }

    public static boolean hasItem(Predicate<ItemStack> predicate) {
        return findItem(predicate) != ItemStack.EMPTY;
    }

    public static ItemStack findItem(Item item) { return findItem(stack -> stack.is(item)); }

    public static ItemStack findItem(Predicate<ItemStack> predicate) {
        Inventory inv = getInventory();
        if (inv == null || predicate == null) return ItemStack.EMPTY;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && predicate.test(stack)) return stack;
        }
        return ItemStack.EMPTY;
    }

    public static boolean hasItemIgnoreHotbar(Item item) {
        return hasItemIgnoreHotbar(stack -> stack.is(item));
    }

    public static boolean hasItemIgnoreHotbar(Predicate<ItemStack> predicate) {
        Inventory inv = getInventory();
        if (inv == null || predicate == null) return false;
        for (int i = Inventory.getSelectionSize(); i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && predicate.test(stack)) return true;
        }
        return false;
    }

    private static Inventory getInventory() {
        return EzClient.player() != null ? EzClient.player().getInventory() : null;
    }
}
