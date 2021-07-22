package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.DyeUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Dyeius extends BaseCropsBlock {

    public Dyeius() {

        super(() -> Items.BLUE_DYE, UCItems.DYEIUS_SEED);
    }

    @Override
    public void harvestItems(World world, BlockPos pos, BlockState state, int fortune) {

        InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, getDyeTime(world));
        InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, new ItemStack(this.getSeed()));
    }

    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {

        player.addStat(Stats.BLOCK_MINED.get(this));
        player.addExhaustion(0.005F);
        ItemStack seed = new ItemStack(getSeed());
        ItemStack dye = getDyeTime(world);

        spawnAsEntity(world, pos, dye);
        spawnAsEntity(world, pos, seed);
    }

    private ItemStack getDyeTime(World world) {

        long time = world.getDayTime() % 24000L;
        int meta = (int)(time / 1500);
        Item dye = DyeUtils.DYE_BY_COLOR.get(DyeColor.byId(meta)).asItem();

        return new ItemStack(dye);
    }
}
