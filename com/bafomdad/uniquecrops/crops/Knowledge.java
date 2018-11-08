package com.bafomdad.uniquecrops.crops;

import java.util.Iterator;
import java.util.Random;
import java.util.regex.Pattern;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.core.UCConfig;
import com.bafomdad.uniquecrops.init.UCItems;

public class Knowledge extends BlockCropsBase {
	
	Pattern pat = Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final String BOOKMARK = "UC:tagBookmark";

	public Knowledge() {
		
		super(EnumCrops.BOOKPLANT);
	}
	
	@Override
	public Item getSeed() {
		
		return UCItems.seedsKnowledge;
	}
	
	@Override
	public Item getCrop() {
		
		return UCItems.generic;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		
		if (getAge(state) < getMaxAge())
			return 0;
		
		return 1;
	}
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		this.checkAndDropBlock(world, pos, state);
		if (this.getAge(state) >= getMaxAge() || world.isRemote)
			return;
		
		int growStages = consumeKnowledge(world, pos);
		if (growStages > 0)
			world.setBlockState(pos, this.withAge(Math.min(getAge(state) + growStages, getMaxAge())), 2);
	}
	
	private int consumeKnowledge(World world, BlockPos pos) {
		
		Iterable<BlockPos> getBox = BlockPos.getAllInBox(pos.add(-4, -2, -4), pos.add(4, 2, 4));
		Iterator it = getBox.iterator();
		while (it.hasNext()) {
			BlockPos posit = (BlockPos)it.next();
			Block loopblock = world.getBlockState(posit).getBlock();
			if (loopblock == Blocks.BOOKSHELF) {
				TileEntity te = world.getTileEntity(posit.up());
				if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)) {
					IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
					for (int i = 0; i < cap.getSlots(); i++) {
						ItemStack book = cap.getStackInSlot(i);
						if (!book.isEmpty() && book.getItem() == Items.WRITTEN_BOOK) {
							NBTTagCompound tag = book.getTagCompound();
							if (ItemWrittenBook.validBookTagContents(tag) && !NBTUtils.getBoolean(book, BOOKMARK, false) && ItemWrittenBook.getGeneration(book) == 0) {
								
								int result = 0;
								NBTTagList tagList = tag.getTagList("pages", 8);
								for (int j = 0; j < tagList.tagCount(); j++) {
									String str = tagList.getStringTagAt(j);
									ITextComponent text; 
									try {
										text = ITextComponent.Serializer.fromJsonLenient(str);
										
									} catch (Exception e) {
										e.printStackTrace();
										text = new TextComponentString(str);
									}
									String newString = eatSomeVowels(text.getUnformattedText());
									ITextComponent newComponent = new TextComponentString(newString);
									tagList.set(j, new NBTTagString(ITextComponent.Serializer.componentToJson(newComponent)));
									result = j + 1;
								}
								tag.setTag("pages", tagList);
								NBTUtils.setBoolean(book, BOOKMARK, true);
								return result;
							}
						}
					}
				}
			}
		}
		return 0;
	}
	
	private String eatSomeVowels(String str) {
		
		StringBuilder sb = new StringBuilder(str);
		if (str.length() >= 100 && str.length() <= 512) {
			sb.replace(0, str.length(), str.replaceAll(pat.pattern(), " "));
			return sb.toString();
		}
		return str;
	}
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	
    	if (getAge(state) >= getMaxAge()) {
    		double x = pos.getX();
    		double y = pos.getY() + 0.5D;
    		double z = pos.getZ();
        	world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, x + rand.nextFloat(), y, z + rand.nextFloat(), rand.nextGaussian(), rand.nextFloat(), rand.nextGaussian());
    	}
    }
    
    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos) {
    	
    	return (getAge(world.getBlockState(pos)) >= getMaxAge()) ? 3 : 0;
    }
}
