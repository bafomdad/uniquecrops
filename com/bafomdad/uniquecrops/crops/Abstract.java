package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;

public class Abstract extends BlockCropsBase {

	public Abstract() {
		
		super(EnumCrops.ABSTRACT);
	}
}
