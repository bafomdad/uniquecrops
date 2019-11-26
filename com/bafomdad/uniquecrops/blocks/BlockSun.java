package com.bafomdad.uniquecrops.blocks;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileSunBlock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSun extends BlockBaseUC {

	public BlockSun() {
		
		super("sunblock", Material.IRON);
		setHardness(50.0F);
		setResistance(2000.0F);
		setSoundType(SoundType.GLASS);
		setLightLevel(1.0F);
		GameRegistry.registerTileEntity(TileSunBlock.class, new ResourceLocation(UniqueCrops.MOD_ID, "TileSunBlock"));
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
    	
        return BlockRenderLayer.CUTOUT;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
    	
        return false;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	
    	return false;
    }
    
	@Override
    public boolean hasTileEntity(IBlockState state) {
        
		return true;
    }
	
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	
    	return new TileSunBlock();
    }
    	
}
