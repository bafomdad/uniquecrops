package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.UniqueCrops;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemEdibleBook extends ItemEnchantedBook {
	
	public ItemEdibleBook() {
		
		setRegistryName(new ResourceLocation("minecraft", "enchanted_book"));
		setTranslationKey(UniqueCrops.MOD_ID + ".enchantedbook");
	}

	@Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityliving) {
       
    	if (entityliving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityliving;
            NBTTagList enchants = ItemEnchantedBook.getEnchantments(stack);
            int foodLevel = 1;
            if (enchants.tagCount() > 0) {
            	for (int i = 0; i < enchants.tagCount(); i++) {
            		NBTTagCompound tag = enchants.getCompoundTagAt(i);
            		foodLevel += (tag.getShort("lvl") + Enchantment.getEnchantmentByID(tag.getShort("id")).getRarity().ordinal());
            	}
            }
            float saturation = 0.6F;
            player.getFoodStats().addStats(Math.min(player.getFoodStats().getFoodLevel() + foodLevel, 20), Math.min(saturation + (float)foodLevel * saturation * 2.0F, (float)foodLevel));
            world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

            if (player instanceof EntityPlayerMP)
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)player, stack);
        }
        stack.shrink(1);
        return stack;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        
    	return 32;
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
    	
        return EnumAction.EAT;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        
    	ItemStack itemstack = player.getHeldItem(hand);
        if (player.canEat(false) && UniqueCrops.baublesLoaded) {
            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
        else
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
    }
}
