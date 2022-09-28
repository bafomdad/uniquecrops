package com.bafomdad.uniquecrops.blocks.supercrops;

import com.bafomdad.uniquecrops.blocks.BaseSuperCropsBlock;
import com.bafomdad.uniquecrops.entities.CookingItemEntity;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class Cocito extends BaseSuperCropsBlock {

    public Cocito() {

        super(Properties.of(Material.PLANT).noCollission().strength(5.0F, 1000.0F).sound(SoundType.CROP).lightLevel(i -> 15).randomTicks());
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {

        world.scheduleTick(pos, this, 20);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {

        if (!world.getBlockTicks().hasScheduledTick(pos, this))
            cookNearbyThings(world, pos);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        this.cookNearbyThings(world, pos);
    }

    private void cookNearbyThings(ServerLevel world, BlockPos pos) {

        List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, new AABB(pos.offset(-4, 0, -4), pos.offset(4, 1, 4)));
        for (ItemEntity ei : items) {
            if (ei.isAlive() && !(ei instanceof CookingItemEntity)) {
                if (ei.getItem().getItem() == UCItems.USELESS_LUMP.get()) continue;
                CookingItemEntity eic = new CookingItemEntity(world, ei, ei.getItem());
                world.addFreshEntity(eic);
                ei.discard();
            }
        }
        world.scheduleTick(pos, this, 20);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {

        if (rand.nextInt(2) == 0)
            world.addParticle(ParticleTypes.LAVA, pos.getX() + rand.nextFloat(), pos.getY(), pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
    }
}
