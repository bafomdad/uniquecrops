package com.bafomdad.uniquecrops.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bafomdad.uniquecrops.core.NBTUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityItemDonk extends EntityItem {
	
	private static Map<Item, Float> damageMap = new HashMap();
	
	static {
		damageMap.put(Item.getItemFromBlock(Blocks.ANVIL), 8.0F);
		damageMap.put(Items.APPLE, 0.5F);
		damageMap.put(Item.getItemFromBlock(Blocks.COBBLESTONE), 2.5F);
	}

	public EntityItemDonk(World world) {
		
		super(world);
	}
	
	public EntityItemDonk(World world, EntityItem oldEntity, ItemStack stack) {
		
		super(world, oldEntity.posX, oldEntity.posY, oldEntity.posZ, stack);
		this.motionX = oldEntity.motionX;
		this.motionY = oldEntity.motionY;
		this.motionZ = oldEntity.motionZ;
		this.lifespan = oldEntity.lifespan;
		this.setDefaultPickupDelay();
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		if (!this.getEntityData().hasKey("UC:CanDonk")) return;
		
		if (this.onGround) {
			this.getEntityData().removeTag("UC:CanDonk");
			return;
		}
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
		for (int i = 0; i < list.size(); ++i) {
			Entity ent = list.get(i);
			if (!ent.isDead && ent instanceof EntityLiving) {
				if (damageMap.containsKey(this.getItem().getItem())) {
					float damage = damageMap.get(this.getItem().getItem());
					ent.attackEntityFrom(DamageSource.GENERIC, damage);
					((EntityLiving)ent).knockBack(this, (float)1 * 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
				}
//				float damage = 4.0F * this.getItem().getCount();
//				ent.attackEntityFrom(DamageSource.ANVIL, damage);
//				((EntityLiving)ent).knockBack(this, (float)1 * 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
				this.getEntityData().removeTag("UC:CanDonk");
				this.motionX = -this.motionX / 4;
				this.motionZ = -this.motionZ / 4;
			}
		}
	}
}
