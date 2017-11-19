package com.bafomdad.uniquecrops.blocks.tiles;

import javax.annotation.Nullable;

import com.bafomdad.uniquecrops.init.UCBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class TileLacusia extends TileBaseUC {

	private ItemStackHandler inv = new ItemStackHandler(1);
	private int dir;
	private int waitTime = 10;
	private int waitTimeStuck = 20;
	private static EnumFacing[] REVERSE = new EnumFacing[] { EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.WEST, EnumFacing.SOUTH };
	
	public void updateStuff() {
		
		boolean hasPower = world.isBlockPowered(getPos());
		if (!world.isRemote) {
			if (this.canAdd() && hasPower) {
				TileEntity tileInv = null;
				for (EnumFacing face : EnumFacing.HORIZONTALS) {
					BlockPos looppos = getPos().offset(face);
					TileEntity tile = world.getTileEntity(looppos);
					if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face)) {
						tileInv = tile;
						dir = face.getIndex();
						break;
					}
				}
				if (tileInv != null) {
//					if (!directionMatches(EnumFacing.getHorizontal(dir))) {
						IItemHandler cap = tileInv.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.getHorizontal(dir));
						for (int i = 0; i < cap.getSlots(); i++) {
							ItemStack extract = cap.getStackInSlot(i);
							if (!extract.isEmpty()) {
								this.setItem(extract.copy());
								cap.extractItem(i, extract.getMaxStackSize(), false);
								this.markBlockForUpdate();
								break;
							}
						}
//					}
				}
			}
			else if (!canAdd()) {
				boolean schedule = false;
				for (EnumFacing face : hasPower ? REVERSE : EnumFacing.HORIZONTALS) {
					if (directionMatches(face)) continue;
					
					BlockPos looppos = getPos().offset(face);
					TileEntity tile = world.getTileEntity(looppos);
					if (tile instanceof TileLacusia) {
						TileLacusia lacusia = (TileLacusia)tile;
						if (lacusia.canAdd()) {
							lacusia.setItem(getItem());
							this.setItem(ItemStack.EMPTY);
							lacusia.markBlockForUpdate();
							this.markBlockForUpdate();
							dir = face.getIndex();
							lacusia.dir = face.getOpposite().getIndex();
							world.scheduleUpdate(looppos, UCBlocks.cropLacusia, waitTime);
							break;
						}
						else {
							dir = face.getIndex();
							world.scheduleUpdate(getPos(), UCBlocks.cropLacusia, waitTimeStuck);
						}
					}
					if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face)) {
						IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face);
						int amount = 0;
						ItemStack simulate = ItemHandlerHelper.insertItem(cap, getItem().copy(), true);
						int available = getItem().getCount() - simulate.getCount();
						
						if (available >= getItem().getCount()) {
							amount = available;	
							ItemHandlerHelper.insertItem(cap, getItem(), false);
							this.setItem(simulate);
							this.markBlockForUpdate();
							dir = face.getIndex();
							if (!getItem().isEmpty())
								world.scheduleUpdate(getPos(), UCBlocks.cropLacusia, waitTime);
							break;
						}
						else if (available <= 0) {
							dir = face.getIndex();
							world.scheduleUpdate(getPos(), UCBlocks.cropLacusia, waitTimeStuck);
						}
					}
				}	
			}
		}
	}
	
	private boolean directionMatches(EnumFacing facing) {
		
		return facing.getIndex() == dir;
	}
	
	public boolean canAdd() {
		
		return inv.getStackInSlot(0).isEmpty();
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
		
		tag.setInteger("UC_facing", dir);
		tag.setTag("inventory", inv.serializeNBT());
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		dir = tag.getInteger("UC_facing");
		inv.deserializeNBT(tag.getCompoundTag("inventory"));
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		
		if (packet != null && packet.getNbtCompound() != null)
			readCustomNBT(packet.getNbtCompound());
		
		markBlockForRenderUpdate();
	}
}
