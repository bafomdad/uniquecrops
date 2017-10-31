package com.bafomdad.uniquecrops.blocks.tiles;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.crafting.UCrafting;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileArtisia extends TileBaseUC {

	public BlockPos core = BlockPos.ORIGIN;
	private ItemStackHandler inv = new ItemStackHandler(1);
	
	private static final BlockPos[] GRIDPOS = { 
			new BlockPos(-1, 0, -1), new BlockPos(1, 0, 1), new BlockPos(-1, 0, 1), new BlockPos(1, 0, -1),
			new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1) 
	};
	
	public void findCore() {
		
		boolean foundGrid = true;
		for (int i = 0; i < GRIDPOS.length; i++) {
			BlockPos looppos = getPos().add(GRIDPOS[i]);
			TileEntity te = getWorld().getTileEntity(looppos);
			if (te instanceof TileArtisia) continue;

			else {
				foundGrid = false;
				break;
			}
		}
		if (foundGrid) {
			for (int i = 0; i < GRIDPOS.length; i++) {
				BlockPos looppos = getPos().add(GRIDPOS[i]);
				TileEntity te = getWorld().getTileEntity(looppos);
				if (te != null && te instanceof TileArtisia) {
					TileArtisia tile = (TileArtisia)te;
					tile.setCore(this.getPos());
				}
			}
			this.setCore(getPos());
		}
	}
	
	public void setCore(BlockPos pos) {
		
		this.core = pos;
	}
	
	public boolean isCore() {
		
		return (isValid()) && (this.core.equals(getPos()));
	}
	
	public boolean isValid() {
		
		return this.core != null && !core.equals(BlockPos.ORIGIN);
	}
	
	public void setStackSpace(EntityItem ei) {
		
		List<ItemStack> stacks = new ArrayList();
		for (int i = 0; i < GRIDPOS.length; i++) {
			BlockPos looppos = getPos().add(GRIDPOS[i]);
			TileEntity te = getWorld().getTileEntity(looppos);
			if (te instanceof TileArtisia) {
				TileArtisia tile = (TileArtisia)te;
				if (canAccept(getWorld(), looppos)) {
					if (!tile.getItem().isEmpty()) {
						stacks.add(tile.getItem());
					}
					else if (tile.getItem().isEmpty()) {
						ItemStack stack = ei.getItem().copy();
						stack.setCount(1);
						if (!getWorld().isRemote) {
							tile.setItem(stack);
							stacks.add(stack);
							ei.getItem().shrink(1);
						}
						tile.markBlockForUpdate();
						UCPacketHandler.sendToNearbyPlayers(getWorld(), looppos, new PacketUCEffect(EnumParticleTypes.EXPLOSION_NORMAL, ei.posX - 1, ei.posY, ei.posZ - 1, 3));
						if (ei.getItem().getCount() <= 0) ei.setDead();
						break;
					}
				}
			}
		}
		if (stacks.size() >= 8 && !ei.isDead) {
			ItemStack stack = ei.getItem().copy();
			stack.setCount(1);
			if (!getWorld().isRemote) {
				this.setItem(stack);
				ei.getItem().shrink(1);
				stacks.add(stack);
			}
			if (ei.getItem().getCount() <= 0) ei.setDead();
			
			for (UCrafting recipe : UCrafting.recipes) {
				if (recipe.matches(stacks)) {
					if (!getWorld().isRemote) {
						ItemStack output = recipe.getOutput().copy();
						clearItems();
						this.setItem(output);
					}
					break;
				}
			}
			this.markBlockForUpdate();
		}
	}
	
	private void clearItems() {
		
		for (int i = 0; i < GRIDPOS.length; i++) {
			BlockPos looppos = getPos().add(GRIDPOS[i]);
			TileEntity te = getWorld().getTileEntity(looppos);
			if (te != null && te instanceof TileArtisia) {
				((TileArtisia)te).setItem(ItemStack.EMPTY);
				((TileArtisia)te).markBlockForUpdate();
				UCPacketHandler.sendToNearbyPlayers(getWorld(), looppos, new PacketUCEffect(EnumParticleTypes.REDSTONE, looppos.getX(), looppos.getY() + 0.5D, looppos.getZ(), 6));
			}
		}
		UCPacketHandler.sendToNearbyPlayers(getWorld(), getPos(), new PacketUCEffect(EnumParticleTypes.REDSTONE, getPos().getX(), getPos().getY(), getPos().getZ(), 6));
	}
	
	private boolean canAccept(World world, BlockPos pos) {
		
		Block block = world.getBlockState(pos).getBlock();
		if (block != null && block instanceof BlockCrops) {
			return world.getBlockState(pos).getValue(BlockCrops.AGE) >= ((BlockCrops)block).getMaxAge();
		}
		return false;
	}
	
	public ItemStack getItem() {
		
		return inv.getStackInSlot(0);
	}
	
	public void setItem(ItemStack stack) {
		
		inv.setStackInSlot(0, stack);
	}
	
	public void markBlockForUpdate() {
		
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}
	
	public void markBlockForRenderUpdate() {
		
		world.markBlockRangeForRenderUpdate(pos, pos);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		
		return writeToNBT(new NBTTagCompound());
	}
	
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeCustomNBT(nbtTag);
		
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		tag.setLong("Core", core.toLong());
		tag.setTag("inventory", inv.serializeNBT());
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		this.core = BlockPos.fromLong(tag.getLong("Core"));
		inv.deserializeNBT(tag.getCompoundTag("inventory"));
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		
		if (packet != null && packet.getNbtCompound() != null)
			readCustomNBT(packet.getNbtCompound());
		
		markBlockForRenderUpdate();
	}
}
