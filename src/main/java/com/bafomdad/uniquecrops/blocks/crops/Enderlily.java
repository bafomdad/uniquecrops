package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EntityTeleportEvent;

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
            BlockPos.getAllInBoxMutable(targetPos.add(-2, -1, -2), targetPos.add(2, 1, 2))
                    .forEach(loopPos -> {
                        BlockState loopState = event.getPlayer().world.getBlockState(loopPos);
                        if (loopState.getBlock() == this) {
                            if (this.isEnderlilyGrown(event.getPlayer().world, loopPos, loopState))
                                return;
                        }
                    });
        }
    }

    @Override
    public void tickCrop(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        this.enderlilyTele(world, pos, state);
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {

        this.enderlilyTele(world, pos, state);
    }

    private boolean isEnderlilyGrown(World world, BlockPos pos, BlockState state) {

        if (isMaxAge(state)) {
            for (int i = 0; i <= world.rand.nextInt(1) + 1; i++)
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.ENDER_PEARL));
            world.setBlockState(pos, this.withAge(0), 3);
            return true;
        }
        return false;
    }

    private void enderlilyTele(ServerWorld world, BlockPos pos, BlockState state) {

        List<BlockPos> targetList = new ArrayList<>();
        for (BlockPos loopPos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 0, 4))) {
            Block loopBlock = world.getBlockState(loopPos).getBlock();
            if ((world.isAirBlock(loopPos) || (loopBlock instanceof IGrowable && loopBlock != this)) && world.getBlockState(loopPos.down()).getBlock() instanceof FarmlandBlock) {
                targetList.add(loopPos.toImmutable());
            }
        }
        targetList = UCUtils.makeCollection(targetList, true);
        for (BlockPos loopPos : targetList) {
            if (world.rand.nextInt(7) == 0) {
                BlockState saveState = world.getBlockState(loopPos);
                world.setBlockState(loopPos, this.withAge(getAge(state) + 1), 2);
                world.setBlockState(pos, saveState);
                UCPacketHandler.sendToNearbyPlayers(world, loopPos, new PacketUCEffect(EnumParticle.PORTAL, loopPos.getX(), loopPos.getY(), loopPos.getZ(), 6));
                UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.PORTAL, pos.getX(), pos.getY(), pos.getZ(), 6));
                return;
            }
        }
    }
}
