package com.bafomdad.uniquecrops.crops;

import java.util.Iterator;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class Petramia extends BlockCropsBase {
	
	private final String PICKUP = "mobGriefing";
	private int range = 3;

	public Petramia() {
		
		super(EnumCrops.BEDROCKIUM);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsPetramia;
	}
	
	@Override
	public Item getCrop() {
		
		return Items.AIR;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
//		if (!world.isRemote) {
//			if (!player.getHeldItemMainhand().isEmpty()) return false;
//			if (getAge(state) >= getMaxAge()) {
//				boolean flag = world.getGameRules().getBoolean(PICKUP);
//				ItemStack stacky = (flag) ? new ItemStack(Blocks.OBSIDIAN) : new ItemStack(UCBlocks.darkBlock);
//				if (stacky.getItem() == Item.getItemFromBlock(UCBlocks.darkBlock) && world.rand.nextInt(5) != 0)
//					stacky = new ItemStack(Blocks.OBSIDIAN);
//				InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, stacky);
//				world.setBlockState(pos, this.withAge(0), 3);
//				return true;
//			}
//		}
//		return false;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (pos.getY() >= 10) return;
		
		if (getAge(state) >= getMaxAge() /*&& world.getGameRules().getBoolean(PICKUP)*/) {
			transformBedrock(world, pos);
		}
		super.updateTick(world, pos, state, rand);
	}
	
	private boolean transformBedrock(World world, BlockPos pos) {
		
		Iterable<BlockPos> poslist = BlockPos.getAllInBox(pos.add(-range, -range, -range), pos.add(range, 0, range));
		Iterator posit = poslist.iterator();
		while (posit.hasNext()) {
			BlockPos looppos = (BlockPos)posit.next();
			if (!world.isAirBlock(looppos) && world.getBlockState(looppos).getBlock() == ((UCConfig.convertObsidian) ? Blocks.OBSIDIAN: Blocks.BEDROCK)) {
//				if (looppos.getY() <= 1) continue;
				if (world.rand.nextBoolean()) {
					world.setBlockState(looppos, UCBlocks.darkBlock.getDefaultState(), 2);
					UCPacketHandler.sendToNearbyPlayers(world, looppos, new PacketUCEffect(EnumParticleTypes.CLOUD, looppos.getX(), looppos.getY(), looppos.getZ(), 6));
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean canBonemeal(World world, BlockPos pos) {
		
		return pos.getY() < 10;
	}
}
