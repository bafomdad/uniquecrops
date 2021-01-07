package com.bafomdad.uniquecrops.entities;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.bafomdad.uniquecrops.core.enums.EnumItems;
import com.bafomdad.uniquecrops.crops.Magnes;
import com.bafomdad.uniquecrops.init.UCBlocks;

public class EntityMovingCrop extends Entity {
	
	EnumFacing dir;
	int distance;
	
	public EntityMovingCrop(World world) {
		
		super(world);
	}

	public EntityMovingCrop(World world, BlockPos pos, EnumFacing facing, int dist) {
		
		super(world);
		this.setNoGravity(true);
		this.setInvisible(true);
		this.setEntityInvulnerable(true);
		this.setPosition(pos.getX() + 0.25, pos.getY(), pos.getZ() + 0.25);
		setSize(0.5F, 0.5F);
		this.dir = facing;
		this.distance = dist;
	}
	
	@Override
	public void onUpdate() {

		if (dir == null) {
			return;
		}
		double vel = 0.2625D;
		if (this.getPassengers().isEmpty() && !this.getEntityData().hasKey("UC:markedForDrop")) {
			this.setDead();
			return;
		}
		if (this.getEntityData().hasKey("UC:markedForDrop")) {
			if (!world.isRemote) {
				IBlockState heldState = ((EntityFallingBlock)this.getPassengers().get(0)).getBlock();
				this.getPassengers().forEach(p -> p.setDead());
				int chance = Math.max(8 - this.distance, 1);
				if (world.rand.nextInt(chance) == 0 && heldState.getBlock() == UCBlocks.cropMagnets && heldState.getValue(Magnes.POLARITY))
					InventoryHelper.spawnItemStack(this.world, this.posX, this.posY, this.posZ, EnumItems.FERROMAGNETIC_IRON.createStack());
				BlockPos toSet = new BlockPos(Math.floor(this.posX), Math.floor(this.posY), Math.floor(this.posZ));
				if (!world.isAirBlock(toSet)) {
					for (EnumFacing facing : EnumFacing.HORIZONTALS) {
						if (world.isAirBlock(toSet.offset(facing)) && world.getBlockState(toSet.offset(facing).down()).getBlock() == Blocks.FARMLAND) {
							world.setBlockState(toSet.offset(facing), UCBlocks.cropMagnets.getDefaultState(), 2);
							break;
						}
					}
				} else
					world.setBlockState(toSet, UCBlocks.cropMagnets.getDefaultState(), 2);
				
				this.getEntityData().removeTag("UC:markedForDrop");
			}
			return;
		}
		List<EntityMovingCrop> entities = world.getEntitiesWithinAABB(EntityMovingCrop.class, this.getEntityBoundingBox());
		if (entities.size() == 2) {
			entities.forEach(e -> {
				this.getEntityData().setBoolean("UC:markedForDrop", true);
//				if (!world.isRemote) {
//					IBlockState heldState = ((EntityFallingBlock)e.getPassengers().get(0)).getBlock();
//					e.getPassengers().forEach(p -> p.setDead());
//					int chance = Math.max(8 - e.distance, 1);
//					if (world.rand.nextInt(chance) == 0 && heldState.getBlock() == UCBlocks.cropMagnets && heldState.getValue(Magnes.POLARITY))
//						InventoryHelper.spawnItemStack(e.world, e.posX, e.posY, e.posZ, EnumItems.FERROMAGNETIC_IRON.createStack());
//					world.setBlockState(new BlockPos(Math.ceil(e.posX), Math.ceil(e.posY), Math.ceil(e.posZ)), UCBlocks.cropMagnets.getDefaultState(), 2);
//				}
			});
		}
		int vecx = this.dir.getDirectionVec().getX();
		int vecy = this.dir.getDirectionVec().getY();
		int vecz = this.dir.getDirectionVec().getZ();
		this.move(MoverType.SELF, vecx * vel, vecy * vel, vecz * vel);
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {

		this.dir = EnumFacing.byIndex(tag.getByte("UC_facing"));
		this.distance = tag.getInteger("UC_searchDistance");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {

		if (dir != null)
			tag.setByte("UC_facing", (byte)this.dir.getIndex());
		tag.setInteger("UC_searchDistance", this.distance);
	}
	
	@Override
    public double getMountedYOffset() {
        
//    	return -0.15D;
		return 0D;
    }
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		
		return false;
	}
}
