package com.bafomdad.uniquecrops.core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

public class UCWorldData extends SavedData {

    public static final String ID = "UCWorldData";

    public UCWorldData() {}

    public UCWorldData(CompoundTag tag) {

        ServerLifecycleHooks.getCurrentServer().getAllLevels().forEach(sw -> {
           String s = sw.dimension().location().toString();
           ListTag savedList = tag.getList(s, 10);
           UCProtectionHandler.getInstance().clearQueue(sw);
           for (int i = 0; i < savedList.size(); i++) {
               CompoundTag saveTag = savedList.getCompound(i);
               ChunkPos cPos = new ChunkPos(saveTag.getInt("chunkX"), saveTag.getInt("chunkZ"));
               UCProtectionHandler.getInstance().addChunk(sw, cPos, false);
           }
        });

        ListTag tagList = tag.getList("savedOres", 10);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag chunkTag = tagList.getCompound(i);
            BlockPos pos = BlockPos.of(chunkTag.getLong("blockPos"));
            BlockPos cPos = BlockPos.of(chunkTag.getLong("chunkPos"));
            UCOreHandler.getInstance().getSaveInfo().put(new ChunkPos(cPos.getX(), cPos.getZ()), pos);
        }
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {

        String[] allDims = UCProtectionHandler.getInstance().getUnsavedDims().toArray(new String[0]);
        for (String s : allDims) {
            ResourceKey<Level> key = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(s));
            Level world = ServerLifecycleHooks.getCurrentServer().getLevel(key);
            if (world != null) {
                ListTag savedList = new ListTag();
                for (ChunkPos pos : UCProtectionHandler.getInstance().getChunkInfo(world)) {
                    CompoundTag chunkTag = new CompoundTag();
                    chunkTag.putInt("chunkX", pos.x);
                    chunkTag.putInt("chunkZ", pos.z);
                    savedList.add(chunkTag);
                }
                tag.put(s, savedList);
            }
        }
        ListTag tagList = new ListTag();
         for (ChunkPos cPos : UCOreHandler.getInstance().getUnsavedChunks()) {
             CompoundTag chunkTag = new CompoundTag();
             chunkTag.putLong("chunkPos", new BlockPos(cPos.x, 0, cPos.z).asLong());
             chunkTag.putLong("blockPos", UCOreHandler.getInstance().getSaveInfo().get(cPos).asLong());
             tagList.add(chunkTag);
         }
         tag.put("savedOres", tagList);
        return tag;
    }

    public static UCWorldData getInstance(Level world) {

        if (world.isClientSide())
            throw new RuntimeException("Don't access this clientside!");

        DimensionDataStorage storage = ((ServerLevel)world).getDataStorage();
        return storage.computeIfAbsent(UCWorldData::new, UCWorldData::new, ID);
    }
}
