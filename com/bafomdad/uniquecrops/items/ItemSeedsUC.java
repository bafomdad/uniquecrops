package com.bafomdad.uniquecrops.items;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.SeedBehavior;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.proxies.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSeedsUC extends Item implements IPlantable {
	
	private BlockCropsBase cropBlock;
	
	public ItemSeedsUC(BlockCropsBase cropBlock) {
		
		this.cropBlock = cropBlock;
		setRegistryName("seed" + cropBlock.getCropType());
		setTranslationKey(UniqueCrops.MOD_ID + ".seed" + cropBlock.getCropType());
		setCreativeTab(UniqueCrops.TAB);
		UCItems.items.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		
		ItemStack stack = player.getHeldItem(hand);
		IBlockState state = world.getBlockState(pos);
		if (!world.isRemote && SeedBehavior.canIgnoreGrowthRestrictions(world)) {
			world.setBlockState(pos.offset(side), cropBlock.withAge(cropBlock.getMaxAge()), 3);
			return EnumActionResult.SUCCESS;
		}
		if (SeedBehavior.canPlantCrop(stack, player, world, pos, side, cropBlock)) {
			stack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {

		return EnumPlantType.Crop;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		
		return cropBlock.getDefaultState();
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
		
		return cropBlock == UCBlocks.cropCinderbella;
	}
}
