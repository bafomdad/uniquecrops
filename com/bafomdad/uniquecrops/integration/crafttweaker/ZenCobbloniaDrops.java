package com.bafomdad.uniquecrops.integration.crafttweaker;

import net.minecraft.item.ItemStack;

import com.bafomdad.uniquecrops.UniqueCropsAPI;
import com.bafomdad.uniquecrops.integration.crafttweaker.mtlib.BaseUndoable;
import com.bafomdad.uniquecrops.integration.crafttweaker.mtlib.InputHelper;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;

@ZenRegister
@ZenClass(ZenCobbloniaDrops.NAME)
public class ZenCobbloniaDrops {

	public static final String NAME = "mods.uniquecrops.CobbloniaDrops";
	
	@ZenMethod
	public static void addDrops(IItemStack drop, double weight) {
		
		CraftTweakerPlugin.LATE_ADDITIONS.add(new Add(InputHelper.toStack(drop), weight));
	}
	
	private static class Add extends BaseUndoable {
		
		private final ItemStack drop;
		private final double weight;
		
		protected Add(ItemStack drop, double weight) {
			
			super(NAME);
			this.drop = drop;
			this.weight = weight;
		}
		
		@Override
		public void apply() {
			
			UniqueCropsAPI.COBBLONIA_DROPS_REGISTRY.addDrop(this.drop, this.weight);
		}
	}
}
