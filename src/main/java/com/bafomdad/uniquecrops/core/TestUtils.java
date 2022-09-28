package com.bafomdad.uniquecrops.core;

import net.minecraft.core.BlockPos;

public class TestUtils {

    public static void main(String[] args) {

        long num = Long.parseLong(args[0]);
        BlockPos pos = BlockPos.of(num);
        System.out.println(pos);
    }
}
