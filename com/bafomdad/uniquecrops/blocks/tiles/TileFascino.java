package com.bafomdad.uniquecrops.blocks.tiles;

import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import com.bafomdad.uniquecrops.UniqueCropsAPI;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.crafting.EnchantmentRecipe;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.ItemWildwoodStaff;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class TileFascino extends TileBaseRenderUC implements ITickable {
	
	private final BlockPos[] ENCHPOS = new BlockPos[] {
		new BlockPos(0, 0, 3), new BlockPos(0, 0, -3), new BlockPos(3, 0, 0), new BlockPos(-3, 0, 0),
		new BlockPos(2, 0, 2), new BlockPos(-2, 0, -2), new BlockPos(2, 0, -2), new BlockPos(-2, 0, 2)
	};
	
	private ItemStackHandler inv = new ItemStackHandler(5) {
	    @Override
	    public int getSlotLimit(int slot) {
	        return 1;
	    }
	};
	
	private final int RANGE = 7;
	private Stage stage = Stage.IDLE;
	private UUID enchanterId;
	private int enchantingTicks = 0;
	private int enchantmentCost = 7;
	private boolean showMissingCrops;

	@Override
	public void update() {

		if (showMissingCrops)
			loopMissingCrops();
		
		if (stage == Stage.IDLE) return;
		
		enchantingTicks++;
		stage.advance(this);
	}
	
	public void loopMissingCrops() {
		
		for (int i = 0; i < ENCHPOS.length; i++) {
			BlockPos loopPos = pos.add(ENCHPOS[i]);
			IBlockState loopState = world.getBlockState(loopPos);
			if (loopState.getBlock() != UCBlocks.cropHexis) {
				UCPacketHandler.sendToNearbyPlayers(world, loopPos, new PacketUCEffect(EnumParticleTypes.SMOKE_NORMAL, loopPos.getX() - 0.5, loopPos.getY() + 0.1, loopPos.getZ() - 0.5, 4));
			}
		}
	}
	
	public void checkEnchants(EntityPlayer player, ItemStack staff) {
		
		ItemStack heldItem = ItemStack.EMPTY;
		this.showMissingCrops = false;
		for (ItemStack stack : player.getHeldEquipment()) {
			if (!stack.isEmpty() && stack.getItem().isEnchantable(stack) && stack.getItem() != UCItems.wildwoodStaff) {
				heldItem = stack;
				break;
			}
		}
		if (!heldItem.isEmpty()) {
			EnchantmentRecipe recipe = UniqueCropsAPI.ENCHANTER_REGISTRY.findRecipe(inv);
			if (recipe != null) {
				if (EnchantmentHelper.getEnchantments(heldItem).containsKey(recipe.getEnchantment())) {
					player.sendStatusMessage(new TextComponentTranslation("uniquecrops.enchanting.enchantmentexists"), true);
					return;
				}
				prepareEnchanting(player, heldItem.getEnchantmentTagList().tagCount() + 1, staff, recipe.getCost());
				return;
			} else {
				player.sendStatusMessage(new TextComponentTranslation("uniquecrops.enchanting.unknownrecipe"), true);
			}
		} else {
			player.sendStatusMessage(new TextComponentTranslation("uniquecrops.enchanting.unenchantable"), true);
		}
	}
	
	private void prepareEnchanting(EntityPlayer player, int enchantmentSize, ItemStack staff, int powerCost) {
		
		int maxGrowth = 7;
		for (int i = 0; i < ENCHPOS.length; i++) {
			BlockPos loopPos = pos.add(ENCHPOS[i]);
			IBlockState loopState = world.getBlockState(loopPos);
			if (loopState.getBlock() == UCBlocks.cropHexis) {
				int age = loopState.getValue(BlockCrops.AGE);
				if (age < maxGrowth)
					maxGrowth = age;
			}
			else {
				player.sendStatusMessage(new TextComponentTranslation("uniquecrops.enchanting.missingcrops"), true);
				this.showMissingCrops = true;
				return;
			}
		}
		if (maxGrowth < enchantmentSize) {
			player.sendStatusMessage(new TextComponentTranslation("uniquecrops.enchanting.cropgrowth" + " " +  enchantmentSize), true);
			return;
		}
		if (!ItemWildwoodStaff.adjustPower(staff, powerCost)) {
			player.sendStatusMessage(new TextComponentTranslation("uniquecrops.enchanting.notenoughpower" + " " + powerCost), true);
			return;
		}
		stage = Stage.PREPARE;
		enchantmentCost = enchantmentSize;
		enchanterId = player.getUniqueID();
		this.markBlockForUpdate();
		this.markDirty();
	}
	
	private EntityPlayer getEnchanter() {
		
		List<EntityPlayer> playerList = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.add(-RANGE, -1, -RANGE), pos.add(RANGE, 2, RANGE)));
		for (EntityPlayer player : playerList) {
			if (player.getUniqueID().equals(enchanterId)) {
				return player;
			}
		}
		return null;
	}
	
	public void advanceEnchanting() {
		
		EntityPlayer player = getEnchanter();
		if (player == null) {
			advanceStage();
		}
		for (int i = 0; i < ENCHPOS.length; i++) {
			BlockPos loopPos = pos.add(ENCHPOS[i]);
			IBlockState loopState = world.getBlockState(loopPos);
			if (loopState.getBlock() == UCBlocks.cropHexis) {
				int age = loopState.getValue(BlockCrops.AGE);
				if (age < (7 - enchantmentCost)) {
					finishEnchanting();
					break;
				}
				world.playEvent(2001, loopPos, Block.getStateId(loopState));
				world.setBlockState(loopPos, loopState.withProperty(BlockCrops.AGE, Math.max(age - 1, 0)));
			}
			else {
				advanceStage();
				break;
			}
		}
	}
	
	private void finishEnchanting() {
		
		if (enchantingTicks < 80) return;
		
		EntityPlayer player = getEnchanter();
		if (player == null) {
			advanceStage();
			return;
		}
		ItemStack heldItem = ItemStack.EMPTY;
		for (ItemStack stack : player.getHeldEquipment()) {
			if (!stack.isEmpty() && stack.getItem().isEnchantable(stack) && stack.getItem() != UCItems.wildwoodStaff) {
				heldItem = stack;
				break;
			}
		}
		if (heldItem.isEmpty()) {
			advanceStage();
			return;
		}
		EnchantmentRecipe recipe = UniqueCropsAPI.ENCHANTER_REGISTRY.findRecipe(inv);
		if (recipe != null) {
			recipe.applyEnchantment(heldItem);
			this.clearInv();
			advanceStage();
			world.playEvent(2004, getPos().add(0, 0.7, 0), 0);
		} else {
			advanceStage();
		}
	}
	
	public void loopEffects() {
		
		for (int i = 0; i < ENCHPOS.length; i++) {
			BlockPos loopPos = pos.add(ENCHPOS[i]);
			IBlockState loopState = world.getBlockState(loopPos);
			if (loopState.getBlock() == UCBlocks.cropHexis) {
				int size = 4;
				for (int j = 0; j < size; ++j) {
					for (int k = 0; k < size; ++k) {
						double x = ((double)j + 0.5D) / 4.0D;
						double z = ((double)k + 0.5D) / 4.0D;
						
						world.spawnParticle(EnumParticleTypes.BLOCK_DUST, (double)loopPos.getX() + x, (double)loopPos.getY() + loopState.getBoundingBox(world, loopPos).maxY, (double)loopPos.getZ() + z, ((double)this.getPos().getX() - loopPos.getX()) / 8, 0, ((double)this.getPos().getZ() - loopPos.getZ()) / 8, Block.getStateId(loopState));
					}
				}
			}
		}
	}
	
	public IItemHandler getInventory() {
		
		return this.inv;
	}
	
	private void clearInv() {
		
		for (int i = 0; i < inv.getSlots(); i++) {
			inv.setStackInSlot(i, ItemStack.EMPTY);
		}
	}
	
	public Stage getStage() {
		
		return this.stage;
	}
	
	public int getEnchantingTicks() {
		
		return this.enchantingTicks;
	}
	
	public void advanceStage() {
		
		int mod = Math.floorMod(stage.ordinal() + 1, Stage.values().length);
		stage = Stage.values()[mod];
		this.enchantingTicks = 0;
		this.markBlockForUpdate();
		this.markDirty();
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		tag.setTag("inventory", inv.serializeNBT());
		tag.setInteger(UCStrings.TAG_ENCHANTSTAGE, stage.ordinal());
		if (enchanterId != null)
			tag.setString("UC:targetEnchanter", enchanterId.toString());
		else
			tag.removeTag("UC:targetEnchanter");
		tag.setInteger(UCStrings.TAG_ENCHANT_TIMER, this.enchantingTicks);
		tag.setInteger(UCStrings.TAG_ENCHANT_COST, this.enchantmentCost);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		inv.deserializeNBT(tag.getCompoundTag("inventory"));
		stage = Stage.values()[tag.getInteger(UCStrings.TAG_ENCHANTSTAGE)];
		if (tag.hasKey("UC:targetEnchanter"))
			enchanterId = UUID.fromString(tag.getString("UC:targetEnchanter"));
		enchantingTicks = tag.getInteger(UCStrings.TAG_ENCHANT_TIMER);
		enchantmentCost = tag.getInteger(UCStrings.TAG_ENCHANT_COST);
	}
	
	public enum Stage {
		IDLE,
		PREPARE {
			@Override
			public void advance(TileFascino tile) {
				
				if (tile.enchantingTicks >= 20)
					tile.advanceStage();
			}
		},
		ENCHANT {
			@Override
			public void advance(TileFascino tile) {
				
				if (tile.enchantingTicks % 4 == 0)
					tile.loopEffects();
				if (tile.enchantingTicks % 20 == 0)
					tile.advanceEnchanting();
			}
		},
		STOP {
			@Override
			public void advance(TileFascino tile) {
				
				tile.enchanterId = null;
				tile.advanceStage();
			}
		};
		
		public void advance(TileFascino tile) {}
	}
}
