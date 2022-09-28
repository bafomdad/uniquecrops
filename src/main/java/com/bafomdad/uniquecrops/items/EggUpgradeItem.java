package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class EggUpgradeItem extends ItemBaseUC {

    public EggUpgradeItem() {

        MinecraftForge.EVENT_BUS.addListener(this::maximumOvercluck);
        MinecraftForge.EVENT_BUS.addListener(this::checkEggUpgrade);
    }

    private void maximumOvercluck(LivingEvent.LivingUpdateEvent event) {

        if (!event.getEntityLiving().level.isClientSide && event.getEntityLiving() instanceof Chicken) {
            Chicken chicken = (Chicken)event.getEntityLiving();
            if (chicken.isBaby()) return;
            CompoundTag tag = chicken.getPersistentData();
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

        if (event.getEntityLiving() instanceof Chicken) {
            if (!event.getEntityLiving().level.isClientSide && !event.getEntityLiving().isBaby()) {
                Chicken chicken = (Chicken)event.getEntityLiving();
                CompoundTag tag = chicken.getPersistentData();
                if (tag.contains(TAG_OVERCLUCK)) {
                    Containers.dropItemStack(chicken.level, chicken.getX(), chicken.getY(), chicken.getZ(), new ItemStack(UCItems.EGGUPGRADE.get()));
                }
            }
        }
    }

    public static final String TAG_OVERCLUCK = "UC_tagEggUpgrade";

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {

        if (target instanceof Chicken && !target.isBaby()) {
            CompoundTag tag = target.getPersistentData();
            if (!tag.contains(TAG_OVERCLUCK)) {
                tag.putInt(TAG_OVERCLUCK, target.level.random.nextInt(60) + 900);
                if (!player.isCreative())
                    stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
