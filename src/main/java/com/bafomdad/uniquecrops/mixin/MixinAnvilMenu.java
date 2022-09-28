package com.bafomdad.uniquecrops.mixin;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public class MixinAnvilMenu extends MixinItemCombinerMenu {

    @Shadow @Final private DataSlot cost;

    @Inject(method = "createResult", at = @At("TAIL"))
    public void uniquecrops_result(CallbackInfo info) {

        ItemStack left = inputSlots.getItem(0);
        ItemStack right = inputSlots.getItem(1);

        if (left.isEmpty() || right.isEmpty()) return;

        if ((left.getItem() == Items.ENCHANTED_BOOK && right.getItem() != Items.ENCHANTED_BOOK) || (left.getItem() != Items.ENCHANTED_BOOK && right.getItem() == Items.ENCHANTED_BOOK)) {
            ItemStack output = resultSlots.getItem(0);
            ItemStack toCheck = (left.getItem() != Items.ENCHANTED_BOOK) ? left.copy() : right.copy();
            if (!output.isEmpty() && NBTUtils.getBoolean(toCheck, UCStrings.TAG_DISCOUNT, false)) {
                int newCost = cost.get();
                if (newCost > 5) {
                    cost.set(newCost - 5);
                    resultSlots.setItem(0, output.copy());
                }
            }
        }
    }
}
