package com.bafomdad.uniquecrops.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
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

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.entities.EntityItemHourglass;

public class BlockHourglass extends Block {
	
	protected static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 1F, 0.75F);

	public BlockHourglass() {
		
		super(Material.IRON);
		setRegistryName("hourglass");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".hourglass");
		setCreativeTab(UniqueCrops.TAB);
		setSoundType(SoundType.GLASS);
		setHardness(1.0F);
		setTickRandomly(true);
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlockHourglass(this), getRegistryName());
	}
	
	@Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB aabb, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity) {
        
    	addCollisionBoxToList(pos, aabb, collidingBoxes, BASE_AABB);
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
