package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.blocks.tiles.TileIndustria;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.BeanBatteryItem;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.CapabilityEnergy;

import java.util.Random;

public class Industria extends BaseCropsBlock implements EntityBlock {

    public Industria() {

        super(UCItems.BEAN_BATTERY, UCItems.INDUSTRIA_SEED);
        setBonemealable(false);
        setClickHarvest(false);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {

        // NO-OP
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {

        if (isMaxAge(state))
            dropBeans(world, pos);

        super.onRemove(state, world, pos, newState, isMoving);
    }

    private void dropBeans(Level world, BlockPos pos) {

        int loopSize = 1 + world.random.nextInt(1);
        for (int i = 0; i < loopSize; i++) {
            ItemStack bean = new ItemStack(UCItems.BEAN_BATTERY.get());
            if (bean.getCapability(CapabilityEnergy.ENERGY).isPresent()) {
                ((BeanBatteryItem)bean.getItem()).setEnergyStored(bean, 500 / loopSize);
            }
            Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), bean);
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return new TileIndustria(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {

        if (!level.isClientSide()) {
            return (lvl, pos, st, te) -> {
                if (te instanceof TileIndustria industria) industria.tickServer();
            };
        }
        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {

        for (int i = 0; i < this.getAge(state) + 1; i++) {
            double d0 = (double)pos.getX() + rand.nextFloat();
            double d1 = (double)pos.getY() + 0.1F;
            double d2 = (double)pos.getZ() + rand.nextFloat();
            world.addParticle(DustParticleOptions.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
