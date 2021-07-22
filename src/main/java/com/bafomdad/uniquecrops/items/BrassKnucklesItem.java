package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class BrassKnucklesItem extends SwordItem {

    private static final String HIT_LIST = "UC:hitList";
    private static final String HIT_ENTITY = "UC:hitEntityId";
    private static final String HIT_TIME = "UC:hitTime";
    private static final String HIT_AMOUNT = "UC:hitAmount";

    public BrassKnucklesItem() {

        super(ItemTier.IRON, 1, 1.31F, UCItems.unstackable());
        MinecraftForge.EVENT_BUS.addListener(this::knuckleDuster);
    }

    private void knuckleDuster(LivingAttackEvent event) {

        if (event.getEntityLiving().world.isRemote) return;
        if (event.getEntityLiving() instanceof LivingEntity && event.getSource().getImmediateSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)event.getSource().getImmediateSource();
            ItemStack brassKnuckles = player.getHeldItemMainhand();
            if (brassKnuckles.getItem() == this) {
                boolean flag = event.getSource() instanceof IndirectEntityDamageSource;
                if (flag) return;
//                boolean flag = NBTUtils.getList(brassKnuckles, HIT_LIST, 10, true) != null && NBTUtils.getList(brassKnuckles, HIT_LIST, 10, true).isEmpty();
//                if (!flag) return;
                float damage = event.getAmount();
                addHitEntity(event.getEntityLiving(), brassKnuckles, damage);
                event.setCanceled(true);
                BlockPos pos = event.getEntityLiving().getPosition();
                UCPacketHandler.sendToNearbyPlayers(player.world, player.getPosition(), new PacketUCEffect(EnumParticle.CRIT, pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, 6));
                return;
            }
        }
    }

    @Override
    public Rarity getRarity(ItemStack stack) {

        return Rarity.RARE;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (!world.isRemote && entity instanceof PlayerEntity && selected)
            removeHitEntity(stack, world, (PlayerEntity)entity);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return !ItemStack.areItemsEqual(oldStack, newStack);
    }

    private void addHitEntity(LivingEntity target, ItemStack stack, float damage) {

        ListNBT tagList = NBTUtils.getList(stack, HIT_LIST, 10, false);
        if (tagList.size() > 4) return;

        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(HIT_ENTITY, target.getEntityId());
        nbt.putInt(HIT_TIME, 25);
        nbt.putFloat(HIT_AMOUNT, damage);
        tagList.add(nbt);
        NBTUtils.setList(stack, HIT_LIST, tagList);
    }

    private void removeHitEntity(ItemStack stack, World world, PlayerEntity player) {

        ListNBT tagList = NBTUtils.getList(stack, HIT_LIST, 10, true);
        if (tagList == null || tagList.isEmpty()) return;

        boolean remove = false;
        for (int i = 0; i < tagList.size(); i++) {
            CompoundNBT nbt = tagList.getCompound(i);
            int timer = nbt.getInt(HIT_TIME);
            if (timer > 0)
                nbt.putInt(HIT_TIME, --timer);
            else {
                LivingEntity elb = (LivingEntity)world.getEntityByID(nbt.getInt(HIT_ENTITY));
                if (elb != null) {
                    float damage = nbt.getFloat(HIT_AMOUNT);
                    elb.attackEntityFrom(DamageSource.causeIndirectDamage(player, player), damage);
                    elb.applyKnockback(damage * 0.131F, (double) MathHelper.sin(player.rotationYaw * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(player.rotationYaw * ((float)Math.PI / 180F))));
                    elb.hurtResistantTime = 0;
                }
                tagList.remove(i);
                remove = true;
            }
        }
        if (remove)
            NBTUtils.setList(stack, HIT_LIST, tagList);
    }
}
