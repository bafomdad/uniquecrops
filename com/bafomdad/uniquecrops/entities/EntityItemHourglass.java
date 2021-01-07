package com.bafomdad.uniquecrops.entities;

import java.util.Iterator;

import com.bafomdad.uniquecrops.UniqueCropsAPI;
import com.bafomdad.uniquecrops.crafting.HourglassRecipe;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityItemHourglass extends EntityItem {
	
	private int range = 3;

	public EntityItemHourglass(World world) {
		
		super(world);
	}
	
	public EntityItemHourglass(World world, EntityItem oldEntity, ItemStack stack) {
		
		super(world, oldEntity.posX, oldEntity.posY, oldEntity.posZ, stack);
		this.motionX = oldEntity.motionX;
		this.motionY = oldEntity.motionY;
		this.motionZ = oldEntity.motionZ;
		this.lifespan = oldEntity.lifespan;
		this.setItem(stack);
		this.setDefaultPickupDelay();
		this.setNoDespawn();
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		if (this.world != null && this.ticksExisted % 300 == 0) {
			Iterable<BlockPos> poslist = BlockPos.getAllInBox(getPosition().add(-range, -range, -range), getPosition().add(range, range, range));
			Iterator it = poslist.iterator();
			while (it.hasNext()) {
				BlockPos posit = (BlockPos)it.next();
				if (!world.isRemote && !world.isAirBlock(posit)) {
					IBlockState loopState = world.getBlockState(posit);
					boolean flag = rand.nextInt(10) == 0;
					
					if (flag) {
						HourglassRecipe recipe = UniqueCropsAPI.HOURGLASS_RECIPE_REGISTRY.findRecipe(loopState);
						if (recipe != null) {
							this.convertBlock(world, posit, recipe.getOutput(), recipe.getOutputMeta());
						}
					}
				}
			}
		}
	}
	
	public static void convertBlock(World world, BlockPos pos, Block output, int outputMeta) {
		
		world.setBlockState(pos, output.getStateFromMeta(outputMeta), 2);
		UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX() + 0.5D, pos.getY() + 1, pos.getZ() + 0.5D, 3));
	}
}
