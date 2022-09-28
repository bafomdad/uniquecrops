package com.bafomdad.uniquecrops.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ResultContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemCombinerMenu.class)
public class MixinItemCombinerMenu {

    @Shadow @Final protected ResultContainer resultSlots;
    @Shadow @Final protected Container inputSlots;
}
