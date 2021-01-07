package com.bafomdad.uniquecrops.blocks;

import java.awt.Color;
import java.util.Random;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileHarvestTrap;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHarvestTrap extends BlockBaseUC {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final AxisAlignedBB TRAP_AABB = new AxisAlignedBB(0.75D, 0.0D, 0.75D, 0.25D, 1.25D, 0.25D);

	public BlockHarvestTrap() {
		
		super("harvesttrap", Material.WOOD);
		setHardness(0.85F);
		setResistance(15.0F);
		setTickRandomly(true);
		GameRegistry.registerTileEntity(TileHarvestTrap.class, new ResourceLocation(UniqueCrops.MOD_ID, "TileHarvestTrap"));
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
       
		return TRAP_AABB;
    }

	@Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        
		return TRAP_AABB;
    }
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, FACING);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		return state.getValue(FACING).getHorizontalIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		
		return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileHarvestTrap) {
			TileHarvestTrap trap = (TileHarvestTrap)tile;
			if (!trap.hasSpirit() && !trap.isCollected()) {
				if (player.getHeldItem(hand).getItem() == EnumItems.SPIRIT_BAIT.createStack().getItem()) {
					trap.setBaitPower(3);
					player.getHeldItem(hand).shrink(1);
					return true;
				}
			}
			if (trap.hasSpirit() && !trap.isCollected()) {
				trap.setSpiritTime(100);
				trap.setCollected();
				trap.setBaitPower(0);
				return true;
			}
		}
		return false;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (world.isRemote) return;
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileHarvestTrap) {
			if (UCUtils.getClosestTile(TileHarvestTrap.class, world, pos, 10.0D) != null) {
				UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.BARRIER, pos.getX(), pos.getY() + 0.75, pos.getZ(), 0));
				return;
			}
			TileHarvestTrap trap = (TileHarvestTrap)tile;
			if (trap.hasSpirit()) return;
			
			if (rand.nextInt(5 - trap.getBaitPower()) == 0) {
				trap.setSpiritTime(200);
			} else {
				trap.tickCropGrowth();
			}
		}
	}
	
	@Override
    public boolean hasTileEntity(IBlockState state) {
        
		return true;
    }
	
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	
        return new TileHarvestTrap();
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	TileEntity tile = world.getTileEntity(pos);
    	if (tile instanceof TileHarvestTrap) {
    		TileHarvestTrap trap = (TileHarvestTrap)tile;
    		if (trap.hasSpirit()) {
    			float[] color = trap.getSpiritColor();
    			UniqueCrops.proxy.sparkFX((double)pos.getX() + (rand.nextInt(3) * 0.1D) + 0.5, (double)pos.getY() + 1.15D + (rand.nextInt(2) * 0.1D), (double)pos.getZ() + (rand.nextInt(3) * 0.1D) + 0.5, color[0], color[1], color[2], 0.5F, 0, 0, 0, 1);
    		}
    	}
    }
}
