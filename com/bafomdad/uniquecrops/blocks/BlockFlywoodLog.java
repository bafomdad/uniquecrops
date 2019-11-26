package com.bafomdad.uniquecrops.blocks;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;

public class BlockFlywoodLog extends BlockLog {

	public BlockFlywoodLog() {

		setRegistryName("flywood_log");
		setTranslationKey(UniqueCrops.MOD_ID + ".flywood_log");
		setCreativeTab(UniqueCrops.TAB);
		UCBlocks.blocks.add(this);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
	}
	
	@Override
    protected BlockStateContainer createBlockState() {
		
        return new BlockStateContainer(this, new IProperty[] { LOG_AXIS });
    }
	
	@Override
    public IBlockState getStateFromMeta(int meta) {
		
        BlockLog.EnumAxis enumfacing$axis = BlockLog.EnumAxis.Y;
        int i = meta & 12;

        if (i == 4)
            enumfacing$axis = BlockLog.EnumAxis.X;

        else if (i == 8)
            enumfacing$axis = BlockLog.EnumAxis.Z;
        
        return this.getDefaultState().withProperty(LOG_AXIS, enumfacing$axis);
    }
	
	@Override
    public int getMetaFromState(IBlockState state) {
       
		int i = 0;
        BlockLog.EnumAxis enumfacing$axis = (BlockLog.EnumAxis)state.getValue(LOG_AXIS);

        if (enumfacing$axis == BlockLog.EnumAxis.X)
            i |= 4;

        else if (enumfacing$axis == BlockLog.EnumAxis.Z)
            i |= 8;

        return i;
    }
	
//	@Override
//    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
//        
//		return this.getStateFromMeta(meta).withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
//    }
}
