package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicReference;

public class CookingItemEntity extends ItemEntity {

    private static final EntityDataAccessor<Integer> COOKING_TIME = SynchedEntityData.defineId(CookingItemEntity.class, EntityDataSerializers.INT);

    public CookingItemEntity(EntityType<? extends ItemEntity> type, Level world) {

        super(EntityType.ITEM, world);
    }

    public CookingItemEntity(Level world, ItemEntity oldEntity, ItemStack stack) {

        super(world, oldEntity.getX(), oldEntity.getY(), oldEntity.getZ(), stack);
        this.setDeltaMovement(oldEntity.getDeltaMovement());
        this.lifespan = oldEntity.lifespan;
        this.setDefaultPickUpDelay();
    }

    @Override
    public void defineSynchedData() {

        super.defineSynchedData();
        this.entityData.define(COOKING_TIME, 0);
    }

    @Override
    public void tick() {

        super.tick();

        int cookTime = getCookingTime();
        if (cookTime > 0 && random.nextBoolean())
            UCPacketHandler.sendToNearbyPlayers(this.level, this.blockPosition(), new PacketUCEffect(EnumParticle.SMOKE, this.getX() - 0.5, this.getY() + 0.1, this.getZ() - 0.5, 0));
        if (cookTime >= 100) {
            UCPacketHandler.sendToNearbyPlayers(this.level, this.blockPosition(), new PacketUCEffect(EnumParticle.FLAME, this.getX(), this.getY() + 0.2, this.getZ(), 5));
            if (!this.level.isClientSide)
                Containers.dropItemStack(this.level, this.getX(), this.getY(), this.getZ(), getCookedItem());
            this.discard();
            return;
        }
        if (this.tickCount % 5 == 0) {
            if (!isCustomNameVisible())
                setCustomNameVisible(true);
            setCustomName(new TextComponent(cookTime + "%"));
            setCookingTime(++cookTime);
        }
    }

    public void setCookingTime(int time) {

        this.entityData.set(COOKING_TIME, time);
    }

    public int getCookingTime() {

        return this.entityData.get(COOKING_TIME);
    }

    private ItemStack getCookedItem() {

        AtomicReference<ItemStack> result = new AtomicReference<>(new ItemStack(UCItems.USELESS_LUMP.get()));
        this.level.getRecipeManager().getRecipeFor(UCItems.HEATER_TYPE, wrap(this.getItem()), this.level)
                .ifPresent(recipe -> {
                   result.set(recipe.getResultItem().copy());
                   result.get().setCount(this.getItem().getCount());
                });
        this.level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, wrap(this.getItem()), this.level)
                .ifPresent(recipe -> {
                   result.set(recipe.getResultItem().copy());
                   result.get().setCount(this.getItem().getCount());
                });
        return result.get();
    }

    private SimpleContainer wrap(ItemStack stack) {

        SimpleContainer inv = new SimpleContainer(1);
        inv.setItem(0, stack);

        return inv;
    }
}
