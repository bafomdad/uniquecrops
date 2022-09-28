package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import com.bafomdad.uniquecrops.network.PacketOpenCube;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;

public class RubiksCubeItem extends ItemBaseUC {

    public RubiksCubeItem() {

        super(UCItems.unstackable().rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {

        if (ctx.getLevel().dimension() == Level.OVERWORLD && ctx.getPlayer().isCrouching() && ctx.getClickedFace() == Direction.UP) {
            ItemStack stack = ctx.getItemInHand();
            if (stack.getItem() == this) {
                int rot = getRotation(stack);
                BlockPos savedPos = ctx.getClickedPos().above();
                if (!ctx.getLevel().isClientSide) {
                    this.savePosition(stack, rot, savedPos);
                    ctx.getPlayer().sendMessage(new TextComponent("Teleport position saved"), ctx.getPlayer().getUUID());
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack cube = player.getMainHandItem();
        if (cube.getItem() == this) {
            if (!world.isClientSide)
                UCPacketHandler.sendTo((ServerPlayer)player, new PacketOpenCube(player.getId()));

            return InteractionResultHolder.success(cube);
        }
        return InteractionResultHolder.pass(cube);
    }

    public void saveRotation(ItemStack stack, int rotation) {

        NBTUtils.setInt(stack, UCStrings.TAG_CUBE_ROTATION, rotation);
    }

    public int getRotation(ItemStack stack) {

        if (stack.isEmpty()) return 0;
        return NBTUtils.getInt(stack, UCStrings.TAG_CUBE_ROTATION, 2);
    }

    public void savePosition(ItemStack stack, int rotation, BlockPos pos) {

        CompoundTag tag = new CompoundTag();
        tag.putLong(UCStrings.TAG_CUBE_SAVEDPOS, pos.asLong());
        NBTUtils.setCompound(stack, UCStrings.TAG_CUBE_ROTATION + rotation, tag);
    }

    public BlockPos getSavedPosition(ItemStack stack, int rotation) {

        CompoundTag tag = NBTUtils.getCompound(stack, UCStrings.TAG_CUBE_ROTATION + rotation, true);
        if (tag != null && tag.contains(UCStrings.TAG_CUBE_SAVEDPOS))
            return BlockPos.of(tag.getLong(UCStrings.TAG_CUBE_SAVEDPOS));

        return BlockPos.ZERO;
    }

    public void teleportToPosition(Player player, int rotation, boolean teleport) {

        if (player.level.dimension() != Level.OVERWORLD) {
            player.displayClientMessage(new TextComponent("Not in the overworld!"), true);
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
                player.displayClientMessage(new TextComponent("No teleport position saved here!"), true);
            }
            saveRotation(stack, rotation);
        }
    }
}
