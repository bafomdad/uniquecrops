package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.crops.supercrops.Itero;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileItero extends TileBaseUC implements ITickable {

	boolean showDemo = false;
	int gameIndex = 0;
	int[] gameCombos;

	@Override
	public void update() {

		if (!world.isRemote && world.getTotalWorldTime() % 40 == 0 && showDemo) {
			if (gameCombos != null && gameCombos.length > 0) {
				if (gameIndex >= gameCombos.length) {
					gameIndex = 0;
					showDemo = false;
					this.markDirty();
					UCPacketHandler.dispatchTEToNearbyPlayers(this);
					return;
				}
				BlockPos platePos = this.pos.subtract(Itero.PLATES[gameCombos[gameIndex]]);
				IBlockState plate = world.getBlockState(platePos);
				if (plate.getBlock() == Blocks.STONE_PRESSURE_PLATE) {
					world.setBlockState(platePos, plate.withProperty(BlockPressurePlate.POWERED, true), 3);
					world.scheduleUpdate(platePos, plate.getBlock(), 20);
				}
				gameIndex++;
			}
		}
	}
	
	public boolean createCombos(int age) {
		
		if (gameCombos != null)
			return false;

		int rand = 4 + world.rand.nextInt(age + 1);
		gameCombos = new int[rand];
		for (int i = 0; i < gameCombos.length; i++) {
			gameCombos[i] = world.rand.nextInt(Itero.PLATES.length);
		}
		return true;
	}
	
	public void matchCombo(BlockPos pos) {
		
		if (showDemo || gameCombos == null) return;
		
		BlockPos subPos = this.pos.subtract(pos);
		if (gameIndex >= gameCombos.length) {
			reset();
			return;
		}
		if (Itero.PLATES[gameCombos[gameIndex]].equals(subPos)) {
			System.out.println("yep");
			if (++this.gameIndex >= this.gameCombos.length) {
				UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.END_ROD, this.pos.getX() + 0.5, this.pos.getY() + 0.3, this.pos.getZ() + 0.5, 4));
				advanceStage();
				reset();
				return;
			}
			UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.HEART, this.pos.getX() + 0.5, this.pos.getY() + 0.3, this.pos.getZ() + 0.5, 0));
			return;
		} else {
			UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.EXPLOSION_LARGE, this.pos.getX() + 0.5, this.pos.getY() + 0.3, this.pos.getZ() + 0.5, 0));
			regressStage();
			reset();
		}
	}
	
	public void tryShowDemo() {
		
		if ((gameCombos != null && gameCombos.length > 0) && gameIndex == 0) {
			showDemo = true;
		}
	}
	
	public void regressStage() {
		
		Itero itero = (Itero)world.getBlockState(this.pos).getBlock();
		int age = itero.getAge(world.getBlockState(this.pos));
		world.setBlockState(this.pos, itero.withAge(Math.max(--age, 0)));
	}
	
	public void advanceStage() {
		
		Itero itero = (Itero)world.getBlockState(this.pos).getBlock();
		world.setBlockState(this.pos, itero.withAge(itero.getAge(world.getBlockState(this.pos)) + 1), 3);
	}
	
	public void reset() {
		
		gameCombos = null;
		gameIndex = 0;
	}
	
	public boolean showingDemo() {
		
		return showDemo;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		
		this.showDemo = tag.getBoolean("showDemo");
		this.gameIndex = tag.getInteger("gameIndex");
		if (this.gameCombos != null)
			this.gameCombos = tag.getIntArray("gameCombos");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		
		tag.setBoolean("showDemo", this.showDemo);
		tag.setInteger("gameIndex", this.gameIndex);
		if (this.gameCombos != null)
			tag.setIntArray("gameCombos", this.gameCombos);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		
		super.onDataPacket(net, packet);
		if (packet != null && packet.getNbtCompound() != null)
			readCustomNBT(packet.getNbtCompound());
		
		getWorld().markBlockRangeForRenderUpdate(pos, pos);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		
		return writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeCustomNBT(nbtTag);
		
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}
}
