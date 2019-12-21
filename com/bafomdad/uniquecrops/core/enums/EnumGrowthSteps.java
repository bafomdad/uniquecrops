package com.bafomdad.uniquecrops.core.enums;

import java.util.Iterator;
import java.util.List;

import com.bafomdad.uniquecrops.blocks.tiles.TileFeroxia;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.UCConfig.GrowthConfig;
import com.bafomdad.uniquecrops.crops.Feroxia;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

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

public enum EnumGrowthSteps {
	
	MOONPHASE(UCStrings.MOON, UCConfig.GrowthConfig.moonPhase) {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			return world.getCurrentMoonPhaseFactor() == 1.0F;
		}
	},
	HASTORCH(UCStrings.HASTORCH, UCConfig.GrowthConfig.hasTorch) {

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
					if ((!hand.isEmpty() && hand.getItem() == Item.getItemFromBlock(Blocks.TORCH)) || (!offhand.isEmpty() && offhand.getItem() == Item.getItemFromBlock(Blocks.TORCH)))
						return true;
				}
			}
			return false;
		}
	},
	LIKESDARKNESS(UCStrings.DARKNESS, UCConfig.GrowthConfig.likesDarkness) {

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			return world.getLightFromNeighbors(pos.up()) < 3 && world.isAirBlock(pos.up());
		}
	},
	DRYFARMLAND(UCStrings.DRYFARMLAND, UCConfig.GrowthConfig.dryFarmland) {
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {
			
			return world.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND && world.getBlockState(pos.down()).getValue(BlockFarmland.MOISTURE) < 7;
		}
	},
	UNDERFARMLAND(UCStrings.UNDERFARMLAND, UCConfig.GrowthConfig.underFarmland) {
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			return world.getBlockState(pos.up()).getBlock() == Blocks.FARMLAND;
		}
	},
	BURNINGPLAYER(UCStrings.BURNINGPLAYER, UCConfig.GrowthConfig.burningPlayer) {
		
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
	},
	HELLWORLD(UCStrings.HELLWORLD, UCConfig.GrowthConfig.hellWorld) {
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			return world.provider.getDimension() == -1;
		}
	},
	LIKESHEIGHTS(UCStrings.LIKESHEIGHTS, UCConfig.GrowthConfig.likesHeights) {
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			return pos.getY() > 100;
		}
	},
	LIKESLILYPADS(UCStrings.LILYPADS, UCConfig.GrowthConfig.likesLilypads) {
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			int lilypads = 0;
			for (EnumFacing facing : EnumFacing.HORIZONTALS) {
				Block lilypad = world.getBlockState(pos.offset(facing)).getBlock();
				if (lilypad != null && (lilypad == Blocks.WATERLILY || lilypad == UCBlocks.lavaLily))
					lilypads++;
			}
			return lilypads >= 4;
		}
	},
	THIRSTYPLANT(UCStrings.THIRSTYPLANT, UCConfig.GrowthConfig.thirstyPlant) {
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)));
			for (EntityItem item : items) {
				if (!item.isDead && !item.getItem().isEmpty()) {
					if (item.getItem().getItem() == Items.WATER_BUCKET)
					{
						UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.WATER_DROP, pos.getX(), pos.getY(), pos.getZ(), 6));
						item.setItem(new ItemStack(Items.BUCKET, 1, 0));
						return true;
					}
				}
			}
			return false;
		}
	},
	HUNGRYPLANT(UCStrings.HUNGRYPLANT, UCConfig.GrowthConfig.hungryPlant) {
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)));
			for (EntityItem item : items) {
				if (!item.isDead && !item.getItem().isEmpty()) {
					if (item.getItem().getItem() instanceof ItemFood) {
						UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 6));
						item.getItem().shrink(1);
						if (item.getItem().getCount() <= 0)
							item.setDead();
						return true;
					}
				}
			}
			return false;
		}
	},
	LIKESCHICKEN(UCStrings.LIKESCHICKEN, UCConfig.GrowthConfig.likesChicken) {
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {
			
			Entity item = null, chicken = null;
			List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.add(-3, 0, -3), pos.add(3, 1, 3)));
			for (Entity ent : entities) {
				if (!ent.isDead) {
					if (ent instanceof EntityChicken)
						chicken = ent;
					if (ent instanceof EntityItem && ((EntityItem)ent).getItem().getItem() == Items.BOWL)
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
						EntityItem ei = new EntityItem(item.world, item.posX, item.posY, item.posZ, new ItemStack(UCItems.teriyaki));
						((EntityItem)item).getItem().shrink(1);
						if (((EntityItem)item).getItem().getCount() <= 0)
							item.setDead();
						chicken.setDead();
						UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.EXPLOSION_NORMAL, chicken.posX, chicken.posY + 0.5D, chicken.posZ, 3));
						if (!world.isRemote)
							world.spawnEntity(ei);
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
	},
	REQUIRESREDSTONE(UCStrings.REDSTONE, UCConfig.GrowthConfig.likesRedstone) { 
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			for (EnumFacing facing : EnumFacing.VALUES) {
				int powersignal = world.getRedstonePower(pos.offset(facing), facing);
				if (powersignal >= 8)
					return true;
			}
			return false;
		}
	},
	VAMPIREPLANT(UCStrings.VAMPIREPLANT, UCConfig.GrowthConfig.vampirePlant) {
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			int sunlight = world.getLightFor(EnumSkyBlock.SKY, pos);
			return sunlight == 0;
		}
	},
	FULLBRIGHTNESS(UCStrings.FULLBRIGHTNESS, UCConfig.GrowthConfig.fullBrightness) {
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			int light = world.getLightFor(EnumSkyBlock.BLOCK, pos.up());
			return light >= 13;
		}
	},
	LIKESWARTS(UCStrings.LIKESWARTS, UCConfig.GrowthConfig.likesWarts) {
		
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
	},
	LIKESCOOKING(UCStrings.LIKESCOOKING, UCConfig.GrowthConfig.likesCooking) {
		
		int range = 6;

		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			Iterable<BlockPos> poslist = BlockPos.getAllInBox(pos.add(-range, -2, -range), pos.add(range, 2, range));
			Iterator iter = poslist.iterator();
			while (iter.hasNext()) {
				BlockPos posit = (BlockPos)iter.next();
				TileEntity tile = world.getTileEntity(posit);
				if (tile instanceof TileEntityFurnace) {
					return ((TileEntityFurnace)tile).getField(2) > 0;
				}
			}
			return false;
		}
	},
	LIKESBREWING(UCStrings.LIKESBREWING, UCConfig.GrowthConfig.likesBrewing) {
		
		int range = 6;
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {
			
			Iterable<BlockPos> poslist = BlockPos.getAllInBox(pos.add(-range, -2, -range), pos.add(range, 2, range));
			Iterator iter = poslist.iterator();
			while (iter.hasNext()) {
				BlockPos posit = (BlockPos)iter.next();
				TileEntity tile = world.getTileEntity(posit);
				if (tile instanceof TileEntityBrewingStand) {
					return ((TileEntityBrewingStand)tile).getField(0) > 0;
				}
			}
			return false;
		}
	},
	CHECKERBOARD(UCStrings.LIKESCHECKERS, UCConfig.GrowthConfig.likesCheckers) {
		
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
	},
	NOBONEMEAL(UCStrings.DONTBONEMEAL, UCConfig.GrowthConfig.dontBonemeal) {
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			return true;
		}
	},
	SELFSACRIFICE(UCStrings.SELFSACRIFICE, UCConfig.GrowthConfig.selfSacrifice) {
		
		@Override
		public boolean canAdvance(World world, BlockPos pos, IBlockState state) {

			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileFeroxia) {
				TileFeroxia te = (TileFeroxia)tile;
				EntityPlayer player = UCUtils.getPlayerFromUUID(te.getOwner().toString());
				if (!world.isRemote && player != null && world.getPlayerEntityByUUID(te.getOwner()) != null) {
					NBTTagCompound tag = player.getEntityData();
					if (!tag.hasKey("hasSacrificed")) {
						player.sendMessage(new TextComponentString(TextFormatting.RED + "The savage plant whispers: \"The time is right to perform a self sacrifice.\""));
						tag.setBoolean("hasSacrificed", false);
						return false;
					}
					if (tag.hasKey("hasSacrificed") && tag.getBoolean("hasSacrificed")) {
						tag.removeTag("hasSacrificed");
						world.setBlockState(pos, UCBlocks.cropFeroxia.withAge(7), 2);
						UCUtils.generateSteps(player);
						return false;
					}
				}
			}
			return false;
		}
	};

	private final String desc;
	private final boolean enabled;
	
	private EnumGrowthSteps(String desc, boolean enabled) {
		
		this.desc = desc;
		this.enabled = enabled;
	}
	
	public String getDescription() {
		
		return this.desc;
	}
	
	public boolean isEnabled() {
		
		return this.enabled;
	}
	
	public TileFeroxia getTile(World world, BlockPos pos) {
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileFeroxia)
			return (TileFeroxia)tile;
		
		return null;
	}
	
	public abstract boolean canAdvance(World world, BlockPos pos, IBlockState state);
}
