package com.bafomdad.uniquecrops.blocks;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileBarrel;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockBarrel extends BlockBaseUC {
	
	public static final AxisAlignedBB BARREL_AABB = new AxisAlignedBB(0.75D, 0.0D, 0.75D, 0.25D, 0.8D, 0.25D);

	public BlockBarrel() {
		
		super("abstractbarrel", Material.WOOD);
		setHardness(3.0F);
		setResistance(5.0F);
		setSoundType(SoundType.WOOD);
		GameRegistry.registerTileEntity(TileBarrel.class, "TileAbstractBarrel");
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
       
		return BARREL_AABB;
    }

	@Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        
		return BARREL_AABB;
    }
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileBarrel) {
			player.openGui(UniqueCrops.instance, 2, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
		
		TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof IInventory) {
            InventoryHelper.dropInventoryItems(world, pos, (IInventory)tile);
            world.updateComparatorOutputLevel(pos, this);
        }
		super.breakBlock(world, pos, state);
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
    	
        return new TileBarrel();
    }
}
