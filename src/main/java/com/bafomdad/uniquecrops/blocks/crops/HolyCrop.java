package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class HolyCrop extends BaseCropsBlock {

    public HolyCrop() {

        super(() -> Items.APPLE, UCItems.BLESSED_SEED, Properties.from(Blocks.WHEAT).setLightLevel(l -> l.get(AGE) == 7 ? 15 : 0));
        setBonemealable(false);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {

        if (entity instanceof LivingEntity) {
            boolean blessed = isMaxAge(state);
            if (!blessed) {
                if (!(entity instanceof VillagerEntity)) return;

                VillagerProfession prof = ((VillagerEntity)entity).getVillagerData().getProfession();
                if (prof == VillagerProfession.CLERIC) {
                    world.setBlockState(pos, withAge(getMaxAge()), 3);
                }
            }
            if (((LivingEntity)entity).getCreatureAttribute() == CreatureAttribute.UNDEAD) {
                entity.setFire(2);
                entity.attackEntityFrom(DamageSource.MAGIC, blessed ? 3.0F : 1.5F);
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {

        // NO-OP
    }
}
