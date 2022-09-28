package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Petramia extends BaseCropsBlock {

    private static final int RANGE = 3;

    public Petramia() {

        super(() -> Items.AIR, UCItems.PETRAMIA_SEED);
        setClickHarvest(false);
        setIgnoreGrowthRestrictions(true);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        if (this.canIgnoreGrowthRestrictions(world, pos)) {
            super.randomTick(state, world, pos, rand);
            return;
        }
        if (isMaxAge(state))
            transformBedrock(world, pos);

        super.randomTick(state, world, pos, rand);
    }

    private void transformBedrock(Level world, BlockPos pos) {

        List<BlockPos> toConvert = new ArrayList<>();
        Iterable<BlockPos> poslist = BlockPos.betweenClosed(pos.offset(-RANGE, -RANGE, -RANGE), pos.offset(RANGE, 0, RANGE));
        for (BlockPos looppos : poslist) {
            if (!world.isEmptyBlock(looppos) && world.getBlockState(looppos).getBlock() == (UCConfig.COMMON.convertObsidian.get() ? Blocks.OBSIDIAN : Blocks.BEDROCK)) {
                if (world.random.nextBoolean()) {
                    toConvert.add(looppos.immutable());
                }
            }
        }
        if (!toConvert.isEmpty()) {
            for (BlockPos loopPos : UCUtils.makeCollection(toConvert, true)) {
                if (world.random.nextBoolean()) {
                    world.setBlock(loopPos, UCBlocks.DARK_BLOCK.get().defaultBlockState(), 2);
                    UCPacketHandler.sendToNearbyPlayers(world, loopPos, new PacketUCEffect(EnumParticle.CLOUD, loopPos.getX(), loopPos.getY() + 0.5, loopPos.getZ(), 6));
                    return;
                }
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        if (ctx.getClickedPos().above().getY() > (ctx.getLevel().getMinBuildHeight() + 9)) return Blocks.AIR.defaultBlockState();
        return super.getStateForPlacement(ctx);
    }
}
