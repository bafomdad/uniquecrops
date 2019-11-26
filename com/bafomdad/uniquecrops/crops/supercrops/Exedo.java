package com.bafomdad.uniquecrops.crops.supercrops;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockSuperCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileExedo;
import com.bafomdad.uniquecrops.core.enums.EnumSuperCrops;

public class Exedo extends BlockSuperCropsBase {

	public Exedo() {
		
		super(EnumSuperCrops.EXEDO);
		GameRegistry.registerTileEntity(TileExedo.class, new ResourceLocation(UniqueCrops.MOD_ID, "TileExedo"));
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		
		return new TileExedo();
	}
}
