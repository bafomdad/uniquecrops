package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCEntities;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = ItemSupplier.class
)
public class WeepingEyeEntity extends ThrowableProjectile implements ItemSupplier {

    public WeepingEyeEntity(EntityType<WeepingEyeEntity> type, Level world) {

        super(type, world);
    }

    public WeepingEyeEntity(LivingEntity thrower) {

        super(UCEntities.WEEPING_EYE.get(), thrower, thrower.level);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void onHit(HitResult rtr) {

        if (!level.isClientSide) {
            BlockPos pos = new BlockPos(rtr.getLocation());
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.offset(-10, -5, -10), pos.offset(10, 5, 10)));
            for (LivingEntity ent : entities) {
                if (ent.isAlive() && (ent instanceof Monster || ent instanceof Slime))
                    ent.addEffect(new MobEffectInstance(MobEffects.GLOWING, 300));
            }
            UCPacketHandler.sendToNearbyPlayers(level, pos, new PacketUCEffect(EnumParticle.CLOUD, pos.getX() - 0.5D, pos.getY() + 0.1D, pos.getZ() - 0.5D, 5));
        }
    }

    @Override
    public ItemStack getItem() {

        return new ItemStack(UCItems.WEEPINGEYE.get());
    }

    @Override
    public Packet<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
