package com.bafomdad.uniquecrops.dimension;

import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.NoiseGeneratorSimplex;

import com.bafomdad.uniquecrops.init.UCBlocks;
import com.google.common.collect.ImmutableList;

public class CropWorldChunkGenerator implements IChunkGenerator {
	
	private Random rand;
	private final World world;
//	private CropTerrainGenerator terraingen;
	private NoiseGeneratorPerlin noiseGen;
	private NoiseGeneratorSimplex simplexGen;
	
	public CropWorldChunkGenerator(World world) {
		
		this.world = world;
		this.rand = new Random(world.getSeed());
		
		this.noiseGen = new NoiseGeneratorPerlin(rand, 4);
		this.simplexGen = new NoiseGeneratorSimplex(rand);
//		this.terraingen = new CropTerrainGenerator();
	}
	
	@Override
	public Chunk generateChunk(int x, int z) {
		
		ChunkPrimer primer = new ChunkPrimer();
		
//		terraingen.generate(x, z, primer);
//		if (shouldFillChunk(x, z))
//			setBlocksInChunk2(x, z, primer);

		Chunk chunk = new Chunk(this.world, primer, x, z);
		chunk.generateSkylightMap();
		return chunk;
	}
	
	public void setBlocksInChunk2(int chunkX, int chunkZ, ChunkPrimer primer) {
		
		int offset = 20;
		
		int x = chunkX * 16;
		int z = chunkZ * 16;
		
		int xRange = rand.nextInt(4) + 8;
		int zRange = rand.nextInt(4) + 8;
		
		double[] noise = new double[256];
		Ellipse2D.Double ellipse = new Ellipse2D.Double(4, 4, xRange, zRange);
		
		int l = 0;
		int lowest = 256;
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				if (ellipse.contains(i, j)) {
					double k = 4 * noiseGen.getValue(x + i, z + j);
//					double d = 0.25;
//					k = (1 + k - d) / 2;
					noise[l] = k + 1;
					if ((int)k < lowest && k > 0)
						lowest = (int)k;
				}
				else
					noise[l] = 0;
//				if (noise[l] > 0 && (noise[l] + offset > 0 && noise[l] < 256)) {
//					int y = (int)noise[l] + offset;
//					primer.setBlockState(i, y, j, UCBlocks.oldGrass.getDefaultState());
//					if (lowest < 256 && lowest > 0) {
//						for (int y1 = y - 1; y1 > lowest / 2; --y1) {
//							primer.setBlockState(i, y1, j, Blocks.DIRT.getDefaultState());
//						}
//					}
//				}
				l++;
			}
		}
	}
	
	public void setBlocksInChunk(int chunkX, int chunkZ, ChunkPrimer primer) {

		int offset = 32;

		int x = chunkX * 16;
		int z = chunkZ * 16;

		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				double k = 32 * getAltitude(x + i, z + j, 0.125); // 0.125

				if ((k + 28) > offset && (k + 28) < 255) {
					primer.setBlockState(i, offset + (int)k, j, UCBlocks.oldGrass.getDefaultState());
					if ((-k + offset) + 1 >= 0)
						primer.setBlockState(i, (int)(-k + offset) + 1, j, Blocks.DIRT.getDefaultState());
				}
				for (int y = 255; y > 0; --y) {
					if (y < (k + 31) && y > (-k + offset))
						primer.setBlockState(i, y, j, Blocks.DIRT.getDefaultState());
				}
			}
		}
	}
	
	private double getAltitude(double i, double j, double delta) {
		
		return Math.sin((i * delta)) * Math.cos((j * delta));
	}
	
	private double getNoiseGen(double i, double j, double delta) {
		
		return this.noiseGen.getValue(i * delta, j * delta);
	}
	
	private boolean shouldFillChunk(int x, int z) {
		
		int xOffset = x;
		int zOffset = z;
		
		int spacing = 4;
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

	@Override
	public void populate(int x, int z) {}

	@Override
	public boolean generateStructures(Chunk chunk, int x, int z) {

		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType type, BlockPos pos) {

		return ImmutableList.of();
	}

	@Override
	public BlockPos getNearestStructurePos(World world, String structureName, BlockPos pos, boolean findUnexplored) {

		return null;
	}

	@Override
	public void recreateStructures(Chunk chunk, int x, int z) {}

	@Override
	public boolean isInsideStructure(World world, String structureName, BlockPos pos) {

		return false;
	}
}
