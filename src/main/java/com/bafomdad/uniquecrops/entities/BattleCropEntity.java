package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BattleCropEntity extends CreatureEntity {

    public BattleCropEntity(EntityType<BattleCropEntity> type, World world) {

        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {

        return MonsterEntity.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.32D).add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void registerGoals() {

        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.31D, false));
        goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        goalSelector.addGoal(8, new LookRandomlyGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, PlayerEntity.class, true));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {

        if (source.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)source.getEntity();
            if (player.isCreative())
                return super.hurt(source, amount);

            if (!source.isExplosion())
                player.hurt(DamageSource.thorns(this), amount);
        }
        if (source.getEntity() == this || source.isFire())
            return super.hurt(source, amount);

        return super.hurt(source, 0);
    }

    @Override
    public void die(DamageSource source) {

        super.die(source);
        if (source.getEntity() == this) {
            this.spawnAtLocation(new ItemStack(UCItems.STEEL_DONUT.get()));
        }
        this.spawnAtLocation(new ItemStack(UCItems.DONUTSTEEL_SEED.get()));
    }

    @Override
    public boolean isIgnoringBlockTriggers() {

        return true;
    }

    @Override
    public boolean isPushable() {

        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {

        return false;
    }
}
