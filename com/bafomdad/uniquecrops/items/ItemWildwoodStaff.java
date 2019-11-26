package com.bafomdad.uniquecrops.items;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.capabilities.CPCapability;
import com.bafomdad.uniquecrops.capabilities.CPProvider;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketSyncCap;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class ItemWildwoodStaff extends Item {
	
	private static final String TEMP_CAP = "cropCap";

	public ItemWildwoodStaff() {
		
		setRegistryName("wildwood_staff");
		setTranslationKey(UniqueCrops.MOD_ID + ".wildwoodstaff");
		setCreativeTab(UniqueCrops.TAB);
		setMaxStackSize(1);
		UCItems.items.add(this);
	}
	
	@Override
    public EnumRarity getRarity(ItemStack stack) {
    	
    	return EnumRarity.UNCOMMON;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
		
		if (isInCreativeTab(tabs)) {
			ItemStack staff = new ItemStack(this);
			list.add(staff.copy());
			staff.getCapability(CPProvider.CROP_POWER, null).setPower(100);
			list.add(staff);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List list, ITooltipFlag whatisthis) {
		
		CPCapability cap = stack.getCapability(CPProvider.CROP_POWER, null);
		boolean flag = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
		
		if (flag && cap != null) {
			list.add(TextFormatting.GREEN + "Crop Power: " + cap.getPower() + "/" + cap.getCapacity());
		} else {
			list.add(TextFormatting.LIGHT_PURPLE + "<Press Shift>");
		}
	}
	
	/*
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		
		return true;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		
		if (stack.hasCapability(CPProvider.CROP_POWER, null)) {
			CPCapability cap = stack.getCapability(CPProvider.CROP_POWER, null);
			if (cap != null) {
				double max = cap.getCapacity();
				double diff = max - cap.getPower();
				
				return diff / max;
			}
		}
		return super.getDurabilityForDisplay(stack);
	}
	*/
    
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
    	
    	if (!stack.hasCapability(CPProvider.CROP_POWER, null))
    		return super.getNBTShareTag(stack);
    	
    	NBTTagCompound tag = stack.hasTagCompound() ? stack.getTagCompound().copy() : new NBTTagCompound();
    	tag.setTag(TEMP_CAP, stack.getCapability(CPProvider.CROP_POWER, null).serializeNBT());
    	return tag;
    }
    
    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt) {
    	
    	CPCapability cap = stack.getCapability(CPProvider.CROP_POWER, null);
    	if (cap != null && nbt != null && nbt.hasKey(TEMP_CAP, 10)) {
    		cap.deserializeNBT(nbt.getCompoundTag(TEMP_CAP));
    		nbt.removeTag(TEMP_CAP);
    	}
    	super.readNBTShareTag(stack, nbt);
    }
    
//    @Override
//    public void onUpdate(ItemStack stack, World world, Entity entity, int itemslot, boolean isSelected) {
//    	
//    	if (!stack.hasCapability(CPProvider.CROP_POWER, null) || !stack.hasTagCompound() || !stack.getTagCompound().hasKey("cropCap", 10))
//    		return;
//    	
//    	CPCapability cap = stack.getCapability(CPProvider.CROP_POWER, null);
//    	cap.deserializeNBT(stack.getTagCompound().getCompoundTag(TEMP_CAP));
//    	stack.getTagCompound().removeTag(TEMP_CAP);
//    	
//    	if (stack.getTagCompound().getSize() <= 0)
//    		stack.setTagCompound(null);
//    }
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
//		IBlockState state = world.getBlockState(pos);
//		if (state.getBlock() instanceof BlockCrops) {
//			int cropgrowth = state.getValue(BlockCrops.AGE);
//			if (cropgrowth > 0) {
//				CPCapability cap = player.getHeldItemMainhand().getCapability(CPProvider.CROP_POWER, null);
//				if (cap != null && cap.getPower() < cap.getCapacity()) {
//					if (!world.isRemote) {
//						cap.add(cropgrowth);
//						if (player instanceof EntityPlayerMP)
//							UCPacketHandler.INSTANCE.sendTo(new PacketSyncCap(cap.serializeNBT()), (EntityPlayerMP)player);
//						world.setBlockState(pos, state.withProperty(BlockCrops.AGE, 0), 2);
//					}
//					return EnumActionResult.SUCCESS;
//				}
//			}
//		}
//		else {
//			CPCapability cap = player.getHeldItemMainhand().getCapability(CPProvider.CROP_POWER, null);
//			if (cap != null && cap.getPower() > 0) {
//				if (!world.isRemote) {
//					cap.remove(1);
//					if (player instanceof EntityPlayerMP)
//						UCPacketHandler.INSTANCE.sendTo(new PacketSyncCap(cap.serializeNBT()), (EntityPlayerMP)player);
//				}
//				return EnumActionResult.SUCCESS;
//			}
//		}
		return EnumActionResult.PASS;
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldstack, ItemStack newstack, boolean slotchanged) {
		
		return oldstack.getItem() != newstack.getItem() && !slotchanged;
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		
		return new CPProvider();
	}
	
	public static boolean adjustPower(ItemStack stack, int amount) {
		
		CPCapability cap = stack.getCapability(CPProvider.CROP_POWER, null);
		if (cap != null && cap.getPower() >= amount) {
			cap.remove(amount);
			return true;
		}
		return false;
	}
}
