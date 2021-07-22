package com.bafomdad.uniquecrops.core.enums;

import com.bafomdad.uniquecrops.init.UCBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public enum EnumLily {

    ENDER(ParticleTypes.PORTAL) {

        @Override
        public void collide(BlockState state, World world, BlockPos pos, Entity entity) {
            if (entity instanceof PlayerEntity) {
                if (world.getGameTime() % 20 == 0) {
                    if (entity.isOnGround() && entity.isSneaking())
                        searchNearbyPads(world, pos, entity, Direction.DOWN);
                }
            }
        }
    },
    ICE(ParticleTypes.ITEM_SNOWBALL) {
        @Override
        public void collide(BlockState state, World world, BlockPos pos, Entity entity) {

            if (entity instanceof ItemEntity) {
                ItemEntity ei = (ItemEntity)entity;
                if (ei.isAlive() && ei.getItem().getEntityLifespan(world) > -6000)
                    ei.setNoDespawn();
            }
        }

        @Override
        public boolean isValidGround(BlockState state, IBlockReader reader, BlockPos pos) {

            FluidState fluidUp = reader.getFluidState(pos.up());
            return (state.getMaterial() == Material.ICE || state.getMaterial() == Material.PACKED_ICE) && fluidUp.getFluid() == Fluids.EMPTY;
        }
    },
    JUNGLE(ParticleTypes.ITEM_SLIME) {
        @Override
        public void collide(BlockState state, World world, BlockPos pos, Entity entity) {

            if (entity instanceof PlayerEntity && entity.fallDistance > 0.0F)
                entity.fallDistance = 0.0F;
        }
    },
    LAVA(ParticleTypes.FLAME) {
        @Override
        public void collide(BlockState state, World world, BlockPos pos, Entity entity) {

            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity)entity;
                if (!player.isCreative()) {
                    ItemStack boots = player.inventory.armorInventory.get(0);
                    if (boots.isEmpty())
                        player.setFire(3);
                }
            }
            else if (entity instanceof LivingEntity) {
                if (!entity.isImmuneToFire())
                    entity.setFire(3);
            }
        }

        @Override
        public boolean isValidGround(BlockState state, IBlockReader reader, BlockPos pos) {

            FluidState fluid = reader.getFluidState(pos);
            FluidState fluidUp = reader.getFluidState(pos.up());
            return (fluid.getFluid() == Fluids.LAVA || state.getMaterial() == Material.LAVA) && fluidUp.getFluid() == Fluids.EMPTY;
        }
    };

    final IParticleData type;

    EnumLily(IParticleData type) {

        this.type = type;
    }

    public IParticleData getParticle() {

        return type;
    }

    public abstract void collide(BlockState state, World world, BlockPos pos, Entity entity);

    public boolean isValidGround(BlockState state, IBlockReader reader, BlockPos pos) {

        FluidState fluid = reader.getFluidState(pos);
        FluidState fluidUp = reader.getFluidState(pos.up());
        return (fluid.getFluid() == Fluids.WATER || state.getMaterial() == Material.ICE) && fluidUp.getFluid() == Fluids.EMPTY;
    }

    public static void searchNearbyPads(World world, BlockPos pos, Entity entity, Direction dir) {

        for (int i = 1; i < 12; i++) {
            BlockPos loopPos = pos.offset(dir, i);
            if (world.getBlockState(loopPos).getBlock() == UCBlocks.LILY_ENDER.get()) {
                entity.setPositionAndUpdate(pos.getX() + 0.5, loopPos.up().getY(), loopPos.getZ() + 0.5);
                return;
            }
        }
    }
}
