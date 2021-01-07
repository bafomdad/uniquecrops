package com.bafomdad.uniquecrops.blocks.tiles;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class TileExedo extends TileBaseRenderUC implements ITickable {
	
	int searchTime = 100;
	int wiggleTime;
	public int timeAfterWiggle;
	public final int maxTime = 15;
	public boolean isWiggling = false;
	public boolean isChomping = false;
	
	boolean foundEntity = false;
	private UUID entityId;
	public EntityLivingBase ent;
	
	@Override
	public double getMaxRenderDistanceSquared() {
		
		return super.getMaxRenderDistanceSquared() * 1.5D;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		
		return new AxisAlignedBB(getPos().getX() - 3, getPos().getY() - 3, getPos().getZ() - 3, getPos().getX() + 3, getPos().getY() + 3, getPos().getZ() + 3);
	}
	
	@Override
	public void update() {

		++timeAfterWiggle;
		if (!world.isRemote && isChomping && timeAfterWiggle >= (maxTime)) {
			reset();
		}
		if (entityId != null)
			ent = UCUtils.getTaggedEntity(world, pos, entityId, 5);
		
		if (entityId == null && ent != null)
			ent = null;
		
		if (!world.isRemote && isWiggling && --wiggleTime <= 0) {
			isWiggling = false;
			UCPacketHandler.dispatchTEToNearbyPlayers(this);
		}
		if (world.getTotalWorldTime() % (searchTime - world.rand.nextInt(20)) == 0) {
			if (!isWiggling && !isChomping) {
				EntityLivingBase elb = getTargetedEntity();
				if (elb != null) {
					if (foundEntity) {
						chomp(elb);
						return;
					}
					entityId = elb.getPersistentID();
					wiggle();
				}
			}
		}
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		this.isWiggling = tag.getBoolean("UC:wiggle");
		this.isChomping = tag.getBoolean("UC:chomp");
		if (tag.hasKey("UC:targetEntity"))
			entityId = UUID.fromString(tag.getString("UC:targetEntity"));
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		tag.setBoolean("UC:wiggle", this.isWiggling);
		tag.setBoolean("UC:chomp", this.isChomping);
		if (entityId != null)
			tag.setString("UC:targetEntity", entityId.toString());
		else
			tag.removeTag("UC:targetEntity");
	}
	
	private void wiggle() {
		
		wiggleTime = 20;
		foundEntity = true;
		isWiggling = true;
		UCPacketHandler.dispatchTEToNearbyPlayers(this);
	}
	
	private void chomp(EntityLivingBase elb) {

		isChomping = true;
		timeAfterWiggle = 0;
		foundEntity = false;
		this.markDirty();
		UCPacketHandler.dispatchTEToNearbyPlayers(this);
	}
	
	private void reset() {
		
		isChomping = false;
		nomAndDrop();
		this.markDirty();
		UCPacketHandler.dispatchTEToNearbyPlayers(this);
	}
	
	private void nomAndDrop() {
		
		EntityLivingBase elb = UCUtils.getTaggedEntity(world, pos, entityId, 5);
		if (elb != null && elb.isEntityAlive()) {
			elb.attackEntityFrom(DamageSource.GENERIC, elb.getMaxHealth());
			UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.EXPLOSION_NORMAL, elb.posX - 1.5, elb.posY, elb.posZ - 1.5, 20));
			elb.setDead(); // dev note: I hope this is good
		}
		entityId = null;
	}
	
	private EntityLivingBase getTargetedEntity() {
		
		if (!world.isRemote) {
			List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.add(-5, 0, -5), pos.add(5, 2, 5)));
			for (EntityLivingBase elb : entities) {
				if (!(elb instanceof EntityPlayer) && !elb.isEntityInvulnerable(DamageSource.OUT_OF_WORLD)) {
					entityId = elb.getPersistentID();
					return elb;
				}
			}
		}
		return null;
	}
}
