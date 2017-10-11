package com.bafomdad.uniquecrops.proxies;

import java.io.IOException;
import java.io.InputStream;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.entities.EntityCustomPotion;
import com.bafomdad.uniquecrops.entities.EntityEulaBook;
import com.bafomdad.uniquecrops.entities.EntityItemWeepingEye;
import com.bafomdad.uniquecrops.entities.RenderThrowable;
import com.bafomdad.uniquecrops.events.UCEventHandlerClient;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCKeys;
import com.bafomdad.uniquecrops.items.ItemGeneric;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class ClientProxy extends CommonProxy {
	
	public static boolean flag = false;
	private final ResourceLocation shader = new ResourceLocation("minecraft", "shaders/post/bits.json");
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new UCEventHandlerClient());
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		
		super.init(event);
		UCKeys.init();
	}
	
	@Override
	public void initAllModels() {
		
		UCBlocks.initModels();
		UCPacketHandler.initClient();
	}
	
	@Override
	public void initEntityRender() {
		
		Minecraft mc = Minecraft.getMinecraft();
		RenderManager rm = mc.getRenderManager();
		RenderItem ri = mc.getRenderItem();
		
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomPotion.class, new RenderThrowable(rm, UCItems.generic, 13, ri));
		RenderingRegistry.registerEntityRenderingHandler(EntityItemWeepingEye.class, new RenderThrowable(rm, UCItems.generic, 16, ri));
		RenderingRegistry.registerEntityRenderingHandler(EntityEulaBook.class, new RenderThrowable(rm, UCItems.generic, 24, ri));
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
		
		if (OpenGlHelper.shadersSupported) {
			Minecraft mc = Minecraft.getMinecraft();
			EntityRenderer renderer = mc.entityRenderer;
			if (renderer.getShaderGroup() != null)
				renderer.getShaderGroup().deleteShaderGroup();
			
			renderer.loadShader(this.shader);
		}
	}
	
	@Override
	public void disableBitsShader() {
		
		if (OpenGlHelper.shadersSupported) {
			EntityRenderer renderer = Minecraft.getMinecraft().entityRenderer;
			if (renderer.getShaderGroup() != null && renderer.getShaderGroup().getShaderGroupName().contains("bits.json"))
				renderer.getShaderGroup().deleteShaderGroup();
		}
	}
	
	@Override
	public void registerColors() {
	
		ItemColors ic = Minecraft.getMinecraft().getItemColors();
		ic.registerItemColorHandler(new IItemColor() 
		{	
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				
				if (stack.getItem() != null) {
					if ((stack.getItem() instanceof ItemGeneric && stack.getItemDamage() == EnumItems.POTIONSPLASH.ordinal()) || stack.getItem() == UCItems.potionreverse) {
						if (tintIndex == 0)
							return 0x845c28;
					}
				}
				return 0xffffff;
			}
		}, UCItems.generic, UCItems.potionreverse);
	}
}
