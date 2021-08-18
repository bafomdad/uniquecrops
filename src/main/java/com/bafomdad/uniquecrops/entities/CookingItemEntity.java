package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCRecipes;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicReference;

public class CookingItemEntity extends ItemEntity {

    private static final DataParameter<Integer> COOKING_TIME = EntityDataManager.defineId(CookingItemEntity.class, DataSerializers.INT);

    public CookingItemEntity(EntityType<? extends ItemEntity> type, World world) {

        super(EntityType.ITEM, world);
    }

    public CookingItemEntity(World world, ItemEntity oldEntity, ItemStack stack) {

        super(world, oldEntity.getX(), oldEntity.getY(), oldEntity.getZ(), stack);
        this.setDeltaMovement(oldEntity.getDeltaMovement());
        this.lifespan = oldEntity.lifespan;
        this.setDefaultPickUpDelay();
    }

    @Override
    public void defineSynchedData() {

        super.defineSynchedData();
        this.entityData.define(COOKING_TIME, Integer.valueOf(0));
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
                InventoryHelper.dropItemStack(this.level, this.getX(), this.getY(), this.getZ(), getCookedItem());
            this.remove();
            return;
        }
        if (this.tickCount % 5 == 0) {
            if (!isCustomNameVisible())
                setCustomNameVisible(true);
            setCustomName(new StringTextComponent(cookTime + "%"));
            setCookingTime(++cookTime);
        }
    }

    public void setCookingTime(int time) {

        this.entityData.set(COOKING_TIME, Integer.valueOf(time));
    }

    public int getCookingTime() {

        return this.entityData.get(COOKING_TIME).intValue();
    }

    private ItemStack getCookedItem() {

        AtomicReference<ItemStack> result = new AtomicReference<>(new ItemStack(UCItems.USELESS_LUMP.get()));
        this.level.getRecipeManager().getRecipeFor(UCRecipes.HEATER_TYPE, wrap(this.getItem()), this.level)
                .ifPresent(recipe -> {
                   result.set(recipe.getResultItem().copy());
                   result.get().setCount(this.getItem().getCount());
                });
        this.level.getRecipeManager().getRecipeFor(IRecipeType.SMELTING, wrap(this.getItem()), this.level)
                .ifPresent(recipe -> {
                   result.set(recipe.getResultItem().copy());
                   result.get().setCount(this.getItem().getCount());
                });
        return result.get();
    }

    private Inventory wrap(ItemStack stack) {

        Inventory inv = new Inventory(1);
        inv.setItem(0, stack);

        return inv;
    }
}
