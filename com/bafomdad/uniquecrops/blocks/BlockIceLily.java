package com.bafomdad.uniquecrops.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockIceLily extends BlockLilyPad {

	protected static final AxisAlignedBB LILY_PAD_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.09375D, 0.9375D);
	
	public BlockIceLily() {
		
		setRegistryName("icelily");
		setTranslationKey(UniqueCrops.MOD_ID + ".icelily");
		setSoundType(SoundType.SNOW);
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
		
		if (entity instanceof EntityItem) {
			EntityItem ei = (EntityItem)entity;
			if (!ei.isDead && ei.getEntityData().getShort("Age") > -6000)
				ei.setNoDespawn();
		}
    }
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	if (rand.nextInt(2) == 0)
    		world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, pos.getX() + rand.nextFloat(), pos.getY(), pos.getZ() + rand.nextFloat(), 0.0D, 0.03D, 0.0D);
    }
}
