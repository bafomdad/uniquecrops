package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class OldBow extends ItemBaseUC {

    public OldBow() {

        super(UCItems.unstackable());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack stack = player.getMainHandItem();
        if (stack.is(this) && player.getInventory().contains(new ItemStack(Items.ARROW))) {
            if (!world.isClientSide()) {
                int charge = 15;
                int i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, player, charge, true);
                if (i < 0) return InteractionResultHolder.fail(stack);

                ItemStack arrowItem = player.getInventory().items.stream().filter(arr -> arr.is(Items.ARROW)).findFirst().get();
                float f = BowItem.getPowerForTime(charge);
                AbstractArrow arrow = ((ArrowItem)Items.ARROW).createArrow(world, arrowItem, player);
                if (player.isCreative())
                    arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);

                world.addFreshEntity(arrow);
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                if (!player.isCreative()) {
                    arrowItem.shrink(1);
                    if (arrowItem.isEmpty())
                        player.getInventory().removeItem(arrowItem);
                }
//                return InteractionResultHolder.success(stack);
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {

        return false;
    }
}
