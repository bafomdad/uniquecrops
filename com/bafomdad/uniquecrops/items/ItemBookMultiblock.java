package com.bafomdad.uniquecrops.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemBookMultiblock extends Item {

	public ItemBookMultiblock() {
		
		setRegistryName("bookmultiblock");
		setTranslationKey(UniqueCrops.MOD_ID + ".book_multiblock");
		setCreativeTab(UniqueCrops.TAB);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		super.addInformation(stack, player, list, whatisthis);
		list.add(I18n.format(UCStrings.TOOLTIP + "guidemultiblock"));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.getItem() == this) {
			player.openGui(UniqueCrops.instance, 3, world, (int)player.posX, (int)player.posY, (int)player.posZ);
			return new ActionResult(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult(EnumActionResult.PASS, stack);
	}
}
