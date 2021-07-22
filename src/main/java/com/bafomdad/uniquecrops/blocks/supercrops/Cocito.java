package com.bafomdad.uniquecrops.blocks.supercrops;

import com.bafomdad.uniquecrops.blocks.BaseSuperCropsBlock;
import com.bafomdad.uniquecrops.entities.CookingItemEntity;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

public class Cocito extends BaseSuperCropsBlock {

    public Cocito() {

        super(Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(5.0F, 1000.0F).sound(SoundType.CROP).setLightLevel(i -> 15));
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {

        world.getPendingBlockTicks().scheduleTick(pos, this, 20);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        this.cookNearbyThings(world, pos);
    }

    private void cookNearbyThings(ServerWorld world, BlockPos pos) {

        List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos.add(-4, 0, -4), pos.add(4, 1, 4)));
        for (ItemEntity ei : items) {
            if (ei.isAlive() && !(ei instanceof CookingItemEntity)) {
                if (ei.getItem().getItem() == UCItems.USELESS_LUMP.get()) continue;
                CookingItemEntity eic = new CookingItemEntity(world, ei, ei.getItem());
                world.addEntity(eic);
                ei.remove();
            }
        }
        world.getPendingBlockTicks().scheduleTick(pos, this, 20);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {

        if (rand.nextInt(2) == 0)
            world.addParticle(ParticleTypes.LAVA, pos.getX() + rand.nextFloat(), pos.getY(), pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
    }
}
