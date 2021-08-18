package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Random;

public class Hexis extends BaseCropsBlock {

    public Hexis() {

        super(() -> Items.AIR, UCItems.HEXIS_SEED);
        setBonemealable(false);
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {

        if (world.isClientSide || isMaxAge(state)) return;

        if (entity instanceof ExperienceOrbEntity) {
            ExperienceOrbEntity orb = (ExperienceOrbEntity)entity;
            if (orb.isAlive() && orb.getValue() > 1) {
                int age = Math.min(getAge(state) + orb.getValue(), getMaxAge());
                orb.remove();
                world.setBlock(pos, setValueAge(age), 3);
            }
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        if (!isMaxAge(state)) {
            if (player.experienceLevel >= 1) {
                if (!player.isCreative())
                    player.giveExperienceLevels(-1);
                world.setBlock(pos, this.setValueAge(getAge(state) + 1), 3);
                return ActionResultType.SUCCESS;
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
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        //NO-OP
    }
}
