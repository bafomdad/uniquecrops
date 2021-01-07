package com.bafomdad.uniquecrops.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCDimension;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemBootsLeague extends ItemArmor implements IBookUpgradeable {
	
	private static final float DEFAULT_SPEED = 0.055F;
	private static final float JUMPFACTOR = 0.2F;
	private static final float FALLBUFFER = 2F;
	
	private static final List<String> CMONSTEPITUP = new ArrayList();

	public ItemBootsLeague(ItemArmor.ArmorMaterial material, int renderindex, EntityEquipmentSlot slot) {
		
		super(material, renderindex, slot);
		setRegistryName("bootsleague");
		setTranslationKey(UniqueCrops.MOD_ID + ".bootsleague");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(200);
		UCItems.items.add(this);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		list.add(I18n.format(UCStrings.TOOLTIP + "bootsleague"));
		int upgradeLevel = getLevel(stack);
		if (upgradeLevel > -1) {
			list.add(TextFormatting.GOLD + "+" + upgradeLevel);
		}
		else
			list.add(TextFormatting.GOLD + "Upgradeable");
	}
	
	@Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		
		String name = getPlayerStr(player);
		if (CMONSTEPITUP.contains(name)) {
			if (world.isRemote) {
				float SPEED = NBTUtils.getFloat(stack, UCStrings.SPEED_MODIFIER, DEFAULT_SPEED);
				if ((player.onGround || player.capabilities.isFlying) && player.moveForward > 0F && !player.isInsideOfMaterial(Material.WATER)) {
					player.moveRelative(0F, 0F, 1F, player.capabilities.isFlying ? SPEED : SPEED);
				}
				if (player.isSneaking())
					player.stepHeight = 0.60001F; // Botania's ItemTravelBelt uses this value to avoid setting the default value of 0.6F, so I'm not gonna step on any toes here
				else player.stepHeight = 1.0625F;

				snapForward(player, stack);
			}
		} else {
			CMONSTEPITUP.add(name);
			player.stepHeight = 1.0625F;
		}
	}
	
	private void snapForward(EntityPlayer player, ItemStack stack) {
		
		if (player.world.provider.getDimension() == UCDimension.dimID) return;
		
		float speedMod = 0.95F;
		int sprintTicks = NBTUtils.getInt(stack, UCStrings.SPRINTING_TICKS, 0);
		if (sprintTicks > 0) {
			NBTUtils.setInt(stack, UCStrings.SPRINTING_TICKS, sprintTicks - 1);
			return;
		}
		if (player.isSprinting() && !player.capabilities.isFlying) {
			if (NBTUtils.getFloat(stack, UCStrings.SPEED_MODIFIER, DEFAULT_SPEED) == DEFAULT_SPEED) {
				NBTUtils.setFloat(stack, UCStrings.SPEED_MODIFIER, speedMod * Math.max(getLevel(stack), 1));
				return;
			}
			else {
				player.setSprinting(false);
				NBTUtils.setInt(stack, UCStrings.SPRINTING_TICKS, 20);
			}
		}
		if (!player.isSprinting()) {
			NBTUtils.setFloat(stack, UCStrings.SPEED_MODIFIER, DEFAULT_SPEED);
		}
	}
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		
		return false;
	}
	
	@SubscribeEvent
	public void playerTick(LivingUpdateEvent event) {
		
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			String name = getPlayerStr(player);
			if (CMONSTEPITUP.contains(name)) {
				ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
				if (boots.isEmpty() || boots.getItem() != UCItems.bootsLeague) {
					player.stepHeight = 0.6F;
					CMONSTEPITUP.remove(name);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event) {
		
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
			if (!boots.isEmpty() && boots.getItem() == UCItems.bootsLeague) {
				player.motionY += JUMPFACTOR;
				player.fallDistance = -FALLBUFFER;
			}
		}
	}
	
	@SubscribeEvent
	public void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
		
		String username = event.player.getGameProfile().getName();
		CMONSTEPITUP.remove(username + ":false");
		CMONSTEPITUP.remove(username + ":true");
	}
	
	private String getPlayerStr(EntityPlayer player) {
		
		return player.getGameProfile().getName() + ":" + player.world.isRemote;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		
		return EnumRarity.EPIC;
	}
}
