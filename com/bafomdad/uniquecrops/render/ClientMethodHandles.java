package com.bafomdad.uniquecrops.render;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

import com.google.common.base.Throwables;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ClientMethodHandles {

	private static final String[] RENDERPOSX = { "renderPosX", "field_78725_b", "o" };
	private static final String[] RENDERPOSY = { "renderPosY", "field_78726_c", "p" };
	private static final String[] RENDERPOSZ = { "renderPosZ", "field_78723_d", "q" };
	
	public static final MethodHandle
		renderPosX_getter, renderPosY_getter, renderPosZ_getter;
	
	static {
		try {
			Field f = ReflectionHelper.findField(RenderManager.class, RENDERPOSX);
			f.setAccessible(true);
			renderPosX_getter = MethodHandles.publicLookup().unreflectGetter(f);
			
			f = ReflectionHelper.findField(RenderManager.class, RENDERPOSY);
			f.setAccessible(true);
			renderPosY_getter = MethodHandles.publicLookup().unreflectGetter(f);
			
			f = ReflectionHelper.findField(RenderManager.class, RENDERPOSZ);
			f.setAccessible(true);
			renderPosZ_getter = MethodHandles.publicLookup().unreflectGetter(f);
		} catch (IllegalAccessException e) {
			System.out.println("[UniqueCrops]: Couldn't initialize methodhandles! Things will be broken!");
			e.printStackTrace();
			throw Throwables.propagate(e);
		}
	}
	
	private ClientMethodHandles() {}
}
