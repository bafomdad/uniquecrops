package com.bafomdad.uniquecrops.blocks.crops;

import com.bafomdad.uniquecrops.blocks.BaseCropsBlock;
import com.bafomdad.uniquecrops.core.enums.EnumParticle;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.List;
import java.util.Random;

public class Malleatoris extends BaseCropsBlock {

    public Malleatoris() {

        super(Blocks.ANVIL::asItem, UCItems.MALLEATORIS_SEED);
        setBonemealable(false);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (this.getAge(state) < getMaxAge()) return InteractionResult.PASS;

        if (!world.isClientSide && hand == InteractionHand.MAIN_HAND) {
            ItemStack stacky = player.getMainHandItem();
            if (stacky.isEmpty() || (!stacky.isEmpty() && !stacky.isDamaged())) return InteractionResult.PASS;

            int repair = stacky.getMaxDamage() / 2;
            stacky.setDamageValue(Math.max(stacky.getDamageValue() - repair, 0));
            world.setBlock(pos, this.setValueAge(0), 2);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        if (this.isMaxAge(state)) return;

        if (!world.isClientSide) {
            AABB aabb = new AABB(pos.offset(-4, 0, -4), pos.offset(4, 1, 4));
            List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, aabb);
            for (ItemEntity item : items) {
                if (calcGrowth(item, rand)) {
                    world.setBlock(pos, this.setValueAge(getAge(state) + 1), 2);
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
            int newDamage = (int)damage + stack.getItem().getDamageValue();
            if (newDamage < stack.getItem().getMaxDamage()) {
                stack.getItem().setDamageValue(stack.getItem().getDamageValue() + (int)damage);
                UCPacketHandler.sendToNearbyPlayers(stack.level, stack.blockPosition(), new PacketUCEffect(EnumParticle.CLOUD, stack.getX(), stack.getY(), stack.getZ(), 6));
                return true;
            }
        }
        return false;
    }
}
