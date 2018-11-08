package com.bafomdad.uniquecrops.render;

import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.entities.EntityMirror;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = UniqueCrops.MOD_ID, value = {Side.CLIENT})
public class WorldRenderHandler {

	private static final Minecraft mc = Minecraft.getMinecraft();
	private static final Map<EntityMirror, Integer> textures = Maps.newConcurrentMap();
	public static final List<EntityMirror> pendingRemoval = Lists.newArrayList();
	
	public static boolean rendering = false;
//	private static Entity renderEntity;
//	private static Entity backupEntity;
	
	public static void generateTexture(EntityMirror mirror) {
		
		int quality = 128;
		synchronized(textures) {
			int textureID = GlStateManager.generateTexture();
			GlStateManager.bindTexture(textureID);
			GlStateManager.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, quality / 2, quality, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(3 * (quality / 2) * quality));
			GlStateManager.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GlStateManager.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			textures.put(mirror, textureID);
		}
	}
	
	public static boolean hasKey(EntityMirror mirror) {
		
		synchronized(textures) {
			return textures.containsKey(mirror);
		}
	}
	
	public static int getValue(EntityMirror mirror) {
		
		synchronized(textures) {
			return textures.get(mirror);
		}
	}
	
//	@SubscribeEvent
//	public static void prePlayerRender(RenderPlayerEvent.Pre event) {
//		
//		if (!rendering) return;
//		if (event.getEntityPlayer() == renderEntity) {
//			backupEntity = mc.getRenderViewEntity();
//			mc.getRenderManager().renderViewEntity = renderEntity;
//		}
//	}
	
//	@SubscribeEvent
//	public static void postPlayerRender(RenderPlayerEvent.Post event) {
//		
//		if (!rendering) return;
//		if (event.getEntityPlayer() == renderEntity) {
//			Minecraft.getMinecraft().getRenderManager().renderViewEntity = backupEntity;
//			renderEntity = null;
//		}
//	}
	
	@SubscribeEvent
	public static void onRenderTick(TickEvent.RenderTickEvent event) {

		int quality = 256;
		int yidth = 100 * (quality / 128);
		
		synchronized(textures) {
			if (!pendingRemoval.isEmpty()) {
				for (EntityMirror mirror : pendingRemoval) {
					if (textures.containsKey(mirror))
						GlStateManager.deleteTexture(textures.get(mirror));
					textures.remove(mirror);
				}
			}
			if (event.phase != TickEvent.Phase.START) return;
			if (mc.inGameHasFocus) {
				for (EntityMirror mirror : textures.keySet()) {
					if (mirror == null) {
						textures.remove(null);
						continue;
					}
					if (!mirror.isEntityAlive()) {
						pendingRemoval.add(mirror);
						continue;
					}
					if (!mirror.rendering) continue;
					
					// Setup
					GameSettings settings = mc.gameSettings;
//					backupEntity = mc.getRenderViewEntity();
					final int widthBackup = mc.displayWidth;
					final int heightBackup = mc.displayHeight;
					final int thirdPersonBackup = settings.thirdPersonView;
					final boolean hideGuiBackup = settings.hideGUI;
					final int mipmapBackup = settings.mipmapLevels;
					final float fovBackup = settings.fovSetting;
					final int textureID = textures.get(mirror);
					
					settings.thirdPersonView = 0;
					settings.hideGUI = true;
					settings.mipmapLevels = 3;
					rendering = true;
//					renderEntity = mc.player;
					
					mc.displayWidth = quality * 2;
					mc.displayHeight = quality;
					mc.setRenderViewEntity(mirror);
					EntityRenderer entityRenderer = mc.entityRenderer;
					entityRenderer.renderWorld(event.renderTickTime, System.nanoTime() + (1000000000L / Math.max(30, mc.gameSettings.limitFramerate)));
					GlStateManager.bindTexture(textureID);
					GL11.glCopyTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, yidth, 0, quality / 2, quality, 0);
					
//					renderEntity = null;
					rendering = false;
					
					// Restore
					mc.setRenderViewEntity(mc.player);
					settings.fovSetting = fovBackup;
					settings.thirdPersonView = thirdPersonBackup;
					settings.hideGUI = hideGuiBackup;
					settings.mipmapLevels = mipmapBackup;
					mc.displayWidth = widthBackup;
					mc.displayHeight = heightBackup;
					mirror.rendering = false;
				}
			}
		}
	}
}
