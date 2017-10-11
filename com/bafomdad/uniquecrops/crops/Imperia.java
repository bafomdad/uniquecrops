package com.bafomdad.uniquecrops.crops;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.data.UCDataHandler;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class Imperia extends BlockCropsBase {

	public Imperia() {
		
		super(EnumCrops.IMPERIA, false, UCConfig.cropImperia);
		this.clickHarvest = false;
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsImperia;
	}
	
	@Override
	public Item getCrop() {
		
		return Item.getItemFromBlock(Blocks.AIR);
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (world.getDifficulty() != EnumDifficulty.PEACEFUL) {
			if (getAge(state) >= getMaxAge()) {
				if (UCDataHandler.getInstance().getChunkInfo(world.provider.getDimension()).size() <= 0)
					UCDataHandler.getInstance().addChunk(world.provider.getDimension(), new ChunkPos(pos), true);
				return;
			}

			String[] mobList = new String[] { "minecraft:witch", "minecraft:skeleton", "minecraft:zombie", "minecraft:spider" };
			Entity randomMob = EntityList.createEntityByIDFromName(new ResourceLocation(mobList[rand.nextInt(mobList.length)]), world);
			randomMob.setPosition(pos.getX(), pos.getY() + 0.5D, pos.getZ());
			NBTTagCompound entityTag = randomMob.getEntityData();
			entityTag.setTag("ImperiaPosTag", NBTUtil.createPosTag(pos));
			entityTag.setInteger("ImperiaStage", getAge(state));
			if (!world.isRemote)
				world.spawnEntity(randomMob);
			return;
		}
		super.updateTick(world, pos, state, rand);
	}
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    
		ChunkPos cPos = new ChunkPos(pos);
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				ChunkPos loopPos = new ChunkPos(cPos.x + i, cPos.z + j);
				UCDataHandler.getInstance().removeChunk(world.provider.getDimension(), loopPos, true);
			}
		}
		super.breakBlock(world, pos, state);
    }
	
	public void advanceStage(World world, BlockPos pos, IBlockState state, int stage) {
	
		if (getAge(state) >= getMaxAge() || stage != getAge(state)) return;
		
		if (getAge(state) + 1 >= getMaxAge()) {
			ChunkPos cPos = new ChunkPos(pos);
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					ChunkPos loopPos = new ChunkPos(cPos.x + i, cPos.z + j);
					UCDataHandler.getInstance().addChunk(world.provider.getDimension(), loopPos, true);
				}
			}
		}
		UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 6));
		world.setBlockState(pos, this.withAge(getAge(state) + 1), 3);
	}
}
