package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class EnderSnookerItem extends ItemBaseUC {

    public EnderSnookerItem() {

        super(UCItems.defaultBuilder().durability(16));
    }

    @Override
    public boolean isFoil(ItemStack stack) {

        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {

        //TODO: fix weirdness
        if (ctx.getPlayer().isCrouching()) {
            BlockState state = ctx.getLevel().getBlockState(ctx.getClickedPos());
            if (state.getBlock() == UCBlocks.DARK_BLOCK.get()) {
                if (!ctx.getLevel().isClientSide) {
                    if (ctx.getClickedPos().getY() <= 1)
                        ctx.getLevel().setBlock(ctx.getClickedPos(), Blocks.BEDROCK.defaultBlockState(), 2);
                    else
                        ctx.getLevel().removeBlock(ctx.getClickedPos(), false);
                }
                ItemHandlerHelper.giveItemToPlayer(ctx.getPlayer(), new ItemStack(UCBlocks.DARK_BLOCK.get()));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        if (hand != InteractionHand.MAIN_HAND) return InteractionResultHolder.pass(player.getItemInHand(hand));

        List<LivingEntity> elb = acquireAllLookTargets(player, 32, 2);
        for (LivingEntity target : elb) {
            if (target.hasLineOfSight(player) && target.isPickable()) {
                BlockPos targetPos = target.blockPosition();
                BlockPos playerPos = player.blockPosition();
                if (!world.isClientSide) {
                    target.teleportTo(playerPos.getX(), playerPos.getY(), playerPos.getZ());
                    player.teleportTo(targetPos.getX(), targetPos.getY(), targetPos.getZ());
                    if (target instanceof Wolf && world.random.nextInt(100) == 0)
                        target.spawnAtLocation(new ItemStack(UCItems.DOGRESIDUE.get()));
                    if (!player.isCreative())
                        player.getItemInHand(hand).hurtAndBreak(1, player, (entity) -> {});
                }
                return InteractionResultHolder.consume(player.getItemInHand(hand));
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    /* Thanks to Coolalias for all code below this line */
    private static final int MAX_DISTANCE = 256;

    private List<LivingEntity> acquireAllLookTargets(LivingEntity seeker, int distance, double radius) {

        if (distance < 0 || distance > MAX_DISTANCE) {
            distance = MAX_DISTANCE;
        }
        List<LivingEntity> targets = new ArrayList<>();
        Vec3 vec3 = seeker.getViewVector(1.0F).normalize();
        double targetX = seeker.getX();
        double targetY = seeker.getY() + seeker.getEyeHeight() - 0.10000000149011612D;
        double targetZ = seeker.getZ();
        double distanceTraveled = 0;

        while ((int) distanceTraveled < distance) {
            targetX += vec3.x;
            targetY += vec3.y;
            targetZ += vec3.z;
            distanceTraveled += vec3.length();
            AABB bb = new AABB(targetX-radius, targetY-radius, targetZ-radius, targetX+radius, targetY+radius, targetZ+radius);
            List<LivingEntity> list = seeker.level.getEntitiesOfClass(LivingEntity.class, bb);
            for (LivingEntity target : list) {
                if (target == seeker || target instanceof Player) continue;
                if (target.isPushable() && isTargetInSight(vec3, seeker, target)) {
                    if (!targets.contains(target)) {
                        targets.add(target);
                    }
                }
            }
        }
        return targets;
    }

    private boolean isTargetInSight(Vec3 vec3, LivingEntity seeker, Entity target) {

        return seeker.hasLineOfSight(target) && isTargetInFrontOf(seeker, target, 60);
    }

    private boolean isTargetInFrontOf(Entity seeker, Entity target, float fov) {
        // thanks again to Battlegear2 for the following code snippet
        double dx = target.getY() - seeker.getY();
        double dz;
        for (dz = target.getZ() - seeker.getZ(); dx * dx + dz * dz < 1.0E-4D; dz = (Math.random() - Math.random()) * 0.01D) {
            dx = (Math.random() - Math.random()) * 0.01D;
        }
        while (seeker.yRotO > 360) { seeker.yRotO -= 360; }
        while (seeker.yRotO < -360) { seeker.yRotO += 360; }
        float yaw = (float)(Math.atan2(dz, dx) * 180.0D / Math.PI) - seeker.yRotO;
        yaw = yaw - 90;
        while (yaw < -180) { yaw += 360; }
        while (yaw >= 180) { yaw -= 360; }

        return yaw < fov && yaw > -fov;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {

        return repair.is(UCItems.LILYTWINE.get());
    }
}
