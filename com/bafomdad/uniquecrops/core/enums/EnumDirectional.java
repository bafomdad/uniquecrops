package com.bafomdad.uniquecrops.core.enums;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;

public enum EnumDirectional implements IStringSerializable {

	NORTH("north"),
	SOUTH("south"),
	EAST("east"),
	WEST("west"),
	NORTHEAST("northeast"),
	NORTHWEST("northwest"),
	SOUTHEAST("southeast"),
	SOUTHWEST("southwest"),
	UP("up"),
	DOWN("down");
	
	private final String name;
	
	private EnumDirectional(String name) {
		
		this.name = name;
	}
	
	public BlockPos getOffset(BlockPos pos) {
		
		switch (this) {
			case NORTH: return pos.north();
			case SOUTH: return pos.south();
			case EAST: return pos.east();
			case WEST: return pos.west();
			case NORTHEAST: return pos.north().east();
			case NORTHWEST: return pos.north().west();
			case SOUTHEAST: return pos.south().east();
			case SOUTHWEST: return pos.south().west();
			default: return pos;
		}
	}
	
	public static EnumDirectional byIndex(int meta) {
		
		return EnumDirectional.values()[meta];
	}
	
	@Override
	public String toString() {
		
		return this.name;
	}

	@Override
	public String getName() {

		return name;
	}
}
