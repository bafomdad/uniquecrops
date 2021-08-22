package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Iterator;
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
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (this.canIgnoreGrowthRestrictions(world, pos)) {
            super.randomTick(state, world, pos, rand);
            return;
        }
        if (isMaxAge(state))
            transformBedrock(world, pos);

        super.randomTick(state, world, pos, rand);
    }

    private boolean transformBedrock(World world, BlockPos pos) {

        List<BlockPos> toConvert = new ArrayList<>();
        Iterable<BlockPos> poslist = BlockPos.betweenClosed(pos.offset(-RANGE, -RANGE, -RANGE), pos.offset(RANGE, 0, RANGE));
        Iterator posit = poslist.iterator();
        while (posit.hasNext()) {
            BlockPos looppos = (BlockPos)posit.next();
            if (!world.isEmptyBlock(looppos) && world.getBlockState(looppos).getBlock() == (UCConfig.COMMON.convertObsidian.get() ? Blocks.OBSIDIAN: Blocks.BEDROCK)) {
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
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {

        if (ctx.getClickedPos().above().getY() > 9) return Blocks.AIR.defaultBlockState();
        return super.getStateForPlacement(ctx);
    }
}
