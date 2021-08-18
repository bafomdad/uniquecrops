package com.bafomdad.uniquecrops.data.recipes;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.init.UCRecipes;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

public class UCRecipeProvider extends RecipeProvider {

    public UCRecipeProvider(DataGenerator gen) {

        super(gen);
    }

    @ParametersAreNonnullByDefault
    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {

        specialRecipe(consumer, (SpecialRecipeSerializer<?>) UCRecipes.DISCOUNTBOOK_SERIALIZER.get());
        slabs(UCBlocks.FLYWOOD_SLAB.get(), UCBlocks.FLYWOOD_PLANKS.get()).save(consumer);
        slabs(UCBlocks.ROSEWOOD_SLAB.get(), UCBlocks.ROSEWOOD_PLANKS.get()).save(consumer);
        slabs(UCBlocks.RUINEDBRICKS_SLAB.get(), UCBlocks.RUINEDBRICKS.get()).save(consumer);
        slabs(UCBlocks.RUINEDBRICKSCARVED_SLAB.get(), UCBlocks.RUINEDBRICKSCARVED.get()).save(consumer);
        stairs(UCBlocks.FLYWOOD_STAIRS.get(), UCBlocks.FLYWOOD_PLANKS.get()).save(consumer);
        stairs(UCBlocks.ROSEWOOD_STAIRS.get(), UCBlocks.ROSEWOOD_PLANKS.get()).save(consumer);
        stairs(UCBlocks.RUINEDBRICKS_STAIRS.get(), UCBlocks.RUINEDBRICKS.get()).save(consumer);
        recipe(UCItems.GLASSES_3D.get(), 1)
                .define('I', Items.IRON_INGOT)
                .define('B', Blocks.BLUE_STAINED_GLASS_PANE)
                .define('R', Blocks.RED_STAINED_GLASS_PANE)
                .define('W', Blocks.WHITE_WOOL)
                .pattern("I I")
                .pattern("I I")
                .pattern("BWR")
                .unlockedBy("has_item", has(Items.IRON_INGOT))
                .save(consumer);
        recipe(UCBlocks.ABSTRACT_BARREL.get(), 1)
                .define('A', UCItems.ABSTRACT.get())
                .define('C', Tags.Items.CHESTS)
                .pattern("AAA")
                .pattern("ACA")
                .pattern("AAA")
                .unlockedBy("has_item", has(UCItems.ABSTRACT.get()))
                .save(consumer);
        recipe(UCItems.ANKH.get(), 1)
                .define('E', UCBlocks.SUN_BLOCK.get())
                .define('O', Blocks.OBSIDIAN)
                .pattern(" E ")
                .pattern("OOO")
                .pattern(" O ")
                .unlockedBy("has_item", has(UCBlocks.SUN_BLOCK.get()))
                .save(consumer);
        recipe(UCItems.BATSTAFF.get(), 1)
                .define('L', Items.LEATHER)
                .define('S', Items.STICK)
                .define('E', UCItems.MILLENNIUMEYE.get())
                .pattern("LEL")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_item", has(UCItems.MILLENNIUMEYE.get()))
                .save(consumer);
        recipe(UCItems.BOOK_MULTIBLOCK.get(), 1)
                .define('D', UCItems.DYEIUS_SEED.get())
                .define('B', UCItems.BOOK_GUIDE.get())
                .pattern(" D ")
                .pattern("DBD")
                .pattern(" D ")
                .unlockedBy("has_item", has(UCItems.DYEIUS_SEED.get()))
                .save(consumer);
        recipe(UCItems.SEVEN_LEAGUE_BOOTS.get(), 1)
                .define('S', Items.STRING)
                .define('E', UCItems.EMERADIC_DIAMOND.get())
                .define('L', UCItems.ENCHANTED_LEATHER.get())
                .define('B', Items.LEATHER_BOOTS)
                .pattern("SES")
                .pattern("LBL")
                .pattern("SLS")
                .unlockedBy("has_item", has(Items.LEATHER_BOOTS))
                .save(consumer);
        recipe(UCBlocks.BUCKET_ROPE.get(), 1)
                .define('F', Tags.Items.FENCE_GATES_WOODEN)
                .define('R', UCItems.ESCAPEROPE.get())
                .define('B', Items.BUCKET)
                .pattern("FFF")
                .pattern(" R ")
                .pattern(" B ")
                .unlockedBy("has_item", has(UCItems.ESCAPEROPE.get()))
                .save(consumer);
        recipe(UCItems.CACTUS_BOOTS.get(), 1)
                .define('C', Blocks.CACTUS)
                .define('I', Items.IRON_BOOTS)
                .pattern("CIC")
                .pattern("C C")
                .unlockedBy("has_item", has(Blocks.CACTUS))
                .save(consumer);
        recipe(UCItems.CACTUS_CHESTPLATE.get(), 1)
                .define('C', Blocks.CACTUS)
                .define('I', Items.IRON_CHESTPLATE)
                .pattern("C C")
                .pattern("CIC")
                .pattern("CCC")
                .unlockedBy("has_item", has(Blocks.CACTUS))
                .save(consumer);
        recipe(UCItems.CACTUS_HELM.get(), 1)
                .define('C', Blocks.CACTUS)
                .define('I', Items.IRON_HELMET)
                .pattern("CCC")
                .pattern("CIC")
                .unlockedBy("has_item", has(Blocks.CACTUS))
                .save(consumer);
        recipe(UCItems.CACTUS_LEGGINGS.get(), 1)
                .define('C', Blocks.CACTUS)
                .define('I', Items.IRON_LEGGINGS)
                .pattern("CCC")
                .pattern("CIC")
                .pattern("C C")
                .unlockedBy("has_item", has(Blocks.CACTUS))
                .save(consumer);
        recipe(UCBlocks.CINDER_TORCH.get(), 4)
                .define('S', Blocks.SMOOTH_STONE_SLAB)
                .define('X', UCItems.CINDERLEAF.get())
                .pattern("S")
                .pattern("X")
                .pattern("S")
                .unlockedBy("has_item", has(UCItems.CINDERLEAF.get()))
                .save(consumer);
        recipe(UCBlocks.DRIED_THATCH.get(), 4)
                .define('H', Blocks.HAY_BLOCK)
                .pattern("HH")
                .pattern("HH")
                .unlockedBy("has_item", has(Blocks.HAY_BLOCK))
                .save(consumer);
        storage(UCBlocks.EGG_BASKET.get(), Items.EGG).save(consumer);
        shapeless(Items.EGG, UCBlocks.EGG_BASKET.get(), 9).save(consumer);
        recipe(UCItems.EMBLEM_BLACKSMITH.get(), 1)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('A', Blocks.ANVIL)
                .define('B', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('D', UCBlocks.DARK_BLOCK.get())
                .pattern("IAI")
                .pattern("IDI")
                .pattern("IBI")
                .unlockedBy("has_item", has(UCBlocks.DARK_BLOCK.get()))
                .save(consumer);
        recipe(UCItems.EMBLEM_BOOKWORM.get(), 1)
                .define('P', Items.POTATO)
                .define('D', UCBlocks.DARK_BLOCK.get())
                .define('B', Items.ENCHANTED_BOOK)
                .pattern(" B ")
                .pattern("PDP")
                .pattern(" B ")
                .unlockedBy("has_item", has(UCBlocks.DARK_BLOCK.get()))
                .save(consumer);
        recipe(UCItems.EMBLEM_DEFENSE.get(), 1)
                .define('A', Items.ARROW)
                .define('B', Items.BOW)
                .define('S', Items.SHIELD)
                .define('D', UCBlocks.DARK_BLOCK.get())
                .pattern("ABA")
                .pattern("SDS")
                .pattern("ABA")
                .unlockedBy("has_item", has(UCBlocks.DARK_BLOCK.get()))
                .save(consumer);
        recipe(UCItems.EMBLEM_FOOD.get(), 1)
                .define('P', Items.POISONOUS_POTATO)
                .pattern("PPP")
                .pattern("P P")
                .pattern("PPP")
                .unlockedBy("has_item", has(Items.POISONOUS_POTATO))
                .save(consumer);
        recipe(UCItems.EMBLEM_IRONSTOMACH.get(), 1)
                .define('R', Items.COOKED_RABBIT)
                .define('C', Items.COOKED_CHICKEN)
                .define('S', Items.COOKED_BEEF)
                .define('P', Items.COOKED_PORKCHOP)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .define('e', Tags.Items.GEMS_EMERALD)
                .define('D', UCBlocks.DARK_BLOCK.get())
                .pattern("SgP")
                .pattern("iDe")
                .pattern("RdC")
                .unlockedBy("has_item", has(UCBlocks.DARK_BLOCK.get()))
                .save(consumer);
        recipe(UCItems.EMBLEM_LEAF.get(), 1)
                .define('L', ItemTags.LOGS)
                .define('l', ItemTags.LEAVES)
                .define('D', UCBlocks.DARK_BLOCK.get())
                .pattern("lLl")
                .pattern("LDL")
                .pattern("lLl")
                .unlockedBy("has_item", has(UCBlocks.DARK_BLOCK.get()))
                .save(consumer);
        recipe(UCItems.EMBLEM_MELEE.get(), 1)
                .define('d', Items.DIAMOND_SWORD)
                .define('g', Items.GOLDEN_SWORD)
                .define('i', Items.IRON_SWORD)
                .define('w', Items.WOODEN_SWORD)
                .define('D', UCBlocks.DARK_BLOCK.get())
                .pattern(" d ")
                .pattern("gDi")
                .pattern(" w ")
                .unlockedBy("has_item", has(UCBlocks.DARK_BLOCK.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(UCItems.EMBLEM_PACIFISM.get(), 1)
                .requires(UCItems.EMBLEM_DEFENSE.get())
                .requires(UCItems.EMBLEM_BLACKSMITH.get())
                .requires(UCItems.EMBLEM_IRONSTOMACH.get())
                .requires(UCItems.EMBLEM_LEAF.get())
                .requires(UCItems.EMBLEM_MELEE.get())
                .requires(UCItems.EMBLEM_POWERFIST.get())
                .requires(UCItems.EMBLEM_RAINBOW.get())
                .requires(UCItems.EMBLEM_SCARAB.get())
                .requires(UCItems.EMBLEM_TRANSFORMATION.get())
                .unlockedBy("has_item", has(UCItems.EMBLEM_DEFENSE.get()))
                .save(consumer);
        recipe(UCItems.EMBLEM_POWERFIST.get(), 1)
                .define('p', Items.DIAMOND_PICKAXE)
                .define('b', Items.BLAZE_POWDER)
                .define('B', Items.BLAZE_ROD)
                .define('D', UCBlocks.DARK_BLOCK.get())
                .pattern(" p ")
                .pattern("BDB")
                .pattern(" b ")
                .unlockedBy("has_item", has(UCBlocks.DARK_BLOCK.get()))
                .save(consumer);
        recipe(UCItems.EMBLEM_RAINBOW.get(), 1)
                .define('W', ItemTags.WOOL)
                .define('D', UCBlocks.DARK_BLOCK.get())
                .pattern("WWW")
                .pattern("WDW")
                .pattern("WWW")
                .unlockedBy("has_item", has(UCBlocks.DARK_BLOCK.get()))
                .save(consumer);
        recipe(UCItems.EMBLEM_SCARAB.get(), 1)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('G', Tags.Items.STORAGE_BLOCKS_GOLD)
                .define('D', UCBlocks.DARK_BLOCK.get())
                .pattern("gGg")
                .pattern("GDG")
                .pattern("gGg")
                .unlockedBy("has_item", has(UCBlocks.DARK_BLOCK.get()))
                .save(consumer);
        recipe(UCItems.EMBLEM_TRANSFORMATION.get(), 1)
                .define('p', UCItems.INVISIFEATHER.get())
                .define('f', Items.FEATHER)
                .define('D', UCBlocks.DARK_BLOCK.get())
                .pattern(" p ")
                .pattern("fDf")
                .pattern(" f ")
                .unlockedBy("has_item", has(UCBlocks.DARK_BLOCK.get()))
                .save(consumer);
        recipe(UCItems.EMBLEM_WEIGHT.get(), 1)
                .define('S', Items.SNOWBALL)
                .define('O', Blocks.OBSIDIAN)
                .define('D', UCBlocks.DARK_BLOCK.get())
                .define('B', Items.FIRE_CHARGE)
                .pattern("BSB")
                .pattern("ODO")
                .pattern("OOO")
                .unlockedBy("has_item", has(UCBlocks.DARK_BLOCK.get()))
                .save(consumer);
        allAround(Items.ENDER_PEARL, Items.SNOWBALL, UCItems.LILYTWINE.get()).save(consumer);
        recipe(UCItems.ENDERSNOOKER.get(), 1)
                .define('P', Items.ENDER_PEARL)
                .define('S', Items.STICK)
                .define('E', UCItems.LILYTWINE.get())
                .pattern("EPE")
                .pattern("PSP")
                .pattern("EPE")
                .unlockedBy("has_item", has(Items.ENDER_PEARL))
                .save(consumer);
        recipe(UCItems.BOOK_GUIDE.get(), 1)
                .define('P', Items.PUMPKIN_SEEDS)
                .define('B', Items.BOOK)
                .define('W', Items.WHEAT_SEEDS)
                .define('M', Items.MELON_SEEDS)
                .define('N', UCItems.NORMAL_SEED.get())
                .pattern(" N ")
                .pattern("WBM")
                .pattern(" P ")
                .unlockedBy("has_item", has(UCItems.ARTISIA_SEED.get()))
                .save(consumer);
        recipe(UCItems.PREGEM.get(), 1)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('N', UCItems.PRENUGGET.get())
                .pattern(" N ")
                .pattern("NDN")
                .pattern(" N ")
                .unlockedBy("has_item", has(UCItems.PRENUGGET.get()))
                .save(consumer);
        recipe(UCItems.TIMEMEAL.get(), 4)
                .define('B', Items.BONE_MEAL)
                .define('C', Items.CLOCK)
                .pattern(" B ")
                .pattern("BCB")
                .pattern(" B ")
                .unlockedBy("has_item", has(Items.BONE_MEAL))
                .save(consumer);
        allAround(UCItems.INVISIFEATHER.get(), Items.FEATHER, UCItems.INVISITWINE.get()).save(consumer);
        recipe(UCItems.WEEPINGEYE.get(), 1)
                .define('T', UCItems.WEEPINGTEAR.get())
                .define('E', Items.ENDER_EYE)
                .pattern(" T ")
                .pattern("TET")
                .pattern(" T ")
                .unlockedBy("has_item", has(UCItems.WEEPINGTEAR.get()))
                .save(consumer);
        recipe(UCItems.BOOK_UPGRADE.get(), 1)
                .define('B', UCItems.BOOK_DISCOUNT.get())
                .define('F', UCItems.INVISIFEATHER.get())
                .pattern(" F ")
                .pattern("FBF")
                .pattern(" F ")
                .unlockedBy("has_item", has(UCItems.BOOK_DISCOUNT.get()))
                .save(consumer);
        recipe(UCItems.EGGUPGRADE.get(), 1)
                .define('E', Items.EGG)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('M', UCItems.MILLENNIUMEYE.get())
                .pattern("IEI")
                .pattern("EME")
                .pattern("IEI")
                .unlockedBy("has_item", has(UCItems.MILLENNIUMEYE.get()))
                .save(consumer);
        recipe(UCItems.EASYBADGE.get(), 1)
                .define('Q', Blocks.QUARTZ_BLOCK)
                .define('E', UCItems.MILLENNIUMEYE.get())
                .define('G', Tags.Items.INGOTS_GOLD)
                .pattern("GEG")
                .pattern("EQE")
                .pattern("GEG")
                .unlockedBy("has_item", has(UCItems.MILLENNIUMEYE.get()))
                .save(consumer);
        recipe(UCItems.BOOK_EULA.get(), 1)
                .define('B', Items.BOOK)
                .define('L', UCItems.LEGALSTUFF.get())
                .pattern(" L ")
                .pattern("LBL")
                .pattern(" L ")
                .unlockedBy("has_item", has(UCItems.LEGALSTUFF.get()))
                .save(consumer);
        recipe(UCItems.ESCAPEROPE.get(), 2)
                .define('E', UCItems.LILYTWINE.get())
                .define('I', UCItems.INVISITWINE.get())
                .pattern("EI")
                .pattern("IE")
                .pattern("EI")
                .unlockedBy("has_item", has(UCItems.LILYTWINE.get()))
                .save(consumer);
        recipe(UCBlocks.SUN_DIAL.get(), 1)
                .define('R', Items.REDSTONE)
                .define('C', Tags.Items.COBBLESTONE)
                .pattern("RCR")
                .pattern("CCC")
                .unlockedBy("has_item", has(Items.REDSTONE))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(UCItems.DIET_PILLS.get())
                .requires(UCItems.ABSTRACT.get())
                .requires(UCItems.ABSTRACT.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy("has_item", has(UCItems.ABSTRACT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(UCItems.EGGNOG.get())
                .requires(Items.EGG)
                .requires(Items.MILK_BUCKET)
                .requires(Items.SUGAR)
                .requires(Items.PAPER)
                .unlockedBy("has_item", has(Items.MILK_BUCKET))
                .save(consumer);
        recipe(UCItems.GOLDEN_BREAD.get(), 1)
                .define('R', UCItems.GOLDENRODS.get())
                .pattern("RRR")
                .unlockedBy("has_item", has(UCItems.GOLDENRODS.get()))
                .save(consumer);
        storage(UCItems.LARGE_PLUM.get(), UCItems.DIRIGIBLEPLUM.get()).save(consumer);
        ShapelessRecipeBuilder.shapeless(UCItems.YOGURT.get())
                .requires(UCItems.BOILED_MILK.get())
                .requires(Items.BOWL)
                .requires(Items.BOWL)
                .requires(UCItems.DYEIUS_SEED.get())
                .unlockedBy("has_item", has(UCItems.BOILED_MILK.get()))
                .save(consumer);
        recipe(UCBlocks.GOBLET.get(), 1)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('R', Blocks.REDSTONE_BLOCK)
                .define('P', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
                .pattern("g g")
                .pattern("gRg")
                .pattern(" P ")
                .unlockedBy("has_item", has(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE))
                .save(consumer);
        recipe(UCItems.HANDMIRROR.get(), 1)
                .define('S', Items.STICK)
                .define('G', Tags.Items.GLASS_PANES)
                .define('E', UCItems.MILLENNIUMEYE.get())
                .pattern("  E")
                .pattern(" G ")
                .pattern("S  ")
                .unlockedBy("has_item", has(UCItems.MILLENNIUMEYE.get()))
                .save(consumer);
        recipe(UCItems.EMERADIC_DIAMOND.get(), 1)
                .define('D', UCItems.PREGEM.get())
                .define('E', Tags.Items.GEMS_EMERALD)
                .pattern("DED")
                .unlockedBy("has_item", has(UCItems.PREGEM.get()))
                .save(consumer);
        recipe(UCBlocks.HARVEST_TRAP.get(), 1)
                .define('P', ItemTags.LOGS)
                .define('S', ItemTags.WOODEN_SLABS)
                .define('D', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('I', Blocks.IRON_BARS)
                .pattern("IDI")
                .pattern("SPS")
                .pattern("SPS")
                .unlockedBy("has_item", has(ItemTags.LOGS))
                .save(consumer);
        recipe(UCBlocks.HOURGLASS.get(), 1)
                .define('P', Tags.Items.GLASS_PANES)
                .define('D', UCItems.TIMEDUST.get())
                .define('G', Tags.Items.STORAGE_BLOCKS_GOLD)
                .pattern("DGD")
                .pattern("PDP")
                .pattern("DGD")
                .unlockedBy("has_item", has(UCItems.TIMEDUST.get()))
                .save(consumer);
        recipe(UCBlocks.LILY_ICE.get(), 1)
                .define('P', Blocks.PACKED_ICE)
                .define('L', Blocks.LILY_PAD)
                .pattern(" P ")
                .pattern("PLP")
                .pattern(" P ")
                .unlockedBy("has_item", has(Blocks.LILY_PAD))
                .save(consumer);
        recipe(UCItems.IMPACT_SHIELD.get(), 1)
                .define('N', Items.NETHER_STAR)
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('S', Items.SHIELD)
                .define('D', Blocks.ANCIENT_DEBRIS)
                .pattern("DSI")
                .pattern("SNS")
                .pattern("ISD")
                .unlockedBy("has_item", has(Items.SHIELD))
                .save(consumer);
        recipe(UCItems.IMPREGNATED_LEATHER.get(), 1)
                .define('C', Items.COAL)
                .define('L', Items.LEATHER)
                .pattern(" C ")
                .pattern("CLC")
                .pattern(" C ")
                .unlockedBy("has_item", has(Items.LEATHER))
                .save(consumer);
        recipe(UCBlocks.INVISIBILIA_GLASS.get(), 4)
                .define('T', UCItems.INVISITWINE.get())
                .define('G', Tags.Items.GLASS)
                .pattern("TGT")
                .pattern("GTG")
                .pattern("TGT")
                .unlockedBy("has_item", has(UCItems.INVISITWINE.get()))
                .save(consumer);
        recipe(UCItems.ITEM_MAGNET.get(), 1)
                .define('E', UCItems.FERROMAGNETICIRON.get())
                .pattern("EEE")
                .pattern("E E")
                .pattern("E E")
                .unlockedBy("has_item", has(UCItems.FERROMAGNETICIRON.get()))
                .save(consumer);
        recipe(UCBlocks.LILY_JUNGLE.get(), 1)
                .define('P', Blocks.JUNGLE_LEAVES)
                .define('L', Blocks.LILY_PAD)
                .pattern(" P ")
                .pattern("PLP")
                .pattern(" P ")
                .unlockedBy("has_item", has(Blocks.LILY_PAD))
                .save(consumer);
        recipe(UCBlocks.LILY_LAVA.get(), 1)
                .define('C', UCItems.CINDERLEAF.get())
                .define('L', Blocks.LILY_PAD)
                .pattern(" C ")
                .pattern("CLC")
                .pattern(" C ")
                .unlockedBy("has_item", has(Blocks.LILY_PAD))
                .save(consumer);
        storage(UCBlocks.NORMIECRATE.get(), UCItems.NORMAL_SEED.get()).save(consumer);
        shapeless(UCItems.NORMAL_SEED.get(), UCBlocks.NORMIECRATE.get(), 9).save(consumer);
        shapeless(Items.DIAMOND, UCBlocks.OLDDIAMOND.get(), 9).save(consumer);
        shapeless(Items.GOLD_INGOT, UCBlocks.OLDGOLD.get(), 9).save(consumer);
        shapeless(Items.IRON_INGOT, UCBlocks.OLDIRON.get(), 9).save(consumer);
        recipe(UCItems.PIXEL_BRUSH.get(), 1)
                .define('P', UCItems.PIXELS.get())
                .define('I', UCItems.INVISITWINE.get())
                .define('S', Items.STICK)
                .pattern(" S ")
                .pattern("ISI")
                .pattern("PPP")
                .unlockedBy("has_item", has(UCItems.PIXELS.get()))
                .save(consumer);
        allAround(UCItems.GLASSES_PIXELS.get(), UCItems.GLASSES_3D.get(), UCItems.PIXELS.get()).save(consumer);
        recipe(UCItems.PONCHO.get(), 1)
                .define('P', UCItems.INVISIFEATHER.get())
                .pattern("P P")
                .pattern("PPP")
                .pattern("PPP")
                .unlockedBy("has_item", has(UCItems.INVISIFEATHER.get()))
                .save(consumer);
        shapeless(UCItems.ABSTRACT_SEED.get(), UCItems.ABSTRACT.get(), 1).save(consumer);
        recipe(UCItems.ARTISIA_SEED.get(), 1)
                .define('S', UCItems.NORMAL_SEED.get())
                .define('C', Blocks.CRAFTING_TABLE)
                .pattern(" S ")
                .pattern("SCS")
                .pattern(" S ")
                .unlockedBy("has_item", has(UCItems.NORMAL_SEED.get()))
                .save(consumer);
        shapeless(UCItems.DIRIGIBLE_SEED.get(), UCItems.LARGE_PLUM.get(), 2).save(consumer);
        recipe(UCItems.SPIRITBAIT.get(), 1)
                .define('V', Blocks.VINE)
                .pattern("V V")
                .pattern(" V ")
                .pattern("V V")
                .unlockedBy("has_item", has(Blocks.VINE))
                .save(consumer);
        recipe(UCBlocks.SUN_BLOCK.get(), 1)
                .define('E', UCItems.SAVAGEESSENCE.get())
                .define('G', UCBlocks.INVISIBILIA_GLASS.get())
                .pattern("EGE")
                .pattern("GEG")
                .pattern("EGE")
                .unlockedBy("has_item", has(UCItems.SAVAGEESSENCE.get()))
                .save(consumer);
        recipe(UCBlocks.LILY_ENDER.get(), 1)
                .define('P', UCItems.LILYTWINE.get())
                .define('L', Blocks.LILY_PAD)
                .define('E', Items.ENDER_EYE)
                .pattern(" E ")
                .pattern("PLP")
                .pattern(" P ")
                .unlockedBy("has_item", has(Blocks.LILY_PAD))
                .save(consumer);
        recipe(UCItems.THUNDERPANTZ.get(), 1)
                .define('W', Blocks.BLUE_WOOL)
                .define('P', Items.LEATHER_LEGGINGS)
                .define('F', UCItems.INVISIFEATHER.get())
                .pattern("WWW")
                .pattern("WPW")
                .pattern("WFW")
                .unlockedBy("has_item", has(UCItems.INVISIFEATHER.get()))
                .save(consumer);
        recipe(UCBlocks.TOTEMHEAD.get(), 1)
                .define('S', Items.STICK)
                .define('L', Blocks.LAPIS_BLOCK)
                .define('M', UCItems.MILLENNIUMEYE.get())
                .pattern("LLL")
                .pattern("LML")
                .pattern(" S ")
                .unlockedBy("has_item", has(UCItems.MILLENNIUMEYE.get()))
                .save(consumer);
        recipe(UCItems.WILDWOOD_STAFF.get(), 1)
                .define('S', Items.STICK)
                .define('N', UCItems.ENDERSNOOKER.get())
                .define('E', UCItems.SAVAGEESSENCE.get())
                .pattern(" SE")
                .pattern(" NS")
                .pattern("S  ")
                .unlockedBy("has_item", has(UCItems.SAVAGEESSENCE.get()))
                .save(consumer);
        recipe(UCBlocks.RUINEDBRICKSCARVED.get(), 1)
                .define('R', UCBlocks.RUINEDBRICKS_SLAB.get())
                .pattern("R")
                .pattern("R")
                .unlockedBy("has_item", has(UCBlocks.RUINEDBRICKS_SLAB.get()))
                .save(consumer);
        recipe(UCBlocks.OBTUSE_PLATFORM.get(), 1)
                .define('D', UCBlocks.DARK_BLOCK.get())
                .define('G', UCBlocks.INVISIBILIA_GLASS.get())
                .define('T', UCItems.LILYTWINE.get())
                .pattern("TGT")
                .pattern("GDG")
                .pattern("TGT")
                .unlockedBy("has_item", has(UCBlocks.DARK_BLOCK.get()))
                .save(consumer);
        recipe(UCBlocks.FLYWOOD_TRAPDOOR.get(), 2)
                .define('P', UCBlocks.FLYWOOD_PLANKS.get())
                .pattern("PPP")
                .pattern("PPP")
                .unlockedBy("has_item", has(UCBlocks.FLYWOOD_PLANKS.get()))
                .save(consumer);
        recipe(UCBlocks.ROSEWOOD_TRAPDOOR.get(), 2)
                .define('P', UCBlocks.ROSEWOOD_PLANKS.get())
                .pattern("PPP")
                .pattern("PPP")
                .unlockedBy("has_item", has(UCBlocks.ROSEWOOD_PLANKS.get()))
                .save(consumer);
    }

    @SuppressWarnings("deprecation")
    private void specialRecipe(Consumer<IFinishedRecipe> consumer, SpecialRecipeSerializer<?> serializer) {

        ResourceLocation name = Registry.RECIPE_SERIALIZER.getKey(serializer);
        if (name != null)
            CustomRecipeBuilder.special(serializer).save(consumer, UniqueCrops.MOD_ID + ":dynamic/" + name.getPath());
    }

    private ShapedRecipeBuilder recipe(IItemProvider output, int count) {

        return ShapedRecipeBuilder.shaped(output, count);
    }

    private ShapelessRecipeBuilder shapeless(IItemProvider output, IItemProvider input, int count) {

        return ShapelessRecipeBuilder.shapeless(output, count).requires(input).unlockedBy("has_item", has(input));
    }

    private ShapedRecipeBuilder allAround(IItemProvider output, IItemProvider center, IItemProvider surrounding) {

        return ShapedRecipeBuilder.shaped(output, 1)
                .define('A', center)
                .define('B', surrounding)
                .pattern("BBB").pattern("BAB").pattern("BBB")
                .unlockedBy("has_item", has(center));
    }

    private ShapedRecipeBuilder storage(IItemProvider output, IItemProvider input) {

        return ShapedRecipeBuilder.shaped(output)
                .define('S', input)
                .pattern("SSS").pattern("SSS").pattern("SSS")
                .unlockedBy("has_item", has(input));
    }

    private ShapedRecipeBuilder slabs(IItemProvider output, IItemProvider input) {

        return ShapedRecipeBuilder.shaped(output, 6)
                .unlockedBy("has_item", has(input))
                .define('S', input)
                .pattern("SSS");
    }

    private ShapedRecipeBuilder stairs(IItemProvider output, IItemProvider input) {

        return ShapedRecipeBuilder.shaped(output, 4)
                .unlockedBy("has_item", has(input))
                .define('S', input)
                .pattern("  S")
                .pattern(" SS")
                .pattern("SSS");
    }
}
