package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;

public class Merlinia extends BaseCropsBlock {

    public Merlinia() {

        super(UCItems.TIMEDUST, UCItems.MERLINIA_SEED);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (this.getAge(state) == 0) {
            if (!world.isClientSide) {
                world.setBlock(pos, this.setValueAge(getMaxAge()), 3);
                int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, player.getMainHandItem());
                this.harvestItems(world, pos, state, 0);
            }
            return InteractionResult.SUCCESS;
        }
        return merliniaGrowth(state, world, pos, player);
    }

    private InteractionResult merliniaGrowth(BlockState state, Level world, BlockPos pos, Player player) {

        ItemStack timemeal = player.getMainHandItem();
        if (timemeal.getItem() == UCItems.TIMEMEAL.get()) {
            if (!world.isClientSide) {
                int i = Math.max(this.getAge(state) - this.getBonemealAgeIncrease(world), 0);
                world.setBlock(pos, this.setValueAge(i), 2);
                world.levelEvent(2005, pos, 0);
                if (!player.isCreative())
                    timemeal.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        return this.setValueAge(getMaxAge());
    }
}
