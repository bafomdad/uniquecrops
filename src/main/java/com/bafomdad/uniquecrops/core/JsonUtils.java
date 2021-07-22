package com.bafomdad.uniquecrops.core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * credit to: TehNut
 */
public class JsonUtils {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();

    /**
     * Reads a {@link T} back from the given file. If the file does not exist, a new file will be generated with the
     * provided default and the default will be returned.
     *
     * @param token The token type to use for deserialization.
     * @param file The file to read the JSON from.
     * @param def The default value to use if the file does not exist.
     * @param <T> The object type to give back.
     *
     * @return a {@link T} that was read from the given file or {@code def} if the file did not exist.
     */
    public static <T> T fromJson(@Nonnull TypeToken<T> token, @Nonnull File file, @Nonnull T def) {

        T ret = fromJson(token, file);
        if (ret == null) {
            toJson(def, token, file);
            ret = def;
        }
        return ret;
    }

    /**
     * Reads a {@link T} back from the given file. If the file does not exist, {@code null} is returned. If an exception
     * is thrown during deserialization, {@code null} is also returned.
     *
     * @param token The token type to use for deserialization.
     * @param file - The file to read the JSON from.
     * @param <T> The object type to give back.
     *
     * @return a {@link T} that was read from the given file, {@code null} if the file does not exist, or {@code null} if
     * an exception was thrown.
     */
    public static <T> T fromJson(@Nonnull TypeToken<T> token, @Nonnull File file) {

        if (!file.exists())
            return null;

        FileReader reader = null;
        try {
            reader = new FileReader(file);
            return GSON.fromJson(reader, token.getType());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return null;
    }

    public static <T> T fromJson(@Nonnull TypeToken<T> token, @Nonnull String json) {

        return GSON.fromJson(json, token.getType());
    }

    /**
     * Converts a {@link T} to JSON and writes it to file. If the file does not exist, a new one is created. If the file
     * does exist, the contents are overwritten with the new value.
     *
     * @param type The object to write to JSON.
     * @param token The token type to use for serialization.
     * @param file The file to write the JSON to.
     * @param <T> The object type to write.
     */
    public static <T> void toJson(@Nonnull T type, @Nonnull TypeToken<T> token, @Nonnull File file) {

        if (!file.exists()) {
            try {
                FileUtils.forceMkdirParent(file);
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(getJson(type, token));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    public static <T> String getJson(@Nonnull T type, @Nonnull TypeToken<T> token) {

        return GSON.toJson(type, token.getType());
    }

    public static JsonObject serializeStack(ItemStack stack) {

        CompoundNBT nbt = stack.write(new CompoundNBT());
        byte c = nbt.getByte("Count");
        if (c != 1)
            nbt.putByte("count", c);

        nbt.remove("Count");
        renameTag(nbt, "id", "item");
        renameTag(nbt, "tag", "nbt");
        Dynamic<INBT> dyn = new Dynamic<>(NBTDynamicOps.INSTANCE, nbt);
        return dyn.convert(JsonOps.INSTANCE).getValue().getAsJsonObject();
    }

    public static void renameTag(CompoundNBT nbt, String oldName, String newName) {

        INBT tag = nbt.get(oldName);
        if (tag != null) {
            nbt.remove(oldName);
            nbt.put(newName, tag);
        }
    }

    public static BlockState readBlockState(JsonObject obj) {

        CompoundNBT nbt = (CompoundNBT)new Dynamic<>(JsonOps.INSTANCE, obj).convert(NBTDynamicOps.INSTANCE).getValue();
        JsonUtils.renameTag(nbt, "name", "Name");
        JsonUtils.renameTag(nbt, "properties", "Properties");
        String name = nbt.getString("Name");
        ResourceLocation id = ResourceLocation.tryCreate(name);
        if (id == null || !ForgeRegistries.BLOCKS.containsKey(id))
            throw new IllegalArgumentException("Invalid or unknown block ID: " + name);

        return NBTUtil.readBlockState(nbt);
    }

    public static JsonObject writeBlockState(BlockState state) {

        CompoundNBT nbt = NBTUtil.writeBlockState(state);
        JsonUtils.renameTag(nbt, "Name", "name");
        JsonUtils.renameTag(nbt, "Properties", "properties");
        Dynamic<INBT> dyn = new Dynamic<>(NBTDynamicOps.INSTANCE, nbt);
        return dyn.convert(JsonOps.INSTANCE).getValue().getAsJsonObject();
    }
}
