package com.bafomdad.uniquecrops.blocks;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.UniqueCropsAPI;
import com.bafomdad.uniquecrops.crafting.HourglassRecipe;
import com.bafomdad.uniquecrops.entities.EntityItemHourglass;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHourglass extends BlockBaseUC {
	
	protected static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 1F, 0.75F);
	private int range = 3;

	public BlockHourglass() {
		
		super("hourglass", Material.IRON);
		setSoundType(SoundType.GLASS);
		setTickRandomly(true);
		setHardness(1.0F);
	}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
       
    	return BASE_AABB;
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
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (world.getRedstonePowerFromNeighbors(pos) > 0) {
			Iterable<BlockPos> poslist = BlockPos.getAllInBox(pos.add(-range, -range, -range), pos.add(range, range, range));
			Iterator it = poslist.iterator();
			while (it.hasNext()) {
				BlockPos posit = (BlockPos)it.next();
				if (!world.isRemote && !world.isAirBlock(posit)) {
					IBlockState loopState = world.getBlockState(posit);
					boolean flag = rand.nextInt(10) == 0;
					
					if (flag) {
						HourglassRecipe recipe = UniqueCropsAPI.HOURGLASS_RECIPE_REGISTRY.findRecipe(loopState);
						if (recipe != null) {
							EntityItemHourglass.convertBlock(world, posit, recipe.getOutput(), recipe.getOutputMeta());
						}
					}
				}
			}
		}
	}
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	if (rand.nextInt(2) == 0 && world.getRedstonePowerFromNeighbors(pos) > 0)
    		world.spawnParticle(EnumParticleTypes.END_ROD, pos.getX() + rand.nextFloat(), pos.getY(), pos.getZ() + rand.nextFloat(), 0.0D, 0.03D, 0.0D);
    }
	
	public static class ItemBlockHourglass extends ItemBlock {

		public ItemBlockHourglass(Block block) {
			
			super(block);
		}
		
		@Override
		public boolean hasCustomEntity(ItemStack stack) {
			
			return true;
		}
		
		@Override
		public int getEntityLifespan(ItemStack stack, World world) {
			
			return Integer.MAX_VALUE;
		}
		
		@Override
		public Entity createEntity(World world, Entity location, ItemStack stack) {
			
			if (location instanceof EntityItem) {
				EntityItemHourglass hourglass = new EntityItemHourglass(world, (EntityItem)location, stack);
				return hourglass;
			}
			return null;
		}
	}
}
