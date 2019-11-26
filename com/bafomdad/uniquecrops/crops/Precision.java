package com.bafomdad.uniquecrops.crops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
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
		
		if (this.getAge(state) == 6)
			return 7;
		
		return 0;
	}
	
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    	
    	if (this.getAge(state) == 6)
    		return this.getCrop();

    	return this.getSeed();
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
