package com.bafomdad.uniquecrops.blocks;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
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
		setCreativeTab(UniqueCrops.TAB);
		setSoundType(SoundType.GLASS);
		setTickRandomly(true);
		setHardness(1.0F);
	}
	
	@Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean wat) {
        
    	addCollisionBoxToList(pos, BASE_AABB, collidingBoxes, BASE_AABB);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
       
    	return BASE_AABB;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
       
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
		
		if (world.isBlockIndirectlyGettingPowered(pos) > 0) {
			Iterable<BlockPos> poslist = BlockPos.getAllInBox(pos.add(-range, -range, -range), pos.add(range, range, range));
			Iterator it = poslist.iterator();
			while (it.hasNext()) {
				BlockPos posit = (BlockPos)it.next();
				if (!world.isRemote && !world.isAirBlock(posit)) {
					Block loopblock = world.getBlockState(posit).getBlock();
					boolean flag = rand.nextInt(10) == 0;
					if (loopblock == Blocks.GRASS && flag) {
						EntityItemHourglass.setOldBlock(world, posit, UCBlocks.oldGrass); break;
					}
					if (loopblock == Blocks.COBBLESTONE && flag) {
						EntityItemHourglass.setOldBlock(world, posit, UCBlocks.oldCobble); break;
					}
					if (loopblock == Blocks.MOSSY_COBBLESTONE && flag) {
						EntityItemHourglass.setOldBlock(world, posit, UCBlocks.oldCobbleMoss); break;
					}
					if (loopblock == Blocks.BRICK_BLOCK && flag) {
						EntityItemHourglass.setOldBlock(world, posit, UCBlocks.oldBrick); break;
					}
					if (loopblock == Blocks.GRAVEL && flag) {
						EntityItemHourglass.setOldBlock(world, posit, UCBlocks.oldGravel); break;
					}
				}
			}
		}
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
