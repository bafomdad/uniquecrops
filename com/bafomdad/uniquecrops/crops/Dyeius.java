package com.bafomdad.uniquecrops.crops;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Dyeius extends BlockCropsBase {

	public Dyeius() {
		
		super(EnumCrops.DYE);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsDyeius;
	}
	
	@Override
	public Item getCrop() {
		
		return Items.DYE;
	}
	
//	@Override
//	public int damageDropped(IBlockState state) {
//		
//		World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
//		if (world != null) {
//			long time = world.getWorldTime() % 24000L;
//			int meta = (int)(time / 1500);
//			
//			LocalDateTime current = LocalDateTime.now();
//			if (current.getDayOfWeek() == DayOfWeek.FRIDAY)
//				return EnumDyeColor.byMetadata(meta).getMetadata();
//			else
//				return EnumDyeColor.byMetadata(meta).getDyeDamage();
//		}
//		return 0;
//	}
	
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        
    	if (this.getAge(state) < getMaxAge())
    		return 0;
    	
    	return random.nextInt(3) + 1;
    }
}
