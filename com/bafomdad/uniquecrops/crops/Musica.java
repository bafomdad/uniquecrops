package com.bafomdad.uniquecrops.crops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent.Instrument;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileMusicaPlant;
import com.bafomdad.uniquecrops.blocks.tiles.TileMusicaPlant.Beat;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;

public class Musica extends BlockCropsBase {
	
	private Item[] recordlist = new Item[] { Items.RECORD_BLOCKS, Items.RECORD_CAT, Items.RECORD_CHIRP, Items.RECORD_FAR, Items.RECORD_MALL, Items.RECORD_MELLOHI, Items.RECORD_STAL, Items.RECORD_STRAD, Items.RECORD_WAIT, Items.RECORD_WARD, Items.RECORD_11, Items.RECORD_13 };
	
	public Musica() {
		
		super(EnumCrops.MUSICAPLANT);
		GameRegistry.registerTileEntity(TileMusicaPlant.class, "TileMusicaPlant");
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsMusica;
	}
	
	@Override
	public Item getCrop() {
		
		Random rand = new Random();
		return recordlist[rand.nextInt(recordlist.length)];
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		return;
	}
	
	public void addAge(World world, BlockPos pos, IBlockState state, int stage) {
		 
		world.setBlockState(pos, this.withAge(getAge(state) + stage), 2);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {

		return new TileMusicaPlant();
	}
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	this.createParticles(state, world, pos, rand, EnumParticleTypes.NOTE, 10);
    }
}
