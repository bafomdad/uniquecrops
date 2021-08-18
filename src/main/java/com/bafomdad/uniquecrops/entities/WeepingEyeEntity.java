package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCEntities;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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
public class WeepingEyeEntity extends ThrowableEntity implements IRendersAsItem {

    public WeepingEyeEntity(EntityType<WeepingEyeEntity> type, World world) {

        super(type, world);
    }

    public WeepingEyeEntity(LivingEntity thrower) {

        super(UCEntities.WEEPING_EYE.get(), thrower, thrower.level);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void onHit(RayTraceResult rtr) {

        if (!level.isClientSide) {
            BlockPos pos = new BlockPos(rtr.getLocation());
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(pos.offset(-10, -5, -10), pos.offset(10, 5, 10)));
            for (LivingEntity ent : entities) {
                if (ent.isAlive() && (ent instanceof MonsterEntity || ent instanceof SlimeEntity))
                    ent.addEffect(new EffectInstance(Effects.GLOWING, 300));
            }
            UCPacketHandler.sendToNearbyPlayers(level, pos, new PacketUCEffect(EnumParticle.CLOUD, pos.getX() - 0.5D, pos.getY() + 0.1D, pos.getZ() - 0.5D, 5));
        }
    }

    @Override
    public ItemStack getItem() {

        return new ItemStack(UCItems.WEEPINGEYE.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
