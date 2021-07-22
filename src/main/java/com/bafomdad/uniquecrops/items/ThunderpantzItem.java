package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.enums.EnumArmorMaterial;
import com.bafomdad.uniquecrops.items.base.ItemArmorUC;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class ThunderpantzItem extends ItemArmorUC implements IBookUpgradeable {

    private static final String TAG_CHARGE = "UC:pantsCharge";
    private static final float MAX_CHARGE = 32.0F;

    public ThunderpantzItem() {

        super(EnumArmorMaterial.THUNDERPANTZ, EquipmentSlotType.LEGS);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingAttack);
    }

    private void onLivingAttack(LivingAttackEvent event) {

        if (!(event.getEntityLiving() instanceof PlayerEntity)) return;

        PlayerEntity player = (PlayerEntity)event.getEntityLiving();
        if (event.getSource().getImmediateSource() instanceof LivingEntity) {
            LivingEntity el = (LivingEntity)event.getSource().getImmediateSource();
            ItemStack pants = player.getItemStackFromSlot(EquipmentSlotType.LEGS);
            if (pants.getItem() == this) {
                if (getCharge(pants) < 1F) return;

                event.setCanceled(true);
                float toDamage = getCharge(pants);
                LightningBoltEntity bolt = EntityType.LIGHTNING_BOLT.create(el.world);
                bolt.setEffectOnly(true);
                player.world.addEntity(bolt);
                el.attackEntityFrom(DamageSource.LIGHTNING_BOLT, toDamage);
                setCharge(pants, 0F);
                return;
            }
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {

        if (world.isRemote) return;
        if (getCharge(stack) >= MAX_CHARGE) return;

        if (player.isOnGround() && player.isSneaking()) {
            BlockPos pos = new BlockPos(MathHelper.floor(player.getPosX()), MathHelper.floor(player.getPosY()), MathHelper.floor(player.getPosZ()));
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof CarpetBlock) {
                if (world.rand.nextInt(11 - Math.max(this.getLevel(stack), 0)) == 0)
                    setCharge(stack, getCharge(stack) + world.rand.nextFloat());
            }
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {

        return false;
    }

    public void setCharge(ItemStack stack, float f) {

        NBTUtils.setFloat(stack, TAG_CHARGE, f);
    }

    public float getCharge(ItemStack stack) {

        return NBTUtils.getFloat(stack, TAG_CHARGE, 0);
    }
}
