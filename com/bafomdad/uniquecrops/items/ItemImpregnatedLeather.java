package com.bafomdad.uniquecrops.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.capabilities.CPCapability;
import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemImpregnatedLeather extends Item {
	
	private static final String TEMP_CAP = "cropCap";
	private static final int MAX_CAPACITY = 10;

	public ItemImpregnatedLeather() {
		
		setRegistryName("impregnated_leather");
		setTranslationKey(UniqueCrops.MOD_ID + ".impregnated_leather");
		setCreativeTab(UniqueCrops.TAB);
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
	
	@Override
    public EnumRarity getRarity(ItemStack stack) {
    	
    	return EnumRarity.UNCOMMON;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
		
		if (isInCreativeTab(tabs)) {
			list.add(new ItemStack(this, 1, 0));
			list.add(new ItemStack(this, 1, 1));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World player, List list, ITooltipFlag whatisthis) {
		
		if (stack.getItemDamage() == 0) {
			CPCapability cap = stack.getCapability(CPProvider.CROP_POWER, null);
			boolean flag = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
			
			if (flag && cap != null) {
				list.add(TextFormatting.GREEN + "Crop Power: " + cap.getPower() + "/" + cap.getCapacity());
			} else {
				list.add(TextFormatting.LIGHT_PURPLE + "<Press Shift>");
			}
		}
	}
	
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
    	
    	if (!stack.hasCapability(CPProvider.CROP_POWER, null))
    		return super.getNBTShareTag(stack);
    	
    	NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound().copy() : new NBTTagCompound();
    	tag.setTag(TEMP_CAP, stack.getCapability(CPProvider.CROP_POWER, null).serializeNBT());
    	return tag;
    }
    
    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt) {
    	
    	CPCapability cap = stack.getCapability(CPProvider.CROP_POWER, null);
    	if (cap != null && nbt != null && nbt.hasKey(TEMP_CAP, 10)) {
    		cap.deserializeNBT(nbt.getCompoundTag(TEMP_CAP));
    		nbt.removeTag(TEMP_CAP);
    	}
    	super.readNBTShareTag(stack, nbt);
    }
    
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {

		if (stack.getItemDamage() > 0) return;
		
		CPCapability cap = stack.getCapability(CPProvider.CROP_POWER, null);
		if (cap != null && cap.getPower() >= cap.getCapacity()) {
			stack.setItemDamage(1);
		}
	}
    
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldstack, ItemStack newstack, boolean slotchanged) {
		
		return oldstack.getItem() != newstack.getItem() && !slotchanged;
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		
		if (stack.getItemDamage() == 0)
			return new CPProvider(MAX_CAPACITY, true);
		
		return super.initCapabilities(stack, nbt);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		
		return stack.getItemDamage() == 1;
	}
}
