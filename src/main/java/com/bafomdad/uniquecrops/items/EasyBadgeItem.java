package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.impl.data.EntityDataAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.List;

public class EasyBadgeItem extends ItemBaseUC {

    private static final int RANGE = 5;

    public EasyBadgeItem() {

        super(UCItems.unstackable());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag whatisthis) {

        list.add(new TranslationTextComponent(UCStrings.TOOLTIP + "easybadge"));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {

        if (!(entity instanceof PlayerEntity) || (entity instanceof FakePlayer)) return;
        if (slot < PlayerInventory.getHotbarSize() && !world.isRemote) {
            BlockPos pos = entity.getPosition();
            List<MonsterEntity> monsters = world.getEntitiesWithinAABB(MonsterEntity.class, new AxisAlignedBB(pos.add(-RANGE, -RANGE, -RANGE), pos.add(RANGE, RANGE, RANGE)));
            for (MonsterEntity ent: monsters) {
                if (ent instanceof SkeletonEntity)
                    ((SkeletonEntity)ent).goalSelector.getRunningGoals().filter(goal -> goal.getGoal() instanceof RangedBowAttackGoal)
                            .findFirst().ifPresent(g -> {
                        ((RangedBowAttackGoal)g.getGoal()).setAttackCooldown(100);
                    });

                if (ent instanceof CreeperEntity)
                    ObfuscationReflectionHelper.setPrivateValue(CreeperEntity.class, (CreeperEntity)ent, 60, "field_82225_f");
            }
        }
    }
}
