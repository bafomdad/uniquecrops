package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileDigger;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class Fossura extends BaseCropsBlock {

    public Fossura() {

        super(() -> Items.AIR, UCItems.QUARRY_SEED, Properties.from(Blocks.WHEAT).hardnessAndResistance(8.0F, 0.0F));
        setClickHarvest(false);
        setBonemealable(false);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {

        if (!this.isMaxAge(state) && !world.isRemote) {
            ItemStack heldItem = player.getHeldItemMainhand();
            if (heldItem.getItem() instanceof PickaxeItem) {
                player.world.playEvent(2001, pos, Block.getStateId(state));
                world.setBlockState(pos, this.withAge(this.getAge(state) + 1), 3);
                return false;
            }
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader world, BlockPos pos) {

        if (player.getHeldItemMainhand().isEmpty())
            return 1.0F;
        return super.getPlayerRelativeBlockHardness(state, player, world, pos);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {

        return false;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return isMaxAge(state);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileDigger();
    }
}