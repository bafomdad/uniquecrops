package com.bafomdad.uniquecrops.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.bafomdad.uniquecrops.core.enums.EnumGrowthSteps;
import com.bafomdad.uniquecrops.core.enums.EnumItems;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketBiomeChange;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class UCUtils {
	
	public static EntityPlayer getPlayerFromUsername(String username) {
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			return null;
		
		return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(username);
	}

	public static EntityPlayer getPlayerFromUUID(String uuid) {
		
		return getPlayerFromUsername(getUsernameFromUUID(uuid));
	}
	
	public static String getUsernameFromUUID(String uuid) {
		
		return UsernameCache.getLastKnownUsername(UUID.fromString(uuid));
	}
	
	public static EntityLivingBase getTaggedEntity(UUID uuid) {
		
		if (uuid != null) {
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			for (WorldServer wServer : server.worlds) {
				for (Object obj : wServer.loadedEntityList) {
					if (obj instanceof EntityLivingBase) {
						EntityLivingBase living = (EntityLivingBase)obj;
						UUID id = living.getPersistentID();
						if (uuid.equals(id) && living.isEntityAlive())
							return living;
					}
				}
			}
		}
		return null;
	}
	
	public static EntityLivingBase getTaggedEntity(World world, BlockPos pos, UUID uuid, int range) {
		
		List<Entity> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.add(-range, 0, -range), pos.add(range, 2, range)));
		if (entities.isEmpty()) return null;
		
		for (Entity ent : entities) {
			if (ent instanceof EntityLivingBase) {
				EntityLivingBase elb = (EntityLivingBase)ent;
				UUID id = elb.getPersistentID();
				if (uuid.equals(id) && elb.isEntityAlive())
					return elb;
			}
		}
		return null;
	}
	
	public static NBTTagList getServerTaglist(int id) {
		
		MinecraftServer ms = FMLCommonHandler.instance().getMinecraftServerInstance();
		if (ms == null)
			return null;
		EntityPlayer player = (EntityPlayer)ms.getEntityWorld().getEntityByID(id);
		if (player != null) {
			NBTTagCompound tag = player.getEntityData();
			if (tag.hasKey(UCStrings.TAG_GROWTHSTAGES)) 
			{
				return tag.getTagList(UCStrings.TAG_GROWTHSTAGES, 10);
			}
		}
		return null;
	}
	
	public static void updateBook(EntityPlayer player) {
		
		if (!player.inventory.hasItemStack(EnumItems.GUIDE.createStack()))
			return;
		
		NBTTagList taglist = UCUtils.getServerTaglist(player.getEntityId());
		if (taglist != null) {
			for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
				ItemStack book = player.inventory.mainInventory.get(i);
				if (!book.isEmpty() && book.getItem() == UCItems.generic && book.getItemDamage() == EnumItems.GUIDE.ordinal()) 
					book.setTagInfo(UCStrings.TAG_GROWTHSTAGES, taglist);
			}
		}
	}
	
	public static TileEntity getClosestTile(Class tileToFind, World world, BlockPos pos, double dist) {
		
		TileEntity closest = null;
		for (TileEntity tile : world.loadedTileEntityList) {
			if (tile.getClass() == tileToFind && !pos.equals(tile.getPos())) {
				double distance = tile.getPos().distanceSq(pos);
				if (closest == null && distance <= dist) {
					closest = tile;
					break;
				}
			}
		}
		return closest;
	}
	
	public static void generateSteps(EntityPlayer player) {
		
		NBTTagCompound tag = player.getEntityData();
		if (player.getEntityWorld().rand.nextInt(200) == 0) {
			if (tag.hasKey(UCStrings.TAG_GROWTHSTAGES))
				tag.removeTag(UCStrings.TAG_GROWTHSTAGES);
			
			NBTTagList taglist = new NBTTagList();
			NBTTagCompound tag2 = new NBTTagCompound();
			tag2.setInteger("stage0", 20);
			taglist.appendTag(tag2);
			tag.setTag(UCStrings.TAG_GROWTHSTAGES, taglist);
			return;
		}
		List<EnumGrowthSteps> copysteps = new ArrayList();
		Arrays.stream(EnumGrowthSteps.values()).forEach(g -> {
			if (g.isEnabled() && g.ordinal() != EnumGrowthSteps.SELFSACRIFICE.ordinal())
				copysteps.add(g);
			});

		Collections.shuffle(copysteps);
		NBTTagList taglist = new NBTTagList();
		for (int i = 0; i < 7; ++i) {
			NBTTagCompound tag2 = new NBTTagCompound();
			int index = copysteps.get(0).ordinal();
			copysteps.remove(0);
			tag2.setInteger("stage" + i, index);
			taglist.appendTag(tag2);
		}
		tag.setTag(UCStrings.TAG_GROWTHSTAGES, taglist);
	}
	
	public static boolean setBiome(int biomeId, World world, BlockPos pos) {

		Biome biome = Biome.getBiome(biomeId);
		if (biome != null) {
			if (biome == world.getBiome(pos)) return false;

			byte[] biomeArray = world.getChunk(pos).getBiomeArray();
			int i = pos.getX() & 15;
			int j = pos.getZ() & 15;
			biomeArray[j << 4 | i] = (byte)biomeId;
			world.getChunk(pos).setBiomeArray(biomeArray);
			world.getChunk(pos).markDirty();
			world.markBlockRangeForRenderUpdate(pos, pos);
			if (!world.isRemote) {
				UCPacketHandler.INSTANCE.sendToAllAround(new PacketBiomeChange(i, pos.getY(), j, (short)biomeId), new NetworkRegistry.TargetPoint(world.provider.getDimension(), i, pos.getY(), j, 32.0D));
			}
		}
		return true;
	}
	
	public static <E> List<E> makeCollection(Iterable<E> iter, boolean shuffle) {
		
		List<E> list = new ArrayList<E>();
		iter.forEach(list::add);
		if (shuffle)
			Collections.shuffle(list);
		
		return list;
	}
}
