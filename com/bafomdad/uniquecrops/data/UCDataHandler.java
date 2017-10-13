package com.bafomdad.uniquecrops.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class UCDataHandler {

	private static UCDataHandler INSTANCE;
	
	private ConcurrentHashMap<Integer, Set<ChunkPos>> saveInfo = new ConcurrentHashMap<Integer, Set<ChunkPos>>();
	
	public static UCDataHandler getInstance() {
		
		if (INSTANCE == null)
			INSTANCE = new UCDataHandler();
		
		return INSTANCE;
	}
	
	public Set<ChunkPos> getChunkInfo(int dimId) {
		
		if (saveInfo.get(dimId) == null) {
			Set<ChunkPos> save = new HashSet<>();
			saveInfo.put(dimId, save);
			saveInfo.get(dimId).add(new ChunkPos(0, 0));
		}
		return saveInfo.get(dimId);
	}
	
	public void refresh(int dimId, ChunkPos pos) {
		
	}
	
	public void addChunk(int dimId, ChunkPos pos, boolean dirty) {
		
		if (!getChunkInfo(dimId).contains(pos))
			getChunkInfo(dimId).add(pos);
		if (dirty)
			UCWorldData.getInstance(dimId).setDirty(true);
	}
	
	public void removeChunk(int dimId, ChunkPos pos, boolean dirty) {
		
		getChunkInfo(dimId).remove(pos);
		if (dirty)
			UCWorldData.getInstance(dimId).setDirty(true);
	}
	
	public synchronized Set<ChunkPos> getSavedChunks(World world, ChunkPos pos) {
		
		if (world == null)
			return null;
		
		Set<ChunkPos> info = getChunkInfo(world.provider.getDimension());
		if (info.contains(pos) && info.size() > 0)
			return info;
		
		return null;
	}
	
	public void clearQueue(int dimId) {
		
		getChunkInfo(dimId).clear();
	}
	
	public Set<Integer> getUnsavedDims() {
		
		return saveInfo.keySet();
	}
}
