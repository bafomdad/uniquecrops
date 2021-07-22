package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.enums.TierItem;
import com.bafomdad.uniquecrops.init.UCItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import javax.annotation.Nullable;
import java.util.List;

public class PrecisionPickaxeItem extends PickaxeItem implements IBookUpgradeable {

    public PrecisionPickaxeItem() {

        super(TierItem.PRECISION, 1, -2.8F, UCItems.unstackable().addToolType(ToolType.PICKAXE, TierItem.PRECISION.getHarvestLevel()));
        MinecraftForge.EVENT_BUS.addListener(this::breakSpawner);
        MinecraftForge.EVENT_BUS.addListener(this::placeSpawner);
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

    private void breakSpawner(BlockEvent.BreakEvent event) {

        if (event.getPlayer() == null) return;
        boolean flag = event.getPlayer().getHeldItemMainhand().getItem() == this;
        if (!flag) return;

        ItemStack pick = event.getPlayer().getHeldItemMainhand();
        if (!this.isMaxLevel(pick)) return;

        if (event.getState().getBlock() == Blocks.SPAWNER) {
            event.setCanceled(true);
            TileEntity tile = event.getWorld().getTileEntity(event.getPos());
            if (tile instanceof MobSpawnerTileEntity) {
                ItemStack stack = new ItemStack(event.getState().getBlock());
                if (!event.getWorld().isRemote() && event.getWorld() instanceof World) {
                    NBTUtils.setCompound(stack, "Spawner", tile.serializeNBT());
                    InventoryHelper.spawnItemStack((World)event.getWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, stack);
                }
            }
            event.getWorld().removeBlock(event.getPos(), false);
            if (event.getPlayer() instanceof ServerPlayerEntity)
                event.getPlayer().getHeldItemMainhand().attemptDamageItem(1, event.getWorld().getRandom(), (ServerPlayerEntity)event.getPlayer());
        }
    }

    private void placeSpawner(PlayerInteractEvent.RightClickBlock event) {

        if (event.getItemStack().getItem()!= Blocks.SPAWNER.asItem()) return;
        if (!(event.getEntity() instanceof PlayerEntity)) return;
        if (!(event.getWorld() instanceof World)) return;

        ItemStack stack = ((PlayerEntity)event.getEntity()).getHeldItem(event.getHand());
        if (stack.getItem() == Blocks.SPAWNER.asItem() && stack.hasTag() && stack.getTag().contains("Spawner")) {
            BlockState spawner = Blocks.SPAWNER.getDefaultState();
            BlockPos pos = event.getPos().offset(event.getFace());
            event.getWorld().setBlockState(pos, spawner);
            TileEntity tile = event.getWorld().getTileEntity(pos);
            CompoundNBT tag = stack.getTag().getCompound("Spawner");
            tag.putInt("x", pos.getX());
            tag.putInt("y", pos.getY());
            tag.putInt("z", pos.getZ());
            tile.read(event.getWorld().getBlockState(pos), tag);
            event.getPlayer().swingArm(event.getHand());
            event.setCanceled(true);
        }
    }

    @Override
    public void onCreated(ItemStack stack, World world, PlayerEntity player) {

        stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
    }
}
