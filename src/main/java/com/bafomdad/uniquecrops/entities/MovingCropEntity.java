package com.bafomdad.uniquecrops.entities;

import com.bafomdad.uniquecrops.blocks.crops.Magnes;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class MovingCropEntity extends Entity {

    Direction dir;
    int distance;

    public MovingCropEntity(EntityType<MovingCropEntity> type, World world) {

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
    protected void registerData() {}

    @Override
    public void tick() {

        if (dir == null) return;

        double vel = 0.2625D;
        if (this.getPassengers().isEmpty() && !this.getPersistentData().contains("UC:markedForDrop")) {
            this.remove();
            return;
        }
        if (this.getPersistentData().contains("UC:markedForDrop")) {
            if (!world.isRemote) {
                BlockState heldState = ((FallingBlockEntity)this.getPassengers().get(0)).getBlockState();
                this.getPassengers().forEach(p -> p.remove());
                int chance = Math.max(8 - this.distance, 1);
                if (world.rand.nextInt(chance) == 0 && heldState.getBlock() == UCBlocks.MAGNES_CROP.get() && heldState.get(Magnes.POLARITY))
                    InventoryHelper.spawnItemStack(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(UCItems.FERROMAGNETICIRON.get()));
                BlockPos toSet = new BlockPos(Math.floor(this.getPosX()), Math.floor(this.getPosY()), Math.floor(this.getPosZ()));
                if (!world.isAirBlock(toSet)) {
                    for (Direction facing : Direction.Plane.HORIZONTAL) {
                        if (world.isAirBlock(toSet.offset(facing)) && world.getBlockState(toSet.offset(facing).down()).getBlock() instanceof FarmlandBlock) {
                            world.setBlockState(toSet.offset(facing), UCBlocks.MAGNES_CROP.get().getDefaultState(), 2);
                            break;
                        }
                    }
                } else {
                    world.setBlockState(toSet, UCBlocks.MAGNES_CROP.get().getDefaultState(), 2);
                }
                this.getPersistentData().remove("UC:markedForDrop");
            }
            return;
        }
        List<MovingCropEntity> entities = world.getEntitiesWithinAABB(MovingCropEntity.class, this.getBoundingBox());
        if (entities.size() == 2) {
            entities.forEach(e -> this.getPersistentData().putBoolean("UC:markedForDrop", true));
        }
        int vecx = this.dir.getDirectionVec().getX();
        int vecy = this.dir.getDirectionVec().getY();
        int vecz = this.dir.getDirectionVec().getZ();
        this.move(MoverType.SELF, new Vector3d(vecx * vel, vecy * vel, vecz * vel));
    }

    @Override
    protected void readAdditional(CompoundNBT tag) {

        this.dir = Direction.byIndex(tag.getByte("UC_facing"));
        this.distance = tag.getByte("UC_distance");
    }

    @Override
    protected void writeAdditional(CompoundNBT tag) {

        if (dir != null)
            tag.putByte("UC_facing", (byte)this.dir.getIndex());
        tag.putByte("UC_distance", (byte)this.distance);
    }

    @Override
    public IPacket<?> createSpawnPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean canBeAttackedWithItem() {

        return false;
    }
}
