package com.bafomdad.uniquecrops.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class UCWorldData extends WorldSavedData {
	
	public static final String ID = "UCWorldData";

	public UCWorldData(String name) {
		
		super(name);
	}
	
	public UCWorldData() {
		
		this(ID);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {

		int[] dims = tag.getIntArray("savedDims");
		
		for (int dimId : dims) {
			NBTTagList savedList = tag.getTagList("savedList" + dimId, 10);
			UCDataHandler.getInstance().clearQueue(dimId);
			for (int i = 0; i < savedList.tagCount(); i++) {
				NBTTagCompound saveTag = savedList.getCompoundTagAt(i);
				ChunkPos cPos = new ChunkPos(saveTag.getInteger("chunkX"), saveTag.getInteger("chunkZ"));
//				System.out.println("UCWorldData: " + cPos);
				UCDataHandler.getInstance().addChunk(dimId, cPos, false);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {

		Integer[] allDims = UCDataHandler.getInstance().getUnsavedDims().toArray(new Integer[0]);
		int[] savedDims = new int[allDims.length];
		for (int i = 0; i < allDims.length; i++)
			savedDims[i] = allDims[i];
		
		tag.setIntArray("savedDims", savedDims);
		for (int d : savedDims) {
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().worlds[d];
			if (world != null) {
				NBTTagList savedList = new NBTTagList();
				for (ChunkPos pos : UCDataHandler.getInstance().getChunkInfo(d)) {
					NBTTagCompound chunkTag = new NBTTagCompound();
					chunkTag.setInteger("chunkX", pos.x);
					chunkTag.setInteger("chunkZ", pos.z);
					savedList.appendTag(chunkTag);
				}
				tag.setTag("savedList" + d, savedList);
			}
		}
		return tag;
	}
	
	public static UCWorldData getInstance(int dimId) {
		
		WorldServer world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(dimId);
		if (world != null) {
			WorldSavedData handler = world.getMapStorage().getOrLoadData(UCWorldData.class, ID);
			if (handler == null) {
				handler = new UCWorldData();
				world.getMapStorage().setData(ID, handler);
			}
			return (UCWorldData)handler;
		}
		return null;
	}
}
