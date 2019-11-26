package com.bafomdad.uniquecrops.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.init.UCItems;

public class ItemColorfulCube extends Item {
	
	public ItemColorfulCube() {
		
		setRegistryName("rubikscube");
		setTranslationKey(UniqueCrops.MOD_ID + ".rubikscube");
		setCreativeTab(UniqueCrops.TAB);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		
		return EnumRarity.EPIC;
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		super.addInformation(stack, player, list, whatisthis);
		list.add(I18n.format(UCStrings.TOOLTIP + "rubikscube"));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		ItemStack stack = player.getHeldItem(hand);
		if (stack.getItem() == this && !player.getCooldownTracker().hasCooldown(this))
			player.openGui(UniqueCrops.instance, 5, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if (world.provider.getDimension() == 0 && player.isSneaking() && facing == EnumFacing.UP) {
			ItemStack stack = player.getHeldItem(hand);
			int rot = getRotation(stack);
			BlockPos savedPos = pos.up();
			if (!world.isRemote) {
				this.savePosition(stack, rot, savedPos);
				player.sendMessage(new TextComponentString("Teleport position saved"));
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
	
	public void saveRotation(ItemStack stack, int rotation) {
		
		NBTUtils.setInt(stack, UCStrings.TAG_CUBE_ROTATION, rotation);
	}
	
	public int getRotation(ItemStack stack) {
		
		return NBTUtils.getInt(stack, UCStrings.TAG_CUBE_ROTATION, 2);
	}
	
	public void savePosition(ItemStack stack, int rotation, BlockPos pos) {

		NBTTagCompound tag = new NBTTagCompound();
		tag.setLong(UCStrings.TAG_CUBE_SAVEDPOS, pos.toLong());
		NBTUtils.setCompound(stack, UCStrings.TAG_CUBE_ROTATION + rotation, tag);
	}
	
	public BlockPos getSavedPosition(ItemStack stack, int rotation) {
		
		NBTTagCompound tag = NBTUtils.getCompound(stack, UCStrings.TAG_CUBE_ROTATION + rotation, true);
		if (tag != null && tag.hasKey(UCStrings.TAG_CUBE_SAVEDPOS)) {
			return BlockPos.fromLong(tag.getLong(UCStrings.TAG_CUBE_SAVEDPOS));
		}
		return BlockPos.ORIGIN;
	}
	
	public void teleportToPosition(EntityPlayer player, int rotation, boolean teleport) {
		
		if (player.world.provider.getDimension() != 0) {
			player.sendStatusMessage(new TextComponentString("Not in the overworld!"), true);
			return;
		}
		for (ItemStack stack : player.getHeldEquipment()) {
			if (!stack.isEmpty() && stack.getItem() == this) {
				if (!teleport) {
					saveRotation(stack, rotation);
					return;
				}
				BlockPos pos = getSavedPosition(stack, rotation);
				if (!pos.equals(BlockPos.ORIGIN)) {
					player.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
					player.world.playEvent(2003, pos, 0);
					player.getCooldownTracker().setCooldown(this, UCConfig.cubeCooldown);
				} else {
					player.sendStatusMessage(new TextComponentString("No teleport position saved here!"), true);
				}
				saveRotation(stack, rotation);
				break;
			}
		}
	}
}
