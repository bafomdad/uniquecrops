package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
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
    public ActionResultType useOn(ItemUseContext ctx) {

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
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (hand != Hand.MAIN_HAND) return ActionResult.pass(player.getItemInHand(hand));

        List<LivingEntity> elb = acquireAllLookTargets(player, 32, 3);
        for (LivingEntity target : elb) {
            if (target.canSee(player) && !(target instanceof PlayerEntity) && target.isPickable()) {
                BlockPos targetPos = target.blockPosition();
                BlockPos playerPos = player.blockPosition();
                if (!world.isClientSide) {
                    target.teleportTo(playerPos.getX(), playerPos.getY(), playerPos.getZ());
                    player.teleportTo(targetPos.getX(), targetPos.getY(), targetPos.getZ());
                    if (target instanceof WolfEntity && world.random.nextInt(100) == 0)
                        target.spawnAtLocation(new ItemStack(UCItems.DOGRESIDUE.get()));
                    if (!player.isCreative())
                        player.getItemInHand(hand).hurtAndBreak(1, player, (entity) -> {});
                }
                return ActionResult.consume(player.getItemInHand(hand));
            }
        }
        return ActionResult.pass(player.getItemInHand(hand));
    }

    /* Thanks to Coolalias for all code below this line */
    private static final int MAX_DISTANCE = 256;

    private List<LivingEntity> acquireAllLookTargets(LivingEntity seeker, int distance, double radius) {

        if (distance < 0 || distance > MAX_DISTANCE) {
            distance = MAX_DISTANCE;
        }
        List<LivingEntity> targets = new ArrayList<>();
        Vector3d vec3 = seeker.getLookAngle();
        double targetX = seeker.getX();
        double targetY = seeker.getY() + seeker.getEyeHeight() - 0.10000000149011612D;
        double targetZ = seeker.getZ();
        double distanceTraveled = 0;

        while ((int) distanceTraveled < distance) {
            targetX += vec3.x;
            targetY += vec3.y;
            targetZ += vec3.z;
            distanceTraveled += vec3.length();
            AxisAlignedBB bb = new AxisAlignedBB(targetX-radius, targetY-radius, targetZ-radius, targetX+radius, targetY+radius, targetZ+radius);
            List<LivingEntity> list = seeker.level.getEntitiesOfClass(LivingEntity.class, bb);
            for (LivingEntity target : list) {
                if (target != seeker && target.canBeCollidedWith() && isTargetInSight(vec3, seeker, target)) {
                    if (!targets.contains(target)) {
                        targets.add(target);
                    }
                }
            }
        }
        return targets;
    }

    private boolean isTargetInSight(Vector3d vec3, LivingEntity seeker, Entity target) {

        return seeker.canSee(target) && isTargetInFrontOf(seeker, target, 60);
    }

    private boolean isTargetInFrontOf(Entity seeker, Entity target, float fov) {
        // thanks again to Battlegear2 for the following code snippet
        double dx = target.getY() - seeker.getY();
        double dz;
        for (dz = target.getZ() - seeker.getZ(); dx * dx + dz * dz < 1.0E-4D; dz = (Math.random() - Math.random()) * 0.01D) {
            dx = (Math.random() - Math.random()) * 0.01D;
        }
        while (seeker.yRot > 360) { seeker.yRot -= 360; }
        while (seeker.yRot < -360) { seeker.yRot += 360; }
        float yaw = (float)(Math.atan2(dz, dx) * 180.0D / Math.PI) - seeker.yRot;
        yaw = yaw - 90;
        while (yaw < -180) { yaw += 360; }
        while (yaw >= 180) { yaw -= 360; }

        return yaw < fov && yaw > -fov;
    }
}
