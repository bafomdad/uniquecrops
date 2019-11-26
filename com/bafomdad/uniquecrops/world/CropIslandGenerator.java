package com.bafomdad.uniquecrops.world;

import java.util.Map;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.IWorldGenerator;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCDimension;
import com.bafomdad.uniquecrops.init.UCLootTables;

public class CropIslandGenerator implements IWorldGenerator {
	
	public static final ResourceLocation PLATFORM = new ResourceLocation(UniqueCrops.MOD_ID, "platform0");
	public static final ResourceLocation ISLAND_TOP = new ResourceLocation(UniqueCrops.MOD_ID, "islandtop0");
	public static final ResourceLocation ISLAND_BOTTOM = new ResourceLocation(UniqueCrops.MOD_ID, "islandbottom0");
	
	private static final ResourceLocation[] LOOTS = new ResourceLocation[] {
		UCLootTables.CHEST_ISLAND,
		LootTableList.CHESTS_WOODLAND_MANSION
	};

	public CropIslandGenerator() {}
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunk, IChunkProvider provider) {
		
		if (!canGenerate(world.provider.getDimension())) return;
		if (!(world instanceof WorldServer)) return;
		
		if (shouldFillChunk(chunkX, chunkZ, 4)) {
			int x = (chunkX * 16) + 1;
			int z = (chunkZ * 16) + 1;
			int y = 20;
			
			MinecraftServer server = ((WorldServer)world).getMinecraftServer();
			Rotation rotation = Rotation.values()[rand.nextInt(Rotation.values().length)];
			PlacementSettings settings = new PlacementSettings().setRotation(rotation);
			BlockPos blockPos = new BlockPos(x, y, z);

			Template islandBottom = ((WorldServer)world).getStructureTemplateManager().getTemplate(server, ISLAND_BOTTOM);
			BlockPos transformedPos = islandBottom.getZeroPositionWithTransform(blockPos, Mirror.NONE, rotation);
			islandBottom.addBlocksToWorld(world, transformedPos, settings);
			Template islandTop = ((WorldServer)world).getStructureTemplateManager().getTemplate(server, ISLAND_TOP);
			BlockPos posTop = blockPos.up(islandBottom.getSize().getY());
			transformedPos = islandTop.getZeroPositionWithTransform(posTop, Mirror.NONE, rotation);
			WorldGenSkyTree tree = new WorldGenSkyTree(true, rand.nextBoolean());
			islandTop.addBlocksToWorld(world, transformedPos, settings);
			Map<BlockPos, String> mapData = islandTop.getDataBlocks(transformedPos, settings);
			for (Map.Entry<BlockPos, String> entry : mapData.entrySet()) {
				String data = entry.getValue();
				if ("islandchest".equals(data)) {
					BlockPos chestPos = entry.getKey();
					TileEntity tile = world.getTileEntity(chestPos.down());
					if (tile instanceof TileEntityLockableLoot)
						((TileEntityLockableLoot)tile).setLootTable(LOOTS[rand.nextInt(LOOTS.length)], rand.nextLong());
				}
				if ("tree0".equals(data)) {
					BlockPos keyPos = entry.getKey();
					if (rand.nextInt(5) == 0) {
						generateTree(world, rand, keyPos);
					}
				}
				if ("tree1".equals(data)) {
					BlockPos keyPos = entry.getKey();
					if (rand.nextInt(4) == 0) {
						generateTree(world, rand, keyPos);
					} else {
						if (rand.nextBoolean()) {
							world.setBlockToAir(keyPos);
							world.setBlockState(keyPos.down(), Blocks.WATER.getDefaultState(), 2);
							world.setBlockState(keyPos, Blocks.WATERLILY.getDefaultState(), 2);
						} else {
							world.setBlockState(keyPos.down(), UCBlocks.oldGrass.getDefaultState(), 2);
						}
					}
				}
			}
		} else {
			generatePlatform(rand, chunkX, chunkZ, ((WorldServer)world));
		}
	}
	
	private void generatePlatform(Random rand, int chunkX, int chunkZ, WorldServer worldServer) {
		
		int x = (chunkX * 16) + 1;
		int z = (chunkZ * 16) + 1;
		int y = 36 + rand.nextInt(1);
		
		MinecraftServer server = worldServer.getMinecraftServer();
		Rotation rotation = Rotation.values()[rand.nextInt(Rotation.values().length)];
		PlacementSettings settings = new PlacementSettings().setRotation(rotation);
		BlockPos pos = new BlockPos(x, y, z);
		
		Template platform = worldServer.getStructureTemplateManager().getTemplate(server, PLATFORM);
		BlockPos transformedPos = platform.getZeroPositionWithTransform(pos, Mirror.NONE, rotation);
		ITemplateProcessor customProcessor = new RuinedBricksProcessor(transformedPos, settings);
		platform.addBlocksToWorld(worldServer, transformedPos, customProcessor, settings, 2);
	}

	private void generateTree(World world, Random rand, BlockPos pos) {
		
		world.setBlockToAir(pos);
		WorldGenSkyTree treeGen = new WorldGenSkyTree(true, rand.nextBoolean());
		treeGen.generate(world, rand, pos);
	}
	
	private boolean shouldFillChunk(int x, int z, int spacing) {
		
		int xOffset = x;
		int zOffset = z;
		
		int clusterSize = 1;
		int width = spacing + clusterSize;
		
		if (xOffset < 0)
			xOffset = -xOffset + clusterSize;
		if (zOffset < 0)
			zOffset = -zOffset + clusterSize;
		
		xOffset %= width;
		zOffset %= width;
		
		return xOffset < clusterSize && zOffset < clusterSize;
	}
	
	private boolean canGenerate(int dim) {
		
		return dim == UCDimension.dimID;
	}
}
