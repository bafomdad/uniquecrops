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
import net.minecraft.entity.EnumCreatureType;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void imperiaDenySpawn(LivingSpawnEvent.CheckSpawn event) {
		
		if (event.getResult() == Event.Result.ALLOW) return;
		
		ChunkPos cPos = new ChunkPos(event.getEntityLiving().getPosition());
		if (!event.getWorld().isRemote && !event.isSpawner() && event.getEntityLiving().isCreatureType(EnumCreatureType.MONSTER, false) && UCDataHandler.getInstance().getChunkInfo(event.getWorld().provider.getDimension()).contains(cPos)) {
			event.setResult(Event.Result.DENY);
		}
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
    public int getLightValue(IBlockState state) {
		
		if (getAge(state) >= getMaxAge())
			return 15;
		
		return 0;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if (world.getDifficulty() != EnumDifficulty.PEACEFUL) {
			if (getAge(state) >= getMaxAge()) {
				setChunksAsNeeded(world, pos, false);
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
    
		setChunksAsNeeded(world, pos, true);
		super.breakBlock(world, pos, state);
    }
	
	public void advanceStage(World world, BlockPos pos, IBlockState state, int stage) {
	
		if (getAge(state) >= getMaxAge() || stage != getAge(state)) return;
		
		if (getAge(state) + 1 >= getMaxAge())
			setChunksAsNeeded(world, pos, false);
		
		UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 6));
		world.setBlockState(pos, this.withAge(getAge(state) + 1), 3);
	}
	
	public void setChunksAsNeeded(World world, BlockPos pos, boolean remove) {
		
		ChunkPos cPos = new ChunkPos(pos);
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				ChunkPos loopPos = new ChunkPos(cPos.x + i, cPos.z + j);
				if (remove)
					UCDataHandler.getInstance().removeChunk(world.provider.getDimension(), loopPos, true);
				else
					UCDataHandler.getInstance().addChunk(world.provider.getDimension(), loopPos, true);
			}
		}
	}
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	this.createParticles(state, world, pos, rand, EnumParticleTypes.END_ROD, 0);
    }
}
