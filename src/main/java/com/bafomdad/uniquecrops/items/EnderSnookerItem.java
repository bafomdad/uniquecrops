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

        super(UCItems.defaultBuilder().maxDamage(16));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {

        return true;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext ctx) {

        //TODO: fix weirdness
        if (ctx.getPlayer().isSneaking()) {
            BlockState state = ctx.getWorld().getBlockState(ctx.getPos());
            if (state.getBlock() == UCBlocks.DARK_BLOCK.get()) {
                if (!ctx.getWorld().isRemote) {
                    if (ctx.getPos().getY() <= 1)
                        ctx.getWorld().setBlockState(ctx.getPos(), Blocks.BEDROCK.getDefaultState(), 2);
                    else
                        ctx.getWorld().removeBlock(ctx.getPos(), false);
                }
                ItemHandlerHelper.giveItemToPlayer(ctx.getPlayer(), new ItemStack(UCBlocks.DARK_BLOCK.get()));
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

        if (hand != Hand.MAIN_HAND) return ActionResult.resultPass(player.getHeldItem(hand));

        List<LivingEntity> elb = acquireAllLookTargets(player, 32, 3);
        for (LivingEntity target : elb) {
            if (target.canEntityBeSeen(player) && !(target instanceof PlayerEntity) && target.isNonBoss()) {
                BlockPos targetPos = target.getPosition();
                BlockPos playerPos = player.getPosition();
                if (!world.isRemote) {
                    target.setPositionAndUpdate(playerPos.getX(), playerPos.getY(), playerPos.getZ());
                    player.setPositionAndUpdate(targetPos.getX(), targetPos.getY(), targetPos.getZ());
                    if (target instanceof WolfEntity && world.rand.nextInt(100) == 0)
                        target.entityDropItem(new ItemStack(UCItems.DOGRESIDUE.get()));
                    if (!player.isCreative())
                        player.getHeldItem(hand).damageItem(1, player, (entity) -> {});
                }
                return ActionResult.resultConsume(player.getHeldItem(hand));
            }
        }
        return ActionResult.resultPass(player.getHeldItem(hand));
    }

    /* Thanks to Coolalias for all code below this line */
    private static final int MAX_DISTANCE = 256;

    private List<LivingEntity> acquireAllLookTargets(LivingEntity seeker, int distance, double radius) {

        if (distance < 0 || distance > MAX_DISTANCE) {
            distance = MAX_DISTANCE;
        }
        List<LivingEntity> targets = new ArrayList<>();
        Vector3d vec3 = seeker.getLookVec();
        double targetX = seeker.getPosX();
        double targetY = seeker.getPosY() + seeker.getEyeHeight() - 0.10000000149011612D;
        double targetZ = seeker.getPosZ();
        double distanceTraveled = 0;

        while ((int) distanceTraveled < distance) {
            targetX += vec3.x;
            targetY += vec3.y;
            targetZ += vec3.z;
            distanceTraveled += vec3.length();
            AxisAlignedBB bb = new AxisAlignedBB(targetX-radius, targetY-radius, targetZ-radius, targetX+radius, targetY+radius, targetZ+radius);
            List<LivingEntity> list = seeker.world.getEntitiesWithinAABB(LivingEntity.class, bb);
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

        return seeker.canEntityBeSeen(target) && isTargetInFrontOf(seeker, target, 60);
    }

    private boolean isTargetInFrontOf(Entity seeker, Entity target, float fov) {
        // thanks again to Battlegear2 for the following code snippet
        double dx = target.getPosX() - seeker.getPosY();
        double dz;
        for (dz = target.getPosZ() - seeker.getPosZ(); dx * dx + dz * dz < 1.0E-4D; dz = (Math.random() - Math.random()) * 0.01D) {
            dx = (Math.random() - Math.random()) * 0.01D;
        }
        while (seeker.rotationYaw > 360) { seeker.rotationYaw -= 360; }
        while (seeker.rotationYaw < -360) { seeker.rotationYaw += 360; }
        float yaw = (float)(Math.atan2(dz, dx) * 180.0D / Math.PI) - seeker.rotationYaw;
        yaw = yaw - 90;
        while (yaw < -180) { yaw += 360; }
        while (yaw >= 180) { yaw -= 360; }

        return yaw < fov && yaw > -fov;
    }
}
