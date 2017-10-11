package com.bafomdad.uniquecrops.items;

import java.util.Iterator;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.PotionBehavior;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemGenericFood extends ItemFood {

	public ItemGenericFood(int amount, float saturation, boolean always, String itemname) {
		
		super(amount, saturation, false);
		setRegistryName("genericfood." + itemname);
		setUnlocalizedName(UniqueCrops.MOD_ID + ".genericfood." + itemname);
		setCreativeTab(UniqueCrops.TAB);
		if (always)
			setAlwaysEdible();
		UCItems.items.add(this);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		if (stack.getItem() == UCItems.potionreverse)
			return EnumAction.DRINK;
		
		return EnumAction.EAT;
	}
	
	@Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		
		if (!world.isRemote) {
			if (stack.getItem() == UCItems.teriyaki)
				player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 3600));
			if (stack.getItem() == UCItems.largeplum)	
				player.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 40));
			if (stack.getItem() == UCItems.potionreverse) {
				PotionBehavior.reverseEffects(player);
				player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
			}
			if (stack.getItem() == UCItems.goldenbread)
				player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 2400));
			if (stack.getItem() == UCItems.heart)
				player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 1200, 4));
		}
	}
	
	@Override
    public int getItemStackLimit(ItemStack stack) {
    	
		if (stack.getItem() == UCItems.potionreverse || stack.getItem() == UCItems.teriyaki)
			return 1;
		
		return super.getItemStackLimit(stack);
    }
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		
		return stack.getItem() == UCItems.potionreverse;
	}
}
