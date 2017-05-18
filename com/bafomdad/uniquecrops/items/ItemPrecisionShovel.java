package com.bafomdad.uniquecrops.items;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPrecisionShovel extends ItemSpade implements IBookUpgradeable {

	public ItemPrecisionShovel() {
		
		super(ToolMaterial.DIAMOND);
		setRegistryName("precision.shovel");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".precision.shovel");
		setCreativeTab(UniqueCrops.TAB);
		GameRegistry.register(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean whatisthis) {
		
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(ItemGeneric.TAG_UPGRADE)) {
			int upgradelevel = stack.getTagCompound().getInteger(ItemGeneric.TAG_UPGRADE);
			list.add(TextFormatting.GOLD + "+" + upgradelevel);
		}
		else
			list.add(TextFormatting.GOLD + "Upgradeable");
	}
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    	
		boolean sametool = toRepair.getItem() == repair.getItem();
		boolean flag = repair.getItem() == UCItems.generic && repair.getItemDamage() == 8;
		return sametool || flag;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
    	
		ItemStack precision = new ItemStack(item);
		precision.addEnchantment(Enchantment.getEnchantmentByID(33), 1);
		list.add(precision);
    }
}
