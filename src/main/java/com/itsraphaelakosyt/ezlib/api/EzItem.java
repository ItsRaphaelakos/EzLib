package com.itsraphaelakosyt.ezlib.api;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** EzItem – convenience checks for ItemStack instances. */
public final class EzItem {
    private EzItem() {}

    public static boolean is(ItemStack stack, Item item) {
        return stack != null && !stack.isEmpty() && stack.is(item);
    }

    public static boolean isEmpty(ItemStack stack) {
        return stack == null || stack.isEmpty();
    }

    /** Safe placeholder: returns true when the visible name differs from the item description id. */
    public static boolean hasName(ItemStack stack) {
        if (isEmpty(stack)) return false;
        return getName(stack) != null && !getName(stack).isBlank();
    }

    public static String getName(ItemStack stack) {
        if (isEmpty(stack)) return "";
        return stack.getHoverName().getString();
    }
}
