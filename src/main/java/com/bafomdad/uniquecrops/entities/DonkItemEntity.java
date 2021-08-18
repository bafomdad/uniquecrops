package com.bafomdad.uniquecrops.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

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

    public DonkItemEntity(EntityType<? extends ItemEntity> type, World world) {

        super(EntityType.ITEM, world);
    }

    public DonkItemEntity(World world, ItemEntity oldEntity, ItemStack stack) {

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
                    ent.knockback( (float)1 * 0.5F, (double) MathHelper.sin(this.yRot * 0.017453292F), (double)(-MathHelper.cos(this.yRot * 0.017453292F)));
                }
                this.getPersistentData().remove("UC:canDonk");
                double motionX = -this.getDeltaMovement().x / 4;
                double motionZ = -this.getDeltaMovement().z / 4;
                this.setDeltaMovement(motionX, this.getDeltaMovement().y, motionZ);
            }
        }
    }
}
