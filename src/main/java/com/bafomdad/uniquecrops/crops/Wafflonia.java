package com.bafomdad.uniquecrops.crops;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class Wafflonia extends BlockCropsBase {

	public Wafflonia() {
		
		super(EnumCrops.WAFFLE, true, UCConfig.cropWafflonia);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsWafflonia;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.waffle;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (this.getAge(state) >= getMaxAge())
			return;
		
		int friends = 0;
		
		Iterable<BlockPos> getBox = BlockPos.getAllInBox(pos.add(-4, 0, -4), pos.add(4, 0, 4));
		Iterator it = getBox.iterator();
		while (it.hasNext()) {
			BlockPos looppos = (BlockPos)it.next();
			Block loopblock = world.getBlockState(looppos).getBlock();
			if (loopblock != null && !world.isAirBlock(looppos) && loopblock == UCBlocks.cropWafflonia) {
				if (!world.isRemote)
					friends++;
			}
		}
		if ((friends != 0 && friends % 4 != 0) || friends == 0)
			return;
		
		super.updateTick(world, pos, state, rand);
	}
}
