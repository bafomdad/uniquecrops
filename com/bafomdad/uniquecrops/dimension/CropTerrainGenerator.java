package com.bafomdad.uniquecrops.dimension;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

public class CropTerrainGenerator {
	
	private double[] densities;
	private long seed = 0x1fff;
	private final Random rand = new Random((seed + 516) * 314);
	
	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	private NoiseGeneratorOctaves noiseGen4;
	private NoiseGeneratorOctaves noiseGen5;
	
	private double[] noiseData1;
	private double[] noiseData2;
	private double[] noiseData3;
	private double[] noiseData4;
	private double[] noiseData5;
	
	private final double maxFactor;
	private final double minFactor;
	private final int offset;
	
	public CropTerrainGenerator() {
		
		maxFactor = -600.0D;
		minFactor = -200.0D;
		offset = 11;
		
		this.noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
		this.noiseGen4 = new NoiseGeneratorOctaves(rand, 10);
		this.noiseGen5 = new NoiseGeneratorOctaves(rand, 16);
	}
	
	private double[] initializeNoiseField(double[] densities, int chunkX, int chunkY, int chunkZ, int sizeX, int sizeY, int sizeZ) {
		
        if (densities == null) {
            densities = new double[sizeX * sizeY * sizeZ];
        }

        double d0 = 684.412D;
        double d1 = 684.412D;
        this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, chunkX, chunkZ, sizeX, sizeZ, 1.121D, 1.121D, 0.5D);
        this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, chunkX, chunkZ, sizeX, sizeZ, 200.0D, 200.0D, 0.5D);
        d0 *= 2.0D;
        this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, chunkX, chunkY, chunkZ, sizeX, sizeY, sizeZ, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, chunkX, chunkY, chunkZ, sizeX, sizeY, sizeZ, d0, d1, d0);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, chunkX, chunkY, chunkZ, sizeX, sizeY, sizeZ, d0, d1, d0);
        int k1 = 0;

        Random random = new Random(chunkX * 13 + chunkY * 157 + chunkZ * 13883);
        random.nextFloat();

        for (int x = 0; x < sizeX; ++x) {
            for (int z = 0; z < sizeZ; ++z) {

                float f2 = -20.0f;

                for (int y = 0; y < sizeY; ++y) {
                    double d5 = 0.0D;

                    double d7 = this.noiseData2[k1] / 512.0D;
                    double d8 = this.noiseData3[k1] / 512.0D;
                    double d9 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;

                    if (d9 < 0.0D) {
                        d5 = d7;
                    }
                    else if (d9 > 1.0D) {
                        d5 = d8;
                    } else {
                        d5 = d7 + (d8 - d7) * d9;
                    }

                    d5 -= 8.0D;
                    d5 += f2;
                    int b0 = 2;
                    double d10;

                    if (y > ((sizeY / 2) - b0)) {
                        d10 = ((y - (sizeY / 2 - b0)) / 64.0F);

                        if (d10 < 0.0D) {
                            d10 = 0.0D;
                        } else if (d10 > 1.0D) {
                            d10 = 1.0D;
                        }

                        d5 = d5 * (1.0D - d10) + maxFactor * d10;
                    }

                    b0 = offset;

                    if (y < b0) {
                        d10 = ((b0 - y) / (b0 - 1.0F));
                        d5 = d5 * (1.0D - d10) + minFactor * d10;
                    }

                    densities[k1] = d5;
                    ++k1;
                }
            }
        }
        return densities;
	}
	
	public void generate(int chunkX, int chunkZ, ChunkPrimer primer) {
		
        byte b0 = 2;
        int k = b0 + 1;
        byte b1 = 33;
        int l = b0 + 1;
        this.densities = this.initializeNoiseField(this.densities, chunkX * b0, 0, chunkZ * b0, k, b1, l);

        for (int x2 = 0; x2 < b0; ++x2) {
            for (int z2 = 0; z2 < b0; ++z2) {
                for (int height32 = 0; height32 < 32; ++height32) {
                    double d0 = 0.25D;
                    double d1 = this.densities[((x2 + 0) * l + z2 + 0) * b1 + height32 + 0];
                    double d2 = this.densities[((x2 + 0) * l + z2 + 1) * b1 + height32 + 0];
                    double d3 = this.densities[((x2 + 1) * l + z2 + 0) * b1 + height32 + 0];
                    double d4 = this.densities[((x2 + 1) * l + z2 + 1) * b1 + height32 + 0];
                    double d5 = (this.densities[((x2 + 0) * l + z2 + 0) * b1 + height32 + 1] - d1) * d0;
                    double d6 = (this.densities[((x2 + 0) * l + z2 + 1) * b1 + height32 + 1] - d2) * d0;
                    double d7 = (this.densities[((x2 + 1) * l + z2 + 0) * b1 + height32 + 1] - d3) * d0;
                    double d8 = (this.densities[((x2 + 1) * l + z2 + 1) * b1 + height32 + 1] - d4) * d0;

                    for (int h = 0; h < 8; ++h) {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        int height = (height32 * 4) + h;

                        for (int x = 0; x < 8; ++x) {
                            int index = ((x + (x2 * 8)) << 12) | ((0 + (z2 * 8)) << 8) | height;
                            short maxheight = 256;
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int z = 0; z < 8; ++z) {
                                if (d15 > 0.0D) {
                                	primer.setBlockState(x, index, z, Blocks.DIRT.getDefaultState());
                                } else {
                                    primer.setBlockState(x, index, z, Blocks.AIR.getDefaultState());
                                }
                                index += maxheight;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }
                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
	}
}
