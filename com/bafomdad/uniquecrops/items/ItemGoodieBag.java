package com.bafomdad.uniquecrops.items;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Random;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

public class ItemGoodieBag extends Item {

	public ItemGoodieBag() {
		
		setRegistryName("goodiebag");
		setTranslationKey(UniqueCrops.MOD_ID + ".goodiebag");
		setCreativeTab(UniqueCrops.TAB);
		UCItems.items.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		
		return EnumRarity.UNCOMMON;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		ItemStack stack = player.getHeldItem(hand);
		Random rand = new Random(world.getSeed() + LocalDateTime.now().getDayOfMonth());
		if (!stack.isEmpty() && stack.getItem() == this) {
			ItemStack prize = getHolidayItem(rand);
			if (!world.isRemote) {
				if (world.rand.nextBoolean())
					ItemHandlerHelper.giveItemToPlayer(player, prize);
				stack.shrink(1);
			}
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}
	
	public static ItemStack getHolidayItem(Random rand) {
		
		if (isHoliday()) {
			ItemStack stack;
			if (rand.nextBoolean())
				stack = new ItemStack(Item.REGISTRY.getRandomObject(rand));
			else
				stack = new ItemStack(Block.REGISTRY.getRandomObject(rand));
			return stack;
		}
		return new ItemStack(Items.COAL);
	}
	
	public static boolean isHoliday() {
		
		LocalDateTime current = LocalDateTime.now();
		return current.getMonth() == Month.DECEMBER && current.getDayOfMonth() <= 25;
	}
}
