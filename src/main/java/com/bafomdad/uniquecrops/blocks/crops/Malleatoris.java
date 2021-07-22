package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class Malleatoris extends BaseCropsBlock {

    public Malleatoris() {

        super(Blocks.ANVIL::asItem, UCItems.MALLEATORIS_SEED);
        setBonemealable(false);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {

        if (this.getAge(state) < getMaxAge()) return ActionResultType.PASS;

        if (!world.isRemote && hand == Hand.MAIN_HAND) {
            ItemStack stacky = player.getHeldItemMainhand();
            if (stacky.isEmpty() || (!stacky.isEmpty() && !stacky.isDamaged())) return ActionResultType.PASS;

            int repair = stacky.getMaxDamage() / 2;
            stacky.setDamage(Math.max(stacky.getDamage() - repair, 0));
            world.setBlockState(pos, this.withAge(0), 2);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {

        if (!world.isRemote) {
            AxisAlignedBB aabb = new AxisAlignedBB(pos.add(-4, 0, -4), pos.add(4, 1, 4));
            List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, aabb);
            for (ItemEntity item : items) {
                if (calcGrowth(item, rand)) {
                    world.setBlockState(pos, this.withAge(getAge(state) + 1), 2);
                    return;
                }
            }
        }
    }

    private boolean calcGrowth(ItemEntity stack, Random rand) {

        if (!stack.getItem().getItem().isRepairable(stack.getItem())) return false;

        double damage = stack.getItem().getMaxDamage() * 0.1;
        double weight = damage / 2;
        double chance = Math.random() * 100;
        if (chance < weight) {
            int newDamage = (int)damage + stack.getItem().getDamage();
            if (newDamage < stack.getItem().getMaxDamage()) {
                stack.getItem().setDamage(stack.getItem().getDamage() + (int)damage);
                UCPacketHandler.sendToNearbyPlayers(stack.world, stack.getPosition(), new PacketUCEffect(EnumParticle.CLOUD, stack.getPosX(), stack.getPosY(), stack.getPosZ(), 6));
                return true;
            }
        }
        return false;
    }
}
