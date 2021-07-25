package com.bafomdad.uniquecrops.core;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.enums.EnumGrowthSteps;
import com.bafomdad.uniquecrops.crafting.RecipeMultiblock;
import com.bafomdad.uniquecrops.network.PacketChangeBiome;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UCUtils {

    public static PlayerEntity getPlayerFromUUID(String uuid) {

        return UniqueCrops.proxy.getPlayerFromUUID(uuid);
    }

    public static LivingEntity getTaggedEntity(UUID uuid) {

        if (uuid != null) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            for (ServerWorld ws : server.getWorlds()) {
                Entity entity = ws.func_242105_c(uuid);
                if (entity instanceof LivingEntity && entity.isAlive())
                    return (LivingEntity)entity;
            }
        }
        return null;
    }

    public static void serializeArray(PacketBuffer buf, String[] str) {

        String serialize = Arrays.stream(str).map(s -> s.replace(",", "\\,")).collect(Collectors.joining(","));
        buf.writeString(serialize);
    }

    public static String[] deserializeString(PacketBuffer buf) {

        String[] deserialize = Pattern.compile("(?<!\\\\),").splitAsStream(buf.readString()).map(s -> s.replace("\\,", ",")).toArray(String[]::new);
        return deserialize;
    }

    public static String[] convertJson(JsonArray array) {

        if (array == null) return null;

        String[] arr = new String[array.size()];
        for (int i = 0; i < arr.length; i++)
            arr[i] = array.get(i).getAsString();

        return arr;
    }

    public static CompoundNBT serializeMap(String name, Map<Character, RecipeMultiblock.Slot> recipe) {

        CompoundNBT nbt = new CompoundNBT();
        ListNBT list = new ListNBT();
        for (Map.Entry<Character, RecipeMultiblock.Slot> map : recipe.entrySet()) {
            CompoundNBT charTag = new CompoundNBT();
            int[] states = map.getValue().states.stream().mapToInt(s -> Block.getStateId(s)).toArray();
            charTag.putIntArray(map.getKey().toString(), states);
            list.add(charTag);
        }
        nbt.put(name, list);
        UniqueCrops.LOGGER.debug("Serializing: " + nbt);
        return nbt;
    }

    public static Map<Character, RecipeMultiblock.Slot> deserializeMap(String name, CompoundNBT nbt) {

        Map<Character, RecipeMultiblock.Slot> map = new HashMap<>();
        if (nbt.contains(name)) {
            ListNBT list = nbt.getList(name, 10);
            for (int i = 0; i < list.size(); i++) {
                CompoundNBT tag = list.getCompound(i);
                for (String str : tag.keySet()) {
                    BlockState[] states = Arrays.stream(tag.getIntArray(str)).mapToObj(b -> Block.getStateById(b)).toArray(BlockState[]::new);
                    RecipeMultiblock.Slot slot = new RecipeMultiblock.Slot(states);
                    map.put(str.charAt(0), slot);
                }
            }
        }
        UniqueCrops.LOGGER.debug("Deserializing: " + nbt);
        return map;
    }

    public static TileEntity getClosestTile(Class tileToFind, World world, BlockPos pos, double dist) {

        TileEntity closest = null;
        for (TileEntity tile : world.loadedTileEntityList) {
            if (tile.getClass() == tileToFind && !pos.equals(tile.getPos())) {
                double distance = tile.getPos().distanceSq(pos);
                if (closest == null && distance <= dist) {
                    closest = tile;
                    break;
                }
            }
        }
        return closest;
    }

    public static ListNBT getServerTaglist(UUID uuid) {

        LivingEntity entity = getTaggedEntity(uuid);
        if (!(entity instanceof PlayerEntity)) return null;

        CompoundNBT tag = entity.getPersistentData();
        if (tag.contains(UCStrings.TAG_GROWTHSTAGES))
            return tag.getList(UCStrings.TAG_GROWTHSTAGES, 10);

        return null;
    }

    public static Inventory wrap(List<ItemStack> stacks) {

        Inventory inv = new Inventory(stacks.size()) {
            @Override
            public int getInventoryStackLimit() {

                return 1;
            }
        };
        for (int i = 0; i < stacks.size(); i++)
            inv.setInventorySlotContents(i, stacks.get(i));

        return inv;
    }

    public static void drawSplitString(MatrixStack ms, FontRenderer font, ITextComponent text, float x, float y, int wordWrap, int color) {

        for (IReorderingProcessor proc : font.trimStringToWidth(text, wordWrap)) {
            font.func_238407_a_(ms, proc, x, y, color);
            y += font.FONT_HEIGHT;
        }
    }

    public static void generateSteps(PlayerEntity player) {

        CompoundNBT tag = player.getPersistentData();
        if (player.getEntityWorld().rand.nextInt(200) == 0) {
            if (tag.contains(UCStrings.TAG_GROWTHSTAGES))
                tag.remove(UCStrings.TAG_GROWTHSTAGES);

            ListNBT taglist = new ListNBT();
            CompoundNBT tag2 = new CompoundNBT();
            tag2.putInt("stage0", 20);
            taglist.add(tag2);
            tag.put(UCStrings.TAG_GROWTHSTAGES, taglist);
            return;
        }
        List<EnumGrowthSteps> copysteps = Arrays.stream(EnumGrowthSteps.values())
                .filter(g -> g.isEnabled() && g != EnumGrowthSteps.SELFSACRIFICE).collect(Collectors.toList());

        Collections.shuffle(copysteps);
        ListNBT taglist = new ListNBT();
        for (int i = 0; i < 7; ++i) {
            CompoundNBT tag2 = new CompoundNBT();
            int index = copysteps.get(0).ordinal();
            copysteps.remove(0);
            tag2.putInt("stage" + i, index);
            taglist.add(tag2);
        }
        tag.put(UCStrings.TAG_GROWTHSTAGES, taglist);
    }

    public static boolean setBiome(ResourceLocation biomeId, World world, BlockPos pos) {

        if (world.isRemote) return false;

        final int WIDTH_BITS = (int) Math.round(Math.log(16.0D) / Math.log(2.0D)) - 2;
        final int HEIGHT_BITS = (int) Math.round(Math.log(256.0D) / Math.log(2.0D)) - 2;
        final int HORIZONTAL_MASK = (1 << WIDTH_BITS) - 1;
        final int VERTICAL_MASK = (1 << HEIGHT_BITS) - 1;
        Biome biome = world.func_241828_r().getRegistry(Registry.BIOME_KEY).getOrDefault(biomeId);
        Chunk chunkAt = world.getChunk(pos.getX() >> 4, pos.getZ() >> 4);

        int x = (pos.getX() >> 2) & HORIZONTAL_MASK;
        int z = (pos.getZ() >> 2) & HORIZONTAL_MASK;
        if (chunkAt.getBiomes().biomes[z << WIDTH_BITS | x] == biome)
            return false;

        for (int dy = 0; dy < 255; dy += 4) {
            int y = MathHelper.clamp(dy >> 2, 0, VERTICAL_MASK);
            chunkAt.getBiomes().biomes[y << WIDTH_BITS + WIDTH_BITS | z << WIDTH_BITS | x] = biome;
        }
        if (world instanceof ServerWorld) {
            PacketChangeBiome msg = new PacketChangeBiome(pos, biome.getRegistryName());
            UCPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunkAt), msg);
        }
        return true;
    }

    public static <E> List<E> makeCollection(Iterable<E> iter, boolean shuffle) {

        List<E> list = new ArrayList<E>();
        iter.forEach(list::add);
        if (shuffle)
            Collections.shuffle(list);

        return list;
    }

    public static <T> T selectRandom(Random rand, T... type) {

        return type[rand.nextInt(type.length)];
    }
}