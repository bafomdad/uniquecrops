package com.bafomdad.uniquecrops.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemImpactShield extends Item {
	
	private static final String DAMAGE_POOL = "UC:ImpactShieldDamage";

	public ItemImpactShield() {
		
		setRegistryName("impactshield");
		setTranslationKey(UniqueCrops.MOD_ID + ".impactshield");
		setCreativeTab(UniqueCrops.TAB);
		setMaxStackSize(1);
		setMaxDamage(25);
		UCItems.items.add(this);
        this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter() {
           
        	@SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase elb) {
            	
                return elb != null && elb.isHandActive() && elb.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		super.addInformation(stack, player, list, whatisthis);
		list.add(I18n.format(UCStrings.TOOLTIP + "impactshield"));
	}
	
	@Override
    public EnumAction getItemUseAction(ItemStack stack) {
		
        return EnumAction.BLOCK;
    }
	
	@Override
    public int getMaxItemUseDuration(ItemStack stack) {
		
        return 72000;
    }
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
        ItemStack stack = player.getHeldItem(hand);
        if (player.getCooldownTracker().hasCooldown(this)) {
        	return new ActionResult(EnumActionResult.PASS, stack);
        }
        player.setActiveHand(hand);
        return new ActionResult(EnumActionResult.SUCCESS, stack);
    }
	
	public void damageImpactShield(EntityPlayer player, ItemStack stack, float damage) {
		
		stack.setItemDamage(stack.getItemDamage() + 1);
		float strength = NBTUtils.getFloat(stack, DAMAGE_POOL, 0);
		if (stack.getItemDamage() > stack.getMaxDamage()) {
			if (!player.world.isRemote) {
				player.world.createExplosion(player, player.posX, player.posY, player.posZ, Math.min(strength, 50F), false);
			}
			stack.setItemDamage(0);
			player.getCooldownTracker().setCooldown(this, 300);
			NBTUtils.setFloat(stack, DAMAGE_POOL, 0);
			player.stopActiveHand();
			return;
		}
		NBTUtils.setFloat(stack, DAMAGE_POOL, strength + damage);
	}
}
