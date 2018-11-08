package com.bafomdad.uniquecrops.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemThunderpants extends ItemArmor implements IBookUpgradeable {
	
	private static final String TAG_CHARGE = "UC:pantsCharge";
	private static final float MAX_CHARGE = 32.0F;

	public ItemThunderpants(ItemArmor.ArmorMaterial material, int renderindex, EntityEquipmentSlot slot) {
		
		super(material, renderindex, slot);
		setRegistryName("thunderpantz");
		setTranslationKey(UniqueCrops.MOD_ID + ".thunderpantz");
		setCreativeTab(UniqueCrops.TAB);
		UCItems.items.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		list.add(I18n.format(UCStrings.TOOLTIP + "thunderpantz"));
		if (getLevel(stack) > -1) {
			int upgradelevel = getLevel(stack);
			list.add(TextFormatting.GOLD + "+" + upgradelevel);
		}
		else
			list.add(TextFormatting.GOLD + "Upgradeable");
	}
	
	@Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		
		if (world.isRemote) return;
		
//		if (getCharge(stack) > 1.0F && world.rand.nextInt(10) == 0)
//			UCPacketHandler.sendToNearbyPlayers(world, player.getPosition(), new PacketUCEffect(EnumParticleTypes.VILLAGER_ANGRY, player.posX - 1, player.posY + 0.65, player.posZ - 1, (int)getCharge(stack)));
		
		if (getCharge(stack) >= MAX_CHARGE) return;
		
		if (player.onGround && player.isSneaking()) {
			BlockPos pos = new BlockPos(MathHelper.floor(player.posX), player.getPosition().getY(), MathHelper.floor(player.posZ));
			Block block = world.getBlockState(pos).getBlock();
			if (block instanceof BlockCarpet) {
				if (world.rand.nextInt(10) == 0)
					setCharge(stack, getCharge(stack) + world.rand.nextFloat());
			}
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
	
	public void setCharge(ItemStack stack, float f) {
		
		NBTUtils.setFloat(stack, TAG_CHARGE, f);
	}
	
	public float getCharge(ItemStack stack) {
		
		return NBTUtils.getFloat(stack, TAG_CHARGE, 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase elb, ItemStack stack, EntityEquipmentSlot slot, ModelBiped model) {
		
		return model;
	}
}
