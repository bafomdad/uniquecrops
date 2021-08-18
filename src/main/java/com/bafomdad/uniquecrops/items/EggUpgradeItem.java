package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class EggUpgradeItem extends ItemBaseUC {

    public EggUpgradeItem() {

        MinecraftForge.EVENT_BUS.addListener(this::maximumOvercluck);
        MinecraftForge.EVENT_BUS.addListener(this::checkEggUpgrade);
    }

    private void maximumOvercluck(LivingEvent.LivingUpdateEvent event) {

        if (!event.getEntityLiving().level.isClientSide && event.getEntityLiving() instanceof ChickenEntity) {
            ChickenEntity chicken = (ChickenEntity)event.getEntityLiving();
            if (chicken.isBaby()) return;
            CompoundNBT tag = chicken.getPersistentData();
            if (tag.contains(TAG_OVERCLUCK)) {
                int timer = tag.getInt(TAG_OVERCLUCK);
                tag.putInt(TAG_OVERCLUCK, --timer);
                if (--timer <= 0) {
                    chicken.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (chicken.level.random.nextFloat() - chicken.level.random.nextFloat()) * 0.2F + 1.0F);
                    chicken.spawnAtLocation(Items.EGG);
                    timer = chicken.level.random.nextInt(60) + 900;
                    tag.putInt(TAG_OVERCLUCK, timer);
                }
            }
        }
    }

    private void checkEggUpgrade(LivingDropsEvent event) {

        if (event.getEntityLiving() instanceof ChickenEntity) {
            if (!event.getEntityLiving().level.isClientSide && !event.getEntityLiving().isBaby()) {
                ChickenEntity chicken = (ChickenEntity)event.getEntityLiving();
                CompoundNBT tag = chicken.getPersistentData();
                if (tag.contains(TAG_OVERCLUCK)) {
                    InventoryHelper.dropItemStack(chicken.level, chicken.getX(), chicken.getY(), chicken.getZ(), new ItemStack(UCItems.EGGUPGRADE.get()));
                }
            }
        }
    }

    public static final String TAG_OVERCLUCK = "UC_tagEggUpgrade";

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand) {

        if (target instanceof ChickenEntity && !target.isBaby()) {
            CompoundNBT tag = target.getPersistentData();
            if (!tag.contains(TAG_OVERCLUCK)) {
                tag.putInt(TAG_OVERCLUCK, target.level.random.nextInt(60) + 900);
                if (!player.isCreative())
                    stack.shrink(1);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }
}
