package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Random;

public class GoodieBagItem extends ItemBaseUC {

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        if (world instanceof ServerLevel) {
            if (stack.getItem() == this) {
                Random rand = new Random(((ServerLevel)world).getSeed() + LocalDateTime.now().getDayOfMonth());
                ItemStack prize = getHolidayItem(rand);
                ItemHandlerHelper.giveItemToPlayer(player, prize);
                stack.shrink(1);
            }
        }
        return InteractionResultHolder.consume(stack);
    }

    private ItemStack getHolidayItem(Random rand) {

        if (isHoliday()) {
            ItemStack stack;
            if (rand.nextBoolean())
                stack = new ItemStack(UCUtils.selectRandom(rand, ForgeRegistries.BLOCKS.getValues().stream().toList()));
            else
                stack = new ItemStack(UCUtils.selectRandom(rand, ForgeRegistries.ITEMS.getValues().stream().toList()));
            return stack;
        }
        return new ItemStack(UCItems.USELESS_LUMP.get());
    }

    public static boolean isHoliday() {

        LocalDateTime current = LocalDateTime.now();
        return current.getMonth() == Month.DECEMBER && current.getDayOfMonth() <= 25;
    }
}
