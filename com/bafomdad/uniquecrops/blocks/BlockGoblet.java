package com.bafomdad.uniquecrops.blocks;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileGoblet;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGoblet extends BlockBaseUC {
	
	public static final PropertyBool FILLED = PropertyBool.create("filled");
	private final AxisAlignedBB AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.5D, 0.625D);

	public BlockGoblet() {
		
		super("goblet", Material.CLAY);
		setSoundType(SoundType.METAL);
		setHardness(0.3F);
		setResistance(1.0F);
		GameRegistry.registerTileEntity(TileGoblet.class, new ResourceLocation(UniqueCrops.MOD_ID, "TileGoblet"));
		setDefaultState(blockState.getBaseState().withProperty(FILLED, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, FILLED);
	}
	
	public boolean isFilled(IBlockState state) {
		
		return state.getValue(FILLED);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		return getDefaultState().withProperty(FILLED, meta == 1 ? true : false);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		return state.getValue(FILLED) ? 1 : 0;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		
		return AABB;
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		
        return NULL_AABB;
    }
	
    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
    	
    	if (!(entity instanceof EntityItem) || (entity instanceof EntityItem && ((EntityItem)entity).getItem().getItem() != UCItems.vampiricOintment)) return;
    	if (isFilled(state)) return;
    	if (!(world.getTileEntity(pos) instanceof TileGoblet)) return;
    	
    	ItemStack ointment = ((EntityItem)entity).getItem();
    	if (!ointment.hasTagCompound() || (ointment.hasTagCompound() && !ointment.getTagCompound().hasKey(UCStrings.TAG_LOCK))) return;
    	
    	if (!world.isRemote) {
	    	world.setBlockState(pos, state.withProperty(FILLED, true), 3);
	    	((TileGoblet)world.getTileEntity(pos)).setTaglock(UUID.fromString(NBTUtils.getString(ointment, UCStrings.TAG_LOCK, "")));
	    	entity.setDead();
    	}
    }
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if (!isFilled(state)) {
			ItemStack stack = player.getHeldItem(hand);
			TileEntity tile = world.getTileEntity(pos);
			if (!stack.isEmpty() && stack.getItem() == UCItems.vampiricOintment && tile instanceof TileGoblet) {
				boolean flag = (stack.hasTagCompound() && stack.getTagCompound().hasKey(UCStrings.TAG_LOCK));
				if (!world.isRemote && flag) {
					((TileGoblet)tile).setTaglock(UUID.fromString(NBTUtils.getString(stack, UCStrings.TAG_LOCK, "")));
					player.setHeldItem(hand, ItemStack.EMPTY);
					world.setBlockState(pos, state.withProperty(FILLED, true), 3);
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		
		return false;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
	
		return 0;
//		return getMetaFromState(state);
	}
	
	@Override
    public int getWeakPower(IBlockState state, IBlockAccess access, BlockPos pos, EnumFacing side) {
		
		if (isFilled(state)) return 15;
		
		return 0;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		
		return new TileGoblet();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		
		if (isFilled(state)) {
			double d0 = pos.getX() + 0.45F;
			double d1 = pos.getY() + 0.4F;
			double d2 = pos.getZ() + 0.5F;
			world.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}
}
