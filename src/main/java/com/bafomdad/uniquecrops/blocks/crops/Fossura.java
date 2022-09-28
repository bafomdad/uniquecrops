package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileDigger;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.Nullable;

public class Fossura extends BaseCropsBlock implements EntityBlock {

    public Fossura() {

        super(() -> Items.AIR, UCItems.QUARRY_SEED, Properties.copy(Blocks.WHEAT).strength(8.0F, 0.0F));
        setClickHarvest(false);
        setBonemealable(false);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {

        if (!this.isMaxAge(state) && !world.isClientSide) {
            ItemStack heldItem = player.getMainHandItem();
            if (heldItem.getItem() instanceof PickaxeItem) {
                player.level.levelEvent(2001, pos, Block.getId(state));
                world.setBlock(pos, this.setValueAge(this.getAge(state) + 1), 3);
                return false;
            }
        }
        return super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter world, BlockPos pos) {

        if (player.getMainHandItem().isEmpty())
            return 1.0F;
        return super.getDestroyProgress(state, player, world, pos);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {

        return false;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return isMaxAge(state) ? new TileDigger(pos, state) : null;
    }
}
