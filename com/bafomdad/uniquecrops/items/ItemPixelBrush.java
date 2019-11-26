package com.bafomdad.uniquecrops.items;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPixelBrush extends Item {

	public ItemPixelBrush() {
		
		setRegistryName("pixelbrush");
		setTranslationKey(UniqueCrops.MOD_ID + ".pixelbrush");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(131);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
		
		if (isInCreativeTab(tabs)) {
			ItemStack brush = new ItemStack(this);
			list.add(brush.copy());
			brush.setItemDamage(brush.getMaxDamage());
			list.add(brush);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		
		return EnumRarity.UNCOMMON;
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(UCStrings.TAG_BIOME)) {
			int biomeId = stack.getTagCompound().getInteger(UCStrings.TAG_BIOME);
			Biome biome = Biome.getBiome(biomeId);
			list.add(TextFormatting.GREEN + "Biome: " + TextFormatting.RESET + biome.getBiomeName());
		} else {
			list.add(TextFormatting.GREEN + "Biome: " + TextFormatting.RESET + "<NONE>");
		}
	}
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		ItemStack stack = player.getHeldItem(hand);
		if (stack.getItem() == this) {
			if (!stack.hasTagCompound() || (stack.hasTagCompound() && !stack.getTagCompound().hasKey(UCStrings.TAG_BIOME))) return EnumActionResult.PASS;

			int biomeId = stack.getTagCompound().getInteger(UCStrings.TAG_BIOME);
			boolean flag = UCUtils.setBiome(biomeId, world, pos);
			
			if (!flag) return EnumActionResult.PASS;
			if (!world.isRemote)
				stack.damageItem(1, player);
		}
		return EnumActionResult.SUCCESS;
	}
}
