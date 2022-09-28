package com.bafomdad.uniquecrops.entities;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonkItemEntity extends ItemEntity {

    private static Map<Item, Float> damageMap = new HashMap();

    static {
        damageMap.put(Blocks.ANVIL.asItem(), 8.0F);
        damageMap.put(Items.APPLE, 0.5F);
        damageMap.put(Blocks.COBBLESTONE.asItem(), 2.5F);
    }

    public DonkItemEntity(EntityType<? extends ItemEntity> type, Level world) {

        super(EntityType.ITEM, world);
    }

    public DonkItemEntity(Level world, ItemEntity oldEntity, ItemStack stack) {

        super(world, oldEntity.getX(), oldEntity.getY(), oldEntity.getZ(), stack);
        this.setDeltaMovement(oldEntity.getDeltaMovement());
        this.lifespan = oldEntity.lifespan;
        this.setDefaultPickUpDelay();
    }

    @Override
    public void tick() {

        super.tick();

        if (!this.getPersistentData().contains("UC:canDonk")) return;

        if (this.onGround) {
            this.getPersistentData().remove("UC:canDonk");
            return;
        }
        List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
        for (int i = 0; i < list.size(); ++i) {
            LivingEntity ent = list.get(i);
            if (ent.isAlive()) {
                if (damageMap.containsKey(this.getItem().getItem())) {
                    float damage = damageMap.get(this.getItem().getItem());
                    ent.hurt(DamageSource.GENERIC, damage);
                    ent.knockback( (float)1 * 0.5F, (double) Mth.sin(this.yRotO * 0.017453292F), (double)(-Mth.cos(this.yRotO * 0.017453292F)));
                }
                this.getPersistentData().remove("UC:canDonk");
                double motionX = -this.getDeltaMovement().x / 4;
                double motionZ = -this.getDeltaMovement().z / 4;
                this.setDeltaMovement(motionX, this.getDeltaMovement().y, motionZ);
            }
        }
    }
}
