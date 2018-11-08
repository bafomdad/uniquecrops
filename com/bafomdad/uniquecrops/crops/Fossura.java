package com.bafomdad.uniquecrops.crops;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileDigger;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Fossura extends BlockCropsBase {

	public Fossura() {
		
		super(EnumCrops.DIGGER);
		GameRegistry.registerTileEntity(TileDigger.class, new ResourceLocation(UniqueCrops.MOD_ID, "TileDigger"));
	}
	
	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
		
		return 8.0F;
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsQuarry;
	}
	
	@Override
	public Item getCrop() {
		
		return Items.AIR;
	}
	
	@Override
    public boolean hasTileEntity(IBlockState state) {
        
		return getAge(state) >= getMaxAge();
    }
	
	@Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		
		if (this.getAge(state) < getMaxAge() && !world.isRemote) {
			ItemStack heldItem = player.getHeldItemMainhand();
			if (!heldItem.isEmpty() && heldItem.getItem() instanceof ItemPickaxe) {
				player.world.playEvent(2001, pos, Block.getStateId(state));
				world.setBlockState(pos, this.withAge(this.getAge(state) + 1), 3);
				return false;
			}
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}
	
	@Override
    public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
		
		ItemStack heldItem = player.getHeldItemMainhand();
		if (!heldItem.isEmpty() && heldItem.getItem() instanceof ItemPickaxe) {
			return ForgeHooks.blockStrength(state, player, world, pos);
		}
		return 1.0F;
	}
	
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	
    	return new TileDigger();
    }
}
