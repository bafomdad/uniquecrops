package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityTeleportEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enderlily extends BaseCropsBlock {

    public Enderlily() {

        super(UCItems.LILYTWINE, UCItems.ENDERLILY_SEED);
        MinecraftForge.EVENT_BUS.addListener(this::onEnderpearl);
    }

    private void onEnderpearl(EntityTeleportEvent.EnderPearl event) {

        if (event.getAttackDamage() > 0) {
            BlockPos targetPos = new BlockPos(event.getTarget());
            BlockPos.betweenClosed(targetPos.offset(-2, -1, -2), targetPos.offset(2, 1, 2))
                    .forEach(loopPos -> {
                        BlockState loopState = event.getPlayer().level.getBlockState(loopPos);
                        if (loopState.getBlock() == this) {
                            if (this.isEnderlilyGrown(event.getPlayer().level, loopPos, loopState))
                                return;
                        }
                    });
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        this.enderlilyTele(world, pos, state);
        super.randomTick(state, world, pos, rand);
    }

    @Override
    public void performBonemeal(ServerLevel world, Random rand, BlockPos pos, BlockState state) {

        this.enderlilyTele(world, pos, state);
    }

    private boolean isEnderlilyGrown(Level world, BlockPos pos, BlockState state) {

        if (isMaxAge(state)) {
            for (int i = 0; i <= world.random.nextInt(1) + 1; i++)
                Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.ENDER_PEARL));
            world.setBlock(pos, this.setValueAge(0), 3);
            return true;
        }
        return false;
    }

    private void enderlilyTele(ServerLevel world, BlockPos pos, BlockState state) {

        if (isMaxAge(state)) return;

        List<BlockPos> targetList = new ArrayList<>();
        for (BlockPos loopPos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 0, 4))) {
            Block loopBlock = world.getBlockState(loopPos).getBlock();
            if ((world.isEmptyBlock(loopPos) || (loopBlock instanceof BonemealableBlock && loopBlock != this)) && world.getBlockState(loopPos.below()).getBlock() instanceof FarmBlock) {
                targetList.add(loopPos.immutable());
            }
        }
        targetList = UCUtils.makeCollection(targetList, true);
        for (BlockPos loopPos : targetList) {
            if (world.random.nextInt(7) == 0) {
                BlockState saveState = world.getBlockState(loopPos);
                world.setBlock(loopPos, this.setValueAge(getAge(state) + 1), 2);
                world.setBlockAndUpdate(pos, saveState);
                UCPacketHandler.sendToNearbyPlayers(world, loopPos, new PacketUCEffect(EnumParticle.PORTAL, loopPos.getX(), loopPos.getY(), loopPos.getZ(), 6));
                UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.PORTAL, pos.getX(), pos.getY(), pos.getZ(), 6));
                return;
            }
        }
    }
}
