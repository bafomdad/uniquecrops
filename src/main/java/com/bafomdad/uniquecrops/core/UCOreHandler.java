package com.bafomdad.uniquecrops.core;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UCOreHandler {

    private static UCOreHandler INSTANCE;

    private ConcurrentHashMap<ChunkPos, BlockPos> saveInfo = new ConcurrentHashMap<>();

    private UCOreHandler() {}

    public static UCOreHandler getInstance() {

        if (INSTANCE == null) {
            for (ServerWorld ws : ServerLifecycleHooks.getCurrentServer().getWorlds()) {
                if (ws.getDimensionKey() == World.OVERWORLD)
                    INSTANCE = new UCOreHandler();
            }
        }
        return INSTANCE;
    }

    public BlockPos getChunkInfo(BlockPos currentPos) {

        ChunkPos chunk = new ChunkPos(currentPos);
        if (saveInfo.containsKey(chunk))
            return saveInfo.get(chunk);

        return BlockPos.ZERO;
    }

    public void addChunk(BlockPos ore, boolean dirty) {

        if (!ore.equals(BlockPos.ZERO))
            saveInfo.put(new ChunkPos(ore), ore);

        if (dirty)
            UCWorldData.getInstance(World.OVERWORLD).setDirty(true);
    }

    public void removeChunk(BlockPos ore, boolean dirty) {

        ChunkPos cPos = new ChunkPos(ore);
        saveInfo.remove(cPos);
        if (dirty)
            UCWorldData.getInstance(World.OVERWORLD).setDirty(true);
    }

    public Set<ChunkPos> getUnsavedChunks() {

        return saveInfo.keySet();
    }

    public ConcurrentHashMap<ChunkPos, BlockPos> getSaveInfo() {

        return saveInfo;
    }

    public void clearOreQueue() {

        saveInfo.clear();
    }
}
