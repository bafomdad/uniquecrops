package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileFeroxia;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumGrowthSteps;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;
import java.util.Random;

public class Feroxia extends BaseCropsBlock {

    public Feroxia() {

        super(UCItems.SAVAGEESSENCE, UCItems.FEROXIA_SEED);
        setClickHarvest(false);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {

        if (isMaxAge(state) && !(player instanceof FakePlayer))
            UCUtils.generateSteps(player);
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (isMaxAge(state)) return;
        if (this.canIgnoreGrowthRestrictions(world, pos)) {
            super.randomTick(state, world, pos, rand);
            return;
        }
        int stage = getStage(world, pos, state);
        if (stage == -1) return;

        if (!EnumGrowthSteps.values()[stage].canAdvance(world, pos, state)) return;

        if (rand.nextInt(3) == 0)
            world.setBlock(pos, this.setValueAge(getAge(state) + 1), 0);
    }

    @Override
    public void performBonemeal(ServerWorld world, Random rand, BlockPos pos, BlockState state) {

        int stage = getStage(world, pos, state);

        if (stage != -1 && EnumGrowthSteps.values()[stage].canAdvance(world, pos, state))
            world.setBlock(pos, this.setValueAge(getAge(state) + 1), 3);
    }

    private int getStage(World world, BlockPos pos, BlockState state) {

        TileEntity tile = world.getBlockEntity(pos);
        if (!(tile instanceof TileFeroxia)) return -1;

        TileFeroxia te = (TileFeroxia)tile;
        if (te.getOwner() == null || (te.getOwner() != null && UCUtils.getPlayerFromUUID(te.getOwner().toString()) == null)) return -1;

        ListNBT taglist = UCUtils.getServerTaglist(te.getOwner());
        if (taglist == null) return -1;

        CompoundNBT tag = taglist.getCompound(this.getAge(state));
        return tag.getInt("stage" + this.getAge(state));
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        TileEntity te = world.getBlockEntity(pos);
        if (te instanceof TileFeroxia) {
            world.levelEvent(2001, pos, Block.getId(state));
            if (placer instanceof PlayerEntity && !(placer instanceof FakePlayer)) {
                if (!world.isClientSide) {
                    ((TileFeroxia)te).setOwner(placer.getUUID());
                    if (!placer.getPersistentData().contains(UCStrings.TAG_GROWTHSTAGES))
                        UCUtils.generateSteps((PlayerEntity)placer);
                }
            }
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {

        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return new TileFeroxia();
    }
}
