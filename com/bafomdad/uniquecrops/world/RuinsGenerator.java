package com.bafomdad.uniquecrops.world;

import java.util.Random;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCConfig;

import net.minecraft.init.Biomes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

public class RuinsGenerator implements IWorldGenerator {
	
	public static final ResourceLocation RUINS_STRUCTURE = new ResourceLocation(UniqueCrops.MOD_ID, "cropruins");

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

		if (!(world instanceof WorldServer) || world.provider.getDimension() != 0) return;
		
		WorldServer serverWorld = (WorldServer)world;

		int x = chunkX * 16 + random.nextInt(8);
		int z = chunkZ * 16 + random.nextInt(8);
		
		BlockPos pos = new BlockPos(x, 10, z);
		Biome biome = world.getBiomeForCoordsBody(pos);
		if (biome != Biomes.OCEAN && biome != Biomes.DEEP_OCEAN) {
			if (random.nextInt(UCConfig.worldGenerationRuinsWeight) == 0) {
				generateRuins(serverWorld, random, pos);
			}
		}
	}
	
	public void generateRuins(WorldServer world, Random rand, BlockPos pos) {
		
		MinecraftServer server = world.getMinecraftServer();
		Template template = world.getStructureTemplateManager().getTemplate(server, RUINS_STRUCTURE);
		PlacementSettings settings = new PlacementSettings().setRotation(Rotation.values()[rand.nextInt(Rotation.values().length)]);
		template.addBlocksToWorld(world, pos, settings);
	}
}
