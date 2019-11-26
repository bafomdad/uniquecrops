package com.bafomdad.uniquecrops.data;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class UCOreHandler {

	private static UCOreHandler INSTANCE;

	private ConcurrentHashMap<ChunkPos, BlockPos> saveInfo = new ConcurrentHashMap();

	private UCOreHandler() {}
	
	public static UCOreHandler getInstance() {
		
		if (INSTANCE == null)
			INSTANCE = new UCOreHandler();
		
		return INSTANCE;
	}
	
	public BlockPos getChunkInfo(BlockPos currentPos) {
		
		ChunkPos chunk = new ChunkPos(currentPos);
		if (saveInfo.containsKey(chunk))
			return saveInfo.get(chunk);
		
		return BlockPos.ORIGIN;
	}
	
	public void addChunk(BlockPos ore, boolean dirty) {
		
		if (!ore.equals(BlockPos.ORIGIN))
			saveInfo.put(new ChunkPos(ore), ore);
			
		if (dirty)
			UCWorldData.getInstance(0).setDirty(true);
	}
	
	public void removeChunk(BlockPos ore, boolean dirty) {
		
		ChunkPos cPos = new ChunkPos(ore);
		saveInfo.remove(cPos);
		
		if (dirty)
			UCWorldData.getInstance(0).setDirty(true);
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
