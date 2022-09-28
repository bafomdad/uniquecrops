package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class BrassKnucklesItem extends SwordItem {

    private static final String HIT_LIST = "UC:hitList";
    private static final String HIT_ENTITY = "UC:hitEntityId";
    private static final String HIT_TIME = "UC:hitTime";
    private static final String HIT_AMOUNT = "UC:hitAmount";

    public BrassKnucklesItem() {

        super(Tiers.IRON, 1, 1.31F, UCItems.unstackable());
        MinecraftForge.EVENT_BUS.addListener(this::knuckleDuster);
    }

    private void knuckleDuster(LivingAttackEvent event) {

        if (event.getEntityLiving().level.isClientSide) return;
        if (event.getEntityLiving() instanceof LivingEntity && event.getSource().getDirectEntity() instanceof Player) {
            Player player = (Player)event.getSource().getDirectEntity();
            ItemStack brassKnuckles = player.getMainHandItem();
            if (brassKnuckles.getItem() == this) {
                boolean flag = event.getSource() instanceof IndirectEntityDamageSource;
                if (flag) return;
//                boolean flag = NBTUtils.getList(brassKnuckles, HIT_LIST, 10, true) != null && NBTUtils.getList(brassKnuckles, HIT_LIST, 10, true).isEmpty();
//                if (!flag) return;
                float damage = event.getAmount();
                addHitEntity(event.getEntityLiving(), brassKnuckles, damage);
                event.setCanceled(true);
                BlockPos pos = event.getEntityLiving().blockPosition();
                UCPacketHandler.sendToNearbyPlayers(player.level, player.blockPosition(), new PacketUCEffect(EnumParticle.CRIT, pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, 6));
                return;
            }
        }
    }

    @Override
    public Rarity getRarity(ItemStack stack) {

        return Rarity.RARE;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {

        if (!world.isClientSide && entity instanceof Player)
            removeHitEntity(stack, world, (Player)entity, selected);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return !ItemStack.isSame(oldStack, newStack);
    }

    private void addHitEntity(LivingEntity target, ItemStack stack, float damage) {

        ListTag tagList = NBTUtils.getList(stack, HIT_LIST, 10, false);
        if (tagList.size() > 4) return;

        CompoundTag nbt = new CompoundTag();
        nbt.putInt(HIT_ENTITY, target.getId());
        nbt.putInt(HIT_TIME, 25);
        nbt.putFloat(HIT_AMOUNT, damage);
        tagList.add(nbt);
        NBTUtils.setList(stack, HIT_LIST, tagList);
    }

    private void removeHitEntity(ItemStack stack, Level world, Player player, boolean selected) {

        ListTag tagList = NBTUtils.getList(stack, HIT_LIST, 10, true);
        if (tagList == null || tagList.isEmpty()) return;

        boolean remove = false;
        if (!selected) {
            tagList.clear();
            return;
        }
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag nbt = tagList.getCompound(i);
            int timer = nbt.getInt(HIT_TIME);
            if (timer > 0)
                nbt.putInt(HIT_TIME, --timer);
            else {
                LivingEntity elb = (LivingEntity)world.getEntity(nbt.getInt(HIT_ENTITY));
                if (elb != null) {
                    float damage = nbt.getFloat(HIT_AMOUNT);
                    elb.hurt(DamageSource.indirectMobAttack(player, player), damage);
                    elb.knockback(damage * 0.131F, (double) Mth.sin(player.yRotO * ((float)Math.PI / 180F)), (double)(-Mth.cos(player.yRotO * ((float)Math.PI / 180F))));
                    elb.invulnerableTime = 0;
                }
                tagList.remove(i);
                remove = true;
            }
        }
        if (remove)
            NBTUtils.setList(stack, HIT_LIST, tagList);
    }
}
