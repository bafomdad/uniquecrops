package com.bafomdad.uniquecrops.blocks.tiles;

import com.bafomdad.uniquecrops.blocks.supercrops.Itero;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCTiles;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketDispatcher;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;

public class TileItero extends BaseTileUC implements ITickableTileEntity {

    public static final BlockPos[] PLATES =  {
            new BlockPos(-2, 0, -2), new BlockPos(2, 0, 2),
            new BlockPos(-2, 0, 2), new BlockPos(2, 0, -2)
    };

    boolean showDemo = false;
    int gameIndex = 0;
    int[] gameCombos;

    public TileItero() {

        super(UCTiles.ITERO.get());
    }

    @Override
    public void tick() {

        if (!level.isClientSide && level.getGameTime() % 40 == 0 && showDemo) {
            if (gameCombos != null && gameCombos.length > 0) {
                if (gameIndex >= gameCombos.length) {
                    gameIndex = 0;
                    showDemo = false;
                    this.setChanged();
                    UCPacketDispatcher.dispatchTEToNearbyPlayers(this);
                    return;
                }
                BlockPos platePos = this.worldPosition.subtract(PLATES[gameCombos[gameIndex]]);
                BlockState plate = level.getBlockState(platePos);
                if (plate.getBlock() == Blocks.STONE_PRESSURE_PLATE) {
                    level.setBlock(platePos, plate.setValue(PressurePlateBlock.POWERED, true), 3);
                    level.getBlockTicks().scheduleTick(platePos, plate.getBlock(), 20);
                }
                gameIndex++;
            }
        }
    }

    public int getDemoPlatePos() {

        if (!this.showingDemo() || gameCombos == null || gameCombos.length == 0) return -1;

        return this.gameCombos[gameIndex];
    }

    public boolean createCombos(int age) {

        if (gameCombos != null)
            return false;

        int rand = 4 + level.random.nextInt(age + 1);
        gameCombos = new int[rand];
        for (int i = 0; i < gameCombos.length; i++) {
            gameCombos[i] = level.random.nextInt(PLATES.length);
        }
        return true;
    }

    public void matchCombo(BlockPos pos) {

        if (showDemo || gameCombos == null) return;

        BlockPos subPos = this.worldPosition.subtract(pos);
        if (gameIndex >= gameCombos.length) {
            reset();
            return;
        }
        if (PLATES[gameCombos[gameIndex]].equals(subPos)) {
            if (++this.gameIndex >= this.gameCombos.length) {
                UCPacketHandler.sendToNearbyPlayers(level, this.worldPosition, new PacketUCEffect(EnumParticle.END_ROD, this.worldPosition.getX(), this.worldPosition.getY() + 0.3, this.worldPosition.getZ(), 4));
                advanceStage();
                reset();
                return;
            }
            UCPacketHandler.sendToNearbyPlayers(level, this.worldPosition, new PacketUCEffect(EnumParticle.HEART, this.worldPosition.getX(), this.worldPosition.getY() + 0.3, this.worldPosition.getZ(), 0));
            return;
        } else {
            UCPacketHandler.sendToNearbyPlayers(level, this.worldPosition, new PacketUCEffect(EnumParticle.EXPLOSION, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.3, this.worldPosition.getZ() + 0.5, 0));
            regressStage();
            reset();
        }
    }

    public void tryShowDemo() {

        if ((gameCombos != null && gameCombos.length > 0) && gameIndex == 0) {
            showDemo = true;
            UCPacketDispatcher.dispatchTEToNearbyPlayers(this);
        }
    }

    public void regressStage() {

        int age = getBlockState().getValue(Itero.AGE);
        level.setBlockAndUpdate(this.worldPosition, getBlockState().setValue(Itero.AGE, Math.max(--age, 0)));
    }

    public void advanceStage() {

        level.setBlock(this.worldPosition, getBlockState().setValue(Itero.AGE, getBlockState().getValue(Itero.AGE) + 1), 3);
    }

    public void reset() {

        gameCombos = null;
        gameIndex = 0;
    }

    public boolean showingDemo() {

        return showDemo;
    }

    @Override
    public void readCustomNBT(CompoundNBT tag) {

        this.showDemo = tag.getBoolean("showDemo");
        this.gameIndex = tag.getInt("gameIndex");
        if (this.gameCombos != null)
            this.gameCombos = tag.getIntArray("gameCombos");
    }

    @Override
    public void writeCustomNBT(CompoundNBT tag) {

        tag.putBoolean("showDemo", this.showDemo);
        tag.putInt("gameIndex", this.gameIndex);
        if (this.gameCombos != null)
            tag.putIntArray("gameCombos", this.gameCombos);
    }
}
