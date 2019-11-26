package com.bafomdad.uniquecrops.blocks;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.world.WorldGenSkyTree;

public class BlockFlywoodSapling extends BlockBush implements IGrowable {
	
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
	
	public BlockFlywoodSapling() {
		
		setRegistryName("flywood_sapling");
		setTranslationKey(UniqueCrops.MOD_ID + ".flywood_sapling");
		setCreativeTab(UniqueCrops.TAB);
		UCBlocks.blocks.add(this);
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        
		return SAPLING_AABB;
    }
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        
		if (!world.isRemote)  {
            super.updateTick(world, pos, state, rand);

            if (!world.isAreaLoaded(pos, 1)) return;
            if (world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
                this.grow(world, rand, pos, state);
        }
    }

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {

		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {

		return (double)world.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {

		if (!TerrainGen.saplingGrowTree(world, rand, pos)) return;
		
		WorldGenSkyTree treeGen = new WorldGenSkyTree(true, rand.nextBoolean());
		treeGen.generate(world, rand, pos);
	}
}
