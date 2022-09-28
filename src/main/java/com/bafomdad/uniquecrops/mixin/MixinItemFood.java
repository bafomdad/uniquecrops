package com.bafomdad.uniquecrops.mixin;

import com.bafomdad.uniquecrops.items.curios.EmblemBookworm;
import com.bafomdad.uniquecrops.items.curios.EmblemIronStomach;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class MixinItemFood {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void use(Level world, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {

        if (EmblemBookworm.isEdible((Item)(Object)this) && !EmblemBookworm.isEquipped(player))
            cir.setReturnValue(InteractionResultHolder.pass(player.getItemInHand(hand)));
        if (EmblemIronStomach.containsTag((Item)(Object)this) && !EmblemIronStomach.isEquipped(player))
            cir.setReturnValue(InteractionResultHolder.pass(player.getItemInHand(hand)));
    }

    @Inject(method = "isEdible", at = @At("HEAD"), cancellable = true)
    private void isEdible(CallbackInfoReturnable<Boolean> cir) {

        if (EmblemIronStomach.containsTag((Item)(Object)this) || EmblemBookworm.isEdible((Item)(Object)this))
            cir.setReturnValue(true);
    }

    @Inject(method = "getFoodProperties", at = @At("HEAD"), cancellable = true)
    private void getFood(CallbackInfoReturnable<FoodProperties> cir) {

        if (EmblemBookworm.isEdible((Item)(Object)this))
            cir.setReturnValue(EmblemBookworm.getFood(new ItemStack(Items.ENCHANTED_BOOK)));
        if (EmblemIronStomach.containsTag((Item)(Object)this))
            cir.setReturnValue(EmblemIronStomach.getFood((Item)(Object)this));
    }
}
