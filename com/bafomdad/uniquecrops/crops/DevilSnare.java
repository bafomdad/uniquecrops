package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

public class DevilSnare extends BlockCropsBase {

	public DevilSnare() {
		
		super(EnumCrops.DEVILSNARE);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsDevilsnare;
	}
	
	@Override
	public Item getCrop() {
		
		return Items.STICK;
	}
	
	@Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
    	
		if (this.getAge(state) < getMaxAge())
			return;
		
		if (entity instanceof EntityLivingBase) {
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 4));
		}
    }
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (world.getLightFor(EnumSkyBlock.BLOCK, pos) > 5) {
			if (this.getAge(state) >= getMaxAge()) {
				int deage = Math.max(world.getLightFor(EnumSkyBlock.BLOCK, pos) - 7, 0);
				if (!world.isRemote)
					world.setBlockState(pos, this.withAge(this.getAge(state) - deage), 2);
			}
			return;
		}
		if (this.getAge(state) >= getMaxAge())
			this.trySpread(world, pos);

		super.updateTick(world, pos, state, rand);
	}
	
	private void trySpread(World world, BlockPos pos) {
		
		for (EnumFacing face : EnumFacing.HORIZONTALS) {
			BlockPos looppos = pos.offset(face);
			if (world.getLightFor(EnumSkyBlock.BLOCK, looppos) > 5) continue;
			if (world.isAirBlock(looppos) && (world.getBlockState(looppos.down()).getBlock() == Blocks.DIRT || world.getBlockState(looppos.down()).getBlock() == Blocks.FARMLAND)) {
				if (world.rand.nextInt(2) == 0) {
					if (world.getBlockState(looppos.down()).getBlock() == Blocks.DIRT)
						world.setBlockState(looppos.down(), Blocks.FARMLAND.getDefaultState(), 3);
					else
						world.setBlockState(looppos, UCBlocks.cropDevilsnare.getDefaultState(), 3);
					break;
				}
			}
		}
	}
}
