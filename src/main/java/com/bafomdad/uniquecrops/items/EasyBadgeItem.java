package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.List;

public class EasyBadgeItem extends ItemBaseUC {

    private static final int RANGE = 5;

    public EasyBadgeItem() {

        super(UCItems.unstackable());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag whatisthis) {

        list.add(new TranslatableComponent(UCStrings.TOOLTIP + "easybadge").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {

        if (!(entity instanceof Player) || (entity instanceof FakePlayer)) return;
        if (slot < Inventory.getSelectionSize() && !world.isClientSide) {
            BlockPos pos = entity.blockPosition();
            List<Monster> monsters = world.getEntitiesOfClass(Monster.class, new AABB(pos.offset(-RANGE, -RANGE, -RANGE), pos.offset(RANGE, RANGE, RANGE)));
            for (Monster ent: monsters) {
                if (ent instanceof Skeleton skele)
                    skele.goalSelector.getRunningGoals().filter(goal -> goal.getGoal() instanceof RangedBowAttackGoal)
                            .findFirst().ifPresent(g -> {
                        ((RangedBowAttackGoal)g.getGoal()).setMinAttackInterval(100);
                    });

                if (ent instanceof Creeper creep)
                    ObfuscationReflectionHelper.setPrivateValue(Creeper.class, creep, 60, "field_82225_f");
            }
        }
    }
}
