package com.bafomdad.uniquecrops.crops;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.enums.EnumCrops;
import com.bafomdad.uniquecrops.entities.EntityBattleCrop;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class DonutSteel extends BlockCropsBase {

	public DonutSteel() {
		
		super(EnumCrops.ORIGINAL);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsDonuts;
	}
	
	@Override
	public Item getCrop() {
		
		return Items.AIR;
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		
		if (player != null && this.getAge(state) >= getMaxAge()) {
			EntityBattleCrop ent = new EntityBattleCrop(world, pos);
			if (!world.isRemote) {
				world.spawnEntity(ent);
			}
			UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.CLOUD, pos.getX(), pos.getY() + 0.3D, pos.getZ(), 6));
			return world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
		}
        this.onBlockHarvested(world, pos, state, player);
        return world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
	}
}
