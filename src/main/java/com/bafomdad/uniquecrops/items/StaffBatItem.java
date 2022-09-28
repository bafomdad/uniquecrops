package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class StaffBatItem extends ItemBaseUC {

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {

        tooltip.add(new TranslatableComponent(UCStrings.TOOLTIP + "batstaff").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isFoil(ItemStack stack) {

        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        boolean damage = false;
        if (stack.getItem() == this) {
            BlockPos pos = player.blockPosition();
            List<? extends LivingEntity> entities = getEntityToErase(player.level, pos);
            for (LivingEntity ent : entities) {
                if (ent != entities && !world.isClientSide) {
                    UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.WITCH, ent.getX(), ent.getY(), ent.getZ(), 4));
                    ent.discard();
                    damage = true;
                }
            }
            if (damage && !world.isClientSide)
                stack.hurt(1, world.random, (ServerPlayer)player);
        }
        return damage ? InteractionResultHolder.success(player.getItemInHand(hand)) : InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    protected List<? extends LivingEntity> getEntityToErase(Level world, BlockPos pos) {

        return world.getEntitiesOfClass(Bat.class, new AABB(pos.offset(-15, -15, -15), pos.offset(15, 15, 15)));
    }
}
