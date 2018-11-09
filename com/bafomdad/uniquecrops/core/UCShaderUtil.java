package com.bafomdad.uniquecrops.core;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Throwables;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = UniqueCrops.MOD_ID)
public class UCShaderUtil {
	
	private static final Map<ResourceLocation, ShaderGroup> screenShaders = new HashMap();
	private static boolean resetScreenShaders;
	private static int oldDisplayWidth = Minecraft.getMinecraft().displayWidth;
	private static int oldDisplayHeight = Minecraft.getMinecraft().displayHeight;
	
	private static final String[] SHADERLIST = { "listShaders", "field_148031_d", "d" };
	public static final MethodHandle list_shaders;
	
	static {
		try {
			Field f = ReflectionHelper.findField(ShaderGroup.class, SHADERLIST);
			f.setAccessible(true);
			list_shaders = MethodHandles.publicLookup().unreflectGetter(f);
		} catch (IllegalAccessException e) {
			System.out.println("[Unique Crops]: Couldn't initialize methodhandles! Things will be broken!");
			e.printStackTrace();
			throw Throwables.propagate(e);
		}
	}
	
	private static boolean shouldUseShaders() {
		
		return OpenGlHelper.shadersSupported;
	}

	public static void enableScreenShader(ResourceLocation res) {
		
		if (shouldUseShaders() && !screenShaders.containsKey(res)) {
			try {
				Minecraft mc = Minecraft.getMinecraft();
				resetScreenShaders = true;
				screenShaders.put(res, new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), res));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void disableScreenShader(ResourceLocation res) {
		
		if (screenShaders.containsKey(res)) {
			screenShaders.remove(res).deleteShaderGroup();
		}
	}
	
	public static void resetScreenShaders() {
		
		Minecraft mc = Minecraft.getMinecraft();
		if (resetScreenShaders || mc.displayWidth != oldDisplayWidth || oldDisplayHeight != mc.displayHeight) {
			for (ShaderGroup sg : screenShaders.values()) {
				sg.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
			}
			oldDisplayWidth = mc.displayWidth;
			oldDisplayHeight = mc.displayHeight;
			resetScreenShaders = false;
		}
	}
	
	public static void setScreenUniform(ResourceLocation res, String uniformName, int value, int... more) {

		if (more.length < 3) {
			int[] temp = new int[3];
			System.arraycopy(more, 0, temp, 0, more.length);
			more = temp;
		}
		ShaderGroup shaderGroup = screenShaders.get(res);
		if (shaderGroup != null) {
			List<Shader> shaders;
			try {
				shaders = (List<Shader>) list_shaders.invokeExact(shaderGroup);
			} catch (Throwable t) {
				return;
			}
			for (Shader shader : shaders) {
				shader.getShaderManager().getShaderUniformOrDefault(uniformName).set(value, more[0], more[1], more[2]);
			}
		}
	}
	
    public static void setScreenUniform(ResourceLocation res, String uniformName, float value, float... more) {
        
    	ShaderGroup shaderGroup = screenShaders.get(res);
        if (shaderGroup != null) {
        	List<Shader> shaders;
        	try {
        		shaders = (List<Shader>)list_shaders.invokeExact(shaderGroup);
        	} catch (Throwable t) {
        		return;
        	}
            for (Shader shader : shaders) {
                ShaderUniform uniform = shader.getShaderManager().getShaderUniformOrDefault(uniformName);
                switch (more.length) {
                    case 0: uniform.set(value); break;
                    case 1: uniform.set(value, more[0]); break;
                    case 2: uniform.set(value, more[0], more[1]); break;
                    case 3: uniform.set(value, more[0], more[1], more[2]); break;
                    default: throw new IllegalArgumentException("There should be between 1 and 4 values total, got " + more.length);
                }
            }
        }
    }
	
	@SubscribeEvent
	public static void renderScreenShaders(RenderGameOverlayEvent.Pre event) {
		
		if (shouldUseShaders() && !screenShaders.isEmpty() && event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
			resetScreenShaders();
			GlStateManager.matrixMode(GL11.GL_TEXTURE);
			GlStateManager.loadIdentity();
			final float partialTicks = event.getPartialTicks();
			screenShaders.forEach((key, shaderGroup) -> {
				GlStateManager.pushMatrix();
				setScreenUniform(key, "SystemTime", System.currentTimeMillis());
				shaderGroup.render(partialTicks);
				GlStateManager.popMatrix();
			});
			Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
		}
	}
}
