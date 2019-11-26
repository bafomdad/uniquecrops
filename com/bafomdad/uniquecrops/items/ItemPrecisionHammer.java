package com.bafomdad.uniquecrops.items;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemPrecisionHammer extends ItemTool {

	public ItemPrecisionHammer() {
		
		super(1.0F, -2.8F, ToolMaterial.DIAMOND, new HashSet<Block>());
		setRegistryName("precision.hammer");
		setTranslationKey(UniqueCrops.MOD_ID + ".precision.hammer");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(1761);
		setMaxStackSize(1);
		this.attackDamage = 3.0F + ToolMaterial.DIAMOND.getAttackDamage();
		UCItems.items.add(this);
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		
		HashSet<String> hashSet = new HashSet<String>();
		hashSet.add("pickaxe");
		hashSet.add("axe");
		hashSet.add("shovel");
		return hashSet;
	}
	
    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack){

        return state.getMaterial().isToolNotRequired() 
        		|| (state.getBlock() == Blocks.SNOW_LAYER 
        		|| state.getBlock() == Blocks.SNOW 
        		// what the hell is this ternary operator, my guy
        		|| (state.getBlock() == Blocks.OBSIDIAN ? this.toolMaterial.getHarvestLevel() >= 3 : (state.getBlock() != Blocks.DIAMOND_BLOCK && state.getBlock() != Blocks.DIAMOND_ORE ? (state.getBlock() != Blocks.EMERALD_ORE && state.getBlock() != Blocks.EMERALD_BLOCK ? (state.getBlock() != Blocks.GOLD_BLOCK && state.getBlock() != Blocks.GOLD_ORE ? (state.getBlock() != Blocks.IRON_BLOCK && state.getBlock() != Blocks.IRON_ORE ? (state.getBlock() != Blocks.LAPIS_BLOCK && state.getBlock() != Blocks.LAPIS_ORE ? (state.getBlock() != Blocks.REDSTONE_ORE && state.getBlock() != Blocks.LIT_REDSTONE_ORE ? (state.getMaterial() == Material.ROCK || (state.getMaterial() == Material.IRON || state.getMaterial() == Material.ANVIL)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2)));
    }
    
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
    	
		boolean sametool = toRepair.getItem() == repair.getItem();
		boolean flag = repair.getItem() == UCItems.generic && repair.getItemDamage() == 8;
		return sametool || flag;
    }
}
