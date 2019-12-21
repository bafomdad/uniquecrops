package com.bafomdad.uniquecrops.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.ICropBook;
import com.bafomdad.uniquecrops.blocks.tiles.TileSunBlock;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;

public abstract class BlockCropsBase extends BlockCrops implements ICropBook {
	
	private EnumCrops type;
	private boolean extra;
	private boolean clickHarvest;
	private boolean bonemealable;
	private boolean ignoreGrowthRestrictions;
	
	public BlockCropsBase(EnumCrops type) {
		
		this.type = type;
		this.extra = true;
		this.clickHarvest = true;
		this.bonemealable = true;
		setRegistryName("crop" + type.getName());
		setTranslationKey(UniqueCrops.MOD_ID + ".crop" + type.getName());
		UCBlocks.blocks.add(this);
	}
	
	public String getCropType() {
		
		return type.getName();
	}
	
	public boolean canPlantCrop() {
		
		return type.getConfig();
	}
	
	public boolean canIgnoreGrowthRestrictions(World world, BlockPos pos) {
		
//		UCUtils.getClosestTile(TileSunBlock.class, world, pos, 8.0D);
		if (!ignoreGrowthRestrictions) return false;
		
		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
			BlockPos loopPos = pos.offset(facing);
			TileEntity tile = world.getTileEntity(loopPos);
			if (tile instanceof TileSunBlock && ((TileSunBlock)tile).powered)
				return true;
		}
		return false;
	}
	
    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {

    	return canBonemeal(world, pos);
    }
    
	public boolean canBonemeal(World world, BlockPos pos) {
		
		if (this.canIgnoreGrowthRestrictions(world, pos)) return true;

		return bonemealable;
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if (getAge(state) < getMaxAge()) return false;

		if (clickHarvest) {
			if (getAge(state) >= getMaxAge() && !world.isRemote) {
				world.setBlockState(pos, this.withAge(0), 3);
				int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand());
//				this.dropBlockAsItem(world, pos, state, fortune);
				harvestItems(world, pos, state, fortune);
			}
			return true;
		}
		return false;
	}
	
	private void harvestItems(World world, BlockPos pos, IBlockState state, int fortune) {
		
		Item item = this.getCrop();
		if (item != Items.AIR) {
			int fortuneDrop = fortune > 0 ? world.rand.nextInt(fortune) : 0;
			for (int i = 0; i < this.quantityDropped(world.rand) + fortuneDrop; i++) {
				InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, new ItemStack(item, 1, this.damageDropped(state)));
			}
		}
		if (this.getCanDropExtra() && world.rand.nextBoolean()) {
			InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, new ItemStack(this.getSeed(), 1, 0));
		}
	}
	
	/*
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	
        List<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this.getSeed(), 1, 0));
        int age = getAge(state);
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        int count = this.quantityDropped(state, fortune, rand);
        
        if (age >= getMaxAge()) {
            for(int i = 0; i < count; i++)
            {
                Item item = this.getItemDropped(state, rand, fortune);
                if (item != Items.AIR)
                {
                    ret.add(new ItemStack(item, 1, this.damageDropped(state)));
                }
            }
        }
        if (age >= getMaxAge() && extra)
        {
            int k = 2 + fortune;

            for (int i = 0; i < k; ++i)
            {
                if (rand.nextInt(2 * getMaxAge()) <= age)
                {
                    ret.add(new ItemStack(this.getSeed(), 1, 0));
                }
            }
        }
        return ret;
    }
    */
    
    @Override
    public void grow(World world, BlockPos pos, IBlockState state) {
    	
    	if (!this.canUseBonemeal(world, world.rand, pos, state))
    		return;
    	
    	super.grow(world, pos, state);
    }

	@SideOnly(Side.CLIENT)
	public void createParticles(IBlockState state, World world, BlockPos pos, Random rand, EnumParticleTypes particle, int rand2) {
		
		if (this.getAge(state) >= getMaxAge()) {
    		double x = pos.getX();
    		double y = pos.getY() + 0.5D;
    		double z = pos.getZ();
    		
    		if (rand2 > 0) {
    			for (int i = 0; i < rand.nextInt(rand2) + 1; i++)
    				world.spawnParticle(particle, x + rand.nextFloat(), y, z + rand.nextFloat(), 0, 0, 0);
    			return;
    		}
    		for (int i = 0; i < rand.nextInt(4) + 1; i++)
    			world.spawnParticle(particle, x + rand.nextFloat(), y, z + rand.nextFloat(), 0, 0, 0);
		}
	}
	
	@Override
	public String getBookDescription() {
		
		return UniqueCrops.MOD_ID + ".book.page" + type.getName();
	}
	
	@Override
	public boolean canIncludeInBook() {

		return true;
	}
	
	// new methods here
	public void setBonemealable(boolean flag) {
		
		this.bonemealable = flag;
	}
	
	public boolean getCanBonemeal() {
		
		return this.bonemealable;
	}
	
	public void setClickHarvest(boolean flag) {
		
		this.clickHarvest = flag;
	}
	
	public boolean getCanClickHarvest() {
		
		return clickHarvest;
	}
	
	public void setExtraDrops(boolean flag) {
		
		this.extra = flag;
	}
	
	public boolean getCanDropExtra() {
		
		return this.extra;
	}
	
	public void setIgnoreGrowthRestrictions(boolean flag) {
		
		this.ignoreGrowthRestrictions = flag;
	}
}
