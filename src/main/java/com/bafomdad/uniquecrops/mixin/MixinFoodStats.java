package com.bafomdad.uniquecrops.mixin;

import com.bafomdad.uniquecrops.items.curios.EmblemBookworm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class MixinFoodStats {

    @Inject(method = "eat(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true, remap = false)
    private void onEat(Item item, ItemStack stack, LivingEntity entity, CallbackInfo ci) {

        if (EmblemBookworm.isEdible(item)) {
            FoodProperties food = EmblemBookworm.getFood(stack);
            ((FoodData)(Object)this).eat(food.getNutrition(), food.getSaturationModifier());
            ci.cancel();
        }
    }
}
