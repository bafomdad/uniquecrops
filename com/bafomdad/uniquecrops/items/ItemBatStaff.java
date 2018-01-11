package com.bafomdad.uniquecrops.items;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBatStaff extends Item {

	public ItemBatStaff() {
		
		setRegistryName("batstaff");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".batstaff");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(64);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		
		return EnumRarity.EPIC;
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		list.add(I18n.format(UCStrings.TOOLTIP + "batstaff"));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		ItemStack stack = player.getHeldItem(hand);
		
		boolean damage = false;
		if (!stack.isEmpty() && stack.getItem() == this) {
			List<EntityBat> entities = player.world.getEntitiesWithinAABB(EntityBat.class, new AxisAlignedBB(player.posX - 15, player.posY - 15, player.posZ - 15, player.posX + 15, player.posY + 15, player.posZ + 15));
			for (EntityBat eb : entities) {
				if (eb != entities && !world.isRemote) {
					UCPacketHandler.sendToNearbyPlayers(world, player.getPosition(), new PacketUCEffect(EnumParticleTypes.SPELL_WITCH, eb.posX, eb.posY, eb.posZ, 4));
					eb.setDead();
					damage = true;
				}
			}
			if (damage && !world.isRemote)
				stack.damageItem(1, player);
		}
		return new ActionResult(EnumActionResult.PASS, stack);
	}
}
