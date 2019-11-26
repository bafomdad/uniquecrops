package com.bafomdad.uniquecrops.crops.supercrops;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.blocks.BlockSuperCropsBase;
import com.bafomdad.uniquecrops.core.enums.EnumSuperCrops;
import com.bafomdad.uniquecrops.entities.EntityItemCooking;
import com.bafomdad.uniquecrops.init.UCItems;

public class Cocito extends BlockSuperCropsBase {

	public Cocito() {
		
		super(EnumSuperCrops.COCITO);
		this.setLightLevel(1.0F);
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		this.checkAndDropBlock(world, pos, state);
		
		this.cookNearbyThings(world, pos);
	}
	
	@Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		
		world.scheduleUpdate(pos, this, 20);
	}
	
	private void cookNearbyThings(World world, BlockPos pos) {
		
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.add(-4, 0, -4), pos.add(4, 1, 4)));
		for (EntityItem ei : items) {
			if (!ei.isDead && !(ei instanceof EntityItemCooking)) {
				if (ei.getItem().getItem() == UCItems.uselessLump) continue; 
				EntityItemCooking eic = new EntityItemCooking(world, ei, ei.getItem());
				if (!world.isRemote)
					world.spawnEntity(eic);
				ei.setDead();
			}
		}
		world.scheduleUpdate(pos, this, 20);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	if (rand.nextInt(2) == 0)
    		world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + rand.nextFloat(), pos.getY(), pos.getZ() + rand.nextFloat(), 0.0D, 0.03D, 0.0D);
    }
}
