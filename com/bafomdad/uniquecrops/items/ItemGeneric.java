package com.bafomdad.uniquecrops.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.GrowthSteps;
import com.bafomdad.uniquecrops.core.UCStrings;
import com.bafomdad.uniquecrops.core.UCUtils;
import com.bafomdad.uniquecrops.crops.Merlinia;
import com.bafomdad.uniquecrops.entities.EntityCustomPotion;
import com.bafomdad.uniquecrops.entities.EntityEulaBook;
import com.bafomdad.uniquecrops.entities.EntityItemPlum;
import com.bafomdad.uniquecrops.entities.EntityItemWeepingEye;
import com.bafomdad.uniquecrops.entities.EntityLegalStuff;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.network.PacketUCEffect;
import com.bafomdad.uniquecrops.network.UCPacketHandler;

public class ItemGeneric extends Item {
	
	private static final String[] FIELD_G = new String[] { "attackCooldown", "field_188501_c", "uo" };
	private static final String[] FUSETIME = new String[] { "fuseTime", "field_82225_f", "yp" };
	public static String TAG_DISCOUNT = "UC_tagDiscount";
	public static String TAG_UPGRADE = "UC_tagUpgrade";
	public static String TAG_OVERCLUCK = "UC_tagEggUpgrade";
	public static String TAG_BINDED = "UC_tagLegallyBound";
	private int range = 5;

	public ItemGeneric() {
		
		setRegistryName("generic");
		setTranslationKey(UniqueCrops.MOD_ID + ".generic");
		setCreativeTab(UniqueCrops.TAB);
		setHasSubtypes(true);
		setMaxDamage(0);
		UCItems.items.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag whatisthis) {
		
		super.addInformation(stack, player, list, whatisthis);
		switch(stack.getItemDamage()) {
			case 0: list.add(I18n.format(UCStrings.TOOLTIP + "guidebook")); break;
			case 1: list.add(I18n.format(UCStrings.TOOLTIP + "discountbook")); break;
			case 14: list.add(I18n.format(UCStrings.TOOLTIP + "slipperglass")); break;
			case 18: list.add(I18n.format(UCStrings.TOOLTIP + "upgradebook")); break;
			case 19: list.add(I18n.format(UCStrings.TOOLTIP + "eggupgrade")); break;
			case 20: list.add(I18n.format(UCStrings.TOOLTIP + "easybadge")); break;
			case 24: list.add(I18n.format(UCStrings.TOOLTIP + "eulabook")); break;
			case 28: list.add(I18n.format(UCStrings.TOOLTIP + "escaperope")); break;
			case 29: list.add(I18n.format(UCStrings.TOOLTIP + "sundial")); break;
		}
	}
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		
		return getTranslationKey() + "." + EnumItems.values()[stack.getItemDamage()].getName();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
		
		if (isInCreativeTab(tabs)) {
			for (int i = 0; i < EnumItems.values().length; ++i)
				if (i != EnumItems.DUMMYITEM.ordinal())
					list.add(new ItemStack(this, 1, i));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		
		if (stack.getItemDamage() == EnumItems.DISCOUNT.ordinal() || stack.getItemDamage() == EnumItems.POTIONSPLASH.ordinal())
			return true;
		
		return false;
	}
	
	public ItemStack createStack(EnumItems item, int stacksize) {
		
		return new ItemStack(this, stacksize, item.ordinal());
	}
	
