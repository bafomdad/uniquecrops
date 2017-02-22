package com.bafomdad.uniquecrops.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumCrops;

public abstract class BlockCropsBase extends BlockCrops {
	
	private EnumCrops type;
	private boolean extra;

	public BlockCropsBase(EnumCrops type, boolean extra) {
		
		this.type = type;
		this.extra = extra;
		setRegistryName("crop" + type.getName());
		setUnlocalizedName(UniqueCrops.MOD_ID + ".crop" + type.getName());
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
	}
	
	public String getCropType() {
		
		return type.getName();
	}
	
    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {

    	return canBonemeal(world, state, pos);
    }
    
	private boolean canBonemeal(World world, IBlockState state, BlockPos pos) {
		
		if (type == EnumCrops.BLAZINGPLANT && !world.provider.doesWaterVaporize())
			return false;
		if (type == EnumCrops.BOOKPLANT || type == EnumCrops.PRECISION || type == EnumCrops.FOREVERPLANT || type == EnumCrops.SHYPLANT || type == EnumCrops.SAVAGEPLANT || type == EnumCrops.MUSICAPLANT)
			return false;
		if (type == EnumCrops.HIGHPLANT && pos.getY() <= 100)
			return false;
		
		return true;
	}
	
    @Override
    public List<ItemStack> getDrops(net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	
        List<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this.getSeed(), 1, 0));
        int age = getAge(state);
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        int count = this.quantityDropped(state, fortune, rand);
        
        if (age >= getMaxAge()) {
            for(int i = 0; i < count; i++)
            {
                Item item = this.getItemDropped(state, rand, fortune);
                if (item != null)
                {
                    ret.add(new ItemStack(item, 1, this.damageDropped(state)));
                }
            }
        }
        if (age >= getMaxAge() && extra)
        {
            int k = 3 + fortune;

            for (int i = 0; i < 3 + fortune; ++i)
            {
                if (rand.nextInt(2 * getMaxAge()) <= age)
                {
                    ret.add(new ItemStack(this.getSeed(), 1, 0));
                }
            }
        }
        return ret;
    }
	
	@SideOnly(Side.CLIENT)
	public void createParticles(IBlockState state, World world, BlockPos pos, Random rand, EnumParticleTypes particle, int rand2) {
		
		if (this.getAge(state) >= getMaxAge()) {
    		double x = pos.getX();
    		double y = pos.getY() + 0.5D;
    		double z = pos.getZ();
    		
    		for (int i = 0; i < rand.nextInt(4) + 1; i++) {
    			if (rand2 > 0) {
    				if (rand.nextInt(rand2) == 0)
            			world.spawnParticle(particle, x + rand.nextFloat(), y, z + rand.nextFloat(), 0, 0, 0);
    			}
    			else
        			world.spawnParticle(particle, x + rand.nextFloat(), y, z + rand.nextFloat(), 0, 0, 0);
    		}
		}
	}
}
