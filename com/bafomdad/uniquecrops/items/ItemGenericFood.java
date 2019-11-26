package com.bafomdad.uniquecrops.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.enums.EnumFoodstuffs;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCPotions;
import com.bafomdad.uniquecrops.potions.PotionBehavior;

public class ItemGenericFood extends ItemFood {
	
	private EnumFoodstuffs type;
	
	public ItemGenericFood(EnumFoodstuffs type) {
		
		super(type.getAmount(), type.getSaturation(), false);
		this.type = type;
		if (type.isAlwaysEdible())
			this.setAlwaysEdible();
		setRegistryName("genericfood." + type.name().toLowerCase());
		setTranslationKey(UniqueCrops.MOD_ID + ".genericfood." + type.name().toLowerCase());
		setCreativeTab(UniqueCrops.TAB);
		UCItems.items.add(this);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		
		if (stack.getItem() == UCItems.potionReverse)
			return EnumAction.DRINK;
		
		return EnumAction.EAT;
	}
	
	@Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		
		if (!world.isRemote) {
			switch (type) {
				case TERIYAKI: player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 3600)); break;
				case LARGEPLUM: player.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 40)); break;
				case REVERSEPOTION:
					PotionBehavior.reverseEffects(player);
					player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
					break;
				case GOLDENBREAD: player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 2400)); break;
				case HEART: player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 1200, 4)); break;
				case ENNUIPOTION: 
					player.addPotionEffect(new PotionEffect(UCPotions.ENNUI, 600, 1));
					player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
					break;
				default: break;
			}
		}
	}
	
	@Override
    public int getItemStackLimit(ItemStack stack) {
    	
		if (stack.getItem() == UCItems.potionReverse || stack.getItem() == UCItems.teriyaki)
			return 1;
		
		return super.getItemStackLimit(stack);
    }
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		
		return stack.getItem() == UCItems.potionReverse || stack.getItem() == UCItems.potionEnnui;
	}
	
	@Override
    public EnumRarity getRarity(ItemStack stack) {
    	
		switch (type) {
			case REVERSEPOTION: return EnumRarity.UNCOMMON;
			case ENNUIPOTION: return EnumRarity.UNCOMMON;
			default: return super.getRarity(stack);
		}
    }
}
