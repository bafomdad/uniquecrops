package com.bafomdad.uniquecrops.mixin;

import com.bafomdad.uniquecrops.items.curios.EmblemBookworm;
import com.bafomdad.uniquecrops.items.curios.EmblemIronStomach;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class MixinItemFood {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void use(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult<ItemStack>> cir) {

        if (EmblemBookworm.isEdible((Item)(Object)this) && !EmblemBookworm.isEquipped(player))
            cir.setReturnValue(ActionResult.pass(player.getItemInHand(hand)));
        if (EmblemIronStomach.containsTag((Item)(Object)this) && !EmblemIronStomach.isEquipped(player))
            cir.setReturnValue(ActionResult.pass(player.getItemInHand(hand)));
    }

    @Inject(method = "isEdible", at = @At("HEAD"), cancellable = true)
    private void isEdible(CallbackInfoReturnable<Boolean> cir) {

        if (EmblemIronStomach.containsTag((Item)(Object)this) || EmblemBookworm.isEdible((Item)(Object)this))
            cir.setReturnValue(true);
    }

    @Inject(method = "getFoodProperties", at = @At("HEAD"), cancellable = true)
    private void getFood(CallbackInfoReturnable<Food> cir) {

        if (EmblemBookworm.isEdible((Item)(Object)this))
            cir.setReturnValue(EmblemBookworm.getFood(new ItemStack(Items.ENCHANTED_BOOK)));
        if (EmblemIronStomach.containsTag((Item)(Object)this))
            cir.setReturnValue(EmblemIronStomach.getFood((Item)(Object)this));
    }
}
