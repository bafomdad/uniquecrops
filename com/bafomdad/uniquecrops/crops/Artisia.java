package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.ItemHandlerHelper;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileArtisia;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;

public class Artisia extends BlockCropsBase implements ITileEntityProvider {

	public Artisia() {
		
		super(EnumCrops.CRAFTER, false, UCConfig.cropArtisia);
		GameRegistry.registerTileEntity(TileArtisia.class, "TileArtisia");
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsArtisia;
	}
	
	@Override
	public Item getCrop() {
		
		return Item.getItemFromBlock(Blocks.CRAFTING_TABLE);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		
		if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileArtisia) {
			ItemStack stack = ((TileArtisia)world.getTileEntity(pos)).getItem();
			if (!stack.isEmpty()) {
				world.removeTileEntity(pos);
				spawnAsEntity(world, pos, stack);
			}
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		
		if (!(entity instanceof EntityItem)) return;
		if (getAge(state) < getMaxAge()) return;
		
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileArtisia) {
			if (!((TileArtisia)te).isCore()) return;
			if (!((TileArtisia)te).getItem().isEmpty()) return;
			
			((TileArtisia)te).setStackSpace((EntityItem)entity);
		}
    }
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
	
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileArtisia && hand == EnumHand.MAIN_HAND) {
			if (((TileArtisia)te).getItem() != null && !world.isRemote) {
				ItemStack tilestack = ((TileArtisia)te).getItem().copy();
				((TileArtisia)te).setItem(ItemStack.EMPTY);
				ItemHandlerHelper.giveItemToPlayer(player, tilestack);
			}
			((TileArtisia)te).markBlockForUpdate();
			return true;
		}
		return false;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (this.getAge(state) >= getMaxAge() || world.isRemote)
			return;
		
		super.updateTick(world, pos, state, rand);
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileArtisia && ((TileArtisia)te).core.equals(BlockPos.ORIGIN)) {
			((TileArtisia)te).findCore();
		}
	}
	
    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
    	
    	super.grow(world, pos, state);
    	if (!world.isRemote) {
    		TileEntity te = world.getTileEntity(pos);
    		if (te instanceof TileArtisia && ((TileArtisia)te).core.equals(BlockPos.ORIGIN))
    			((TileArtisia)te).findCore();
    	}
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileArtisia();
	}
}
