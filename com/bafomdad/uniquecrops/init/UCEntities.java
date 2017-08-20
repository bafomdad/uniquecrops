package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.entities.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UCEntities {

	public static void init() {
		
		EntityRegistry.registerModEntity(EntityCustomPotion.class, UniqueCrops.MOD_ID + "reversepotion", 0, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityItemWeepingEye.class, UniqueCrops.MOD_ID + "weepingeye", 1, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityItemHourglass.class, UniqueCrops.MOD_ID + "hourglass", 2, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityItemPlum.class, UniqueCrops.MOD_ID + "flyingplum", 3, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityEulaBook.class, UniqueCrops.MOD_ID + "eulabook", 4, UniqueCrops.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityLivingBlock.class, UniqueCrops.MOD_ID + "livingblock", 5, UniqueCrops.instance, 64, 1, true);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		
		Minecraft mc = Minecraft.getMinecraft();
		RenderManager rm = mc.getRenderManager();
		RenderItem ri = mc.getRenderItem();
		
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomPotion.class, new RenderThrowable(rm, UCItems.generic, 13, ri));
		RenderingRegistry.registerEntityRenderingHandler(EntityItemWeepingEye.class, new RenderThrowable(rm, UCItems.generic, 16, ri));
		RenderingRegistry.registerEntityRenderingHandler(EntityEulaBook.class, new RenderThrowable(rm, UCItems.generic, 24, ri));
	}
}
