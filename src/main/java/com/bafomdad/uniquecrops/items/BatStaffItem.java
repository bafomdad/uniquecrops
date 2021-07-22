package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class BatStaffItem extends ItemBaseUC {

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {

        tooltip.add(new TranslationTextComponent(UCStrings.TOOLTIP + "batstaff"));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {

        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getHeldItem(hand);
        boolean damage = false;
        if (stack.getItem() == this) {
            BlockPos pos = player.getPosition();
            List<BatEntity> entities = player.world.getEntitiesWithinAABB(BatEntity.class, new AxisAlignedBB(pos.add(-15, -15, -15), pos.add(15, 15, 15)));
            for (BatEntity bat : entities) {
                if (bat != entities && !world.isRemote) {
                    UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.WITCH, bat.getPosX(), bat.getPosY(), bat.getPosZ(), 4));
                    bat.remove();
                    damage = true;
                }
            }
            if (damage && !world.isRemote)
                stack.attemptDamageItem(1, world.rand, (ServerPlayerEntity)player);
        }
        return damage ? ActionResult.resultSuccess(player.getHeldItem(hand)) : ActionResult.resultPass(player.getHeldItem(hand));
    }
}
