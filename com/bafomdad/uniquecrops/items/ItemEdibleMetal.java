package com.bafomdad.uniquecrops.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;
import baubles.api.BaublesApi;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumEdibleMetal;
import com.bafomdad.uniquecrops.init.UCBaubles;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemEdibleMetal extends ItemFood {
	
	private EnumEdibleMetal metal;

	public ItemEdibleMetal(EnumEdibleMetal metal, String itemname) {
		
		super(metal.getAmount(), metal.getSaturation(), false);
		this.metal = metal;
		setRegistryName("ediblemetal." + itemname);
		setUnlocalizedName(UniqueCrops.MOD_ID + ".ediblemetal." + itemname);
		setCreativeTab(UniqueCrops.TAB);
		GameRegistry.register(this);
	}
	
	@Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		
		int amount = metal.getAmount() * 500;
		if (!world.isRemote) {
			if (stack.getItem() == UCItems.edibleDiamond)
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, amount, 1));
			if (stack.getItem() == UCItems.edibleNuggetGold || stack.getItem() == UCItems.edibleIngotGold)
				player.addPotionEffect(new PotionEffect(MobEffects.LUCK, amount, 1));
			if (stack.getItem() == UCItems.edibleEmerald)
				player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, amount, 1));
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
         
		ItemStack toConvert = convertEdibles(stack);
		if (!UniqueCrops.baublesLoaded) {
			if (!world.isRemote)
				player.setHeldItem(hand, toConvert);
			return ActionResult.newResult(EnumActionResult.PASS, toConvert);
		} else {
			ItemStack bauble = BaublesApi.getBaublesHandler(player).getStackInSlot(6);
			if (bauble == null || (bauble != null && bauble.getItem() != UCBaubles.emblemIronstomach)) {
				if (!world.isRemote) {
					player.setHeldItem(hand, toConvert);
				}
				return ActionResult.newResult(EnumActionResult.PASS, toConvert);
			}
		}
		return super.onItemRightClick(stack, world, player, hand);
    }
	
	private ItemStack convertEdibles(ItemStack stack) {
		
		int[] oreIDs = OreDictionary.getOreIDs(stack);
		if (oreIDs.length == 0) return stack;

		String oreName = OreDictionary.getOreName(oreIDs[0]);
		ItemStack converted = OreDictionary.getOres(oreName).get(0);
		if (converted != null)
			return new ItemStack(converted.getItem(), stack.stackSize, converted.getItemDamage());
		
		return stack;
	}
}
