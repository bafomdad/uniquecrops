package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;

import java.util.List;

public class TileWeepingBells extends BaseTileUC implements ITickableTileEntity {

    private boolean looking = false;
    private static final int RANGE = 10;

    public TileWeepingBells() {

        super(UCTiles.WEEPINGBELLS.get());
    }

    @Override
    public void tick() {

        if (level.isClientSide || level.getGameTime() % 10L != 0) return;

        boolean wasLooking = this.isLooking();
        boolean looker = false;

        List<PlayerEntity> players = level.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB(worldPosition.offset(-RANGE, -RANGE, -RANGE), worldPosition.offset(RANGE, RANGE, RANGE)));
        for (PlayerEntity player : players) {
            ItemStack helm = player.getItemBySlot(EquipmentSlotType.HEAD);
            if (helm.getItem().isEnderMask(helm, player, null)) continue;

            RayTraceResult rtr = player.pick(RANGE, 0, false);
            if (rtr != null && rtr.getType() == RayTraceResult.Type.BLOCK && ((BlockRayTraceResult)rtr).getBlockPos().equals(this.getBlockPos())) {
                looker = true;
                break;
            }
            if (!wasLooking && !player.isCreative() && getBlockState().getValue(BaseCropsBlock.AGE) >= 7)
                player.hurt(DamageSource.OUT_OF_WORLD, 1.0F);
        }
        if (looker != wasLooking)
            setLooking(looker);
    }

    public boolean isLooking() {

        return this.looking;
    }

    private void setLooking(boolean flag) {

        this.looking = flag;
    }

    @Override
    public void writeCustomNBT(CompoundNBT tag) {

        tag.putBoolean("UC:tagLooking", this.looking);
    }

    @Override
    public void readCustomNBT(CompoundNBT tag) {

        this.looking = tag.getBoolean("UC:tagLooking");
    }
}
