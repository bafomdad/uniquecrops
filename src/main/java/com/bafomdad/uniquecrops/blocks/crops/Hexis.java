package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Random;

public class Hexis extends BaseCropsBlock {

    public Hexis() {

        super(() -> Items.AIR, UCItems.HEXIS_SEED);
        setBonemealable(false);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {

        if (world.isClientSide || isMaxAge(state)) return;

        if (entity instanceof ExperienceOrb) {
            ExperienceOrb orb = (ExperienceOrb)entity;
            if (orb.isAlive() && orb.getValue() > 1) {
                int age = Math.min(getAge(state) + orb.getValue(), getMaxAge());
                orb.discard();
                world.setBlock(pos, setValueAge(age), 3);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (!isMaxAge(state)) {
            if (player.experienceLevel >= 1) {
                if (!player.isCreative())
                    player.giveExperienceLevels(-1);
                world.setBlock(pos, this.setValueAge(getAge(state) + 1), 3);
                return InteractionResult.SUCCESS;
            }
        }
        ItemStack bottle = player.getItemInHand(hand);
        if (bottle.getItem() == Items.GLASS_BOTTLE) {
            if (!world.isClientSide) {
                bottle.shrink(1);
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(Items.EXPERIENCE_BOTTLE));
                if (world.random.nextBoolean())
                    world.setBlock(pos, this.setValueAge(0), 3);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        //NO-OP
    }
}
