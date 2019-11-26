package com.bafomdad.uniquecrops.items.baubles;

import java.util.List;
import java.util.UUID;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumEmblems;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public abstract class ItemBauble extends Item implements IBauble {
	
	private static final String HASHCODE = "playerHashcode";
	private static final String UUID_MOST = "attributeUUIDMost";
	private static final String UUID_LEAST = "attributeUUIDLeast";
	
	final Multimap<String, AttributeModifier> attributes = HashMultimap.create();
	private EnumEmblems type;
	
	public ItemBauble(EnumEmblems type) {
		
		this.type = type;
		setRegistryName("emblem." + type.toString().toLowerCase());
		setTranslationKey(UniqueCrops.MOD_ID + ".emblem" + type.toString().toLowerCase());
		setCreativeTab(UniqueCrops.TAB);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag advanced) {
		
		if (getDescription() != null && !getDescription().isEmpty())
			list.add(I18n.format(UniqueCrops.MOD_ID + ".tooltip." + getDescription()));
    }
	
	public abstract String getDescription();

	@Override
	public BaubleType getBaubleType(ItemStack stack) {
		
		return BaubleType.CHARM;
	}
	
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		
		if (getLastPlayerHashcode(stack) != player.hashCode()) {
			onEquippedOrLoadedIntoWorld(stack, player);
			setLastPlayerHashcode(stack, player.hashCode());
		}
	}
	
	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase player) {
		
		if (player != null) {
			onEquippedOrLoadedIntoWorld(stack, player);
			setLastPlayerHashcode(stack, player.hashCode());
		}
	}
	
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {}
	
	@Override
	public boolean canEquip(ItemStack stack, EntityLivingBase player) {
		
		return true;
	}
	
	@Override
	public boolean canUnequip(ItemStack stack, EntityLivingBase player) {
		
		return true;
	}
	
	@Override
	public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {

		return false;
	}
	
	abstract void fillModifiers(Multimap<String, AttributeModifier> attributes, ItemStack stack);
	
	public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {}
	
	public UUID getBaubleUUID(ItemStack stack) {
		
		long most = NBTUtils.getLong(stack, UUID_MOST, 0);
		if (most == 0) {
			UUID uuid = UUID.randomUUID();
			NBTUtils.setLong(stack, UUID_MOST, uuid.getMostSignificantBits());
			NBTUtils.setLong(stack, UUID_LEAST, uuid.getLeastSignificantBits());
			return getBaubleUUID(stack);
		}
		long least = NBTUtils.getLong(stack, UUID_LEAST, 0);
		return new UUID(most, least);
	}
	
	public int getLastPlayerHashcode(ItemStack stack) {
		
		return NBTUtils.getInt(stack, HASHCODE, 0);
	}
	
	public void setLastPlayerHashcode(ItemStack stack, int hash) {
		
		NBTUtils.setInt(stack, HASHCODE, hash);
	}
}
