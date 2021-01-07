package com.bafomdad.uniquecrops.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemDyedBonemeal extends Item {

	public ItemDyedBonemeal() {
		
		setRegistryName("dyedbonemeal");
		setTranslationKey(UniqueCrops.MOD_ID + ".dyedbonemeal");
		setCreativeTab(UniqueCrops.TAB);
		setHasSubtypes(true);
		setMaxDamage(0);
		UCItems.items.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
		
		if (isInCreativeTab(tabs)) {
			for (int i = 0; i < EnumDyeColor.values().length; ++i)
				list.add(new ItemStack(this, 1, i));
		}
	}
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		
		return getTranslationKey() + "." + EnumDyeColor.byDyeDamage(stack.getItemDamage()).getName().toLowerCase();
	}
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        
		ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
            return EnumActionResult.FAIL;
        
		if (ItemDye.applyBonemeal(itemstack, worldIn, pos, player, hand)) {
			if (!worldIn.isRemote) {
				worldIn.playEvent(2005, pos, 0);
			}
		}
		return EnumActionResult.SUCCESS;
    }
}
