package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileSucco;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Succo extends BlockCropsBase {

	public Succo() {
		
		super(EnumCrops.VAMPIRE);
		GameRegistry.registerTileEntity(TileSucco.class, new ResourceLocation(UniqueCrops.MOD_ID, "TileSucco"));
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsSucco;
	}
	
	@Override
	public Item getCrop() {
		
		return Items.AIR;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (world.getCurrentMoonPhaseFactor() == 0.0F) {
			super.updateTick(world, pos, state, rand);
			return;
		}
		this.checkAndDropBlock(world, pos, state);
	}
	
	@Override
    public boolean hasTileEntity(IBlockState state) {
		
		return true;
	}
	
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	
    	return new TileSucco();
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
    	
        return EnumBlockRenderType.INVISIBLE;
    }
    
    @Override
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
    	
    	EntityPlayer player = UniqueCrops.proxy.getPlayer();
    	if (player != null && player.capabilities.isCreativeMode)
    		return super.collisionRayTrace(state, world, pos, start, end);
    	
    	return null;
    }
}
