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

        super(TierItem.PRECISION, 1, -2.8F, UCItems.unstackable().addToolType(ToolType.PICKAXE, TierItem.PRECISION.getLevel()));
        MinecraftForge.EVENT_BUS.addListener(this::breakSpawner);
        MinecraftForge.EVENT_BUS.addListener(this::placeSpawner);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {

        if (stack.getItem() instanceof IBookUpgradeable) {
            if (((IBookUpgradeable)stack.getItem()).getLevel(stack) > -1)
                list.add(new StringTextComponent(TextFormatting.GOLD + "+" + ((IBookUpgradeable)stack.getItem()).getLevel(stack)));
            else
                list.add(new StringTextComponent(TextFormatting.GOLD + "Upgradeable"));
        }
    }

    private void breakSpawner(BlockEvent.BreakEvent event) {

        if (event.getPlayer() == null) return;
        boolean flag = event.getPlayer().getMainHandItem().getItem() == this;
        if (!flag) return;

        ItemStack pick = event.getPlayer().getMainHandItem();
        if (!this.isMaxLevel(pick)) return;

        if (event.getState().getBlock() == Blocks.SPAWNER) {
            event.setCanceled(true);
            TileEntity tile = event.getWorld().getBlockEntity(event.getPos());
            if (tile instanceof MobSpawnerTileEntity) {
                ItemStack stack = new ItemStack(event.getState().getBlock());
                if (!event.getWorld().isClientSide() && event.getWorld() instanceof World) {
                    NBTUtils.setCompound(stack, "Spawner", tile.serializeNBT());
                    InventoryHelper.dropItemStack((World)event.getWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, stack);
                }
            }
            event.getWorld().removeBlock(event.getPos(), false);
            if (event.getPlayer() instanceof ServerPlayerEntity)
                event.getPlayer().getMainHandItem().hurt(1, event.getWorld().getRandom(), (ServerPlayerEntity)event.getPlayer());
        }
    }

    private void placeSpawner(PlayerInteractEvent.RightClickBlock event) {

        if (event.getItemStack().getItem()!= Blocks.SPAWNER.asItem()) return;
        if (!(event.getEntity() instanceof PlayerEntity)) return;
        if (!(event.getWorld() instanceof World)) return;

        ItemStack stack = ((PlayerEntity)event.getEntity()).getItemInHand(event.getHand());
        if (stack.getItem() == Blocks.SPAWNER.asItem() && stack.hasTag() && stack.getTag().contains("Spawner")) {
            BlockState spawner = Blocks.SPAWNER.defaultBlockState();
            BlockPos pos = event.getPos().relative(event.getFace());
            event.getWorld().setBlockAndUpdate(pos, spawner);
            TileEntity tile = event.getWorld().getBlockEntity(pos);
            CompoundNBT tag = stack.getTag().getCompound("Spawner");
            tag.putInt("x", pos.getX());
            tag.putInt("y", pos.getY());
            tag.putInt("z", pos.getZ());
            tile.load(event.getWorld().getBlockState(pos), tag);
            event.getPlayer().swing(event.getHand());
            event.setCanceled(true);
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, World world, PlayerEntity player) {

        stack.enchant(Enchantments.SILK_TOUCH, 1);
    }
}
