package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.init.UCEntities;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketOpenBook;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
public class EulaBookEntity extends ThrowableEntity implements IRendersAsItem {

    public EulaBookEntity(EntityType<EulaBookEntity> type, World world) {

        super(type, world);
    }

    public EulaBookEntity(LivingEntity thrower) {

        super(UCEntities.THROWABLE_BOOK.get(), thrower, thrower.world);
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void onImpact(RayTraceResult rtr) {

        if (!world.isRemote) {
            AxisAlignedBB aabb = this.getBoundingBox().grow(2.0D, 2.0D, 2.0D);
            List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, aabb);
            for (LivingEntity elb : entities) {
                if (elb instanceof PlayerEntity) {
                    double d0 = this.getDistanceSq(elb);
                    if (d0 < 4.0D) {
                        if (elb instanceof ServerPlayerEntity)
                            UCPacketHandler.sendTo((ServerPlayerEntity)elb, new PacketOpenBook(elb.getEntityId()));
                    }
                }
            }
            this.remove();
            if (rtr.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos pos = new BlockPos(((BlockRayTraceResult)rtr).getPos().offset(((BlockRayTraceResult)rtr).getFace()));
                ItemStack book = new ItemStack(UCItems.BOOK_EULA.get());
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), book);
            }
        }
    }

    @Override
    public ItemStack getItem() {

        return new ItemStack(UCItems.BOOK_EULA.get());
    }

    @Override
    public IPacket<?> createSpawnPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
