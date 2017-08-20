package com.bafomdad.uniquecrops.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class EntityEulaBook extends EntityPotion {

	
	public EntityEulaBook(World world) {
		
		super(world);
	}
	
	public EntityEulaBook(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn) {
		
		super(worldIn, throwerIn, potionDamageIn);
	}
	
	@Override
    protected void onImpact(RayTraceResult result) {
    	
		BlockPos pos = new BlockPos(result.hitVec);
		if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
			Entity ent = result.entityHit;
			if (!ent.isDead && ent instanceof EntityPlayerMP && ent.worldObj.isRemote) {
				((EntityPlayerMP)ent).openGui(UniqueCrops.instance, 1, worldObj, (int)ent.posX, (int)ent.posY, (int)ent.posZ);
			}
			this.setDead();
			UCPacketHandler.sendToNearbyPlayers(worldObj, pos, new PacketUCEffect(EnumParticleTypes.CRIT, pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, 5));
		}
		else if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
			ItemStack stack = UCItems.generic.createStack(EnumItems.EULA);
			EntityItem eibook = new EntityItem(worldObj, pos.offset(result.sideHit).getX(), pos.offset(result.sideHit).getY(), pos.offset(result.sideHit).getZ(), stack);
			if (!worldObj.isRemote)
				worldObj.spawnEntityInWorld(eibook);
			this.setDead();
		}
		else
			this.setDead();
    }
}
