package com.bafomdad.uniquecrops.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockTotemhead extends Block {
	
	protected static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 1.75F, 0.75F);
	private int range = 12;

	public BlockTotemhead() {
		
		super(Material.WOOD);
		setRegistryName("totemhead");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".totemhead");
		setCreativeTab(UniqueCrops.TAB);
		setHardness(2.0F);
		setResistance(20.0F);
		setTickRandomly(true);
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
	}
	
	@Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB aabb, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity) {
        
    	addCollisionBoxToList(pos, aabb, collidingBoxes, BASE_AABB);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
       
    	return BASE_AABB;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        
    	return false;
    }
    
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		
		return false;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		List<EntityLivingBase> elb = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.add(-range, -3, -range), pos.add(range, 3, range)));
		for (EntityLivingBase entity : elb) {
			if (!entity.isDead && (entity instanceof EntityMob || entity instanceof EntitySlime) && !world.isRemote) {
				entity.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 500));
			}
		}
		world.scheduleUpdate(pos, this, 100);
	}
}
