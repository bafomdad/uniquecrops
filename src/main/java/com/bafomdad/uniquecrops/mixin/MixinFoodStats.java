package com.bafomdad.uniquecrops.mixin;

import com.bafomdad.uniquecrops.items.curios.EmblemBookworm;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodStats.class)
public class MixinFoodStats {

    @Inject(method = "consume", at = @At("HEAD"), cancellable = true)
    private void consume(Item item, ItemStack stack, CallbackInfo ci) {

        if (EmblemBookworm.isFood(item)) {
            Food food = EmblemBookworm.getFood(stack);
            ((FoodStats)(Object)this).addStats(food.getHealing(), food.getSaturation());
            ci.cancel();
        }
    }
}
