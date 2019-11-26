package com.bafomdad.uniquecrops.render.entity;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderThrowable extends RenderSnowball {
	
	int damage;

	public RenderThrowable(RenderManager renderManagerIn, Item item, int damage, RenderItem renderitem) {
		
		super(renderManagerIn, item, renderitem);
		this.damage = damage;
	}
	
	@Override
    public ItemStack getStackToRender(Entity entityIn) {
		
        return new ItemStack(this.item, 1, damage);
    }
}
