package com.bafomdad.uniquecrops.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

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
        public final ForgeConfigSpec.BooleanValue convertObsidian;

        public final ForgeConfigSpec.BooleanValue moonPhase;
        public final ForgeConfigSpec.BooleanValue hasTorch;
        public final ForgeConfigSpec.BooleanValue likesDarkness;
        public final ForgeConfigSpec.BooleanValue dryFarmland;
        public final ForgeConfigSpec.BooleanValue underFarmland;
        public final ForgeConfigSpec.BooleanValue burningPlayer;
        public final ForgeConfigSpec.BooleanValue hellWorld;
        public final ForgeConfigSpec.BooleanValue likesLilypads;
        public final ForgeConfigSpec.BooleanValue likesHeights;
        public final ForgeConfigSpec.BooleanValue thirstyPlant;
        public final ForgeConfigSpec.BooleanValue hungryPlant;
        public final ForgeConfigSpec.BooleanValue likesChicken;
        public final ForgeConfigSpec.BooleanValue likesRedstone;
        public final ForgeConfigSpec.BooleanValue vampirePlant;
        public final ForgeConfigSpec.BooleanValue fullBrightness;
        public final ForgeConfigSpec.BooleanValue likesWarts;
        public final ForgeConfigSpec.BooleanValue likesCooking;
        public final ForgeConfigSpec.BooleanValue likesBrewing;
        public final ForgeConfigSpec.BooleanValue likesCheckers;
        public final ForgeConfigSpec.BooleanValue dontBonemeal;
        public final ForgeConfigSpec.BooleanValue selfSacrifice;

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
            convertObsidian = builder
                    .comment("Lets the Petramia crop convert obsidian instead of bedrock. Use if there are no bedrock nearby to convert.")
                    .define("convertObsidian", false);

            moonPhase = builder.define("moonPhase", true);
            hasTorch = builder.define("hasTorch", true);
            likesDarkness = builder.define("likesDarkness", true);
            dryFarmland = builder.define("dryFarmland", true);
            underFarmland = builder.define("underFarmland", true);
            burningPlayer = builder.define("burningPlayer", true);
            hellWorld = builder.define("hellWorld", true);
            likesLilypads = builder.define("likesLilypads", true);
            likesHeights = builder.define("likesHeights", true);
            thirstyPlant = builder.define("thirstyPlant", true);
            hungryPlant = builder.define("hungryPlant", true);
            likesChicken = builder.define("likesChicken", true);
            likesRedstone = builder.define("likesRedstone", true);
            vampirePlant = builder.define("vampirePlant", true);
            fullBrightness = builder.define("fullBrightness", true);
            likesWarts = builder.define("likesWarts", true);
            likesCooking = builder.define("likesCooking", true);
            likesBrewing = builder.define("likesBrewing", true);
            likesCheckers = builder.define("likesCheckers", true);
            dontBonemeal = builder.define("dontBonemeal", true);
            selfSacrifice = builder.define("selfSacrifice", true);
        }
    }
}
