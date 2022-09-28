package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.init.UCTiles;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.awt.*;
import java.util.Iterator;

public class TileHarvestTrap extends BaseTileUC {

    boolean hasSpirit;
    boolean collectedSpirit;
    int spiritTime = 0;
    int baitPower = 0;

    static final int RANGE = 4;

    public TileHarvestTrap(BlockPos pos, BlockState state) {

        super(UCTiles.HARVESTTRAP.get(), pos, state);
    }

    public void tickServer() {

        if (spiritTime <= 0) return;

        if (collectedSpirit && level.getGameTime() % 20 == 0) {
            tickCropGrowth();
            spiritTime--;
            if (spiritTime <= 0) {
                this.collectedSpirit = false;
                this.markBlockForUpdate();
                return;
            }
        } else if (!collectedSpirit) {
            spiritTime--;
        }
    }

    public void tickCropGrowth() {

        if (level.isClientSide) return;

        Iterable<BlockPos> posList = BlockPos.betweenClosed(worldPosition.offset(-RANGE, 0, -RANGE), worldPosition.offset(RANGE, 1, RANGE));
        Iterator<BlockPos> iterator = posList.iterator();
        while (iterator.hasNext()) {
            BlockPos loopPos = iterator.next();
            BlockState loopState = level.getBlockState(loopPos);
            if (loopState.getBlock() instanceof BonemealableBlock && ((BonemealableBlock)loopState.getBlock()).isValidBonemealTarget(level, loopPos, loopState, level.isClientSide)) {
                level.levelEvent(2005, loopPos, 0);
                loopState.getBlock().randomTick(loopState, (ServerLevel)level, loopPos, level.random);
            }
        }
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

        tag.putBoolean("UC:hasSpirit", this.hasSpirit);
        tag.putBoolean("UC:collectedSpirit", this.collectedSpirit);
        tag.putInt("UC:spiritTime", this.spiritTime);
    }

    @Override
    public void readCustomNBT(CompoundTag tag) {

        this.hasSpirit = tag.getBoolean("UC:hasSpirit");
        this.collectedSpirit = tag.getBoolean("UC:collectedSpirit");
        this.spiritTime = tag.getInt("UC:spiritTime");
    }

    public void setSpiritTime(int time) {

        this.spiritTime = time;
        this.markBlockForUpdate();
    }

    public void setCollected() {

        this.collectedSpirit = true;
    }

    public boolean hasSpirit() {

        return this.spiritTime > 0;
    }

    public boolean isCollected() {

        return this.collectedSpirit;
    }

    public int getBaitPower() {

        return this.baitPower;
    }

    public void setBaitPower(int power) {

        this.baitPower = power;
    }

    public float[] getSpiritColor() {

        return collectedSpirit ? new float[] { (float) Color.GREEN.getRed(), (float)Color.GREEN.getGreen(), (float)Color.GREEN.getBlue() } : new float[] { (float)Color.ORANGE.getRed(), (float)Color.ORANGE.getGreen(), (float)Color.ORANGE.getBlue() };
    }
}
