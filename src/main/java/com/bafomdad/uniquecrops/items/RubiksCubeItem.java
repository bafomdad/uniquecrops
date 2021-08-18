package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import com.bafomdad.uniquecrops.network.PacketOpenCube;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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

    public RubiksCubeItem() {

        super(UCItems.unstackable().rarity(Rarity.EPIC));
    }

    @Override
    public ActionResultType useOn(ItemUseContext ctx) {

        if (ctx.getLevel().dimension() == World.OVERWORLD && ctx.getPlayer().isCrouching() && ctx.getClickedFace() == Direction.UP) {
            ItemStack stack = ctx.getItemInHand();
            if (stack.getItem() == this) {
                int rot = getRotation(stack);
                BlockPos savedPos = ctx.getClickedPos().above();
                if (!ctx.getLevel().isClientSide) {
                    this.savePosition(stack, rot, savedPos);
                    ctx.getPlayer().sendMessage(new StringTextComponent("Teleport position saved"), ctx.getPlayer().getUUID());
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack cube = player.getMainHandItem();
        if (cube.getItem() == this) {
            if (!world.isClientSide)
                UCPacketHandler.sendTo((ServerPlayerEntity)player, new PacketOpenCube(player.getId()));

            return ActionResult.success(cube);
        }
        return ActionResult.pass(cube);
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
        tag.putLong(UCStrings.TAG_CUBE_SAVEDPOS, pos.asLong());
        NBTUtils.setCompound(stack, UCStrings.TAG_CUBE_ROTATION + rotation, tag);
    }

    public BlockPos getSavedPosition(ItemStack stack, int rotation) {

        CompoundNBT tag = NBTUtils.getCompound(stack, UCStrings.TAG_CUBE_ROTATION + rotation, true);
        if (tag != null && tag.contains(UCStrings.TAG_CUBE_SAVEDPOS))
            return BlockPos.of(tag.getLong(UCStrings.TAG_CUBE_SAVEDPOS));

        return BlockPos.ZERO;
    }

    public void teleportToPosition(PlayerEntity player, int rotation, boolean teleport) {

        if (player.level.dimension() != World.OVERWORLD) {
            player.displayClientMessage(new StringTextComponent("Not in the overworld!"), true);
            return;
        }
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() == this) {
            if (!teleport) {
                saveRotation(stack, rotation);
                return;
            }
            BlockPos pos = getSavedPosition(stack, rotation);
            if (!pos.equals(BlockPos.ZERO)) {
                player.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                player.level.levelEvent(2003, pos, 0);
                player.getCooldowns().addCooldown(this, UCConfig.COMMON.cubeCooldown.get());
            } else {
                player.displayClientMessage(new StringTextComponent("No teleport position saved here!"), true);
            }
            saveRotation(stack, rotation);
        }
    }
}
