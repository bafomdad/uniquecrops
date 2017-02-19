package com.bafomdad.uniquecrops.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.blocks.tiles.TileFeroxia;
import com.bafomdad.uniquecrops.crops.Feroxia;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class GrowthSteps {
	
	private String desc;
	private Condition cond;
	private int index;
	private static Random rand = new Random();
	public static String TAG_GROWTHSTAGES = "UC_FeroxiaGrowth";

	public GrowthSteps(String desc, Condition cond, int index) {
		
		this.desc = desc;
		this.cond = cond;
		this.index = index;
	}
	
	public String getDescription() {
		
		return desc;
	}
	
	public Condition getCondition() {
		
		return cond;
	}
	
	public int getIndex() {
		
		return index;
	}
	
	public static void generateSteps(EntityPlayer player) {
		
		NBTTagCompound tag = player.getEntityData();
		if (rand.nextInt(200) == 0) {
			if (tag.hasKey(TAG_GROWTHSTAGES))
				tag.removeTag(TAG_GROWTHSTAGES);
			
			NBTTagList taglist = new NBTTagList();
			NBTTagCompound tag2 = new NBTTagCompound();
			tag2.setInteger("stage0", 19);
			taglist.appendTag(tag2);
			tag.setTag(TAG_GROWTHSTAGES, taglist);
			return;
		}
		List<GrowthSteps> copysteps = new ArrayList<GrowthSteps>();
		for (int k = 0; k < Feroxia.steps.size(); k++) {
			copysteps.add(Feroxia.steps.get(k));
		}
		copysteps.remove(copysteps.size() - 1);
		NBTTagList taglist = new NBTTagList();
		for (int i = 0; i < 7; ++i) {
			NBTTagCompound tag2 = new NBTTagCompound();
			Collections.shuffle(copysteps);
			int index = copysteps.get(0).getIndex();
			copysteps.remove(0);
			tag2.setInteger("stage" + i, index);
			taglist.appendTag(tag2);
		}
		tag.setTag(TAG_GROWTHSTAGES, taglist);
		UCUtils.updateBook(player);
	}
	
	public static TileFeroxia getTile(World world, BlockPos pos) {
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile != null && tile instanceof TileFeroxia)
			return (TileFeroxia)tile;
		
		return null;
	}
	
	public static interface Condition {
		
		public boolean canAdvance(World world, BlockPos pos, IBlockState state);
	}
	
	public static class MoonPhase implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {
			
			return world.getCurrentMoonPhaseFactor() == 1.0F;
		}
	}
	
	public static class HasTorch implements Condition {
		
		int range = 10;

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			if (getTile(world, pos) == null)
				return false;
			
			List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(-range, -range, -range), pos.add(range, range, range)));
			for (EntityPlayer player : players) {
				if (player.getUniqueID().equals(getTile(world, pos).getOwner())) {
					ItemStack hand = player.getHeldItemMainhand();
					ItemStack offhand = player.getHeldItemOffhand();
					if ((hand != null && hand.getItem() == Item.getItemFromBlock(Blocks.TORCH)) || (offhand != null && offhand.getItem() == Item.getItemFromBlock(Blocks.TORCH)))
						return true;
				}
			}
			return false;
		}
	}
	
	public static class LikesDarkness implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			return world.getLightFromNeighbors(pos.up()) < 3 && world.isAirBlock(pos.up());
		}
	}
	
	public static class DryFarmland implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			return world.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND && ((BlockFarmland)world.getBlockState(pos.down()).getBlock()).getMetaFromState(world.getBlockState(pos.down())) == 0;
		}
	}
	
	public static class UnderFarmland implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			return world.getBlockState(pos.up()).getBlock() == Blocks.FARMLAND;
		}
	}
	
	public static class BurningPlayer implements Condition {

		int range = 10;
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			if (getTile(world, pos) == null)
				return false;
			
			List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(-range, -range, -range), pos.add(range, range, range)));
			for (EntityPlayer player : players) {
				if (player.getUniqueID().equals(getTile(world, pos).getOwner())) {
					if (player.isBurning() && !player.isImmuneToFire())
						return true;
				}
			}
			return false;
		}
	}
	
	public static class HellWorld implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			return world.provider.getDimension() == -1;
		}
	}
	
	public static class LikesHeights implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			return pos.getY() > 100;
		}
	}
	
	public static class LikesLilypads implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			int lilypads = 0;
			for (EnumFacing facing : EnumFacing.HORIZONTALS) {
				Block lilypad = world.getBlockState(pos.offset(facing)).getBlock();
				if (lilypad != null && (lilypad == Blocks.WATERLILY || lilypad == UCBlocks.lavalily))
					lilypads++;
			}
			return lilypads >= 4;
		}
	}
	
	public static class ThirstyPlant implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)));
			for (EntityItem item : items) {
				if (!item.isDead && item.getEntityItem() != null) {
					if (item.getEntityItem().getItem() == Items.WATER_BUCKET)
					{
						UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.WATER_DROP, pos.getX(), pos.getY(), pos.getZ(), 6));
						item.setEntityItemStack(new ItemStack(Items.BUCKET, 1, 0));
						return true;
					}
				}
			}
			return false;
		}
	}
	
	public static class HungryPlant implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)));
			for (EntityItem item : items) {
				if (!item.isDead && item.getEntityItem() != null) {
					if (item.getEntityItem().getItem() instanceof ItemFood)
					{
						UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 6));
						item.getEntityItem().stackSize--;
						if (item.getEntityItem().stackSize <= 0)
							item.setDead();
						return true;
					}
				}
			}
			return false;
		}
	}
	
	public static class LikesChicken implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {
			
			Entity item = null, chicken = null;
			List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.add(-3, 0, -3), pos.add(3, 1, 3)));
			for (Entity ent : entities) {
				if (!ent.isDead) {
					if (ent instanceof EntityChicken)
						chicken = ent;
					if (ent instanceof EntityItem && ((EntityItem)ent).getEntityItem().getItem() == Items.BOWL)
						item = ent;
					if (item != null && chicken != null)
						break;
				}
			}
			if (chicken != null && item != null) {
				AxisAlignedBB aabb = new AxisAlignedBB(chicken.getPosition().add(0, 0, 0), chicken.getPosition().add(1, 1, 1));
				List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(chicken, aabb);
				for (Entity entity : list) {
					if (entity != null && entity == item) {
						EntityItem ei = new EntityItem(item.worldObj, item.posX, item.posY, item.posZ, new ItemStack(UCItems.teriyaki));
						((EntityItem)item).getEntityItem().stackSize--;
						if (((EntityItem)item).getEntityItem().stackSize <= 0)
							item.setDead();
						chicken.setDead();
						UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.EXPLOSION_NORMAL, chicken.posX, chicken.posY + 0.5D, chicken.posZ, 3));
						if (!world.isRemote)
							world.spawnEntityInWorld(ei);
						return true;
					}
				}
			}
			else if (chicken != null) {
				UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.HEART, chicken.posX, chicken.posY + 1D, chicken.posZ, 3));
				return true;
			}
			return false;
		}
	}
	
	public static class RequiresRedstone implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			for (EnumFacing facing : EnumFacing.VALUES) {
				int powersignal = world.getRedstonePower(pos.offset(facing), facing);
				if (powersignal >= 8)
					return true;
			}
			return false;
		}
	}
	
	public static class VampirePlant implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			int sunlight = world.getLightFor(EnumSkyBlock.SKY, pos);
			return sunlight == 0;
		}
	}
	
	public static class FullBrightness implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			return world.getLightFromNeighbors(pos) >= 13;
		}
	}
	
	public static class LikesWarts implements Condition {

		int range = 1;
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			int warts = 0;
			Iterable<BlockPos> poslist = BlockPos.getAllInBox(pos.add(-range, 0, -range), pos.add(range, 0, range));
			Iterator posit = poslist.iterator();
			while (posit.hasNext()) {
				BlockPos looppos = (BlockPos)posit.next();
				if (!world.isAirBlock(looppos) && world.getBlockState(looppos).getBlock() == Blocks.NETHER_WART) {
					warts++;
				}
			}
			return warts == 8;
		}
	}
	
	public static class LikesCooking implements Condition {
		
		int range = 6;

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			Iterable<BlockPos> poslist = BlockPos.getAllInBox(pos.add(-range, -2, -range), pos.add(range, 2, range));
			Iterator iter = poslist.iterator();
			while (iter.hasNext()) {
				BlockPos posit = (BlockPos)iter.next();
				TileEntity tile = world.getTileEntity(posit);
				if (tile != null && tile instanceof TileEntityFurnace) {
					return ((TileEntityFurnace)tile).getField(2) > 0;
				}
			}
			return false;
		}
	}
	
	public static class LikesBrewing implements Condition {

		int range = 6;
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {
			
			Iterable<BlockPos> poslist = BlockPos.getAllInBox(pos.add(-range, -2, -range), pos.add(range, 2, range));
			Iterator iter = poslist.iterator();
			while (iter.hasNext()) {
				BlockPos posit = (BlockPos)iter.next();
				TileEntity tile = world.getTileEntity(posit);
				if (tile != null && tile instanceof TileEntityBrewingStand) {
					return ((TileEntityBrewingStand)tile).getField(0) > 0;
				}
			}
			return false;
		}
	}
	
	public static class CheckerBoard implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			BlockPos[] neighbors = new BlockPos[] { pos.add(1, 0, 1), pos.add(-1, 0, -1), pos.add(1, 0, -1), pos.add(-1, 0, 1) };
			BlockPos[] noneighbors = new BlockPos[] { pos.offset(EnumFacing.EAST), pos.offset(EnumFacing.NORTH), pos.offset(EnumFacing.SOUTH), pos.offset(EnumFacing.WEST) };
			int crops = 0;
			for (BlockPos looppos1 : neighbors) {
				if (world.getBlockState(looppos1).getBlock() == UCBlocks.cropFeroxia)
					crops++;
			}
			for (BlockPos looppos2 : noneighbors) {
				if (world.getBlockState(looppos2).getBlock() == UCBlocks.cropFeroxia)
					crops--;
			}
			return crops == 4;
		}
	}
	
	public static class SacrificeSelf implements Condition {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			TileEntity tile = world.getTileEntity(pos);
			if (tile != null && tile instanceof TileFeroxia) {
				TileFeroxia te = (TileFeroxia)tile;
				EntityPlayer player = UCUtils.getPlayerFromUUID(te.getOwner().toString());
				if (!world.isRemote && player != null && world.getPlayerEntityByUUID(te.getOwner()) != null) {
					NBTTagCompound tag = player.getEntityData();
					if (!tag.hasKey("hasSacrificed"))
					{
						player.addChatMessage(new TextComponentString(TextFormatting.RED + "The savage plant whispers: \"The time is right to perform a self sacrifice.\""));
						tag.setBoolean("hasSacrificed", false);
						return false;
					}
					if (tag.hasKey("hasSacrificed") && tag.getBoolean("hasSacrificed"))
					{
						tag.removeTag("hasSacrificed");
						world.setBlockState(pos, ((Feroxia)state.getBlock()).withAge(7), 2);
						GrowthSteps.generateSteps(player);
						return false;
					}
				}
			}
			return false;
		}
	}
}
