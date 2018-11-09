package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityBattleCrop extends EntityCreature {

	public EntityBattleCrop(World world) {
		
		super(world);
	}
	
	public EntityBattleCrop(World world, BlockPos pos) {
		
		super(world);
		this.setPosition(pos.getX(), pos.getY(), pos.getZ());
		this.setSize(0.6F, 0.4F);
	}
	
	protected void initEntityAI() {
		
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.31D, false));
		this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}
	
	protected void applyEntityAttributes() {
		
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.32D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingData) {
		
		livingData = super.onInitialSpawn(difficulty, livingData);
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(UCItems.steelDonut));
		this.inventoryHandsDropChances[EntityEquipmentSlot.MAINHAND.getIndex()] = 0.0F;
		
		return livingData;
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        
		if (source.getTrueSource() instanceof EntityPlayer) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)source.getTrueSource();
            if (((EntityPlayer)entitylivingbase).capabilities.isCreativeMode)
            	return super.attackEntityFrom(source, amount);

            if (!source.isExplosion())
                entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), amount);
        }
		if (source.getTrueSource() == this || source.isFireDamage()) {
			return super.attackEntityFrom(source, amount);
		}
        return super.attackEntityFrom(source, 0);
    }
	
	@Override
    public boolean attackEntityAsMob(Entity entityIn) {
		
		if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) return super.attackEntityAsMob(entityIn);
		
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;

        if (entityIn instanceof EntityLivingBase) {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag) {
            if (i > 0 && entityIn instanceof EntityLivingBase) {
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }
            if (entityIn instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer))
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }
            this.applyEnchantments(this, entityIn);
        }
        return flag;
    }

	@Override
	public void onDeath(DamageSource source) {
		
		super.onDeath(source);
		
		if (source.getTrueSource() == this) {
			this.entityDropItem(new ItemStack(UCItems.steelDonut), 0.0F);
			this.entityDropItem(new ItemStack(UCItems.seedsDonuts), 0.0F);
		}
	}
	
	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		
		return true;
	}
	
	@Override
	public boolean canBePushed() {
		
		return false;
	}
}
