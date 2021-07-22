package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Dirigible extends BaseCropsBlock {

    public Dirigible() {

        super(UCItems.DIRIGIBLEPLUM, UCItems.DIRIGIBLE_SEED);
        setBonemealable(false);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        super.randomTick(state, world, pos, rand);
        if ((this.getAge(state) + 1) >= getMaxAge()) {
            InventoryHelper.spawnItemStack(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, new ItemStack(UCItems.DIRIGIBLEPLUM.get()));
            world.setBlockState(pos, withAge(0), 2);
            UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.CLOUD, pos.getX(), pos.getY() + 0.1D, pos.getZ(), 4));
        }
    }
}
