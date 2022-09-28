package com.bafomdad.uniquecrops.core;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UCOreHandler {

    private static UCOreHandler INSTANCE;

    private ConcurrentHashMap<ChunkPos, BlockPos> saveInfo = new ConcurrentHashMap<>();

    private UCOreHandler() {}

    public static UCOreHandler getInstance() {

        if (INSTANCE == null) {
            for (ServerLevel ws : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
                if (ws.dimension() == Level.OVERWORLD)
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

    public void addChunk(Level level, BlockPos ore, boolean dirty) {

        if (!ore.equals(BlockPos.ZERO))
            saveInfo.put(new ChunkPos(ore), ore);

        if (dirty)
            UCWorldData.getInstance(level).setDirty(true);
    }

    public void removeChunk(Level level, BlockPos ore, boolean dirty) {

        ChunkPos cPos = new ChunkPos(ore);
        saveInfo.remove(cPos);
        if (dirty)
            UCWorldData.getInstance(level).setDirty(true);
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
