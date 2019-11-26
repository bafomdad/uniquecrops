package com.bafomdad.uniquecrops.proxies;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.bafomdad.uniquecrops.core.UCShaderUtil;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumItems;
import com.bafomdad.uniquecrops.entities.EntityBattleCrop;
import com.bafomdad.uniquecrops.entities.EntityCustomPotion;
import com.bafomdad.uniquecrops.entities.EntityEulaBook;
import com.bafomdad.uniquecrops.entities.EntityItemWeepingEye;
import com.bafomdad.uniquecrops.entities.EntityMirror;
import com.bafomdad.uniquecrops.events.UCEventHandlerClient;
import com.bafomdad.uniquecrops.gui.GuiBookEula;
import com.bafomdad.uniquecrops.gui.GuiColorfulCubes;
import com.bafomdad.uniquecrops.gui.GuiStaffOverlay;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCKeys;
import com.bafomdad.uniquecrops.items.ItemGeneric;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import com.bafomdad.uniquecrops.render.FXSpark;
import com.bafomdad.uniquecrops.render.UCBipedLayerRenderer;
import com.bafomdad.uniquecrops.render.WorldRenderHandler;
import com.bafomdad.uniquecrops.render.entity.RenderBattleCropEntity;
import com.bafomdad.uniquecrops.render.entity.RenderMirrorEntity;
import com.bafomdad.uniquecrops.render.entity.RenderThrowable;

public class ClientProxy extends CommonProxy {
	
	public static boolean flag = false;
	private static final ResourceLocation BITS = new ResourceLocation("minecraft", "shaders/post/bits.json");
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new UCEventHandlerClient());
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		
		super.init(event);
		MinecraftForge.EVENT_BUS.register(new GuiStaffOverlay(Minecraft.getMinecraft()));
		UCKeys.init();
	}
	
	@Override
	public void initAllModels() {
		
		UCBlocks.initModels();
	}
	
	@Override
	public void initEntityRender() {
		
		Minecraft mc = Minecraft.getMinecraft();
		RenderManager rm = mc.getRenderManager();
		RenderItem ri = mc.getRenderItem();
		
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomPotion.class, new RenderThrowable(rm, UCItems.generic, 13, ri));
		RenderingRegistry.registerEntityRenderingHandler(EntityItemWeepingEye.class, new RenderThrowable(rm, UCItems.generic, 16, ri));
		RenderingRegistry.registerEntityRenderingHandler(EntityEulaBook.class, new RenderThrowable(rm, UCItems.generic, 24, ri));
		
		Map<String, RenderPlayer> skinMap = rm.getSkinMap();
		RenderPlayer render = skinMap.get("default");
		render.addLayer(new UCBipedLayerRenderer());
		render = skinMap.get("slim");
		render.addLayer(new UCBipedLayerRenderer());
	}
	
	@Override
	public void checkResource() {
		
		try {
			for (int i = 1; i < 6; i++) {
				InputStream fis = getClass().getClassLoader().getResourceAsStream("assets/uniquecrops/textures/blocks/invisibilia" + i + ".png");
				String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
				if (!md5.equals(UCStrings.MD5[i - 1]))
					flag = true;
				fis.close();
			}
			InputStream fis2 = getClass().getClassLoader().getResourceAsStream("assets/uniquecrops/textures/blocks/invisiglass.png");
			String md5Glass = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis2);
			if (!md5Glass.equals(UCStrings.MD5_GLASS))
				flag = true;
			fis2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean invisiTrace() {
		
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player != null && !player.capabilities.isCreativeMode) {
			if (flag || player.inventory.armorInventory.get(3).isEmpty() || (!player.inventory.armorInventory.get(3).isEmpty() && player.inventory.armorInventory.get(3).getItem() != UCItems.glasses3D))
				return false;
		}
		return true;
	}
	
	@Override
	public void enableBitsShader() {
		
		UCShaderUtil.enableScreenShader(BITS);
	}
	
	@Override
	public void disableBitsShader() {
		
		UCShaderUtil.disableScreenShader(BITS);
	}
	
	@Override
	public void killMirror(EntityMirror mirror) {
		
		WorldRenderHandler.pendingRemoval.add(mirror);
	}
	
	@Override
	public void openEulaBook(EntityPlayer player) {

		Minecraft.getMinecraft().displayGuiScreen(new GuiBookEula(player));
	}
	
	@Override
	public EntityPlayer getPlayer() {
		
		return Minecraft.getMinecraft().player;
	}
	
	@Override
	public World getClientWorld() {
		
		return Minecraft.getMinecraft().world;
	}
	
	@Override
	public void spawnParticles(EnumParticleTypes particle, double x, double y, double z, int loopSize) {
		
		World world = Minecraft.getMinecraft().world;
		x += 0.5D;
		z += 0.5D;
		
		if (loopSize > 0) {
			for (int i = 0; i < loopSize; i++)
				world.spawnParticle(particle, x + world.rand.nextFloat(), y, z + world.rand.nextFloat(), 0, 0, 0);
		}
		else
			world.spawnParticle(particle, x, y, z, 0, 0, 0);
		
	}
	
	@Override
	public void sparkFX(double x, double y, double z, float r, float g, float b, float size, float motionX, float motionY, float motionZ, float maxAgeMul) {
		
		FXSpark spark = new FXSpark(Minecraft.getMinecraft().world, x, y, z, size, r, g, b, motionX, motionY, motionZ, maxAgeMul);
		Minecraft.getMinecraft().effectRenderer.addEffect(spark);
	}
}
