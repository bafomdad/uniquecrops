package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketOpenBook;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.nbt.CompoundNBT;

public class Eula extends BaseCropsBlock {

    public Eula() {

        super(UCItems.LEGALSTUFF, UCItems.EULA_SEED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {

        CompoundNBT data = ctx.getPlayer().getPersistentData();
        if (!data.contains(UCStrings.TAG_EULA)) {
            if (!ctx.getLevel().isClientSide) {
                data.putBoolean(UCStrings.TAG_EULA, true);
                if (ctx.getPlayer() instanceof ServerPlayerEntity)
                    UCPacketHandler.sendTo((ServerPlayerEntity)ctx.getPlayer(), new PacketOpenBook(ctx.getPlayer().getId()));
            }
        }
        return super.getStateForPlacement(ctx);
    }
}
