package com.bafomdad.uniquecrops.core;

import java.util.stream.Stream;

public class UCTest {

    public static void main(String[] args) {

        String[] testArray = new String[] {
                "XOX",
                "JLJ",
                "WOW"
        };
        String[] newTestArray = Stream.of(testArray).map(s -> s.contains("O")).toArray(String[]::new);
        System.out.println(newTestArray);
    }
}
