package com.bafomdad.uniquecrops.crops.supercrops;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.blocks.BlockSuperCropsBase;
import com.bafomdad.uniquecrops.blocks.tiles.TileFascino;
import com.bafomdad.uniquecrops.blocks.tiles.TileFascino.Stage;
import com.bafomdad.uniquecrops.core.enums.EnumSuperCrops;
import com.bafomdad.uniquecrops.init.UCItems;

public class Fascino extends BlockSuperCropsBase {

	public Fascino() {
		
		super(EnumSuperCrops.FASCINO);
		GameRegistry.registerTileEntity(TileFascino.class, new ResourceLocation(UniqueCrops.MOD_ID, "TileFascino"));
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileFascino) {
			TileFascino fe = (TileFascino)tile;
			if (fe.getStage() != Stage.IDLE) return true;
			
			ItemStack stack = player.getHeldItem(hand);
			if (stack.getItem() == UCItems.wildwoodStaff) {
				if (fe.getStage() == Stage.IDLE) {
					fe.checkEnchants(player, stack);
				}
				return true;
			}
			if (!stack.isEmpty() && !player.isSneaking()) {
				player.setHeldItem(hand, ItemHandlerHelper.insertItem(fe.getInventory(), stack, false));
				fe.markBlockForUpdate();
				return true;
			}
			if (stack.isEmpty() && player.isSneaking()) {
				for (int i = fe.getInventory().getSlots() - 1; i >= 0; i--) {
					ItemStack tileStack = fe.getInventory().getStackInSlot(i);
					if (!tileStack.isEmpty()) {
						ItemHandlerHelper.giveItemToPlayer(player, tileStack);
						((ItemStackHandler)fe.getInventory()).setStackInSlot(i, ItemStack.EMPTY);
						fe.markDirty();
						break;
					}
				}
				fe.markBlockForUpdate();
				return true;
			}
		}
		return false;
	}
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileFascino) {
			TileFascino fe = (TileFascino)tile;
			for (int i = 0; i < fe.getInventory().getSlots(); i++) {
				ItemStack stack = fe.getInventory().getStackInSlot(i);
				if (!stack.isEmpty())
					InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			}
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override
    public boolean canSustainBush(IBlockState state) {
        
    	return state.getBlock() != Blocks.AIR;
    }
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		
        return FULL_BLOCK_AABB;
    }
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return Item.getItemFromBlock(Blocks.ENCHANTING_TABLE);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		
		return new TileFascino();
	}
}
