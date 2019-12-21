package com.bafomdad.uniquecrops.crops.supercrops;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
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

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockSuperCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileItero;
import com.bafomdad.uniquecrops.core.enums.EnumItems;
import com.bafomdad.uniquecrops.core.enums.EnumSuperCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Itero extends BlockSuperCropsBase {
	
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
    private static final AxisAlignedBB[] AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
    
	public static final BlockPos[] PLATES =  {
		new BlockPos(-1, 0, -1), new BlockPos(1, 0, 1),
		new BlockPos(-1, 0, 1), new BlockPos(1, 0, -1)
	};

	public Itero() {

		super(EnumSuperCrops.ITERO);
		setDefaultState(this.getDefaultState().withProperty(this.getAgeProperty(), 0));
		GameRegistry.registerTileEntity(TileItero.class, new ResourceLocation(UniqueCrops.MOD_ID, "TileItero"));
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	
        return AABB[((Integer)state.getValue(this.getAgeProperty())).intValue()];
    }
	
    @Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, AGE);
	}
	
    @Override
	public IBlockState getStateFromMeta(int meta) {
		
		return this.withAge(meta);
	}
	
    @Override
	public int getMetaFromState(IBlockState state) {
		
		return this.getAge(state);
	}
	
	protected PropertyInteger getAgeProperty() {
		
		return AGE;
	}
	
	public int getAge(IBlockState state) {
		
		return state.getValue(this.getAgeProperty()).intValue();
	}
	
	public IBlockState withAge(int age) {
		
		return this.getDefaultState().withProperty(this.getAgeProperty(), age);
	}
	
	public int getMaxAge() {
		
		return 7;
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if (getAge(state) >= getMaxAge()) {
			InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, EnumItems.CUBEY.createStack(1 + world.rand.nextInt(1)));
			world.setBlockState(pos, this.getDefaultState(), 2);
			return true;
		}
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileItero && !world.isRemote) {
			TileItero itero = (TileItero)tile;
			itero.tryShowDemo();
			return itero.createCombos(getAge(state));
		}
		return false;
	}
	
	@Override
    public boolean hasTileEntity(IBlockState state) {
        
		return true;
    }
	
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	
    	return new TileItero();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	if (rand.nextInt(2) == 0) {
    		TileEntity tile = world.getTileEntity(pos);
    		if (tile instanceof TileItero && ((TileItero)tile).showingDemo())
    			world.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + rand.nextFloat(), pos.getY() + 0.25, pos.getZ() + rand.nextFloat(), 0.0D, 0.03D, 0.0D);
    	}
    }
}
