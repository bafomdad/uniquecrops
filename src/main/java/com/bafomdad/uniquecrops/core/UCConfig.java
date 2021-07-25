package com.bafomdad.uniquecrops.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

//TODO: config stuff
public final class UCConfig {

    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static class Client {

        public final ForgeConfigSpec.IntValue guiWidth;
        public final ForgeConfigSpec.IntValue guiHeight;

        public Client(ForgeConfigSpec.Builder builder) {

            guiWidth = builder
                    .comment("Adjust placement of Wildwood staff GUI on the x axis.")
                    .defineInRange("guiWidth", -191, -1000, 1000);
            guiHeight = builder
                    .comment("Adjust placement of Wildwood staff GUI on the y axis.")
                    .defineInRange("guiHeight", -50, -1000, 1000);
        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common {

        public final ForgeConfigSpec.IntValue millenniumTime;
        public final ForgeConfigSpec.IntValue cubeCooldown;
        public final ForgeConfigSpec.IntValue energyPerTick;

        public Common(ForgeConfigSpec.Builder builder) {

            millenniumTime = builder
                    .comment("Time (in minutes) it takes for Millennium crop to advance a stage.")
                    .defineInRange("millenniumTime", 10, 10, Integer.MAX_VALUE);
            cubeCooldown = builder
                    .comment("Cooldown time (in ticks) for rubik's cube between successful teleports.")
                    .defineInRange("cubeCooldown", 3000, 30, Integer.MAX_VALUE);
            energyPerTick = builder
                    .comment("Amount of energy gained per tick while the Industria crop grows.")
                    .defineInRange("energyPerTick", 20, 1, 200);
        }
    }
}
