package com.bafomdad.uniquecrops.blocks;

import java.util.Random;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockJungleLily extends BlockLilyPad {

	public BlockJungleLily() {
		
		setRegistryName("junglelily");
		setTranslationKey(UniqueCrops.MOD_ID + ".junglelily");
		setCreativeTab(UniqueCrops.TAB);
		useNeighborBrightness = true;
		UCBlocks.blocks.add(this);
	}
	
	@Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
    	
		Material material = world.getBlockState(pos.down()).getMaterial();
		return (super.canPlaceBlockAt(world, pos)) && (material != null) && (material.isLiquid());
    }

	@Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
    	
		if (world.isRemote)
			return;
		
		if (entity instanceof EntityPlayer) {
			if (entity.fallDistance > 0.0F)
				entity.fallDistance = 0.0F;
		}
    }
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	if (rand.nextInt(2) == 0)
    		world.spawnParticle(EnumParticleTypes.SLIME, pos.getX() + rand.nextFloat(), pos.getY(), pos.getZ() + rand.nextFloat(), 0.0D, 0.03D, 0.0D);
    }
}
