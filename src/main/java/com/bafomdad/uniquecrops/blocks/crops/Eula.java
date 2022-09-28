package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketOpenBook;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.nbt.CompoundTag;

public class Eula extends BaseCropsBlock {

    public Eula() {

        super(UCItems.LEGALSTUFF, UCItems.EULA_SEED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        CompoundTag data = ctx.getPlayer().getPersistentData();
        if (!data.contains(UCStrings.TAG_EULA)) {
            if (!ctx.getLevel().isClientSide) {
                data.putBoolean(UCStrings.TAG_EULA, true);
                if (ctx.getPlayer() instanceof ServerPlayer)
                    UCPacketHandler.sendTo((ServerPlayer)ctx.getPlayer(), new PacketOpenBook(ctx.getPlayer().getId()));
            }
        }
        return super.getStateForPlacement(ctx);
    }
}
