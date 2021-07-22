package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.entities.BattleCropEntity;
import com.bafomdad.uniquecrops.init.UCEntities;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DonutSteel extends BaseCropsBlock {

    public DonutSteel() {

        super(UCItems.STEEL_DONUT, UCItems.DONUTSTEEL_SEED);
        setBonemealable(false);
        setClickHarvest(false);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {

        if (player != null && isMaxAge(state)) {
            BattleCropEntity ent = UCEntities.BATTLE_CROP.get().create(world);
            ent.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            if (!world.isRemote) {
                world.addEntity(ent);
                UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.CLOUD, pos.getX(), pos.getY() + 0.3D, pos.getZ(), 6));
                world.removeBlock(pos, false);
            }
            return false;
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }
}
