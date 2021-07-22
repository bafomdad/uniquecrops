package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.enums.TierItem;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class PrecisionAxeItem extends AxeItem implements IBookUpgradeable {

    public PrecisionAxeItem() {

        super(TierItem.PRECISION, 5, -3.0F, UCItems.unstackable().addToolType(ToolType.AXE, TierItem.PRECISION.getHarvestLevel()));
        MinecraftForge.EVENT_BUS.addListener(this::checkDrops);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {

        if (stack.getItem() instanceof IBookUpgradeable) {
            if (((IBookUpgradeable)stack.getItem()).getLevel(stack) > -1)
                list.add(new StringTextComponent(TextFormatting.GOLD + "+" + ((IBookUpgradeable)stack.getItem()).getLevel(stack)));
            else
                list.add(new StringTextComponent(TextFormatting.GOLD + "Upgradeable"));
        }
    }

    private void checkDrops(LivingDropsEvent event) {

        if (!(event.getEntityLiving() instanceof PlayerEntity) && event.getSource().getTrueSource() instanceof PlayerEntity) {
            LivingEntity el = event.getEntityLiving();
            PlayerEntity player = (PlayerEntity)event.getSource().getTrueSource();
            ItemStack boots = el.getItemStackFromSlot(EquipmentSlotType.FEET);
            if (!boots.isEmpty() && player.inventory.hasItemStack(new ItemStack(UCItems.SLIPPERGLASS.get()))) {
                if (player.world.rand.nextInt(5) == 0) {
                    addDrop(event, new ItemStack(UCItems.GLASS_SLIPPERS.get()));
                    for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
                        ItemStack oneboot = player.inventory.getStackInSlot(i);
                        if (oneboot.getItem() == UCItems.SLIPPERGLASS.get()) {
                            player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                            break;
                        }
                    }
                }
            }
            if (player.getHeldItemMainhand().getItem() == this) {
                ItemStack axe = player.getHeldItemMainhand();
                if (((IBookUpgradeable)axe.getItem()).isMaxLevel(axe)) {
                    Random rand = el.world.rand;
                    int looting = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand());
                    if (rand.nextInt(15) <= 2 + looting) {
                        if (el instanceof SkeletonEntity)
                            addDrop(event, new ItemStack(Items.SKELETON_SKULL));
                        if (el instanceof WitherSkeletonEntity)
                            addDrop(event, new ItemStack(Items.WITHER_SKELETON_SKULL));
                        if (el instanceof ZombieEntity)
                            addDrop(event, new ItemStack(Items.ZOMBIE_HEAD));
                        if (el instanceof CreeperEntity)
                            addDrop(event, new ItemStack(Items.CREEPER_HEAD));
                    }
                }
            }
        }
    }

    private void addDrop(LivingDropsEvent event, ItemStack drop) {

        ItemEntity ei = new ItemEntity(event.getEntityLiving().world, event.getEntityLiving().getPosX(), event.getEntityLiving().getPosY(), event.getEntityLiving().getPosZ(), drop);
        ei.setPickupDelay(10);
        event.getDrops().add(ei);
    }

    @Override
    public void onCreated(ItemStack stack, World world, PlayerEntity player) {

        stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
    }
}
