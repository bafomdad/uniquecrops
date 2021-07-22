package com.bafomdad.uniquecrops.core.enums;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;

public enum EnumDirectional implements IStringSerializable {

    NORTH(0, 1, "north"),
    SOUTH(1, 0, "south"),
    EAST(2, 3, "east"),
    WEST(3, 2, "west"),
    NORTHEAST(4, 7, "northeast"),
    NORTHWEST(5, 6, "northwest"),
    SOUTHEAST(6, 5, "southeast"),
    SOUTHWEST(7, 4, "southwest"),
    UP(8, 9, "up"),
    DOWN(9, 8, "down");

    private final String name;
    private final int index;
    private final int opposite;

    private EnumDirectional(int index, int opposite, String name) {

        this.name = name;
        this.index = index;
        this.opposite = opposite;
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

    public EnumDirectional getOpposite() {

        return byIndex(this.opposite);
    }

    public static EnumDirectional byIndex(int meta) {

        return EnumDirectional.values()[meta];
    }

    @Override
    public String toString() {

        return this.name;
    }

    @Override
    public String getString() {

        return this.name;
    }
}
