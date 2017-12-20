package com.bafomdad.uniquecrops.items;

import java.util.List;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Item3DGlasses extends ItemArmor implements IBookUpgradeable {

	public Item3DGlasses(ItemArmor.ArmorMaterial material, int renderindex, EntityEquipmentSlot slot) {
		
		super(material, renderindex, slot);
		setRegistryName("3dglasses");
		setUnlocalizedName(UniqueCrops.MOD_ID + ".3dglasses");
		setCreativeTab(UniqueCrops.TAB);
		setMaxDamage(200);
		UCItems.items.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		super.addInformation(stack, player, list, whatisthis);
		if (getLevel(stack) > -1) {
			int upgradelevel = getLevel(stack);
			list.add(TextFormatting.GOLD + "+" + upgradelevel);
		}
		else
			list.add(TextFormatting.GOLD + "Upgradeable");
	}
	
	@Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
    	
		if (world.isRemote)
			return;
		
		int upgradelevel = getLevel(itemStack);
		if (upgradelevel != 10)
			return;
		
		int sunlight = world.getLightFor(EnumSkyBlock.SKY, player.getPosition().add(0, player.getEyeHeight(), 0));
		if (sunlight <= 3) {
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 30));
		}
    }
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		
		return false;
	}

	@Override
	public int getLevel(ItemStack stack) {

		return NBTUtils.getInt(stack, ItemGeneric.TAG_UPGRADE, -1);
	}

	@Override
	public void setLevel(ItemStack stack, int level) {

		NBTUtils.setInt(stack, ItemGeneric.TAG_UPGRADE, level);
	}
}
