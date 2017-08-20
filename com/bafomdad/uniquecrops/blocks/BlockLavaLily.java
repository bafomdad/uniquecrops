package com.bafomdad.uniquecrops.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLavaLily extends BlockBush {
	
	protected static final AxisAlignedBB LILY_PAD_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.09375D, 0.9375D);

	public BlockLavaLily() {
		
		super(Material.GRASS);
		setRegistryName("lavalily");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".lavalily");
		setCreativeTab(UniqueCrops.TAB);
		useNeighborBrightness = true;
		GameRegistry.register(this);
		GameRegistry.register(new ItemLavaLily(this), getRegistryName());
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
       
		return LILY_PAD_AABB;
    }

	@Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
        
		return LILY_PAD_AABB;
    }
	
	@Override
	public boolean canSustainBush(IBlockState state) {
		
		return state.getBlock() == Blocks.LAVA;
	}
	
	@Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
    	
		Material material = world.getBlockState(pos).getMaterial();
		return (super.canPlaceBlockAt(world, pos)) && (material != null) && (!material.isLiquid());
    }
	
	@Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
    	
		IBlockState lavasource = world.getBlockState(pos.add(0, -1, 0));
		Material material = lavasource.getMaterial();
		boolean flag = ((Integer)lavasource.getValue(BlockLiquid.LEVEL)).intValue() == 0;
		
		return pos.getY() >= 0 && pos.getY() < 256 ? material == Material.LAVA && flag : false;
    }
	
	@Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
    	
		if (world.isRemote)
			return;
		
		if (entity instanceof EntityPlayer) {
			EntityPlayer ep = (EntityPlayer)entity;
			if (!ep.capabilities.isCreativeMode) {
				ItemStack boots = ep.inventory.armorInventory[0];
				if (boots == null)
					ep.setFire(3);
			}
		}
		else if (entity instanceof EntityLiving) {
			EntityLiving el = (EntityLiving)entity;
			if (!el.isImmuneToFire()) {
				el.setFire(3);
			}
		}
    }
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	if (rand.nextInt(2) == 0) {
    		world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + rand.nextFloat(), pos.getY(), pos.getZ() + rand.nextFloat(), 0.0D, 0.03D, 0.0D);
    	}
    }
	
	public static class ItemLavaLily extends ItemBlock {

		public ItemLavaLily(Block block) {
			
			super(block);
		}
		
		@Override
	    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	    {
	        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

	        if (raytraceresult == null)
	        {
	            return new ActionResult(EnumActionResult.PASS, itemStackIn);
	        }
	        else
	        {
	            if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
	            {
	                BlockPos blockpos = raytraceresult.getBlockPos();

	                if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemStackIn))
	                {
	                    return new ActionResult(EnumActionResult.FAIL, itemStackIn);
	                }

	                BlockPos blockpos1 = blockpos.up();
	                IBlockState iblockstate = worldIn.getBlockState(blockpos);

	                if (iblockstate.getMaterial() == Material.LAVA && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0 && worldIn.isAirBlock(blockpos1))
	                {
	                    worldIn.setBlockState(blockpos1, UCBlocks.lavalily.getDefaultState(), 11);

	                    if (!playerIn.capabilities.isCreativeMode)
	                        --itemStackIn.stackSize;
	                    
	                    worldIn.playSound(playerIn, blockpos, SoundEvents.BLOCK_WATERLILY_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
	                    return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	                }
	            }
	            return new ActionResult(EnumActionResult.FAIL, itemStackIn);
	        }
	    }
	}
}
