package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class RubiksCubeItem extends ItemBaseUC {

    @Override
    public Rarity getRarity(ItemStack stack) {

        return Rarity.EPIC;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {

        if (ctx.getWorld().getDimensionKey() == World.OVERWORLD && ctx.getPlayer().isSneaking() && ctx.getFace() == Direction.UP) {
            ItemStack stack = ctx.getItem();
            if (stack.getItem() == this) {
                int rot = getRotation(stack);
                BlockPos savedPos = ctx.getPos().up();
                if (!ctx.getWorld().isRemote) {
                    this.savePosition(stack, rot, savedPos);
                    ctx.getPlayer().sendMessage(new StringTextComponent("Teleport position saved"), ctx.getPlayer().getUniqueID());
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        ItemStack cube = player.getHeldItemMainhand();
        if (cube.getItem() == this) {
            if (!world.isRemote) {
                UniqueCrops.proxy.openCube();
            }
            return ActionResult.resultSuccess(cube);
        }
        return ActionResult.resultPass(cube);
    }

    public void saveRotation(ItemStack stack, int rotation) {

        NBTUtils.setInt(stack, UCStrings.TAG_CUBE_ROTATION, rotation);
    }

    public int getRotation(ItemStack stack) {

        if (stack.isEmpty()) return 0;
        return NBTUtils.getInt(stack, UCStrings.TAG_CUBE_ROTATION, 2);
    }

    public void savePosition(ItemStack stack, int rotation, BlockPos pos) {

        CompoundNBT tag = new CompoundNBT();
        tag.putLong(UCStrings.TAG_CUBE_SAVEDPOS, pos.toLong());
        NBTUtils.setCompound(stack, UCStrings.TAG_CUBE_ROTATION + rotation, tag);
    }

    public BlockPos getSavedPosition(ItemStack stack, int rotation) {

        CompoundNBT tag = NBTUtils.getCompound(stack, UCStrings.TAG_CUBE_ROTATION + rotation, true);
        if (tag != null && tag.contains(UCStrings.TAG_CUBE_SAVEDPOS))
            return BlockPos.fromLong(tag.getLong(UCStrings.TAG_CUBE_SAVEDPOS));

        return BlockPos.ZERO;
    }

    public void teleportToPosition(PlayerEntity player, int rotation, boolean teleport) {

        if (player.world.getDimensionKey() != World.OVERWORLD) {
            player.sendStatusMessage(new StringTextComponent("Not in the overworld!"), true);
            return;
        }
        ItemStack stack = player.getHeldItemMainhand();
        if (stack.getItem() == this) {
            if (!teleport) {
                saveRotation(stack, rotation);
                return;
            }
            BlockPos pos = getSavedPosition(stack, rotation);
            if (!pos.equals(BlockPos.ZERO)) {
                player.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                player.world.playEvent(2003, pos, 0);
                player.getCooldownTracker().setCooldown(this, 300);
            } else {
                player.sendStatusMessage(new StringTextComponent("No teleport position saved here!"), true);
            }
            saveRotation(stack, rotation);
        }
    }
}
