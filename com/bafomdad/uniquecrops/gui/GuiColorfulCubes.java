package com.bafomdad.uniquecrops.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.bafomdad.uniquecrops.events.UCTickHandler;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.ItemColorfulCube;
import com.bafomdad.uniquecrops.network.PacketColorfulCube;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class GuiColorfulCubes extends GuiScreen {
	
	double lastRotationTime;
	boolean hasRotated = false;
//	Vec3i lastVec;
	
	@Override
	public void initGui() {
		
		super.initGui();
		this.buttonList.clear();
		int k = this.width / 2;
		int l = this.height / 2;
		buttonList.add(new GuiButton(0, k - 20, l - 20, 40, 40, "rubik's cube") {
			@Override
		    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {}
		});
	}

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		this.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		EntityPlayer player = mc.player;
		
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		double time = (UCTickHandler.ticksInGame + UCTickHandler.partialTicks) * 12F;
		ScaledResolution scaled = new ScaledResolution(mc);
		int x = scaled.getScaledWidth() / 2;
		int y = scaled.getScaledHeight() / 2;
		GlStateManager.translate(x, y, this.zLevel + 100F);
		GlStateManager.scale(100F, 100F, 100F);
		Vec3i vec3 = this.getRotationVec();
		boolean north = vec3.equals(EnumFacing.NORTH.getDirectionVec());
		if (hasRotated) {
			if (lastRotationTime == -1)
				this.lastRotationTime = time;
			double rotationElapsed = time - lastRotationTime;
			if (!north) {
				GlStateManager.rotate((float)rotationElapsed, vec3.getX(), vec3.getY(), vec3.getZ());
				if ((float)rotationElapsed >= 90F) {
					this.lastRotationTime = -1;
					this.hasRotated = false;
				}
			} else {
				GlStateManager.rotate((float)rotationElapsed + 90F, 0, 1, 0);
				if ((float)rotationElapsed + 90F >= 180F) {
					this.lastRotationTime = -1;
					this.hasRotated = false;
				}
			}
		} else {
			if (!north)
				GlStateManager.rotate(90F, vec3.getX(), vec3.getY(), vec3.getZ());
			else
				GlStateManager.rotate(180F, 0, 1, 0);
		}
		mc.getItemRenderer().renderItem(player, new ItemStack(UCItems.rubiksCube), TransformType.FIXED);
	
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
    public boolean doesGuiPauseGame() {
    	
    	return false;
    }
	
	@Override
	protected void actionPerformed(GuiButton button) {
		
		if (button.id == 0) {
			int rot = ((ItemColorfulCube)UCItems.rubiksCube).getRotation(getCube());
			UCPacketHandler.INSTANCE.sendToServer(new PacketColorfulCube(rot, true));
			mc.displayGuiScreen(null);
		}
	}

	@Override
	protected void keyTyped(char character, int key) throws IOException {
		
		super.keyTyped(character, key);
		if (this.hasRotated) return;
		
		int i = ((ItemColorfulCube)UCItems.rubiksCube).getRotation(getCube());
		switch (key) {
			case Keyboard.KEY_W: rotateUp(i); break;
			case Keyboard.KEY_A: rotateLeft(i); break;
			case Keyboard.KEY_S: rotateDown(i); break;
			case Keyboard.KEY_D: rotateRight(i); break;
		}
	}
	
	private Vec3i getRotationVec() {
		
		int rot = ((ItemColorfulCube)UCItems.rubiksCube).getRotation(getCube());
		EnumFacing facing = EnumFacing.byIndex(rot);
		return facing.getDirectionVec();
	}
	
	private void rotateLeft(int i) {
		
		EnumFacing facing = EnumFacing.byIndex(i);
		if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) return; 
		updateCube(facing.rotateY().getIndex());
	}
	
	private void rotateRight(int i) {
		
		EnumFacing facing = EnumFacing.byIndex(i);
		if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) return;
		updateCube(facing.rotateYCCW().getIndex());
	}
	
	private void rotateUp(int i) {
		
		EnumFacing facing = EnumFacing.byIndex(i);
		if (facing != EnumFacing.UP && facing != EnumFacing.DOWN)
			updateCube(EnumFacing.UP.ordinal());
		if (facing != EnumFacing.UP && facing == EnumFacing.DOWN) {
			updateCube(EnumFacing.NORTH.ordinal());
		}
	}
	
	private void rotateDown(int i) {
		
		EnumFacing facing = EnumFacing.byIndex(i);
		if (facing != EnumFacing.DOWN && facing != EnumFacing.UP)
			updateCube(EnumFacing.DOWN.ordinal());
		if (facing != EnumFacing.DOWN && facing == EnumFacing.UP) {
			updateCube(EnumFacing.NORTH.ordinal());
		}
	}
	
	private void updateCube(int rotation) {
		
		UCPacketHandler.INSTANCE.sendToServer(new PacketColorfulCube(rotation, false));
		this.hasRotated = true;
	}
	
	private ItemStack getCube() {
		
		for (ItemStack stack : mc.player.getHeldEquipment()) {
			if (!stack.isEmpty() && stack.getItem() == UCItems.rubiksCube)
				return stack;
		}
		return ItemStack.EMPTY;
	}
}
