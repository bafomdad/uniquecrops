package com.bafomdad.uniquecrops.crops;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileFeroxia;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.core.enums.EnumGrowthSteps;
import com.bafomdad.uniquecrops.init.UCItems;

public class Feroxia extends BlockCropsBase {
	
	public Feroxia() {
		
		super(EnumCrops.SAVAGEPLANT);
		GameRegistry.registerTileEntity(TileFeroxia.class, new ResourceLocation(UniqueCrops.MOD_ID, "feroxia"));
	}

	@Override
	public Item getSeed() {
		
		return UCItems.seedsFeroxia;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public boolean canPlantCrop(World world, EntityPlayer player, EnumFacing side, BlockPos pos, ItemStack stack) {
		
		this.onBlockPlacedBy(world, pos.offset(side), getDefaultState(), player, stack);
		return super.canPlantCrop(world, player, side, pos, stack);
	}
	
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        
    	if (this.getAge(state) < getMaxAge())
    		return 0;
    	
    	return random.nextInt(4) + 2;
    }
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 1;
		
		return 9;
	}
	
	@Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
       
    	IBlockState soil = worldIn.getBlockState(pos.down());
        return soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
    }
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (this.getAge(state) >= getMaxAge()) return;
		if (this.canIgnoreGrowthRestrictions(world, pos)) {
			super.updateTick(world, pos, state, rand);
			return;
		}
		if (world.isRemote) return;
		
		if (!this.canBlockStay(world, pos, state)) {
			EntityItem ei = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, new ItemStack(this.getSeed()));
			world.spawnEntity(ei);
			world.setBlockToAir(pos);
			return;
		}
		int stage = getStage(world, pos, state);
		if (stage == -1) return;
		
		if (!EnumGrowthSteps.values()[stage].canAdvance(world, pos, state))
			return;
		
		if (rand.nextInt(3) == 0)
			world.setBlockState(pos, this.withAge(getAge(state) + 1), 3);
	}
	
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    
    	world.setBlockState(pos, state, 2);
    	TileEntity te = world.getTileEntity(pos);
    	if (te instanceof TileFeroxia) {
    		world.playEvent(2001, pos, Block.getStateId(state));
    		if (placer instanceof EntityPlayer && !(placer instanceof FakePlayer)) {
    			if (!world.isRemote) {
            		((TileFeroxia)te).setOwner(placer.getUniqueID());
            		if (!placer.getEntityData().hasKey(UCStrings.TAG_GROWTHSTAGES)) {
            			UCUtils.generateSteps((EntityPlayer)placer);
            		}
            		UCUtils.updateBook((EntityPlayer)placer);
    			}
    		}
    	}
    }
    
    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
    	
    	if (getAge(state) >= getMaxAge() || world.isRemote) return;
    	
    	int stage = getStage(world, pos, state);
    	if (stage != -1 && EnumGrowthSteps.values()[stage].ordinal() == EnumGrowthSteps.NOBONEMEAL.ordinal()) {
    		world.setBlockState(pos, this.withAge(0), 3);
    		return;
    	}
    	else if (stage != -1 && EnumGrowthSteps.values()[stage].canAdvance(world, pos, state)) {
    		world.setBlockState(pos, this.withAge(getAge(state) + 1), 3);
    		return;
    	}
    }
    
    private int getStage(World world, BlockPos pos, IBlockState state) {
    	
		TileEntity tile = world.getTileEntity(pos);
		if (!(tile instanceof TileFeroxia)) return -1;
		
		TileFeroxia te = (TileFeroxia)tile;
		if (te.getOwner() == null || (te.getOwner() != null && UCUtils.getPlayerFromUUID(te.getOwner().toString()) == null)) return -1;

		NBTTagList taglist = UCUtils.getServerTaglist(te.getOwner());
		if (taglist == null) return -1;
		
		NBTTagCompound tag = taglist.getCompoundTagAt(this.getAge(state));
		return tag.getInteger("stage" + this.getAge(state));
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
    	
    	return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	
    	return new TileFeroxia();
    }
}
