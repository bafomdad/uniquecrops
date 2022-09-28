package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCTiles;
import com.bafomdad.uniquecrops.network.UCPacketDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.UUID;

public class TileExedo extends BaseTileUC {

    int searchTime = 100;
    int wiggleTime;
    public int timeAfterWiggle;
    public final int maxTime = 15;
    public boolean isWiggling = false;

    boolean foundEntity = false;
    private UUID entityId;
    public LivingEntity ent;

    public TileExedo(BlockPos pos, BlockState state) {

        super(UCTiles.EXEDO.get(), pos, state);
    }

    public void tickServer() {

        ++timeAfterWiggle;
//        if (!level.isClientSide && timeAfterWiggle >= maxTime)
//            nomAndDrop();

        if (!level.isClientSide && isWiggling && --wiggleTime <= 0) {
            isWiggling = false;
            UCPacketDispatcher.dispatchTEToNearbyPlayers(this);
        }
        if (level.getGameTime() % (searchTime - level.random.nextInt(20)) == 0) {
            if (!isWiggling) {
                LivingEntity elb = getTargetedEntity();
                if (elb != null) {
                    if (foundEntity) {
                        chomp();
                        return;
                    }
                    entityId = elb.getUUID();
                    wiggle();
                }
            }
        }
    }

    private void wiggle() {

        wiggleTime = 20;
        foundEntity = true;
        isWiggling = true;
        UCPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }

    private void chomp() {

        timeAfterWiggle = 0;
        foundEntity = false;
        this.setChanged();
        nomAndDrop();
        UCPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }

    private void nomAndDrop() {

        LivingEntity elb = UCUtils.getTaggedEntity(entityId);
        if (elb != null && elb.isAlive()) {
            float f = (float) Mth.atan2(elb.getZ() - worldPosition.getZ(), elb.getX() - worldPosition.getX());
            EvokerFangs evoke = new EvokerFangs(level, elb.getX(), elb.getY(), elb.getZ(), f, 0, null);
            level.addFreshEntity(evoke);
            elb.hurt(DamageSource.MAGIC, elb.getMaxHealth());
        }
        entityId = null;
    }

    private LivingEntity getTargetedEntity() {

        if (!level.isClientSide) {
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(worldPosition.offset(-5, -1, -5), worldPosition.offset(5, 2, 5)));
            for (LivingEntity elb : entities) {
                if (!(elb instanceof Player) && !elb.isInvulnerable()) {
                    entityId = elb.getUUID();
                    return elb;
                }
            }
        }
        return null;
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

        tag.putBoolean("UC:wiggle", this.isWiggling);
        if (entityId != null)
            tag.putString("UC:targetEntity", entityId.toString());
        else
            tag.remove("UC:targetEntity");
    }

    @Override
    public void readCustomNBT(CompoundTag tag) {

        this.isWiggling = tag.getBoolean("UC:wiggle");
        if (tag.contains("UC:targetEntity"))
            entityId = UUID.fromString(tag.getString("UC:targetEntity"));
    }
}
