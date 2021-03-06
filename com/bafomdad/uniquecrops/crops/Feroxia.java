package com.bafomdad.uniquecrops.crops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileFeroxia;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.GrowthSteps;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;

public class Feroxia extends BlockCropsBase implements ITileEntityProvider {
	
	public static List<GrowthSteps> steps = new ArrayList<GrowthSteps>();

	public Feroxia() {
		
		super(EnumCrops.SAVAGEPLANT, false, UCConfig.cropFeroxia);
		this.clickHarvest = false;
		GameRegistry.registerTileEntity(TileFeroxia.class, "TileFeroxia");
		init();
	}
	
	private void init() {
		
		steps.add(new GrowthSteps(UCStrings.MOON, new GrowthSteps.MoonPhase(), 0));
		steps.add(new GrowthSteps(UCStrings.BURNINGPLAYER, new GrowthSteps.BurningPlayer(), 1));
		steps.add(new GrowthSteps(UCStrings.DRYFARMLAND, new GrowthSteps.DryFarmland(), 2));
		steps.add(new GrowthSteps(UCStrings.HASTORCH, new GrowthSteps.HasTorch(), 3));
		steps.add(new GrowthSteps(UCStrings.HELLWORLD, new GrowthSteps.HellWorld(), 4));
		steps.add(new GrowthSteps(UCStrings.DARKNESS, new GrowthSteps.LikesDarkness(), 5));
		steps.add(new GrowthSteps(UCStrings.LIKESHEIGHTS, new GrowthSteps.LikesHeights(), 6));
		steps.add(new GrowthSteps(UCStrings.UNDERFARMLAND, new GrowthSteps.UnderFarmland(), 7));
		steps.add(new GrowthSteps(UCStrings.LILYPADS, new GrowthSteps.LikesLilypads(), 8));
		steps.add(new GrowthSteps(UCStrings.HUNGRYPLANT, new GrowthSteps.HungryPlant(), 9));
		steps.add(new GrowthSteps(UCStrings.THIRSTYPLANT, new GrowthSteps.ThirstyPlant(), 10));
		steps.add(new GrowthSteps(UCStrings.LIKESCHICKEN, new GrowthSteps.LikesChicken(), 11));
		steps.add(new GrowthSteps(UCStrings.REDSTONE, new GrowthSteps.RequiresRedstone(), 12));
		steps.add(new GrowthSteps(UCStrings.VAMPIREPLANT, new GrowthSteps.VampirePlant(), 13));
		steps.add(new GrowthSteps(UCStrings.FULLBRIGHTNESS, new GrowthSteps.FullBrightness(), 14));
		steps.add(new GrowthSteps(UCStrings.LIKESWARTS, new GrowthSteps.LikesWarts(), 15));
		steps.add(new GrowthSteps(UCStrings.LIKESCOOKING, new GrowthSteps.LikesCooking(), 16));
		steps.add(new GrowthSteps(UCStrings.LIKESBREWING, new GrowthSteps.LikesBrewing(), 17));
		steps.add(new GrowthSteps(UCStrings.LIKESCHECKERS, new GrowthSteps.CheckerBoard(), 18));
		steps.add(new GrowthSteps(UCStrings.SELFSACRIFICE, new GrowthSteps.SacrificeSelf(), 19));
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsFeroxia;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
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
		
		return EnumItems.ESSENCE.ordinal();
	}
	
	public boolean isFullyGrown(World world, BlockPos pos, IBlockState state) {
		
		return this.getAge(state) >= this.getMaxAge();
	}
	
	@Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
       
    	IBlockState soil = worldIn.getBlockState(pos.down());
        return soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
    }
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (this.getAge(state) >= getMaxAge() || world.isRemote)
			return;
		
		if (!this.canBlockStay(world, pos, state)) {
			EntityItem ei = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, new ItemStack(this.getSeed()));
			world.spawnEntityInWorld(ei);
			world.setBlockToAir(pos);
			return;
		}
		TileEntity tile = world.getTileEntity(pos);
		if (tile == null || (tile != null && !(tile instanceof TileFeroxia)))
			return;
		
		TileFeroxia te = (TileFeroxia)tile;
		if (te.getOwner() == null || (te.getOwner() != null && UCUtils.getPlayerFromUUID(te.getOwner().toString()) == null))
			return;
		
		NBTTagList taglist = UCUtils.getServerTaglist(UCUtils.getPlayerFromUUID(te.getOwner().toString()).getEntityId());
		if (taglist == null)
			return;
		NBTTagCompound tag = taglist.getCompoundTagAt(this.getAge(state));
		int stage = tag.getInteger("stage" + this.getAge(state));
		
		if (!steps.get(stage).getCondition().canAdvance(world, pos, state)) {
			return;
		}
        int i = this.getAge(state);

        if (i < this.getMaxAge()) {
            float f = getGrowthChance(this, world, pos);
            if (rand.nextInt((int)(25.0F / f) + 1) == 0)
            	world.setBlockState(pos, this.withAge(i + 1), 2);
        }
	}
	
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    
    	world.setBlockState(pos, state.getBlock().getDefaultState(), 2);
    	TileEntity te = world.getTileEntity(pos);
    	if (te != null && te instanceof TileFeroxia) {
    		if (placer instanceof EntityPlayer && !(placer instanceof FakePlayer))
    		{
        		((TileFeroxia)te).setOwner(placer.getUniqueID());
        		if (!world.isRemote && !placer.getEntityData().hasKey(GrowthSteps.TAG_GROWTHSTAGES)) {
        			GrowthSteps.generateSteps((EntityPlayer)placer);
        		}
    		}
    	}
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileFeroxia();
	}
}
