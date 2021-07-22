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

    private static final DataParameter<Integer> COOKING_TIME = EntityDataManager.createKey(CookingItemEntity.class, DataSerializers.VARINT);

    public CookingItemEntity(EntityType<? extends ItemEntity> type, World world) {

        super(EntityType.ITEM, world);
    }

    public CookingItemEntity(World world, ItemEntity oldEntity, ItemStack stack) {

        super(world, oldEntity.getPosX(), oldEntity.getPosY(), oldEntity.getPosZ(), stack);
        this.setMotion(oldEntity.getMotion());
        this.lifespan = oldEntity.lifespan;
        this.setDefaultPickupDelay();
    }

    @Override
    public void registerData() {

        super.registerData();
        this.dataManager.register(COOKING_TIME, Integer.valueOf(0));
    }

    @Override
    public void tick() {

        super.tick();

        int cookTime = getCookingTime();
        if (cookTime > 0 && rand.nextBoolean())
            UCPacketHandler.sendToNearbyPlayers(this.world, this.getPosition(), new PacketUCEffect(EnumParticle.SMOKE, this.getPosX() - 0.5, this.getPosY() + 0.1, this.getPosZ() - 0.5, 0));
        if (cookTime >= 100) {
            UCPacketHandler.sendToNearbyPlayers(this.world, this.getPosition(), new PacketUCEffect(EnumParticle.FLAME, this.getPosX(), this.getPosY() + 0.2, this.getPosZ(), 5));
            if (!this.world.isRemote)
                InventoryHelper.spawnItemStack(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), getCookedItem());
            this.remove();
            return;
        }
        if (this.ticksExisted % 5 == 0) {
            if (!isCustomNameVisible())
                setCustomNameVisible(true);
            setCustomName(new StringTextComponent(cookTime + "%"));
            setCookingTime(++cookTime);
        }
    }

    public void setCookingTime(int time) {

        this.dataManager.set(COOKING_TIME, Integer.valueOf(time));
    }

    public int getCookingTime() {

        return this.dataManager.get(COOKING_TIME).intValue();
    }

    private ItemStack getCookedItem() {

        AtomicReference<ItemStack> result = new AtomicReference<>(new ItemStack(UCItems.USELESS_LUMP.get()));
        this.world.getRecipeManager().getRecipe(UCRecipes.HEATER_TYPE, wrap(this.getItem()), this.world)
                .ifPresent(recipe -> {
                   result.set(recipe.getRecipeOutput().copy());
                   result.get().setCount(this.getItem().getCount());
                });
        this.world.getRecipeManager().getRecipe(IRecipeType.SMELTING, wrap(this.getItem()), this.world)
                .ifPresent(recipe -> {
                   result.set(recipe.getRecipeOutput().copy());
                   result.get().setCount(this.getItem().getCount());
                });
        return result.get();
    }

    private Inventory wrap(ItemStack stack) {

        Inventory inv = new Inventory(1);
        inv.setInventorySlotContents(0, stack);

        return inv;
    }
}
