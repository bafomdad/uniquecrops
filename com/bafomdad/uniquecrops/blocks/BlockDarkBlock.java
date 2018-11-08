package com.bafomdad.uniquecrops.blocks;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockDarkBlock extends BlockBaseUC {

	public BlockDarkBlock() {
		
		super("darkblock", Material.ROCK);
		setSoundType(SoundType.STONE);
		setResistance(6000000.0F);
		setBlockUnbreakable();
	}
}
