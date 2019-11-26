package com.bafomdad.uniquecrops.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.google.common.collect.Multimap;

public class ItemBrassKnuckles extends ItemSword {
	
	private static final String HIT_LIST = "UC:hitList";
	private static final String HIT_ENTITY = "UC:hitEntityId";
	private static final String HIT_TIME = "UC:hitTime";
	private static final String HIT_AMOUNT = "UC:hitAmount";

	public ItemBrassKnuckles() {
		
		super(ToolMaterial.IRON);
		setRegistryName("brassknuckles");
		setTranslationKey(UniqueCrops.MOD_ID + ".brassknuckles");
		setCreativeTab(UniqueCrops.TAB);
		UCItems.items.add(this);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		
		return EnumRarity.RARE;
	}
	
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		
		return super.hitEntity(stack, target, attacker);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

		if (!world.isRemote && entity instanceof EntityPlayer && selected) {
			removeHitEntity(stack, world, ((EntityPlayer)entity));
		}
	}
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    	
		boolean sametool = toRepair.getItem() == repair.getItem();
		boolean flag = repair.getItem() == Items.IRON_INGOT;
		return sametool || flag;
    }
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldstack, ItemStack newstack, boolean slotchanged) {
		
		return oldstack.getItem() != newstack.getItem() && !slotchanged;
	}
	
	@Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot slot) {
		
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);
        if (slot == EntityEquipmentSlot.MAINHAND)
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 2.4000000953674316D, 0));
        
        return multimap;
	}
	
	public static void addHitEntity(EntityLivingBase target, ItemStack stack, long worldTime, float amount) {
		
		NBTTagList tagList = (stack.hasTagCompound()) ? stack.getTagCompound().getTagList(HIT_LIST, 10) : null;
		if (tagList == null) tagList = new NBTTagList();
		if (tagList.tagCount() >= 5) return;
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger(HIT_ENTITY, target.getEntityId());
		nbt.setLong(HIT_TIME, worldTime);
		nbt.setFloat(HIT_AMOUNT, amount);
		tagList.appendTag(nbt);
		NBTUtils.setList(stack, HIT_LIST, tagList);
	}
	
	public static void removeHitEntity(ItemStack stack, World world, EntityPlayer player) {
		
		NBTTagList tagList = (stack.hasTagCompound()) ? stack.getTagCompound().getTagList(HIT_LIST, 10) : null;
		if (tagList == null || tagList.isEmpty()) return;
		
		long worldTime = world.getTotalWorldTime();
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound nbt = tagList.getCompoundTagAt(i);
			long time = nbt.getLong(HIT_TIME);
			if (worldTime - time > 20) {
				if (worldTime - time < 25) {
					EntityLivingBase elb = (EntityLivingBase)world.getEntityByID(nbt.getInteger(HIT_ENTITY));
					if (elb != null) {
						elb.attackEntityFrom(DamageSource.causePlayerDamage(player), nbt.getFloat(HIT_AMOUNT));
					}
				}
				tagList.removeTag(i);
				NBTUtils.setList(stack, HIT_LIST, tagList);
				break;
			}
		}
	}
}
