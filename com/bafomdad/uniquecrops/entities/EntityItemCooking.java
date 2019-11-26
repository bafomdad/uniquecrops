package com.bafomdad.uniquecrops.entities;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCropsAPI;
import com.bafomdad.uniquecrops.core.enums.EnumItems;
import com.bafomdad.uniquecrops.crafting.HeaterRecipe;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityItemCooking extends EntityItem {
	
	private static final DataParameter<Integer> COOKING_TIME = EntityDataManager.createKey(EntityItemCooking.class, DataSerializers.VARINT);

	public EntityItemCooking(World world) {
		
		super(world);
	}
	
	public EntityItemCooking(World world, EntityItem oldEntity, ItemStack stack) {
		
		super(world, oldEntity.posX, oldEntity.posY, oldEntity.posZ, stack);
		this.motionX = oldEntity.motionX;
		this.motionY = oldEntity.motionY;
		this.motionZ = oldEntity.motionZ;
		this.lifespan = oldEntity.lifespan;
		this.setDefaultPickupDelay();
	}
	
	@Override
	protected void entityInit() {
		
		super.entityInit();
		this.dataManager.register(COOKING_TIME, Integer.valueOf(0));
	}
	
	public void setCookTime(int time) {
		
		this.dataManager.set(COOKING_TIME, Integer.valueOf(time));
	}
	
	public int getCookingTime() {
		
		return this.dataManager.get(COOKING_TIME).intValue();
	}
	
	private ItemStack getCookedItem() {
		
		ItemStack result = new ItemStack(UCItems.uselessLump);
		
		HeaterRecipe recipe = UniqueCropsAPI.MASSHEATER_REGISTRY.findRecipe(this.getItem());
		if (recipe != null) {
			result = recipe.getOutput().copy();
			result.setCount(this.getItem().getCount());
			return result;
		}
		ItemStack smeltResult = FurnaceRecipes.instance().getSmeltingResult(this.getItem());
		if (!smeltResult.isEmpty())
			result = smeltResult;
		
		result.setCount(this.getItem().getCount());
		return result;
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();
		
		int cookTime = getCookingTime();
		if (cookTime > 0 && rand.nextBoolean()) {
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.1, this.posZ, 0, 0, 0);
		}
		if (cookTime >= 100) {
			EntityItem ei = new EntityItem(this.world, this.posX, this.posY, this.posZ, getCookedItem());
			UCPacketHandler.sendToNearbyPlayers(this.getEntityWorld(), this.getPosition(), new PacketUCEffect(EnumParticleTypes.FLAME, this.posX, this.posY + 0.2, this.posZ, 5));
			if (!this.world.isRemote)
				this.world.spawnEntity(ei);
			this.setDead();
			return;
		}
		if (this.ticksExisted % 5 == 0) {
			setCookTime(++cookTime);
		}
	}
}
