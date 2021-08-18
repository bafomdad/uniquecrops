package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class ImpactShieldItem extends ItemBaseUC {

    private static final String DAMAGE_POOL = "UC:ImpactShieldDamage";

    public ImpactShieldItem() {

        super(UCItems.defaultBuilder().durability(25));
        MinecraftForge.EVENT_BUS.addListener(this::onShieldBlock);
    }

    private void onShieldBlock(LivingAttackEvent event) {

        if (event.getEntityLiving().level.isClientSide || !(event.getEntityLiving() instanceof PlayerEntity)) return;

        PlayerEntity player = (PlayerEntity)event.getEntityLiving();
        if (event.getSource() != DamageSource.MAGIC && event.getSource().getEntity() instanceof LivingEntity) {
            ItemStack activeStack = player.getUseItem();
            if (activeStack.getItem() == UCItems.IMPACT_SHIELD.get()) {
                damageImpactShield(player, activeStack, event.getAmount());
                event.setCanceled(true);
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {

        return 72000;
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {

        return UseAction.BLOCK;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this))
            return new ActionResult(ActionResultType.PASS, stack);

        player.startUsingItem(hand);
        return new ActionResult(ActionResultType.SUCCESS, stack);
    }

    private void damageImpactShield(PlayerEntity player, ItemStack stack, float damage) {

        stack.setDamageValue(stack.getDamageValue() + 1);
        float strength = NBTUtils.getFloat(stack, DAMAGE_POOL, 0);
        if (stack.getDamageValue() > stack.getMaxDamage()) {
            player.level.explode(player, player.getX(), player.getY(), player.getZ(), Math.min(strength, 50F), Explosion.Mode.NONE);

            stack.setDamageValue(0);
            player.getCooldowns().addCooldown(this, 300);
            NBTUtils.setFloat(stack, DAMAGE_POOL, 0);
            player.stopUsingItem();
            return;
        }
        NBTUtils.setFloat(stack, DAMAGE_POOL, strength + damage);
    }
}
