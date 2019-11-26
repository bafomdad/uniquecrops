package com.bafomdad.uniquecrops.world;

import java.util.Random;

import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

public class RuinedBricksProcessor implements ITemplateProcessor {
	
	private final Random rand;
	
	public RuinedBricksProcessor(BlockPos pos, PlacementSettings settings) {
		
		this.rand = settings.getRandom(pos);
	}

	@Override
	public BlockInfo processBlock(World world, BlockPos pos, BlockInfo info) {

		if (info.blockState.getBlock() == UCBlocks.ruinedBricksRed) {
			if (this.rand.nextFloat() > 0.85F)
				return new BlockInfo(info.pos, UCBlocks.ruinedBricksGhost.getDefaultState(), info.tileentityData);
		}
		return info;
	}
}
