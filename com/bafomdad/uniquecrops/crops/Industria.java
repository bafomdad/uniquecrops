package com.bafomdad.uniquecrops.crops;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileIndustria;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.ItemBeanBattery;

public class Industria extends BlockCropsBase {

	public Industria() {
		
		super(EnumCrops.ENERGY);
		GameRegistry.registerTileEntity(TileIndustria.class, new ResourceLocation(UniqueCrops.MOD_ID, "industria"));
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsIndustria;
	}
	
	@Override
	public Item getCrop() {
		
		return Items.AIR;
	}
	
	@Override
    public int quantityDropped(IBlockState state, int fortune, Random rand) {
		
		return 1 + rand.nextInt(2);
	}
	
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	
    	List<ItemStack> ret = super.getDrops(world, pos, state, fortune);
    	Random rand = world instanceof World ? ((World)world).rand : new Random();
        int count = this.quantityDropped(state, fortune, rand);
        
        if (this.isMaxAge(state)) {
            for (int i = 0; i < count; i++) {
            	ItemStack battery = new ItemStack(UCItems.beanBattery);
            	((ItemBeanBattery)battery.getItem()).setEnergy(battery, UCConfig.beanBatteryCapacity);
            	ret.add(battery);
            }
        }
        ret.add(new ItemStack(this.getSeed()));
    	return ret;
    }
    
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	
    	// NO-OP ?
    }
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		this.checkAndDropBlock(world, pos, state);
	}
	
	@Override
    public boolean hasTileEntity(IBlockState state) {
        
		return true;
    }
	
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	
    	return new TileIndustria();
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
		for (int i = 0; i < this.getAge(state) + 1; i++)
			world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + rand.nextFloat(), pos.getY() + 0.1, pos.getZ() + rand.nextFloat(), 0, 0, 0);
    }
}
