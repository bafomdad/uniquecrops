package com.bafomdad.uniquecrops.events;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.ItemHandlerHelper;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.blocks.tiles.TileMusicaPlant;
import com.bafomdad.uniquecrops.blocks.tiles.TileMusicaPlant.Beat;
import com.bafomdad.uniquecrops.core.EnumItems;
import com.bafomdad.uniquecrops.core.GrowthSteps;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.SeedBehavior;
import com.bafomdad.uniquecrops.crops.Feroxia;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.ItemGeneric;

public class UCEventHandlerServer {
	
//	private static final String[] ENCHCOST = new String[] { "maximumCost", "field_82854_e", "abf" };
	private int cost;
	private int range = 5;
	
	@SubscribeEvent
	public void updateAnvilCost(AnvilUpdateEvent event) {
		
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		
		if (left == null || right == null)
			return;
		
		if ((left.getItem() == Items.ENCHANTED_BOOK && right.getItem() != Items.ENCHANTED_BOOK) || (left.getItem() != Items.ENCHANTED_BOOK && right.getItem() == Items.ENCHANTED_BOOK))
		{
			ItemStack output = updateRepairOutput(left, right);
			ItemStack toCheck = (left.getItem() != Items.ENCHANTED_BOOK) ? left.copy() : right.copy();
			if (output != null && checkNBT(toCheck))
			{
				int newCost = this.cost;
				if (newCost > 5)
				{
					newCost = newCost - 5;
					event.setOutput(output.copy());
					event.setCost(newCost);
				}
			}
			return;
		}
		else if ((left.getItem() == UCItems.generic && left.getItemDamage() == 18 && right.getItem() instanceof IBookUpgradeable) || (left.getItem() instanceof IBookUpgradeable && right.getItem() == UCItems.generic && right.getItemDamage() == 18))
		{
			ItemStack output = (left.getItem() instanceof IBookUpgradeable) ? left.copy(): right.copy();
			if (output != null) {
				ItemStack newcopy = output.copy();
				if (NBTUtils.getInt(newcopy, ItemGeneric.TAG_UPGRADE, -1) >= 10)
					return;
				
				if (!newcopy.hasTagCompound() || (newcopy.hasTagCompound() && !newcopy.getTagCompound().hasKey(ItemGeneric.TAG_UPGRADE))) {
					NBTUtils.setInt(newcopy, ItemGeneric.TAG_UPGRADE, 1);
				}
				else {
					int upgradelevel = newcopy.getTagCompound().getInteger(ItemGeneric.TAG_UPGRADE);
					NBTUtils.setInt(newcopy, ItemGeneric.TAG_UPGRADE, ++upgradelevel);
				}
				event.setOutput(newcopy);
				event.setCost(5);
			}
			return;
		}
		else if (left.getItem() != null && left.getItem() != null) {
			
			ItemStack output = updateRepairOutput(left, right);
			if (output != null && checkNBT(output)) {
				int newCost = this.cost;
				if (newCost > 5)
				{
					newCost = newCost - 5;
					event.setOutput(output.copy());
					event.setCost(newCost);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void setUpgradeChance(AnvilRepairEvent event) {
		
		ItemStack output = event.getItemResult();
		EntityPlayer player = event.getEntityPlayer();
		if (output == null)
			return;
		if (player == null)
			return;
		
		if (player.worldObj.isRemote)
			return;
		
		if (output.getItem() instanceof IBookUpgradeable) {
			int upgradelevel = NBTUtils.getInt(output, ItemGeneric.TAG_UPGRADE, -1);
			if (upgradelevel == 10) {
				Random rand = new Random();
				if (rand.nextBoolean() == false && !player.worldObj.isRemote) {
					player.addChatMessage(new TextComponentString(TextFormatting.RED + "You attempt to refine this item, but it breaks.."));
					output.stackSize = 0;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void noteBlockPlayEvent(NoteBlockEvent.Play event) {
		
		if (event.getWorld().isRemote)
			return;
		
		BlockPos epos = event.getPos();
		int range = 10;
		for (BlockPos pos : BlockPos.getAllInBoxMutable(epos.add(-range, -2, -range), epos.add(range, 2, range))) {
			TileEntity te = event.getWorld().getTileEntity(pos);
			if (te != null && te instanceof TileMusicaPlant) {
				TileMusicaPlant plant = (TileMusicaPlant)te;
				if (plant.getBeats().size() > 0)
				{
					for (int i = 0; i < plant.getBeats().size(); i++) {
						Beat beat = plant.getBeats().get(i);
						if (beat.beatMatches(new Beat(event.getNote(), event.getInstrument(), event.getOctave(), event.getWorld().getTotalWorldTime()))) {
							plant.setNewBeatTime(i, event.getWorld().getTotalWorldTime());
							return;
						}
					}
				}
				if (plant.canAddNote())
					plant.setNote(event.getNote(), event.getInstrument(), event.getOctave(), event.getWorld().getTotalWorldTime());
			}
		}
	}
	
	@SubscribeEvent
	public void checkHarvestedCrops(BlockEvent.HarvestDropsEvent event) {
		
		Block crop = event.getState().getBlock();
		if (event.getHarvester() != null && event.getHarvester().capabilities.isCreativeMode)
			return;
		
		if (crop == UCBlocks.cropInvisibilia) {
			if (event.getHarvester() == null) 
			{
				event.getDrops().clear();
				event.setResult(Result.DEFAULT);
			}
		}
		if (crop == UCBlocks.cropFeroxia && ((Feroxia)crop).isFullyGrown(event.getWorld(), event.getPos(), event.getState())) {
			if (event.getHarvester() != null && !(event.getHarvester() instanceof FakePlayer))
				GrowthSteps.generateSteps(event.getHarvester());
			else
				event.getDrops().clear();
			event.setResult(Result.DEFAULT);
		}
		if (crop == UCBlocks.cropDyeius && !event.getDrops().isEmpty()) {
			for (ItemStack stack : event.getDrops()) {
				if (stack.getItem() == Items.DYE)
				{
					long time = event.getWorld().getWorldTime() % 24000L;
					int meta = (int)(time / 1500);
					LocalDateTime current = LocalDateTime.now();
					if (current.getDayOfWeek() == DayOfWeek.FRIDAY)
						stack.setItemDamage(EnumDyeColor.byMetadata(meta).getMetadata());
					else
						stack.setItemDamage(EnumDyeColor.byMetadata(meta).getDyeDamage());
				}
			}
		}
	}
	
	@SubscribeEvent
	public void checkPlayerDeath(LivingDeathEvent event) {
		
		if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			NBTTagCompound tag = player.getEntityData();
			if (tag.hasKey("hasSacrificed") && !tag.getBoolean("hasSacrificed"))
			{
				EntityItem ei = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, new ItemStack(UCItems.heart));
				tag.setBoolean("hasSacrificed", true);
				if (!player.worldObj.isRemote)
					player.worldObj.spawnEntityInWorld(ei);
				return;
			}
		}
	}
	
	@SubscribeEvent
	public void checkDrops(LivingDropsEvent event) {
		
		if (event.getEntityLiving() != null && event.getEntityLiving() instanceof EntityChicken) {
	    	if (!event.getEntityLiving().worldObj.isRemote) {
	    		EntityChicken chicken = (EntityChicken)event.getEntityLiving();
	    		NBTTagCompound tag = chicken.getEntityData();
	    		if (!chicken.isChild() && tag.hasKey(ItemGeneric.TAG_OVERCLUCK)) 
	    			addDrop(event, UCItems.generic.createStack(EnumItems.EGGUPGRADE));
	    	}
		}
		else if (event.getEntityLiving() != null && !(event.getEntityLiving() instanceof EntityPlayer) && event.getSource().getSourceOfDamage() instanceof EntityPlayer) {
			EntityLiving el = (EntityLiving)event.getEntityLiving();
			EntityPlayer player = (EntityPlayer)event.getSource().getSourceOfDamage();
			EntityEquipmentSlot[] slot = new EntityEquipmentSlot[] { EntityEquipmentSlot.FEET };
			ItemStack boots = el.getItemStackFromSlot(slot[0]);
			if (boots != null && player.inventory.hasItemStack(UCItems.generic.createStack(EnumItems.SLIPPER))) {
				if (player.worldObj.rand.nextInt(5) == 0) {
					addDrop(event, new ItemStack(UCItems.slippers));
					for (int i = 0; i < player.inventory.mainInventory.length; i++) {
						ItemStack oneboot = player.inventory.getStackInSlot(i);
						if (oneboot != null && oneboot.getItem() == UCItems.generic && oneboot.getItemDamage() == 14) {
							player.inventory.setInventorySlotContents(i, null);
							break;
						}
					}
				}
			}
			if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == UCItems.precisionAxe) {
				if (NBTUtils.getInt(player.getHeldItemMainhand(), ItemGeneric.TAG_UPGRADE, -1) == 10) {
					Random rand = el.worldObj.rand;
					int looting = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand());
					if (rand.nextInt(15) <= 2 + looting) {
						if (el instanceof EntitySkeleton)
							addDrop(event, new ItemStack(Items.SKULL, 1, ((EntitySkeleton)el).func_189771_df().ordinal()));
						if (el instanceof EntityZombie)
							addDrop(event, new ItemStack(Items.SKULL, 1, 2));
						if (el instanceof EntityCreeper)
							addDrop(event, new ItemStack(Items.SKULL, 1, 4));
					}
				}
			}
		}
	}
	
	private void addDrop(LivingDropsEvent event, ItemStack drop) {
		
		EntityItem ei = new EntityItem(event.getEntityLiving().worldObj, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, drop);
		ei.setPickupDelay(10);
		event.getDrops().add(ei);
	}
	
	@SubscribeEvent
	public void checkPlayerNBT(PlayerEvent.Clone event) {
		
		if (!event.isWasDeath())
			return;
		
		EntityPlayer old = event.getOriginal();
		EntityPlayer player = event.getEntityPlayer();
		
		NBTTagCompound oldtag = old.getEntityData();
		NBTTagCompound tag = player.getEntityData();
		if (oldtag.hasKey(GrowthSteps.TAG_GROWTHSTAGES)) {
			tag.setTag(GrowthSteps.TAG_GROWTHSTAGES, oldtag.getTagList(GrowthSteps.TAG_GROWTHSTAGES, 10).copy());
		}
		if (oldtag.hasKey("hasSacrificed"))
			tag.setBoolean("hasSacrificed", oldtag.getBoolean("hasSacrificed"));
		if (oldtag.hasKey(SeedBehavior.TAG_ABSTRACT))
			tag.setInteger(SeedBehavior.TAG_ABSTRACT, oldtag.getInteger(SeedBehavior.TAG_ABSTRACT));
	}
	
	@SubscribeEvent
	public void checkSetTarget(LivingSetAttackTargetEvent event) {
		
		if (event.getTarget() == null)
			return;
		if (!(event.getTarget() instanceof EntityPlayer) || event.getTarget() instanceof FakePlayer)
			return;
		if (!(event.getEntity() instanceof EntityLiving))
			return;
		
		EntityPlayer player = (EntityPlayer)event.getTarget();
		EntityLiving ent = (EntityLiving)event.getEntity();
		boolean flag = player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() == UCItems.poncho && NBTUtils.getInt(player.inventory.armorInventory[2], ItemGeneric.TAG_UPGRADE, -1) == 10;
		if (flag && ent.isNonBoss() && !(ent instanceof EntityGuardian || ent instanceof EntityShulker))
		{
			ent.setAttackTarget(null);
			ent.setRevengeTarget(null);
		}
	}
	
	@SubscribeEvent
	public void checkSlippers(LivingAttackEvent event) {
		
		if (event.getSource() != DamageSource.cactus)
			return;
		
		if (!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer)event.getEntityLiving();
		ItemStack boots = player.inventory.armorInventory[0];
		BlockPos pos = new BlockPos(MathHelper.floor_double(player.posX), player.getPosition().down().getY(), MathHelper.floor_double(player.posZ));
		Block cactus = player.worldObj.getBlockState(pos).getBlock();
		if ((cactus != null && cactus == Blocks.CACTUS) && (boots != null && boots.getItem() == UCItems.slippers))
			event.setCanceled(true);
	}
	
    @SubscribeEvent
    public void maximumOvercluck(LivingEvent.LivingUpdateEvent event) {
    	
    	if (!event.getEntityLiving().worldObj.isRemote && event.getEntityLiving() instanceof EntityChicken) {
    		EntityChicken chicken = (EntityChicken)event.getEntityLiving();
    		NBTTagCompound tag = chicken.getEntityData();
    		if (!chicken.isChild() && tag.hasKey(ItemGeneric.TAG_OVERCLUCK)) {
    			int timer = tag.getInteger(ItemGeneric.TAG_OVERCLUCK);
    			tag.setInteger(ItemGeneric.TAG_OVERCLUCK, --timer);
    			if (--timer <= 0)
    			{
    	            chicken.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (chicken.worldObj.rand.nextFloat() - chicken.worldObj.rand.nextFloat()) * 0.2F + 1.0F);
    	            chicken.dropItem(Items.EGG, 1);
    	            timer = chicken.worldObj.rand.nextInt(60) + 900;
    	            tag.setInteger(ItemGeneric.TAG_OVERCLUCK, timer);
    			}
    		}
    	}
    }
    
    @SubscribeEvent
    public void silverfishBlocks(BlockEvent.BreakEvent event) {
    	
    	if (event.getPlayer() == null)
    		return;

    	EntityPlayer player = event.getPlayer();
    	boolean flag = player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == UCItems.precisionPick;
    	if (!flag) return;
    	
    	if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == UCItems.precisionPick) {
    		if (NBTUtils.getInt(player.getHeldItemMainhand(), ItemGeneric.TAG_UPGRADE, -1) < 10) 
    			return;
    		else {
    			if (event.getState().getBlock() == Blocks.MONSTER_EGG) {
        			event.setCanceled(true);
        			event.getWorld().setBlockToAir(event.getPos());
        			EntityItem ei = new EntityItem(event.getWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, new ItemStack(event.getState().getBlock()));
        			if (!event.getWorld().isRemote)
        				event.getWorld().spawnEntityInWorld(ei);
        			player.getHeldItemMainhand().attemptDamageItem(1, event.getWorld().rand);
        			return;
    			}
    			if (event.getState().getBlock() == Blocks.MOB_SPAWNER) {
					event.setCanceled(true);
    				TileEntity tile = event.getWorld().getTileEntity(event.getPos());
    				if (tile != null && tile instanceof TileEntityMobSpawner) {
    					ItemStack stack = new ItemStack(Blocks.MOB_SPAWNER);
    					if (!event.getWorld().isRemote) { 
    						NBTUtils.setCompound(stack, "Spawner", ((TileEntityMobSpawner)tile).serializeNBT());
    						
    						EntityItem ei = new EntityItem(event.getWorld(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, stack);
    						event.getWorld().spawnEntityInWorld(ei);
    					}
    					event.getWorld().setBlockToAir(event.getPos());
    					player.getHeldItemMainhand().attemptDamageItem(1, event.getWorld().rand);
    					return;
    				}
    			}
    		}
    	}
    }
    
    @SubscribeEvent
    public void placeMobSpawner(BlockEvent.PlaceEvent event) {
    	
    	if (event.getState().getBlock() != Blocks.MOB_SPAWNER) return;
    	
    	ItemStack spawner = event.getItemInHand();
    	if (spawner.hasTagCompound() && spawner.getTagCompound().hasKey("Spawner") && Block.getBlockFromItem(spawner.getItem()) == Blocks.MOB_SPAWNER) {
    		TileEntity tile = TileEntity.func_190200_a(event.getWorld(), spawner.getTagCompound().getCompoundTag("Spawner"));
    		event.getWorld().setTileEntity(event.getPos(), tile);
    	}
    }
    
    @SubscribeEvent
    public void showTooltip(ItemTooltipEvent event) {
    	
    	if (event.getItemStack().getItem() != Item.getItemFromBlock(Blocks.MOB_SPAWNER)) return;
    	
    	ItemStack tooltipper = event.getItemStack();
    	if (!tooltipper.hasTagCompound() || (tooltipper.hasTagCompound() && !tooltipper.getTagCompound().hasKey("Spawner"))) return;
    	
    	NBTTagCompound tag = tooltipper.getTagCompound().getCompoundTag("Spawner");
    	event.getToolTip().add("Mob spawner data:");
    	event.getToolTip().add(TextFormatting.GOLD + tag.getCompoundTag("SpawnData").getString("id"));
    }
    
	@SubscribeEvent
	public void onBlockFall(EntityJoinWorldEvent event) {
		
		if (event.getEntity() != null && event.getEntity() instanceof EntityFallingBlock) {
			EntityPlayer player = event.getEntity().worldObj.getClosestPlayerToEntity(event.getEntity(), range);
			if (player != null && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == UCItems.precisionShovel) {
				if (NBTUtils.getInt(player.getHeldItemMainhand(), ItemGeneric.TAG_UPGRADE, -1) == 10)
					event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {

		if (event.player.inventory.armorInventory[3] != null && event.player.inventory.armorInventory[3].getItem() == UCItems.pixelglasses) {
			boolean flag = NBTUtils.getBoolean(event.player.inventory.armorInventory[3], "isActive", false);
			if (event.side == Side.CLIENT) {
				if (flag)
					UniqueCrops.proxy.enableBitsShader();
				else
					UniqueCrops.proxy.disableBitsShader();
			}
		}
		else
			UniqueCrops.proxy.disableBitsShader();
		
		if (event.player.getEntityData().hasKey(SeedBehavior.TAG_ABSTRACT)) {
			if (event.phase == Phase.START && event.player.worldObj.rand.nextInt(1000) == 0) {
				Random rand = new Random();
				if (rand.nextInt(10) != 0) {
					ItemHandlerHelper.giveItemToPlayer(event.player, UCItems.generic.createStack(EnumItems.ABSTRACT));
					if (!event.player.worldObj.isRemote)
						SeedBehavior.setAbstractCropGrowth(event.player, false);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event) {
		
		if (!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer)event.getEntityLiving();
		if (player.getActivePotionEffect(MobEffects.SLOWNESS) != null && player.getActivePotionEffect(MobEffects.SLOWNESS).getAmplifier() >= 4)
			player.motionY = 0;
	}
	
    private ItemStack updateRepairOutput(ItemStack input1, ItemStack input2) {

        ItemStack itemstack = input1;
        int maximumCost = 1;
        int materialCost = 0;
        int i = 0;
        int j = 0;
        int k = 0;

        if (itemstack == null)
        {
        	return null;
        }
        else
        {
            ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = input2;
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemstack1);
            j = j + itemstack.getRepairCost() + (itemstack2 == null ? 0 : itemstack2.getRepairCost());
            materialCost = 0;
            boolean flag = false;

            if (itemstack2 != null)
            {
                flag = itemstack2.getItem() == Items.ENCHANTED_BOOK && !Items.ENCHANTED_BOOK.getEnchantments(itemstack2).hasNoTags();

                if (itemstack1.isItemStackDamageable() && itemstack1.getItem().getIsRepairable(itemstack, itemstack2))
                {
                    int j2 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);

                    if (j2 <= 0)
                    {
                    	return null;
                    }
                    int k2;

                    for (k2 = 0; j2 > 0 && k2 < itemstack2.stackSize; ++k2)
                    {
                        int l2 = itemstack1.getItemDamage() - j2;
                        itemstack1.setItemDamage(l2);
                        ++i;
                        j2 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);
                    }
                    materialCost = k2;
                }
                else
                {
                    if (!flag && (itemstack1.getItem() != itemstack2.getItem() || !itemstack1.isItemStackDamageable()))
                    {
                    	return null;
                    }
                    if (itemstack1.isItemStackDamageable() && !flag)
                    {
                        int l = itemstack.getMaxDamage() - itemstack.getItemDamage();
                        int i1 = itemstack2.getMaxDamage() - itemstack2.getItemDamage();
                        int j1 = i1 + itemstack1.getMaxDamage() * 12 / 100;
                        int k1 = l + j1;
                        int l1 = itemstack1.getMaxDamage() - k1;

                        if (l1 < 0)
                        {
                            l1 = 0;
                        }

                        if (l1 < itemstack1.getMetadata())
                        {
                            itemstack1.setItemDamage(l1);
                            i += 2;
                        }
                    }
                    Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack2);

                    for (Enchantment enchantment1 : map1.keySet())
                    {
                        if (enchantment1 != null)
                        {
                            int i3 = map.containsKey(enchantment1) ? ((Integer)map.get(enchantment1)).intValue() : 0;
                            int j3 = ((Integer)map1.get(enchantment1)).intValue();
                            j3 = i3 == j3 ? j3 + 1 : Math.max(j3, i3);
                            boolean flag1 = enchantment1.canApply(itemstack);

                            if (itemstack.getItem() == Items.ENCHANTED_BOOK)
                            {
                                flag1 = true;
                            }

                            for (Enchantment enchantment : map.keySet())
                            {
                                if (enchantment != enchantment1 && !(enchantment1.canApplyTogether(enchantment) && enchantment.canApplyTogether(enchantment1)))  //Forge BugFix: Let Both enchantments veto being together
                                {
                                    flag1 = false;
                                    ++i;
                                }
                            }
                            if (flag1)
                            {
                                if (j3 > enchantment1.getMaxLevel())
                                {
                                    j3 = enchantment1.getMaxLevel();
                                }
                                map.put(enchantment1, Integer.valueOf(j3));
                                int k3 = 0;

                                switch (enchantment1.getRarity())
                                {
                                    case COMMON:
                                        k3 = 1;
                                        break;
                                    case UNCOMMON:
                                        k3 = 2;
                                        break;
                                    case RARE:
                                        k3 = 4;
                                        break;
                                    case VERY_RARE:
                                        k3 = 8;
                                }
                                if (flag)
                                {
                                    k3 = Math.max(1, k3 / 2);
                                }
                                i += k3 * j3;
                            }
                        }
                    }
                }
            }
            if (flag && !itemstack1.getItem().isBookEnchantable(itemstack1, itemstack2)) 
            	itemstack1 = null;

            maximumCost = j + i;

            if (i <= 0)
            {
                itemstack1 = null;
            }
            if (k == i && k > 0 && maximumCost >= 40)
            {
                maximumCost = 39;
            }
            if (maximumCost >= 40 && (maximumCost - 5) >= 40)
            {
                itemstack1 = null;
            }
            if (itemstack1 != null)
            {
                int i2 = itemstack1.getRepairCost();

                if (itemstack2 != null && i2 < itemstack2.getRepairCost())
                {
                    i2 = itemstack2.getRepairCost();
                }

                if (k != i || k == 0)
                {
                    i2 = i2 * 2 + 1;
                }

                itemstack1.setRepairCost(i2);
                EnchantmentHelper.setEnchantments(map, itemstack1);
            }
            setCost(maximumCost);
            return itemstack1;
        }
    }
    
    private void setCost(int cost) {
    	
    	this.cost = cost;
    }
    
    private boolean checkNBT(ItemStack input) {
    	
    	return NBTUtils.getBoolean(input, ItemGeneric.TAG_DISCOUNT, false);
    }
}
