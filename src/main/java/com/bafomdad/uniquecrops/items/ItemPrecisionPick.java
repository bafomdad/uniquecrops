package com.bafomdad.uniquecrops.items;

import java.util.List;
import java.util.Set;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemPrecisionPick extends ItemTool implements IBookUpgradeable {
	
	private static final Set<Block> BLOCKS = Sets.newHashSet(new Block[] { Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.GLOWSTONE, Blocks.MONSTER_EGG, Blocks.GLASS, Blocks.GLASS_PANE, Blocks.STAINED_GLASS, Blocks.STAINED_GLASS_PANE });

	public ItemPrecisionPick() {
		
		super(1.0F, -2.8F, ToolMaterial.DIAMOND, BLOCKS);
		setRegistryName("precision.pick");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".precision.pick");
		setCreativeTab(UniqueCrops.TAB);
		UCItems.items.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		super.addInformation(stack, player, list, whatisthis);
		if (getLevel(stack) > -1) {
			int upgradelevel = getLevel(stack);
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
	
	// note: copied from vanilla code
	@Override
  public boolean canHarvestBlock(IBlockState blockIn) {
       
		Block block = blockIn.getBlock();
        if (block == Blocks.OBSIDIAN)
            return this.toolMaterial.getHarvestLevel() == 3;

        else if (block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE) {
            if (block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK) {
                if (block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE) {
                    if (block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE) {
                        if (block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE) {
                            if (block != Blocks.REDSTONE_ORE && block != Blocks.LIT_REDSTONE_ORE) {
                                Material material = blockIn.getMaterial();
                                return material == Material.ROCK ? true : (material == Material.IRON ? true : material == Material.ANVIL);
                            }
                            else
                                return this.toolMaterial.getHarvestLevel() >= 2;
                        }
                        else
                            return this.toolMaterial.getHarvestLevel() >= 1;
                    }
                    else
                        return this.toolMaterial.getHarvestLevel() >= 1;
                }
                else
                    return this.toolMaterial.getHarvestLevel() >= 2;
            }
            else
                return this.toolMaterial.getHarvestLevel() >= 2;
        }
        else
            return this.toolMaterial.getHarvestLevel() >= 2;
  }

  @Override
  public float getDestroySpeed(ItemStack stack, IBlockState state) {
    	
    Material material = state.getMaterial();
    return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK ? super.getDestroySpeed(stack, state) : this.efficiency;
  }
    
	@Override
	public int getLevel(ItemStack stack) {

		return NBTUtils.getInt(stack, ItemGeneric.TAG_UPGRADE, -1);
	}

	@Override
	public void setLevel(ItemStack stack, int level) {

		NBTUtils.setInt(stack, ItemGeneric.TAG_UPGRADE, level);
	}
}
