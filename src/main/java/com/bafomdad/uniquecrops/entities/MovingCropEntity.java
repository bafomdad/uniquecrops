package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.blocks.crops.Magnes;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class MovingCropEntity extends Entity {

    Direction dir;
    int distance;

    public MovingCropEntity(EntityType<MovingCropEntity> type, Level world) {

        super(type, world);
        this.setNoGravity(true);
        this.setInvisible(true);
        this.setInvulnerable(true);
    }

    public void setFacingAndDistance(Direction facing, int dist) {

        this.dir = facing;
        this.distance = dist;
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    public void tick() {

        if (dir == null) return;

        double vel = 0.2625D;
        if (this.getPassengers().isEmpty() && !this.getPersistentData().contains("UC:markedForDrop")) {
            this.discard();
            return;
        }
        if (this.getPersistentData().contains("UC:markedForDrop")) {
            if (!level.isClientSide) {
                BlockState heldState = ((FallingBlockEntity)this.getPassengers().get(0)).getBlockState();
                this.getPassengers().forEach(p -> p.discard());
                int chance = Math.max(8 - this.distance, 1);
                if (level.random.nextInt(chance) == 0 && heldState.getBlock() == UCBlocks.MAGNES_CROP.get() && heldState.getValue(Magnes.POLARITY))
                    Containers.dropItemStack(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(UCItems.FERROMAGNETICIRON.get()));
                BlockPos toSet = new BlockPos(Math.floor(this.getX()), Math.floor(this.getY()), Math.floor(this.getZ()));
                if (!level.isEmptyBlock(toSet)) {
                    for (Direction facing : Direction.Plane.HORIZONTAL) {
                        if (level.isEmptyBlock(toSet.relative(facing)) && level.getBlockState(toSet.relative(facing).below()).getBlock() instanceof FarmBlock) {
                            level.setBlock(toSet.relative(facing), UCBlocks.MAGNES_CROP.get().defaultBlockState(), 2);
                            break;
                        }
                    }
                } else {
                    level.setBlock(toSet, UCBlocks.MAGNES_CROP.get().defaultBlockState(), 2);
                }
                this.getPersistentData().remove("UC:markedForDrop");
            }
            return;
        }
        List<MovingCropEntity> entities = level.getEntitiesOfClass(MovingCropEntity.class, this.getBoundingBox());
        if (entities.size() == 2) {
            entities.forEach(e -> this.getPersistentData().putBoolean("UC:markedForDrop", true));
        }
        int vecx = this.dir.getNormal().getX();
        int vecy = this.dir.getNormal().getY();
        int vecz = this.dir.getNormal().getZ();
        this.move(MoverType.SELF, new Vec3(vecx * vel, vecy * vel, vecz * vel));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {

        this.dir = Direction.from3DDataValue(tag.getByte("UC_facing"));
        this.distance = tag.getByte("UC_distance");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {

        if (dir != null)
            tag.putByte("UC_facing", (byte)this.dir.ordinal());
        tag.putByte("UC_distance", (byte)this.distance);
    }

    @Override
    public Packet<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isAttackable() {

        return false;
    }
}
