package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileFeroxia;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.core.enums.EnumGrowthSteps;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;
import java.util.Random;

public class Feroxia extends BaseCropsBlock implements EntityBlock {

    public Feroxia() {

        super(UCItems.SAVAGEESSENCE, UCItems.FEROXIA_SEED);
        setClickHarvest(false);
    }

    @Override
    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, BlockEntity tile, ItemStack stack) {

        if (isMaxAge(state) && !(player instanceof FakePlayer))
            UCUtils.generateSteps(player);
        super.playerDestroy(world, player, pos, state, tile, stack);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

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
    public void performBonemeal(ServerLevel world, Random rand, BlockPos pos, BlockState state) {

        int stage = getStage(world, pos, state);

        if (stage != -1 && EnumGrowthSteps.values()[stage].canAdvance(world, pos, state))
            world.setBlock(pos, this.setValueAge(getAge(state) + 1), 3);
    }

    private int getStage(Level world, BlockPos pos, BlockState state) {

        BlockEntity tile = world.getBlockEntity(pos);
        if (!(tile instanceof TileFeroxia)) return -1;

        TileFeroxia te = (TileFeroxia)tile;
        if (te.getOwner() == null || (te.getOwner() != null && UCUtils.getPlayerFromUUID(te.getOwner().toString()) == null)) return -1;

        ListTag taglist = UCUtils.getServerTaglist(te.getOwner());
        if (taglist == null) return -1;

        CompoundTag tag = taglist.getCompound(this.getAge(state));
        return tag.getInt("stage" + this.getAge(state));
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof TileFeroxia) {
            world.levelEvent(2001, pos, Block.getId(state));
            if (placer instanceof Player && !(placer instanceof FakePlayer)) {
                if (!world.isClientSide) {
                    ((TileFeroxia)te).setOwner(placer.getUUID());
                    if (!placer.getPersistentData().contains(UCStrings.TAG_GROWTHSTAGES))
                        UCUtils.generateSteps((Player)placer);
                }
            }
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return new TileFeroxia(pos, state);
    }
}
