package com.bafomdad.uniquecrops.entities;

import java.util.Iterator;

import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.block.Block;
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
					Block loopblock = world.getBlockState(posit).getBlock();
					boolean flag = rand.nextInt(10) == 0;
					if (loopblock == Blocks.GRASS && flag) {
						setOldBlock(world, posit, UCBlocks.oldGrass); break;
					}
					if (loopblock == Blocks.COBBLESTONE && flag) {
						setOldBlock(world, posit, UCBlocks.oldCobble); break;
					}
					if (loopblock == Blocks.MOSSY_COBBLESTONE && flag) {
						setOldBlock(world, posit, UCBlocks.oldCobbleMoss); break;
					}
					if (loopblock == Blocks.BRICK_BLOCK && flag) {
						setOldBlock(world, posit, UCBlocks.oldBrick); break;
					}
					if (loopblock == Blocks.GRAVEL && flag) {
						setOldBlock(world, posit, UCBlocks.oldGravel); break;
					}
				}
			}
		}
	}
	
	public static void setOldBlock(World world, BlockPos pos, Block block) {
		
		world.setBlockState(pos, block.getDefaultState(), 2);
		UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX() + 0.5D, pos.getY() + 1, pos.getZ() + 0.5D, 3));
	}
}
