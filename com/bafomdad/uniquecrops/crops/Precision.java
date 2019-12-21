package com.bafomdad.uniquecrops.crops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Precision extends BlockCropsBase {

	public Precision() {
	
		super(EnumCrops.PRECISION);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsPrecision;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		return this.getAge(state) == 6 ? 7 : 0;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
        this.checkAndDropBlock(world, pos, state);

        if (!world.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (world.getLightFromNeighbors(pos.up()) >= 9) {
            int i = this.getAge(state);

            if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, world, pos);

                if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0)) {
                    world.setBlockState(pos, this.withAge(i + 1), 3);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
                }
            }
        }
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    	
    	if (this.getAge(state) == 6)
    		return this.getCrop();

    	return this.getSeed();
    }
	
	@Override
    public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        
		if (this.getAge(state) != 6)
			return 0;
		
		return 15;
    }
	
	@Override
    public boolean canProvidePower(IBlockState state) {
        
		return this.getAge(state) == 6;
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	
    	List<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this.getSeed(), 1, 0));
        int age = getAge(state);
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        int count = this.quantityDropped(state, fortune, rand);
        
        if (age == 6) {
            Item item = this.getItemDropped(state, rand, fortune);
            if (item != null)
                ret.add(new ItemStack(item, 1, this.damageDropped(state)));
        }
    	return ret;
    }
}
