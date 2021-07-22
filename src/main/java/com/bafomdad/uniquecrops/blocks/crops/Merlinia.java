package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class Merlinia extends BaseCropsBlock {

    public Merlinia() {

        super(UCItems.TIMEDUST, UCItems.MERLINIA_SEED);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        if (this.getAge(state) == 0) {
            if (!world.isRemote) {
                world.setBlockState(pos, this.withAge(getMaxAge()), 3);
                int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand());
                this.harvestItems(world, pos, state, 0);
            }
            return ActionResultType.SUCCESS;
        }
        return merliniaGrowth(state, world, pos, player);
    }

    private ActionResultType merliniaGrowth(BlockState state, World world, BlockPos pos, PlayerEntity player) {

        ItemStack timemeal = player.getHeldItemMainhand();
        if (timemeal.getItem() == UCItems.TIMEMEAL.get()) {
            if (!world.isRemote) {
                int i = Math.max(this.getAge(state) - this.getBonemealAgeIncrease(world), 0);
                world.setBlockState(pos, this.withAge(i), 2);
                world.playEvent(2005, pos, 0);
                if (!player.isCreative())
                    timemeal.shrink(1);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext ctx) {

        return this.withAge(getMaxAge());
    }
}
