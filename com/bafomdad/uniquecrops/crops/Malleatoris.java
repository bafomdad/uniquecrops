package com.bafomdad.uniquecrops.crops;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class Malleatoris extends BlockCropsBase {

	public Malleatoris() {
		
		super(EnumCrops.ANVILICIOUS, false, UCConfig.cropMalleatoris);
		this.clickHarvest = false;
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsMalleatoris;
	}
	
	@Override
	public Item getCrop() {
		
		return Item.getItemFromBlock(Blocks.ANVIL);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
	
		if (this.getAge(state) < getMaxAge()) return false;
		
		if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
			ItemStack stacky = player.getHeldItemMainhand();
			if (stacky.isEmpty() || (!stacky.isEmpty() && !stacky.isItemDamaged())) return false;
			
			int repair = stacky.getMaxDamage() / 2;
			stacky.setItemDamage(Math.max(stacky.getItemDamage() - repair, 0));
			world.setBlockState(pos, this.withAge(0), 2);
			return true;
		}
		return false;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (!world.isRemote) {
			AxisAlignedBB aabb = new AxisAlignedBB(pos.add(-4, 0, -4), pos.add(4, 1, 4));
			List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);
			for (Entity entity : entities) {
				if (entity instanceof EntityItem) {
					if (calcGrowth((EntityItem)entity, rand)) {
						world.setBlockState(pos, this.withAge(getAge(state) + 1), 2);
						return;
					}
				}
			}
		}
	}
	
	private boolean calcGrowth(EntityItem stack, Random rand) {
		
		if (!stack.getItem().getItem().isRepairable()) return false;
		
		double damage = stack.getItem().getMaxDamage() * 0.1;
		double weight = damage / 2;
		double chance = Math.random() * 100;
		if (chance < weight) {
			int newDamage = (int)damage + stack.getItem().getItemDamage();
			if (newDamage < stack.getItem().getMaxDamage()) {
				stack.getItem().setItemDamage(stack.getItem().getItemDamage() + (int)damage);
				UCPacketHandler.sendToNearbyPlayers(stack.world, stack.getPosition(), new PacketUCEffect(EnumParticleTypes.CLOUD, stack.posX, stack.posY, stack.posZ, 6));
				return true;
			}
		}
		return false;
	}
}
