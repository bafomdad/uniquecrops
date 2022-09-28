package com.bafomdad.uniquecrops.core.enums;

import com.bafomdad.uniquecrops.init.UCBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

public enum EnumLily {

    ENDER(ParticleTypes.PORTAL) {

        @Override
        public void collide(BlockState state, Level world, BlockPos pos, Entity entity) {
            if (entity instanceof Player) {
                if (world.getGameTime() % 20 == 0) {
                    if (entity.isOnGround() && entity.isCrouching())
                        searchNearbyPads(world, pos, entity, Direction.DOWN);
                }
            }
        }
    },
    ICE(ParticleTypes.ITEM_SNOWBALL) {
        @Override
        public void collide(BlockState state, Level world, BlockPos pos, Entity entity) {

            if (entity instanceof ItemEntity) {
                ItemEntity ei = (ItemEntity)entity;
                if (ei.isAlive() && ei.getItem().getEntityLifespan(world) > -6000)
                    ei.setExtendedLifetime();
            }
        }

        @Override
        public boolean isValidGround(BlockState state, BlockGetter reader, BlockPos pos) {

            FluidState fluidUp = reader.getFluidState(pos.above());
            return (state.getMaterial() == Material.ICE || state.getMaterial() == Material.ICE_SOLID) && fluidUp.getType() == Fluids.EMPTY;
        }
    },
    JUNGLE(ParticleTypes.ITEM_SLIME) {
        @Override
        public void collide(BlockState state, Level world, BlockPos pos, Entity entity) {

            if (entity instanceof Player && entity.fallDistance > 0.0F)
                entity.fallDistance = 0.0F;
        }
    },
    LAVA(ParticleTypes.FLAME) {
        @Override
        public void collide(BlockState state, Level world, BlockPos pos, Entity entity) {

            if (entity instanceof Player) {
                Player player = (Player)entity;
                if (!player.isCreative()) {
                    ItemStack boots = player.getInventory().armor.get(0);
                    if (boots.isEmpty())
                        player.setSecondsOnFire(3);
                }
            }
            else if (entity instanceof LivingEntity) {
                if (!entity.fireImmune())
                    entity.setSecondsOnFire(3);
            }
        }

        @Override
        public boolean isValidGround(BlockState state, BlockGetter reader, BlockPos pos) {

            FluidState fluid = reader.getFluidState(pos);
            FluidState fluidUp = reader.getFluidState(pos.above());
            return (fluid.getType() == Fluids.LAVA || state.getMaterial() == Material.LAVA) && fluidUp.getType() == Fluids.EMPTY;
        }
    };

    final ParticleOptions type;

    EnumLily(ParticleOptions type) {

        this.type = type;
    }

    public ParticleOptions getParticle() {

        return type;
    }

    public abstract void collide(BlockState state, Level world, BlockPos pos, Entity entity);

    public boolean isValidGround(BlockState state, BlockGetter reader, BlockPos pos) {

        FluidState fluid = reader.getFluidState(pos);
        FluidState fluidUp = reader.getFluidState(pos.above());
        return (fluid.getType() == Fluids.WATER || state.getMaterial() == Material.ICE) && fluidUp.getType() == Fluids.EMPTY;
    }

    public static void searchNearbyPads(Level world, BlockPos pos, Entity entity, Direction dir) {

        for (int i = 1; i < 12; i++) {
            BlockPos loopPos = pos.relative(dir, i);
            if (world.getBlockState(loopPos).getBlock() == UCBlocks.LILY_ENDER.get()) {
                entity.teleportTo(pos.getX() + 0.5, loopPos.above().getY(), loopPos.getZ() + 0.5);
                return;
            }
        }
    }
}
