package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;

public class Hexis extends BlockCropsBase {

	public Hexis() {
		
		super(EnumCrops.HEXIS, true, UCConfig.cropHexis);
		this.clickHarvest = false;
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsHexis;
	}
	
	@Override
	public Item getCrop() {
		
		return Items.AIR;
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if (!isMaxAge(state)) return false;
		
		ItemStack bottle = player.getHeldItem(hand);
		if (!bottle.isEmpty() && bottle.getItem() == Items.GLASS_BOTTLE) {
			if (!world.isRemote) {
				bottle.shrink(1);
				ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(Items.EXPERIENCE_BOTTLE));
				if (world.rand.nextBoolean())
					world.setBlockState(pos, this.withAge(0), 3);
			}
			return true;
		}
		return false;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		this.checkAndDropBlock(world, pos, state);
	}
	
	@Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
    	
		if (world.isRemote || isMaxAge(state))
			return;
		
		if (entity instanceof EntityXPOrb) {
			EntityXPOrb orb = (EntityXPOrb)entity;
			if (!orb.isDead && orb.xpValue > 1) {
				orb.setDead();
				world.setBlockState(pos, this.withAge(getAge(state) + 1), 3);
			}
		}
    }
}
