package com.bafomdad.uniquecrops.crops;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

public class Wafflonia extends BlockCropsBase {

	public Wafflonia() {
		
		super(EnumCrops.WAFFLE);
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
		
		this.checkAndDropBlock(world, pos, state);
		if (!waffleAbout(world, state, pos)) return;
		
		super.updateTick(world, pos, state, rand);
	}
	
    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
    	
    	if (waffleAbout(world, state, pos))
    		super.grow(world, pos, state);
    }
    
    public boolean waffleAbout(World world, IBlockState state, BlockPos pos) {
    	
		if (this.getAge(state) >= getMaxAge())
			return false;
		
		int friends = 0;
		
		Iterable<BlockPos> getBox = BlockPos.getAllInBox(pos.add(-4, 0, -4), pos.add(4, 0, 4));
		Iterator it = getBox.iterator();
		while (it.hasNext()) {
			BlockPos looppos = (BlockPos)it.next();
			Block loopblock = world.getBlockState(looppos).getBlock();
			if (loopblock == UCBlocks.cropWafflonia) {
				if (!world.isRemote)
					friends++;
			}
		}
		if ((friends != 0 && friends % 4 != 0) || friends == 0)
			return false;
		
		return true;
    }
}
