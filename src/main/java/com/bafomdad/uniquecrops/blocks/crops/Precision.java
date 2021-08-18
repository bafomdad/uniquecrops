package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Precision extends BaseCropsBlock {

    public Precision() {

        super(UCItems.PRENUGGET, UCItems.PRECISION_SEED);
        this.setBonemealable(false);
        this.setIncludeSeed(false);
    }

    @Override
    public int getHarvestAge() {

        return 6;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        if (this.getAge(state) != 6) return ActionResultType.PASS;

        if (!world.isClientSide) {
            world.setBlock(pos, this.setValueAge(0), 3);
            int fortune = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, player.getMainHandItem());
            harvestItems(world, pos, state, fortune);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {

        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getRawBrightness(pos, 0) >= 9 && worldIn.getRawBrightness(pos.above(), 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, worldIn, pos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int)(25.0F / f) + 1) == 0)) {
                    worldIn.setBlock(pos, this.setValueAge(i + 1), 3); // flag set to 3 instead of 2 to cause redstone updates
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            }
        }
    }

    @Override
    public int getSignal(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {

        if (this.getAge(state) != 6)
            return 0;

        return 15;
    }

    @Override
    public boolean isSignalSource(BlockState state) {

        return this.getAge(state) == 6;
    }
}
