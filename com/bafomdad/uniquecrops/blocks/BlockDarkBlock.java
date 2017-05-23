package com.bafomdad.uniquecrops.blocks;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockDarkBlock extends Block {

	public BlockDarkBlock() {
		
		super(Material.ROCK);
		setRegistryName("darkblock");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".darkblock");
		setCreativeTab(UniqueCrops.TAB);
		setSoundType(SoundType.STONE);
		setBlockUnbreakable();
		EntityEnderman.setCarriable(this, true);
		GameRegistry.register(this);
		GameRegistry.register(new ItemDarkBlock(this), getRegistryName());
	}
	
	public class ItemDarkBlock extends ItemBlock {

		public ItemDarkBlock(Block block) {
			
			super(block);
		}
		
		@Override
		public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean whatisthis) {
			
			list.add(TextFormatting.RED + "(WIP)");
		}
	}
}
