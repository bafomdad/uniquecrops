package com.bafomdad.uniquecrops.crops;

import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.BlockCrops;
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
import net.minecraftforge.fml.common.FMLCommonHandler;
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

	public Knowledge() {
		
		super(EnumCrops.BOOKPLANT, true, UCConfig.cropKnowledge);
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
		
		if (this.getAge(state) >= getMaxAge() || world.isRemote)
			return;
		
		Iterable<BlockPos> getBox = BlockPos.getAllInBox(pos.add(-4, -2, -4), pos.add(4, 2, 4));
		Iterator it = getBox.iterator();
		while (it.hasNext()) {
			BlockPos fromIt = (BlockPos)it.next();
			IBlockState loopstate = world.getBlockState(fromIt);
			if (loopstate.getBlock() == Blocks.BOOKSHELF) {
				TileEntity te = world.getTileEntity(fromIt.add(0, 1, 0));
				if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)) {
					IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
					boolean found = false;
					for (int i = 0; i < cap.getSlots(); i++) {
						if (found)
							return;
						ItemStack book = cap.getStackInSlot(i);
						if (!book.isEmpty() && book.getItem() == Items.WRITTEN_BOOK) {
							NBTTagCompound tag = book.getTagCompound();
							if (ItemWrittenBook.validBookTagContents(tag) && !NBTUtils.getBoolean(book, "UC_tagRead", false) && ItemWrittenBook.getGeneration(book) == 0)
							{
								NBTTagList taglist = tag.getTagList("pages", 8);
								for (int j = 0; j < taglist.tagCount(); ++j)
								{
									String str = taglist.getStringTagAt(j);
									ITextComponent text = ITextComponent.Serializer.fromJsonLenient(str);
									try
									{
										if (str.length() >= 100 && str.length() <= 512)
											found = true;
									} 
									catch (Exception e) 
									{
										System.out.println("whoopsy doopsy");
										return;
									}
									if (found) {
										ITextComponent newStr = eatSomeLetters(text);
										taglist.set(j, new NBTTagString(newStr.getUnformattedText()));
										break;
									}
								}
								tag.setTag("pages", taglist);
								tag.setBoolean("UC_tagRead", true);
								
								if (found) {
									int addAge = taglist.tagCount();
									if (getAge(state) + addAge >= ((BlockCrops)state.getBlock()).getMaxAge())
										world.setBlockState(pos, ((BlockCrops)state.getBlock()).withAge(((BlockCrops)state.getBlock()).getMaxAge()), 2);
									else
										world.setBlockState(pos, ((BlockCrops)state.getBlock()).withAge(getAge(state) + addAge), 2);
								}
								return;
							}
						}
					}
				}
			}
		}
	}
	
	private static ITextComponent eatSomeLetters(ITextComponent text) {
		
		Random rand = new Random();
		String str = text.getUnformattedText();
		StringBuilder sb = new StringBuilder(str);
		for (int i = 0; i < str.length(); ++i) {
			if (sb.charAt(i) != Character.MIN_VALUE && rand.nextInt(2) == 0)
			{
				sb.setCharAt(i, ' ');
			}
		}
		return new TextComponentString(sb.toString());
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
}
