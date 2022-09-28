package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;

import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class BattleCropEntity extends PathfinderMob {

    public BattleCropEntity(EntityType<BattleCropEntity> type, Level world) {

        super(type, world);
    }

    public static AttributeSupplier.Builder registerAttributes() {

        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.32D).add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void registerGoals() {

        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.31D, false));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {

        if (source.getEntity() instanceof Player player) {
            if (player.isCreative())
                return super.hurt(source, amount);

            if (!source.isExplosion())
                player.hurt(DamageSource.thorns(this), amount);
        }
        if (!(source.getEntity() instanceof Player) || source.isFire())
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
