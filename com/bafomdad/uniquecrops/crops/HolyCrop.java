package com.bafomdad.uniquecrops.crops;

import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class HolyCrop extends BlockCropsBase {

	public HolyCrop() {
		
		super(EnumCrops.HOLY);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsBlessed;
	}
	
	@Override
	public Item getCrop() {
		
		return Items.APPLE;
	}
	
	@Override
	public boolean canIncludeInBook() {

		return false;
	}
	
	@Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
    	
		boolean blessed = isBlessed(state);
		if (entity instanceof EntityLivingBase) {
			if (entity instanceof EntityVillager && !blessed) {
				EntityVillager villager = (EntityVillager)entity;
				int prof = villager.getProfession();
				if (prof == 2) {
					world.setBlockState(pos, getDefaultState().withProperty(AGE, getMaxAge()), 3);
				}
			}			
			if (((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
				entity.setFire(2);
				entity.attackEntityFrom(DamageSource.MAGIC, (blessed) ? 3.0F : 1.5F);
			}
		}
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (this.isBlessed(state)) {
			boolean found = false;
			List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(-7, -3, -7, 7, 3, 7));
			for (EntityLivingBase elb : entities) {
				if (elb.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
					found = true;
					elb.attackEntityFrom(DamageSource.MAGIC, 6.0F);
				}
			}
			if (found)
				world.scheduleUpdate(pos, this, 40);
		}
		if (this.getAge(state) < (getMaxAge() - 1))
			super.updateTick(world, pos, state, rand);
	}
	
    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
    	
    	if (this.getAge(state) == (getMaxAge() - 1)) return;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	this.createParticles(state, world, pos, rand, EnumParticleTypes.END_ROD, 0);
    }
	
	public boolean isBlessed(IBlockState state) {
		
		return this.getAge(state) >= this.getMaxAge();
	}
}
