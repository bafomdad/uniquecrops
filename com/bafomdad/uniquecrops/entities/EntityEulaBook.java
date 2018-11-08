package com.bafomdad.uniquecrops.entities;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.PotionBehavior;
import com.bafomdad.uniquecrops.gui.GuiBookEula;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketBookOpen;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityEulaBook extends EntityPotion {
	
	public EntityEulaBook(World world) {
		
		super(world);
	}
	
	public EntityEulaBook(World worldIn, EntityLivingBase throwerIn, ItemStack throwingBook) {
		
		super(worldIn, throwerIn, throwingBook);
	}
	
	@Override
    protected void onImpact(RayTraceResult result) {
    
		if (!world.isRemote) {
			applyBook(result);
			this.setDead();
		}
//		BlockPos pos = new BlockPos(result.hitVec);
//		if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
//			Entity ent = result.entityHit;
//			if (!ent.isDead && ent instanceof EntityPlayer && ent.world.isRemote)
//				((EntityPlayer)ent).openGui(UniqueCrops.instance, 1, world, (int)ent.posX, (int)ent.posY, (int)ent.posZ);
//			
//			UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.CRIT, pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, 5));
//		}
//		else if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
//			ItemStack stack = UCItems.generic.createStack(EnumItems.EULA);
//			EntityItem eibook = new EntityItem(world, pos.offset(result.sideHit).getX(), pos.offset(result.sideHit).getY(), pos.offset(result.sideHit).getZ(), stack);
//			if (!world.isRemote)
//				world.spawnEntity(eibook);
//			this.setDead();
//		}
    }
	
	private void applyBook(RayTraceResult result) {
		
		AxisAlignedBB aabb = this.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D);
		List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
		
		if (!list.isEmpty()) {
			for (EntityLivingBase elb : list) {
				if (elb instanceof EntityPlayer) {
					double d0 = this.getDistanceSq(elb);
					if (d0 < 4.0D) {
						if (elb instanceof EntityPlayerMP)
							UCPacketHandler.INSTANCE.sendTo(new PacketBookOpen((EntityPlayer)elb), (EntityPlayerMP)elb);
//						UniqueCrops.proxy.openEulaBook((EntityPlayer)elb);
						return;
					}
				}
			}
		}
		BlockPos pos = (result.sideHit != null) ? new BlockPos(result.hitVec).offset(result.sideHit) : new BlockPos(result.hitVec);
		ItemStack stack = UCItems.generic.createStack(EnumItems.EULA);
		EntityItem eibook = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		world.spawnEntity(eibook);
	}
}
