package com.bafomdad.uniquecrops.blocks;

import java.util.Iterator;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.tiles.TileMirror;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.entities.EntityMirror;
import com.bafomdad.uniquecrops.events.UCEventHandlerClient;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMirror extends BlockBaseUC {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyEnum<BlockDoor.EnumDoorHalf> HALF = BlockDoor.HALF;
	
	protected static final AxisAlignedBB NORTHSOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D);
	protected static final AxisAlignedBB EASTWEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);

	public BlockMirror() {
		
		super("mirror", Material.CIRCUITS);
		setHardness(0.3F);
		setResistance(10.0F);
		GameRegistry.registerTileEntity(TileMirror.class, "UC:TileMirror");
	}
	
    @Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
    
    	if (!UCConfig.cropVampire) return;
    	
    	IBlockState state = world.getBlockState(pos);
    	if (player.getAdjustedHorizontalFacing().getOpposite() != state.getValue(FACING)) return;
    	
    	if (!world.isRemote) {
			BlockPos newPos = (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) ? pos.down().offset(state.getValue(FACING), 3) : pos.offset(state.getValue(FACING), 3);
			Iterable<BlockPos> iter = BlockPos.getAllInBox(newPos.add(-2, 0, -2), newPos.add(2, 1, 2));
			Iterator it = iter.iterator();
			while (it.hasNext()) {
				BlockPos loopPos = (BlockPos)it.next();
				IBlockState loopState = world.getBlockState(loopPos);
				if (loopState.getBlock() == UCBlocks.cropSucco && loopState.getValue(BlockCrops.AGE) >= UCBlocks.cropSucco.getMaxAge()) {
					world.spawnEntity(new EntityItem(world, loopPos.getX(), loopPos.getY(), loopPos.getZ(), new ItemStack(UCItems.vampiricOintment)));
					world.destroyBlock(loopPos, true);
					break;
				}
			}
//    		EntityMirror ent = getMirrorEntity(world, pos, player.getAdjustedHorizontalFacing().getOpposite());
//    		if (ent != null) {
//    			float yaw = ent.rotationYaw;
//    			float pitch = ent.rotationPitch;
//    			BlockPos entPos = ent.getPosition();
//    			setPosition(ent, player);
//    			RayTraceResult rtr = ent.rayTrace(5, 1.0F);
//    			if (rtr != null && rtr.typeOfHit == RayTraceResult.Type.BLOCK) {
//    				BlockPos pos2 = rtr.getBlockPos();
//    				IBlockState state = world.getBlockState(pos2);
//    				Block block = state.getBlock();
//    				if (block instanceof BlockCrops) {
//    					NonNullList<ItemStack> items = NonNullList.create();
//    					block.getDrops(items, world, pos2, state, 0);
//    					
//    					world.setBlockToAir(pos2);
//    					player.world.playEvent(2001, pos2, Block.getStateId(state));
//    					
//    					for (ItemStack stack : items)
//    						world.spawnEntity(new EntityItem(world, pos2.getX() + 0.5, pos2.getY() + 0.5, pos2.getZ() + 0.5, stack));
//    				}
//    			}
//    			ent.setPositionAndRotation(entPos.getX(), entPos.getY(), entPos.getZ(), yaw, pitch);
//    		}
    	}
    }
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		/*
		BlockPos posOff = pos.offset(facing);
		Vec3d posHit = new Vec3d(posOff.getX() + hitX, posOff.getY() + hitY, posOff.getZ() + hitZ);
		Vec3d playerLook = player.getLookVec();
		Vec3d normFacing = new Vec3d(facing.getDirectionVec().getX(), facing.getDirectionVec().getY(), facing.getDirectionVec().getZ());

		Vec3d dirReflection = playerLook.subtract(normFacing.scale(2.0 * playerLook.dotProduct(normFacing)));
		RayTraceResult rtr = world.rayTraceBlocks(posHit, dirReflection, false, true, false);
		if (rtr != null && rtr.typeOfHit == RayTraceResult.Type.BLOCK) {
			if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
				UCEventHandlerClient.setDrawVecs(posHit, dirReflection);
				return true;
			}
		}
		*/
    	if (!UCConfig.cropVampire) return false;
    	if (facing != state.getValue(FACING)) return false;
    	
		ItemStack stack = player.getHeldItem(hand);
		if (!stack.isEmpty() && stack.getItem() == UCItems.seedsSucco) {
			BlockPos newPos = (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) ? pos.down().offset(state.getValue(FACING), 3) : pos.offset(state.getValue(FACING), 3);
			Iterable<BlockPos> iter = BlockPos.getAllInBox(newPos.add(-2, 0, -2), newPos.add(2, 1, 2));
			Iterator it = iter.iterator();
			while (it.hasNext()) {
				BlockPos loopPos = (BlockPos)it.next();
				if (world.isAirBlock(loopPos)) {
					if (!world.isRemote && world.getBlockState(loopPos.down()).getBlock() instanceof BlockFarmland) {
						world.setBlockState(loopPos, UCBlocks.cropSucco.getDefaultState(), 3);
						if (!player.capabilities.isCreativeMode)
							stack.shrink(1);
						break;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	private EntityMirror getMirrorEntity(World world, BlockPos pos, EnumFacing facing) {
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileMirror) {
			EntityMirror mirror = ((TileMirror)tile).getMirrorEntity();
			if (mirror.getAdjustedHorizontalFacing() == facing)
				return mirror;
		}
		else if (world.getTileEntity(pos.down()) instanceof TileMirror) {
			EntityMirror mirror = ((TileMirror)world.getTileEntity(pos.down())).getMirrorEntity();
			if (mirror.getAdjustedHorizontalFacing() == facing)
				return mirror;
		}
		return null;
	}

	private void setPosition(EntityMirror ent, EntityPlayer player) {
		
		switch (ent.getAdjustedHorizontalFacing()) {
			case WEST: ent.setPositionAndRotation(ent.posX -= 0.5D, ent.posY, ent.posZ, -player.rotationYawHead, player.rotationPitch); break;
			case EAST: ent.setPositionAndRotation(ent.posX += 0.5D, ent.posY, ent.posZ, -player.rotationYawHead, player.rotationPitch); break;
			case NORTH: ent.setPositionAndRotation(ent.posX, ent.posY, ent.posZ -= 0.5D, -player.rotationYawHead, player.rotationPitch); break;
			case SOUTH: ent.setPositionAndRotation(ent.posX -= 0.5D, ent.posY, ent.posZ -= 0.5D, -player.rotationYawHead, player.rotationPitch); break;
			default: break;
		}
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		
		state = state.getActualState(source, pos);
		EnumFacing facing = (EnumFacing)state.getValue(FACING);
		if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH)
			return new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D);
		return new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, FACING, HALF);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
		return state.getValue(FACING).getHorizontalIndex() | (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? 0b0100 : 0b0000);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		return this.getDefaultState()
				.withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 0b0011))
				.withProperty(HALF, (meta & 0b0100) != 0 ? BlockDoor.EnumDoorHalf.UPPER : BlockDoor.EnumDoorHalf.LOWER);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		
		return this.getDefaultState().withProperty(HALF, BlockDoor.EnumDoorHalf.LOWER).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		
		return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		
		world.setBlockState(pos.up(), this.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(HALF, BlockDoor.EnumDoorHalf.UPPER));
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos from) {
		
		if (block == this) {
			BlockPos pos2 = state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? pos.down() : pos.up();
			IBlockState secondHalf = world.getBlockState(pos2);
			if (secondHalf.getBlock() != this || secondHalf.getValue(FACING) != state.getValue(FACING) || secondHalf.getValue(HALF) == state.getValue(HALF))
				world.setBlockToAir(pos);
		}
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    	
    	return true;
    }
    
    @Override
    public boolean isBlockNormalCube(IBlockState state) {
    	
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
    public EnumBlockRenderType getRenderType(IBlockState state) {
    	
    	return EnumBlockRenderType.MODEL;
    }
	
	@Override
    public boolean hasTileEntity(IBlockState state) {
        
		return state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER;
    }
	
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	
    	return new TileMirror();
    }
}
