package com.bafomdad.uniquecrops.core;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class NBTUtils {

    /** Checks if an ItemStack has a Tag Compound **/
    public static boolean detectNBT(ItemStack stack) {
        return stack.hasTag();
    }

    /** Tries to initialize an NBT Tag Compound in an ItemStack,
     * this will not do anything if the stack already has a tag
     * compound **/
    public static void initNBT(ItemStack stack) {
        if(!detectNBT(stack))
            injectNBT(stack, new CompoundTag());
    }

    /** Injects an NBT Tag Compound to an ItemStack, no checks
     * are made previously **/
    public static void injectNBT(ItemStack stack, CompoundTag nbt) {
        stack.setTag(nbt);
    }

    /** Gets the NBTTagCompound in an ItemStack. Tries to init it
     * previously in case there isn't one present **/
    public static CompoundTag getNBT(ItemStack stack) {
        initNBT(stack);
        return stack.getTag();
    }

    // SETTERS ///////////////////////////////////////////////////////////////////

    public static void setBoolean(ItemStack stack, String tag, boolean b) {
        getNBT(stack).putBoolean(tag, b);
    }

    public static void setByte(ItemStack stack, String tag, byte b) {
        getNBT(stack).putByte(tag, b);
    }

    public static void setByteArray(ItemStack stack, String tag, byte[] b) {

        getNBT(stack).putByteArray(tag, b);
    }

    public static void setShort(ItemStack stack, String tag, short s) {
        getNBT(stack).putShort(tag, s);
    }

    public static void setInt(ItemStack stack, String tag, int i) {
        getNBT(stack).putInt(tag, i);
    }

    public static void setLong(ItemStack stack, String tag, long l) {
        getNBT(stack).putLong(tag, l);
    }

    public static void setFloat(ItemStack stack, String tag, float f) {
        getNBT(stack).putFloat(tag, f);
    }

    public static void setDouble(ItemStack stack, String tag, double d) {
        getNBT(stack).putDouble(tag, d);
    }

    public static void setCompound(ItemStack stack, String tag, CompoundTag cmp) {
        if(!tag.equalsIgnoreCase("ench")) // not override the enchantments
            getNBT(stack).put(tag, cmp);
    }

    public static void setString(ItemStack stack, String tag, String s) {
        getNBT(stack).putString(tag, s);
    }

    public static void setList(ItemStack stack, String tag, ListTag list) {
        getNBT(stack).put(tag, list);
    }

    public static void setIntArray(ItemStack stack, String key, int[] val) {

        getCompound(stack, key, true).putIntArray(key, val);
    }

    // GETTERS ///////////////////////////////////////////////////////////////////

    public static int[] getIntArray(ItemStack stack, String key) {

        return detectNBT(stack) ? getCompound(stack, key, true).getIntArray(key) : new int[0];
    }

    public static boolean verifyExistance(ItemStack stack, String tag) {
        return stack != null && getNBT(stack).contains(tag);
    }

    public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
        return verifyExistance(stack, tag) ? getNBT(stack).getBoolean(tag) : defaultExpected;
    }

    public static byte getByte(ItemStack stack, String tag, byte defaultExpected) {
        return verifyExistance(stack, tag) ? getNBT(stack).getByte(tag) : defaultExpected;
    }

    public static byte[] getByteArray(ItemStack stack, String tag) {

        return verifyExistance(stack, tag) ? getNBT(stack).getByteArray(tag) : null;
    }

    public static short getShort(ItemStack stack, String tag, short defaultExpected) {
        return verifyExistance(stack, tag) ? getNBT(stack).getShort(tag) : defaultExpected;
    }

    public static int getInt(ItemStack stack, String tag, int defaultExpected) {
        return verifyExistance(stack, tag) ? getNBT(stack).getInt(tag) : defaultExpected;
    }

    public static long getLong(ItemStack stack, String tag, long defaultExpected) {
        return verifyExistance(stack, tag) ? getNBT(stack).getLong(tag) : defaultExpected;
    }

    public static float getFloat(ItemStack stack, String tag, float defaultExpected) {
        return verifyExistance(stack, tag) ? getNBT(stack).getFloat(tag) : defaultExpected;
    }

    public static double getDouble(ItemStack stack, String tag, double defaultExpected) {
        return verifyExistance(stack, tag) ? getNBT(stack).getDouble(tag) : defaultExpected;
    }

    /** If nullifyOnFail is true it'll return null if it doesn't find any
     * compounds, otherwise it'll return a new one. **/
    public static CompoundTag getCompound(ItemStack stack, String tag, boolean nullifyOnFail) {
        return verifyExistance(stack, tag) ? getNBT(stack).getCompound(tag) : nullifyOnFail ? null : new CompoundTag();
    }

    public static String getString(ItemStack stack, String tag, String defaultExpected) {
        return verifyExistance(stack, tag) ? getNBT(stack).getString(tag) : defaultExpected;
    }

    public static ListTag getList(ItemStack stack, String tag, int objtype, boolean nullifyOnFail) {
        return verifyExistance(stack, tag) ? getNBT(stack).getList(tag, objtype) : nullifyOnFail ? null : new ListTag();
    }
}

