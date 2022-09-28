package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.init.UCEntities;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketOpenBook;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
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
public class EulaBookEntity extends ThrowableProjectile implements ItemSupplier {

    public EulaBookEntity(EntityType<EulaBookEntity> type, Level world) {

        super(type, world);
    }

    public EulaBookEntity(LivingEntity thrower) {

        super(UCEntities.THROWABLE_BOOK.get(), thrower, thrower.level);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void onHit(HitResult rtr) {

        if (!level.isClientSide) {
            AABB aabb = this.getBoundingBox().inflate(2.0D, 2.0D, 2.0D);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, aabb);
            for (LivingEntity elb : entities) {
                if (elb instanceof Player) {
                    double d0 = this.distanceToSqr(elb);
                    if (d0 < 4.0D) {
                        if (elb instanceof ServerPlayer)
                            UCPacketHandler.sendTo((ServerPlayer)elb, new PacketOpenBook(elb.getId()));
                    }
                }
            }
            this.discard();
            if (rtr.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = new BlockPos(((BlockHitResult)rtr).getBlockPos().relative(((BlockHitResult)rtr).getDirection()));
                ItemStack book = new ItemStack(UCItems.BOOK_EULA.get());
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), book);
            }
        }
    }

    @Override
    public ItemStack getItem() {

        return new ItemStack(UCItems.BOOK_EULA.get());
    }

    @Override
    public Packet<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
