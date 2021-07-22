package com.bafomdad.uniquecrops.core;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class UCWorldData extends WorldSavedData {

    public static final String ID = "UCWorldData";

    public UCWorldData() {

        super(ID);
    }

    @Override
    public void read(CompoundNBT tag) {

        ServerLifecycleHooks.getCurrentServer().getWorlds().forEach(sw -> {
           String s = sw.getDimensionKey().getLocation().toString();
           ListNBT savedList = tag.getList(s, 10);
           UCProtectionHandler.getInstance().clearQueue(sw.getDimensionKey());
           for (int i = 0; i < savedList.size(); i++) {
               CompoundNBT saveTag = savedList.getCompound(i);
               ChunkPos cPos = new ChunkPos(saveTag.getInt("chunkX"), saveTag.getInt("chunkZ"));
               UCProtectionHandler.getInstance().addChunk(sw.getDimensionKey(), cPos, false);
           }
        });

        ListNBT tagList = tag.getList("savedOres", 10);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundNBT chunkTag = tagList.getCompound(i);
            BlockPos pos = BlockPos.fromLong(chunkTag.getLong("blockPos"));
            BlockPos cPos = BlockPos.fromLong(chunkTag.getLong("chunkPos"));
            UCOreHandler.getInstance().getSaveInfo().put(new ChunkPos(cPos.getX(), cPos.getZ()), pos);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {

        String[] allDims = UCProtectionHandler.getInstance().getUnsavedDims().toArray(new String[0]);
        ListNBT savedDims = new ListNBT();
        for (String s : allDims) {
            RegistryKey<World> world = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(s));
            if (world != null) {
                ListNBT savedList = new ListNBT();
                for (ChunkPos pos : UCProtectionHandler.getInstance().getChunkInfo(world)) {
                    CompoundNBT chunkTag = new CompoundNBT();
                    chunkTag.putInt("chunkX", pos.x);
                    chunkTag.putInt("chunkZ", pos.z);
                    savedList.add(chunkTag);
                }
                tag.put(s, savedList);
            }
        }

        ListNBT tagList = new ListNBT();
         for (ChunkPos cPos : UCOreHandler.getInstance().getUnsavedChunks()) {
             CompoundNBT chunkTag = new CompoundNBT();
             chunkTag.putLong("chunkPos", new BlockPos(cPos.x, 0, cPos.z).toLong());
             chunkTag.putLong("blockPos", UCOreHandler.getInstance().getSaveInfo().get(cPos).toLong());
             tagList.add(chunkTag);
         }
         tag.put("savedOres", tagList);
        return tag;
    }

    public static UCWorldData getInstance(RegistryKey<World> worldkey) {

        ServerWorld sw = ServerLifecycleHooks.getCurrentServer().getWorld(worldkey);
        UCWorldData data = sw.getSavedData().get(UCWorldData::new, ID);
        if (data == null) {
            data = new UCWorldData();
            data.markDirty();
            sw.getSavedData().set(data);
        }
        return data;
    }
}
