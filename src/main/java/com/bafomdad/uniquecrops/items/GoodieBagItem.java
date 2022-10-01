package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
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
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        if (world instanceof ServerLevel) {
            if (stack.getItem() == this) {
                if (player.isCrouching()) {
                    if (player.getPersistentData().contains(UCStrings.TAG_ADVENT))
                        player.getPersistentData().remove(UCStrings.TAG_ADVENT);
                    return InteractionResultHolder.consume(stack);
                }
                Random rand = new Random(((ServerLevel)world).getSeed() + getDayOfMonth(player, false));
                ItemStack prize = getHolidayItem(player, rand);
                ItemHandlerHelper.giveItemToPlayer(player, prize);
                stack.shrink(1);
            }
        }
        return InteractionResultHolder.consume(stack);
    }

    private ItemStack getHolidayItem(Player player, Random rand) {

        if (isHolidayTime(player)) {
            ItemStack stack;
            if (rand.nextBoolean())
                stack = new ItemStack(UCUtils.selectRandom(rand, ForgeRegistries.BLOCKS.getValues().stream().toList()));
            else
                stack = new ItemStack(UCUtils.selectRandom(rand, ForgeRegistries.ITEMS.getValues().stream().toList()));
            return stack;
        }
        return new ItemStack(UCItems.USELESS_LUMP.get());
    }

    private int getDayOfMonth(Player player, boolean increment) {

        CompoundTag tag = player.getPersistentData();
        int currentDay = LocalDateTime.now().getDayOfMonth();
        int day;
        if (tag.contains(UCStrings.TAG_ADVENT)) {
            day = tag.getInt(UCStrings.TAG_ADVENT);
            if (day < currentDay && increment)
                tag.putInt(UCStrings.TAG_ADVENT, day + 1);
        } else {
            day = 1;
            tag.putInt(UCStrings.TAG_ADVENT, day);
        }
        return Mth.clamp(1, day, currentDay);
    }

    public static boolean isHoliday() {

        LocalDateTime current = LocalDateTime.now();
        return current.getMonth() == Month.DECEMBER && current.getDayOfMonth() <= 25;
    }

    public boolean isHolidayTime(Player player) {

        if (!isHoliday()) {
            if (player.getPersistentData().contains(UCStrings.TAG_ADVENT))
                player.getPersistentData().remove(UCStrings.TAG_ADVENT);
            return false;
        }
        return getDayOfMonth(player, true) < LocalDateTime.now().getDayOfMonth();
    }
}
