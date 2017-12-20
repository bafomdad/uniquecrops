package com.bafomdad.uniquecrops.blocks;

import java.util.Random;

import javax.annotation.Nonnull;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockOldGrass extends BlockBaseUC {

	public BlockOldGrass() {
		
		super("oldgrass", Material.GRASS);
		setHardness(0.6F);
		setSoundType(SoundType.PLANT);
		setTickRandomly(true);
	}
	
	@Override
	public boolean isToolEffective(String type, @Nonnull IBlockState state) {
		
		return type.equals("shovel");
	}
	
	@Nonnull
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return Blocks.DIRT.getItemDropped(state, rand, fortune);
	}
	
	@Override
	public boolean canSustainPlant(@Nonnull IBlockState state, @Nonnull IBlockAccess world, BlockPos pos, @Nonnull EnumFacing facing, IPlantable plantable) {
		
		EnumPlantType type = plantable.getPlantType(world, pos.down());
		return type == EnumPlantType.Plains || type == EnumPlantType.Beach;
	}
	
	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        
		if (!worldIn.isRemote) {
            if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2)
                worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());

            else {
                if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
                    for (int i = 0; i < 4; ++i) {
                        BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos))
                            return;

                        IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

                        if (iblockstate1.getBlock() == Blocks.DIRT && iblockstate1.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2)
                            worldIn.setBlockState(blockpos, UCBlocks.oldGrass.getDefaultState());
                    }
                }
            }
        }
    }
}
