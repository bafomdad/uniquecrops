package com.bafomdad.uniquecrops.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemDiamondBunch extends Item {

	public ItemDiamondBunch() {
		
		setRegistryName("diamonds");
		setTranslationKey(UniqueCrops.MOD_ID + ".diamonds");
		setCreativeTab(UniqueCrops.TAB);
		setMaxStackSize(1);
		setMaxDamage(5);
		UCItems.items.add(this);
		this.addPropertyOverride(new ResourceLocation("count"), new IItemPropertyGetter() {
			
			@Override
			public float apply(ItemStack stack, World world, EntityLivingBase elb) {
				
				return stack.getItemDamage();
			}
		});
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		super.addInformation(stack, player, list, whatisthis);
		list.add(I18n.format(UCStrings.TOOLTIP + "diamondbunch"));
	}
	
	@Override
    public boolean showDurabilityBar(ItemStack stack) {
    	
        return false;
    }
}
