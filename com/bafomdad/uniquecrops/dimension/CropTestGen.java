package com.bafomdad.uniquecrops.dimension;

import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class CropTestGen {
	
	public static void main(String[] args) {
		
		Random rand = new Random();
		NoiseGeneratorPerlin perlin = new NoiseGeneratorPerlin(rand, 4);
		double[] noise = new double[256];
		Ellipse2D.Double ellipse = new Ellipse2D.Double(4, 4, 8, 8);
		
		int index = 0;
		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				if (ellipse.contains(i, j)) {
					double k = 4 * perlin.getValue(i, j);
					noise[index] = k;
				}
				index++;
//				double k = 8 * getAltitude(i, j, 360);
//				double d = 0.25;
//				k = (1 + k - d) / 2;
//				System.out.println((int)k);
			}
		}
		Arrays.sort(noise);
		for (double d : noise)
			System.out.println(d);
	}
	
	private static double getAltitude(double i, double j, double alpha) {
		
		return .5 + .5 * Math.sin((i * alpha) / (alpha * Math.PI)) * Math.cos((j * alpha) / (alpha * Math.PI));
	}
}