	public ItemStack createStack(EnumItems item) {
		
		return createStack(item, 1);
	}
	
	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		
		return stack.getItemDamage() == 2;
	}
	
	@Override
	public Entity createEntity(World world, Entity location, ItemStack stack) {
		
		if (location instanceof EntityItem) {
			EntityItemPlum plum = new EntityItemPlum(world, (EntityItem)location, stack);
			return plum;
		}
		return null;
	}
	
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		ItemStack stack = player.getHeldItem(hand);
		
		if (stack.getItemDamage() == EnumItems.TIMEMEAL.ordinal() && player.canPlayerEdit(pos, facing, stack)) {
			Block crops = world.getBlockState(pos).getBlock();
			if (crops instanceof BlockCrops) {
				if (crops != UCBlocks.cropMerlinia && crops != UCBlocks.cropImperia)
					world.setBlockState(pos, ((BlockCrops)crops).withAge(0), 2);
				else if (crops == UCBlocks.cropMerlinia)
					((Merlinia)crops).merliniaGrowth(world, pos, world.rand.nextInt(1) + 1);
				else if (crops instanceof BlockNetherWart)
					((BlockNetherWart)crops).updateTick(world, pos, world.getBlockState(pos), world.rand);
				if (!player.capabilities.isCreativeMode && !player.world.isRemote)
					stack.shrink(1);
				UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() - 0.5D, pos.getY(), pos.getZ() - 0.5D, 6));
				return EnumActionResult.SUCCESS;
			}
		}
		if (stack.getItemDamage() == EnumItems.SUNDIAL.ordinal() && player.canPlayerEdit(pos, facing, stack)) {
			if (!world.isRemote) {
				world.setBlockState(pos.offset(facing), UCBlocks.sundial.getDefaultState(), 2);
				if (!player.capabilities.isCreativeMode)
					stack.shrink(1);
			}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		if (!stack.isEmpty() && stack.getItem() == this) {
			if (stack.getItemDamage() == EnumItems.GUIDE.ordinal() && hand == EnumHand.MAIN_HAND && !player.isSneaking()) {
				if (!world.isRemote && (!stack.hasTagCompound()) || (!player.getEntityData().hasKey(GrowthSteps.TAG_GROWTHSTAGES) || (player.getEntityData().hasKey(GrowthSteps.TAG_GROWTHSTAGES) && (stack.hasTagCompound() && !stack.getTagCompound().hasKey(GrowthSteps.TAG_GROWTHSTAGES)))))
					UCUtils.updateBook(player);
				player.openGui(UniqueCrops.instance, 0, world, (int)player.posX, (int)player.posY, (int)player.posZ);
				return new ActionResult(EnumActionResult.SUCCESS, stack);
			}
			if (stack.getItemDamage() == EnumItems.POTIONSPLASH.ordinal()) {
		        if (!player.capabilities.isCreativeMode)
		            stack.shrink(1);

		        world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		        if (!world.isRemote)
		        {
		            EntityCustomPotion entitypotion = new EntityCustomPotion(world, player, stack);
		            entitypotion.shoot(player, player.rotationPitch, player.rotationYaw, -20.0F, 0.5F, 1.0F);
		            world.spawnEntity(entitypotion);
		        }
		        return new ActionResult(EnumActionResult.SUCCESS, stack);
			}
			if (stack.getItemDamage() == EnumItems.WEEPINGEYE.ordinal()) {
		        if (!player.capabilities.isCreativeMode)
		            stack.shrink(1);

		        world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		        if (!world.isRemote)
		        {
		            EntityItemWeepingEye eye = new EntityItemWeepingEye(world, player.posX, player.posY + 1.5D, player.posZ);
		            eye.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
		            world.spawnEntity(eye);
		        }
		        return new ActionResult(EnumActionResult.SUCCESS, stack);
			}
			if (stack.getItemDamage() == EnumItems.DOGRESIDUE.ordinal()) {
				boolean canFill = false;
				for (int i = 0; i < player.inventory.getSizeInventory() - player.inventory.armorInventory.size(); i++) {
					ItemStack loopstack = player.inventory.getStackInSlot(i);
					if (loopstack.isEmpty()) {
						player.inventory.setInventorySlotContents(i, new ItemStack(this, 1, 21));
						canFill = true;
					}
				}
				if (!world.isRemote) {
					if (canFill)
						player.sendMessage(new TextComponentString("The rest of your inventory filled up with dog residue."));
					else
						player.sendMessage(new TextComponentString("You finished using it. An uneasy silence fills the room."));
				}
				return new ActionResult(EnumActionResult.SUCCESS, stack);
			}
			if (stack.getItemDamage() == EnumItems.EULA.ordinal()) {
		        if (!player.capabilities.isCreativeMode)
		            stack.shrink(1);

		        world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		        if (!world.isRemote) {
		            EntityEulaBook entitybook = new EntityEulaBook(world, player, stack);
		            entitybook.setPosition(player.posX, player.posY + player.getEyeHeight() + 0.5, player.posZ);
		            entitybook.shoot(player, player.rotationPitch, player.rotationYaw, -20.0F, 0.5F, 1.0F);
		            world.spawnEntity(entitybook);
		        }
		        return new ActionResult(EnumActionResult.SUCCESS, stack);
			}
			if (stack.getItemDamage() == EnumItems.ESCAPEROPE.ordinal() && !world.provider.doesWaterVaporize()) {
				BlockPos highestPos = world.getHeight(player.getPosition());
				if (!player.capabilities.isCreativeMode)
					stack.shrink(1);
				if (!world.isRemote) {
					player.setPositionAndUpdate(highestPos.getX() + 0.5, highestPos.getY(), highestPos.getZ() + 0.5);
				}
				return new ActionResult(EnumActionResult.SUCCESS, stack);
			}
		}
		return new ActionResult(EnumActionResult.PASS, stack);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase elb, EnumHand hand) {
		
		if (stack.getItemDamage() == EnumItems.EGGUPGRADE.ordinal() && elb instanceof EntityChicken) {
			NBTTagCompound tag = elb.getEntityData();
			if (!elb.isChild() && !tag.hasKey(TAG_OVERCLUCK)) {
				tag.setInteger(TAG_OVERCLUCK, elb.world.rand.nextInt(60) + 900);
				if (!player.capabilities.isCreativeMode)
					stack.shrink(1);
				return true;
			}
		}
		return super.itemInteractionForEntity(stack, player, elb, hand);
	}
	
	@Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		
		if (stack.getItemDamage() != 20)
			return;
		
		if (!(entity instanceof EntityPlayer) || (entity instanceof FakePlayer))
			return;
		
		if (itemSlot < ((EntityPlayer)entity).inventory.getHotbarSize()) {
			List<EntityLiving> entities = entity.world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(entity.getPosition().add(-range, -range, -range), entity.getPosition().add(range, range, range)));
			
			for (EntityLiving ent : entities) {
				List<EntityAITaskEntry> entries = new ArrayList(ent.tasks.taskEntries);
				entries.addAll(new ArrayList(ent.targetTasks.taskEntries));
				
				for (EntityAITaskEntry entry : entries) {
					if (entry.action instanceof EntityAIAttackRangedBow) {
						makeSkellyShootSlower((EntityAIAttackRangedBow)entry.action);
					}
				}
				if (ent instanceof EntityCreeper)
					ReflectionHelper.setPrivateValue(EntityCreeper.class, (EntityCreeper)ent, 60, this.FUSETIME);
			}
		}
	}
	
	private void makeSkellyShootSlower(EntityAIAttackRangedBow aiEntry) {
		
		int timeShoot = ReflectionHelper.getPrivateValue(EntityAIAttackRangedBow.class, aiEntry, this.FIELD_G);
		
		if (timeShoot == 40) 
			ReflectionHelper.setPrivateValue(EntityAIAttackRangedBow.class, aiEntry, 100, this.FIELD_G);
	}
	
	@Override
    public int getItemStackLimit(ItemStack stack) {
    	
		if (stack.getTranslationKey().contains("book") || stack.getItemDamage() == EnumItems.DOGRESIDUE.ordinal() || stack.getItemDamage() == EnumItems.ESCAPEROPE.ordinal())
			return 1;
		
		return super.getItemStackLimit(stack);
    }

	@Override
	public int getItemBurnTime(ItemStack fuel) {
		
		if (fuel.getItem() == this && fuel.getItemDamage() == 3)
			return 16000;
		
		return 0;
	}
}
