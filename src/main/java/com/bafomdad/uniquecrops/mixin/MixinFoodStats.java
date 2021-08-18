package com.bafomdad.uniquecrops.mixin;

import com.bafomdad.uniquecrops.items.curios.EmblemBookworm;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodStats.class)
public class MixinFoodStats {

    @Inject(method = "eat(Lnet/minecraft/item/Item;Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private void eat(Item item, ItemStack stack, CallbackInfo ci) {

        if (EmblemBookworm.isEdible(item)) {
            Food food = EmblemBookworm.getFood(stack);
            ((FoodStats)(Object)this).eat(food.getNutrition(), food.getSaturationModifier());
            ci.cancel();
        }
    }
}
