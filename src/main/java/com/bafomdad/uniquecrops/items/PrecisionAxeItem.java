package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.enums.TierItem;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class PrecisionAxeItem extends AxeItem implements IBookUpgradeable {

    public PrecisionAxeItem() {

        super(TierItem.PRECISION, 5, -3.0F, UCItems.unstackable());
        MinecraftForge.EVENT_BUS.addListener(this::checkDrops);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flag) {

        if (stack.getItem() instanceof IBookUpgradeable) {
            if (((IBookUpgradeable)stack.getItem()).getLevel(stack) > -1)
                list.add(new TextComponent(ChatFormatting.GOLD + "+" + ((IBookUpgradeable)stack.getItem()).getLevel(stack)));
            else
                list.add(new TextComponent(ChatFormatting.GOLD + "Upgradeable"));
        }
    }

    private void checkDrops(LivingDropsEvent event) {

        if (!(event.getEntityLiving() instanceof Player) && event.getSource().getEntity() instanceof Player) {
            LivingEntity el = event.getEntityLiving();
            Player player = (Player)event.getSource().getEntity();
            ItemStack boots = el.getItemBySlot(EquipmentSlot.FEET);
            if (!boots.isEmpty() && player.getInventory().contains(new ItemStack(UCItems.SLIPPERGLASS.get()))) {
                if (player.level.random.nextInt(5) == 0) {
                    addDrop(event, new ItemStack(UCItems.GLASS_SLIPPERS.get()));
                    for (int i = 0; i < player.getInventory().items.size(); i++) {
                        ItemStack oneboot = player.getInventory().getItem(i);
                        if (oneboot.getItem() == UCItems.SLIPPERGLASS.get()) {
                            player.getInventory().setItem(i, ItemStack.EMPTY);
                            break;
                        }
                    }
                }
            }
            if (player.getMainHandItem().getItem() == this) {
                ItemStack axe = player.getMainHandItem();
                if (((IBookUpgradeable)axe.getItem()).isMaxLevel(axe)) {
                    Random rand = el.level.random;
                    int looting = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, player.getMainHandItem());
                    if (rand.nextInt(15) <= 2 + looting) {
                        if (el instanceof Skeleton)
                            addDrop(event, new ItemStack(Items.SKELETON_SKULL));
                        if (el instanceof WitherSkeleton)
                            addDrop(event, new ItemStack(Items.WITHER_SKELETON_SKULL));
                        if (el instanceof Zombie)
                            addDrop(event, new ItemStack(Items.ZOMBIE_HEAD));
                        if (el instanceof Creeper)
                            addDrop(event, new ItemStack(Items.CREEPER_HEAD));
                    }
                }
            }
        }
    }

    private void addDrop(LivingDropsEvent event, ItemStack drop) {

        ItemEntity ei = new ItemEntity(event.getEntityLiving().level, event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ(), drop);
        ei.setPickUpDelay(10);
        event.getDrops().add(ei);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {

        stack.enchant(Enchantments.SILK_TOUCH, 1);
    }
}
