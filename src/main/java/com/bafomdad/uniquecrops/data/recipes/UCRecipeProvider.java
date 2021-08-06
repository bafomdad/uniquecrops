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
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        specialRecipe(consumer, (SpecialRecipeSerializer<?>) UCRecipes.DISCOUNTBOOK_SERIALIZER.get());
        slabs(UCBlocks.FLYWOOD_SLAB.get(), UCBlocks.FLYWOOD_PLANKS.get()).build(consumer);
        slabs(UCBlocks.ROSEWOOD_SLAB.get(), UCBlocks.ROSEWOOD_PLANKS.get()).build(consumer);
        slabs(UCBlocks.RUINEDBRICKS_SLAB.get(), UCBlocks.RUINEDBRICKS.get()).build(consumer);
        slabs(UCBlocks.RUINEDBRICKSCARVED_SLAB.get(), UCBlocks.RUINEDBRICKSCARVED.get()).build(consumer);
        stairs(UCBlocks.FLYWOOD_STAIRS.get(), UCBlocks.FLYWOOD_PLANKS.get()).build(consumer);
        stairs(UCBlocks.ROSEWOOD_STAIRS.get(), UCBlocks.ROSEWOOD_PLANKS.get()).build(consumer);
        stairs(UCBlocks.RUINEDBRICKS_STAIRS.get(), UCBlocks.RUINEDBRICKS.get()).build(consumer);
        recipe(UCItems.GLASSES_3D.get(), 1)
                .key('I', Items.IRON_INGOT)
                .key('B', Blocks.BLUE_STAINED_GLASS_PANE)
                .key('R', Blocks.RED_STAINED_GLASS_PANE)
                .key('W', Blocks.WHITE_WOOL)
                .patternLine("I I")
                .patternLine("I I")
                .patternLine("BWR")
                .addCriterion("has_item", hasItem(Items.IRON_INGOT))
                .build(consumer);
        recipe(UCBlocks.ABSTRACT_BARREL.get(), 1)
                .key('A', UCItems.ABSTRACT.get())
                .key('C', Tags.Items.CHESTS)
                .patternLine("AAA")
                .patternLine("ACA")
                .patternLine("AAA")
                .addCriterion("has_item", hasItem(UCItems.ABSTRACT.get()))
                .build(consumer);
        recipe(UCItems.ANKH.get(), 1)
                .key('E', UCBlocks.SUN_BLOCK.get())
                .key('O', Blocks.OBSIDIAN)
                .patternLine(" E ")
                .patternLine("OOO")
                .patternLine(" O ")
                .addCriterion("has_item", hasItem(UCBlocks.SUN_BLOCK.get()))
                .build(consumer);
        recipe(UCItems.BATSTAFF.get(), 1)
                .key('L', Items.LEATHER)
                .key('S', Items.STICK)
                .key('E', UCItems.MILLENNIUMEYE.get())
                .patternLine("LEL")
                .patternLine(" S ")
                .patternLine(" S ")
                .addCriterion("has_item", hasItem(UCItems.MILLENNIUMEYE.get()))
                .build(consumer);
        recipe(UCItems.BOOK_MULTIBLOCK.get(), 1)
                .key('D', UCItems.DYEIUS_SEED.get())
                .key('B', UCItems.BOOK_GUIDE.get())
                .patternLine(" D ")
                .patternLine("DBD")
                .patternLine(" D ")
                .addCriterion("has_item", hasItem(UCItems.DYEIUS_SEED.get()))
                .build(consumer);
        recipe(UCItems.SEVEN_LEAGUE_BOOTS.get(), 1)
                .key('S', Items.STRING)
                .key('E', UCItems.EMERADIC_DIAMOND.get())
                .key('L', UCItems.ENCHANTED_LEATHER.get())
                .key('B', Items.LEATHER_BOOTS)
                .patternLine("SES")
                .patternLine("LBL")
                .patternLine("SLS")
                .addCriterion("has_item", hasItem(Items.LEATHER_BOOTS))
                .build(consumer);
        recipe(UCBlocks.BUCKET_ROPE.get(), 1)
                .key('F', Tags.Items.FENCE_GATES_WOODEN)
                .key('R', UCItems.ESCAPEROPE.get())
                .key('B', Items.BUCKET)
                .patternLine("FFF")
                .patternLine(" R ")
                .patternLine(" B ")
                .addCriterion("has_item", hasItem(UCItems.ESCAPEROPE.get()))
                .build(consumer);
        recipe(UCItems.CACTUS_BOOTS.get(), 1)
                .key('C', Blocks.CACTUS)
                .key('I', Items.IRON_BOOTS)
                .patternLine("CIC")
                .patternLine("C C")
                .addCriterion("has_item", hasItem(Blocks.CACTUS))
                .build(consumer);
        recipe(UCItems.CACTUS_CHESTPLATE.get(), 1)
                .key('C', Blocks.CACTUS)
                .key('I', Items.IRON_CHESTPLATE)
                .patternLine("C C")
                .patternLine("CIC")
                .patternLine("CCC")
                .addCriterion("has_item", hasItem(Blocks.CACTUS))
                .build(consumer);
        recipe(UCItems.CACTUS_HELM.get(), 1)
                .key('C', Blocks.CACTUS)
                .key('I', Items.IRON_HELMET)
                .patternLine("CCC")
                .patternLine("CIC")
                .addCriterion("has_item", hasItem(Blocks.CACTUS))
                .build(consumer);
        recipe(UCItems.CACTUS_LEGGINGS.get(), 1)
                .key('C', Blocks.CACTUS)
                .key('I', Items.IRON_LEGGINGS)
                .patternLine("CCC")
                .patternLine("CIC")
                .patternLine("C C")
                .addCriterion("has_item", hasItem(Blocks.CACTUS))
                .build(consumer);
        recipe(UCBlocks.CINDER_TORCH.get(), 4)
                .key('S', Blocks.SMOOTH_STONE_SLAB)
                .key('X', UCItems.CINDERLEAF.get())
                .patternLine("S")
                .patternLine("X")
                .patternLine("S")
                .addCriterion("has_item", hasItem(UCItems.CINDERLEAF.get()))
                .build(consumer);
        recipe(UCBlocks.DRIED_THATCH.get(), 4)
                .key('H', Blocks.HAY_BLOCK)
                .patternLine("HH")
                .patternLine("HH")
                .addCriterion("has_item", hasItem(Blocks.HAY_BLOCK))
                .build(consumer);
        storage(UCBlocks.EGG_BASKET.get(), Items.EGG).build(consumer);
        shapeless(Items.EGG, UCBlocks.EGG_BASKET.get(), 9).build(consumer);
        recipe(UCItems.EMBLEM_BLACKSMITH.get(), 1)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('A', Blocks.ANVIL)
                .key('B', Tags.Items.STORAGE_BLOCKS_IRON)
                .key('D', UCBlocks.DARK_BLOCK.get())
                .patternLine("IAI")
                .patternLine("IDI")
                .patternLine("IBI")
                .addCriterion("has_item", hasItem(UCBlocks.DARK_BLOCK.get()))
                .build(consumer);
        recipe(UCItems.EMBLEM_BOOKWORM.get(), 1)
                .key('P', Items.POTATO)
                .key('D', UCBlocks.DARK_BLOCK.get())
                .key('B', Items.ENCHANTED_BOOK)
                .patternLine(" B ")
                .patternLine("PDP")
                .patternLine(" B ")
                .addCriterion("has_item", hasItem(UCBlocks.DARK_BLOCK.get()))
                .build(consumer);
        recipe(UCItems.EMBLEM_DEFENSE.get(), 1)
                .key('A', Items.ARROW)
                .key('B', Items.BOW)
                .key('S', Items.SHIELD)
                .key('D', UCBlocks.DARK_BLOCK.get())
                .patternLine("ABA")
                .patternLine("SDS")
                .patternLine("ABA")
                .addCriterion("has_item", hasItem(UCBlocks.DARK_BLOCK.get()))
                .build(consumer);
        recipe(UCItems.EMBLEM_FOOD.get(), 1)
                .key('P', Items.POISONOUS_POTATO)
                .patternLine("PPP")
                .patternLine("P P")
                .patternLine("PPP")
                .addCriterion("has_item", hasItem(Items.POISONOUS_POTATO))
                .build(consumer);
        recipe(UCItems.EMBLEM_IRONSTOMACH.get(), 1)
                .key('R', Items.COOKED_RABBIT)
                .key('C', Items.COOKED_CHICKEN)
                .key('S', Items.COOKED_BEEF)
                .key('P', Items.COOKED_PORKCHOP)
                .key('g', Tags.Items.INGOTS_GOLD)
                .key('i', Tags.Items.INGOTS_IRON)
                .key('d', Tags.Items.GEMS_DIAMOND)
                .key('e', Tags.Items.GEMS_EMERALD)
                .key('D', UCBlocks.DARK_BLOCK.get())
                .patternLine("SgP")
                .patternLine("iDe")
                .patternLine("RdC")
                .addCriterion("has_item", hasItem(UCBlocks.DARK_BLOCK.get()))
                .build(consumer);
        recipe(UCItems.EMBLEM_LEAF.get(), 1)
                .key('L', ItemTags.LOGS)
                .key('l', ItemTags.LEAVES)
                .key('D', UCBlocks.DARK_BLOCK.get())
                .patternLine("lLl")
                .patternLine("LDL")
                .patternLine("lLl")
                .addCriterion("has_item", hasItem(UCBlocks.DARK_BLOCK.get()))
                .build(consumer);
        recipe(UCItems.EMBLEM_MELEE.get(), 1)
                .key('d', Items.DIAMOND_SWORD)
                .key('g', Items.GOLDEN_SWORD)
                .key('i', Items.IRON_SWORD)
                .key('w', Items.WOODEN_SWORD)
                .key('D', UCBlocks.DARK_BLOCK.get())
                .patternLine(" d ")
                .patternLine("gDi")
                .patternLine(" w ")
                .addCriterion("has_item", hasItem(UCBlocks.DARK_BLOCK.get()))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(UCItems.EMBLEM_PACIFISM.get(), 1)
                .addIngredient(UCItems.EMBLEM_DEFENSE.get())
                .addIngredient(UCItems.EMBLEM_BLACKSMITH.get())
                .addIngredient(UCItems.EMBLEM_IRONSTOMACH.get())
                .addIngredient(UCItems.EMBLEM_LEAF.get())
                .addIngredient(UCItems.EMBLEM_MELEE.get())
                .addIngredient(UCItems.EMBLEM_POWERFIST.get())
                .addIngredient(UCItems.EMBLEM_RAINBOW.get())
                .addIngredient(UCItems.EMBLEM_SCARAB.get())
                .addIngredient(UCItems.EMBLEM_TRANSFORMATION.get())
                .addCriterion("has_item", hasItem(UCItems.EMBLEM_DEFENSE.get()))
                .build(consumer);
        recipe(UCItems.EMBLEM_POWERFIST.get(), 1)
                .key('p', Items.DIAMOND_PICKAXE)
                .key('b', Items.BLAZE_POWDER)
                .key('B', Items.BLAZE_ROD)
                .key('D', UCBlocks.DARK_BLOCK.get())
                .patternLine(" p ")
                .patternLine("BDB")
                .patternLine(" b ")
                .addCriterion("has_item", hasItem(UCBlocks.DARK_BLOCK.get()))
                .build(consumer);
        recipe(UCItems.EMBLEM_RAINBOW.get(), 1)
                .key('W', ItemTags.WOOL)
                .key('D', UCBlocks.DARK_BLOCK.get())
                .patternLine("WWW")
                .patternLine("WDW")
                .patternLine("WWW")
                .addCriterion("has_item", hasItem(UCBlocks.DARK_BLOCK.get()))
                .build(consumer);
        recipe(UCItems.EMBLEM_SCARAB.get(), 1)
                .key('g', Tags.Items.INGOTS_GOLD)
                .key('G', Tags.Items.STORAGE_BLOCKS_GOLD)
                .key('D', UCBlocks.DARK_BLOCK.get())
                .patternLine("gGg")
                .patternLine("GDG")
                .patternLine("gGg")
                .addCriterion("has_item", hasItem(UCBlocks.DARK_BLOCK.get()))
                .build(consumer);
        recipe(UCItems.EMBLEM_TRANSFORMATION.get(), 1)
                .key('p', UCItems.INVISIFEATHER.get())
                .key('f', Items.FEATHER)
                .key('D', UCBlocks.DARK_BLOCK.get())
                .patternLine(" p ")
                .patternLine("fDf")
                .patternLine(" f ")
                .addCriterion("has_item", hasItem(UCBlocks.DARK_BLOCK.get()))
                .build(consumer);
        recipe(UCItems.EMBLEM_WEIGHT.get(), 1)
                .key('S', Items.SNOWBALL)
                .key('O', Blocks.OBSIDIAN)
                .key('D', UCBlocks.DARK_BLOCK.get())
                .key('B', Items.FIRE_CHARGE)
                .patternLine("BSB")
                .patternLine("ODO")
                .patternLine("OOO")
                .addCriterion("has_item", hasItem(UCBlocks.DARK_BLOCK.get()))
                .build(consumer);
        allAround(Items.ENDER_PEARL, Items.SNOWBALL, UCItems.LILYTWINE.get()).build(consumer);
        recipe(UCItems.ENDERSNOOKER.get(), 1)
                .key('P', Items.ENDER_PEARL)
                .key('S', Items.STICK)
                .key('E', UCItems.LILYTWINE.get())
                .patternLine("EPE")
                .patternLine("PSP")
                .patternLine("EPE")
                .addCriterion("has_item", hasItem(Items.ENDER_PEARL))
                .build(consumer);
        recipe(UCItems.BOOK_GUIDE.get(), 1)
                .key('P', Items.PUMPKIN_SEEDS)
                .key('B', Items.BOOK)
                .key('W', Items.WHEAT_SEEDS)
                .key('M', Items.MELON_SEEDS)
                .key('N', UCItems.NORMAL_SEED.get())
                .patternLine(" N ")
                .patternLine("WBM")
                .patternLine(" P ")
                .addCriterion("has_item", hasItem(UCItems.ARTISIA_SEED.get()))
                .build(consumer);
        recipe(UCItems.PREGEM.get(), 1)
                .key('D', Tags.Items.GEMS_DIAMOND)
                .key('N', UCItems.PRENUGGET.get())
                .patternLine(" N ")
                .patternLine("NDN")
                .patternLine(" N ")
                .addCriterion("has_item", hasItem(UCItems.PRENUGGET.get()))
                .build(consumer);
        recipe(UCItems.TIMEMEAL.get(), 4)
                .key('B', Items.BONE_MEAL)
                .key('C', Items.CLOCK)
                .patternLine(" B ")
                .patternLine("BCB")
                .patternLine(" B ")
                .addCriterion("has_item", hasItem(Items.BONE_MEAL))
                .build(consumer);
        allAround(UCItems.INVISIFEATHER.get(), Items.FEATHER, UCItems.INVISITWINE.get()).build(consumer);
        recipe(UCItems.WEEPINGEYE.get(), 1)
                .key('T', UCItems.WEEPINGTEAR.get())
                .key('E', Items.ENDER_EYE)
                .patternLine(" T ")
                .patternLine("TET")
                .patternLine(" T ")
                .addCriterion("has_item", hasItem(UCItems.WEEPINGTEAR.get()))
                .build(consumer);
        recipe(UCItems.BOOK_UPGRADE.get(), 1)
                .key('B', UCItems.BOOK_DISCOUNT.get())
                .key('F', UCItems.INVISIFEATHER.get())
                .patternLine(" F ")
                .patternLine("FBF")
                .patternLine(" F ")
                .addCriterion("has_item", hasItem(UCItems.BOOK_DISCOUNT.get()))
                .build(consumer);
        recipe(UCItems.EGGUPGRADE.get(), 1)
                .key('E', Items.EGG)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('M', UCItems.MILLENNIUMEYE.get())
                .patternLine("IEI")
                .patternLine("EME")
                .patternLine("IEI")
                .addCriterion("has_item", hasItem(UCItems.MILLENNIUMEYE.get()))
                .build(consumer);
        recipe(UCItems.EASYBADGE.get(), 1)
                .key('Q', Blocks.QUARTZ_BLOCK)
                .key('E', UCItems.MILLENNIUMEYE.get())
                .key('G', Tags.Items.INGOTS_GOLD)
                .patternLine("GEG")
                .patternLine("EQE")
                .patternLine("GEG")
                .addCriterion("has_item", hasItem(UCItems.MILLENNIUMEYE.get()))
                .build(consumer);
        recipe(UCItems.BOOK_EULA.get(), 1)
                .key('B', Items.BOOK)
                .key('L', UCItems.LEGALSTUFF.get())
                .patternLine(" L ")
                .patternLine("LBL")
                .patternLine(" L ")
                .addCriterion("has_item", hasItem(UCItems.LEGALSTUFF.get()))
                .build(consumer);
        recipe(UCItems.ESCAPEROPE.get(), 2)
                .key('E', UCItems.LILYTWINE.get())
                .key('I', UCItems.INVISITWINE.get())
                .patternLine("EI")
                .patternLine("IE")
                .patternLine("EI")
                .addCriterion("has_item", hasItem(UCItems.LILYTWINE.get()))
                .build(consumer);
        recipe(UCBlocks.SUN_DIAL.get(), 1)
                .key('R', Items.REDSTONE)
                .key('C', Tags.Items.COBBLESTONE)
                .patternLine("RCR")
                .patternLine("CCC")
                .addCriterion("has_item", hasItem(Items.REDSTONE))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(UCItems.DIET_PILLS.get())
                .addIngredient(UCItems.ABSTRACT.get())
                .addIngredient(UCItems.ABSTRACT.get())
                .addIngredient(Items.GLASS_BOTTLE)
                .addCriterion("has_item", hasItem(UCItems.ABSTRACT.get()))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(UCItems.EGGNOG.get())
                .addIngredient(Items.EGG)
                .addIngredient(Items.MILK_BUCKET)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.PAPER)
                .addCriterion("has_item", hasItem(Items.MILK_BUCKET))
                .build(consumer);
        recipe(UCItems.GOLDEN_BREAD.get(), 1)
                .key('R', UCItems.GOLDENRODS.get())
                .patternLine("RRR")
                .addCriterion("has_item", hasItem(UCItems.GOLDENRODS.get()))
                .build(consumer);
        storage(UCItems.LARGE_PLUM.get(), UCItems.DIRIGIBLEPLUM.get()).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(UCItems.YOGURT.get())
                .addIngredient(UCItems.BOILED_MILK.get())
                .addIngredient(Items.BOWL)
                .addIngredient(Items.BOWL)
                .addIngredient(UCItems.DYEIUS_SEED.get())
                .addCriterion("has_item", hasItem(UCItems.BOILED_MILK.get()))
                .build(consumer);
        recipe(UCBlocks.GOBLET.get(), 1)
                .key('g', Tags.Items.INGOTS_GOLD)
                .key('R', Blocks.REDSTONE_BLOCK)
                .key('P', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)
                .patternLine("g g")
                .patternLine("gRg")
                .patternLine(" P ")
                .addCriterion("has_item", hasItem(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE))
                .build(consumer);
        recipe(UCItems.HANDMIRROR.get(), 1)
                .key('S', Items.STICK)
                .key('G', Tags.Items.GLASS_PANES)
                .key('E', UCItems.MILLENNIUMEYE.get())
                .patternLine("  E")
                .patternLine(" G ")
                .patternLine("S  ")
                .addCriterion("has_item", hasItem(UCItems.MILLENNIUMEYE.get()))
                .build(consumer);
        recipe(UCItems.EMERADIC_DIAMOND.get(), 1)
                .key('D', UCItems.PREGEM.get())
                .key('E', Tags.Items.GEMS_EMERALD)
                .patternLine("DED")
                .addCriterion("has_item", hasItem(UCItems.PREGEM.get()))
                .build(consumer);
        recipe(UCBlocks.HARVEST_TRAP.get(), 1)
                .key('P', ItemTags.LOGS)
                .key('S', ItemTags.WOODEN_SLABS)
                .key('D', Tags.Items.STORAGE_BLOCKS_IRON)
                .key('I', Blocks.IRON_BARS)
                .patternLine("IDI")
                .patternLine("SPS")
                .patternLine("SPS")
                .addCriterion("has_item", hasItem(ItemTags.LOGS))
                .build(consumer);
        recipe(UCBlocks.HOURGLASS.get(), 1)
                .key('P', Tags.Items.GLASS_PANES)
                .key('D', UCItems.TIMEDUST.get())
                .key('G', Tags.Items.STORAGE_BLOCKS_GOLD)
                .patternLine("DGD")
                .patternLine("PDP")
                .patternLine("DGD")
                .addCriterion("has_item", hasItem(UCItems.TIMEDUST.get()))
                .build(consumer);
        recipe(UCBlocks.LILY_ICE.get(), 1)
                .key('P', Blocks.PACKED_ICE)
                .key('L', Blocks.LILY_PAD)
                .patternLine(" P ")
                .patternLine("PLP")
                .patternLine(" P ")
                .addCriterion("has_item", hasItem(Blocks.LILY_PAD))
                .build(consumer);
        recipe(UCItems.IMPACT_SHIELD.get(), 1)
                .key('N', Items.NETHER_STAR)
                .key('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .key('S', Items.SHIELD)
                .key('D', Blocks.ANCIENT_DEBRIS)
                .patternLine("DSI")
                .patternLine("SNS")
                .patternLine("ISD")
                .addCriterion("has_item", hasItem(Items.SHIELD))
                .build(consumer);
        recipe(UCItems.IMPREGNATED_LEATHER.get(), 1)
                .key('C', Items.COAL)
                .key('L', Items.LEATHER)
                .patternLine(" C ")
                .patternLine("CLC")
                .patternLine(" C ")
                .addCriterion("has_item", hasItem(Items.LEATHER))
                .build(consumer);
        recipe(UCBlocks.INVISIBILIA_GLASS.get(), 4)
                .key('T', UCItems.INVISITWINE.get())
                .key('G', Tags.Items.GLASS)
                .patternLine("TGT")
                .patternLine("GTG")
                .patternLine("TGT")
                .addCriterion("has_item", hasItem(UCItems.INVISITWINE.get()))
                .build(consumer);
        recipe(UCItems.ITEM_MAGNET.get(), 1)
                .key('E', UCItems.FERROMAGNETICIRON.get())
                .patternLine("EEE")
                .patternLine("E E")
                .patternLine("E E")
                .addCriterion("has_item", hasItem(UCItems.FERROMAGNETICIRON.get()))
                .build(consumer);
        recipe(UCBlocks.LILY_JUNGLE.get(), 1)
                .key('P', Blocks.JUNGLE_LEAVES)
                .key('L', Blocks.LILY_PAD)
                .patternLine(" P ")
                .patternLine("PLP")
                .patternLine(" P ")
                .addCriterion("has_item", hasItem(Blocks.LILY_PAD))
                .build(consumer);
        recipe(UCBlocks.LILY_LAVA.get(), 1)
                .key('C', UCItems.CINDERLEAF.get())
                .key('L', Blocks.LILY_PAD)
                .patternLine(" C ")
                .patternLine("CLC")
                .patternLine(" C ")
                .addCriterion("has_item", hasItem(Blocks.LILY_PAD))
                .build(consumer);
        storage(UCBlocks.NORMIECRATE.get(), UCItems.NORMAL_SEED.get()).build(consumer);
        shapeless(UCItems.NORMAL_SEED.get(), UCBlocks.NORMIECRATE.get(), 9).build(consumer);
        shapeless(Items.DIAMOND, UCBlocks.OLDDIAMOND.get(), 9).build(consumer);
        shapeless(Items.GOLD_INGOT, UCBlocks.OLDGOLD.get(), 9).build(consumer);
        shapeless(Items.IRON_INGOT, UCBlocks.OLDIRON.get(), 9).build(consumer);
        recipe(UCItems.PIXEL_BRUSH.get(), 1)
                .key('P', UCItems.PIXELS.get())
                .key('I', UCItems.INVISITWINE.get())
                .key('S', Items.STICK)
                .patternLine(" S ")
                .patternLine("ISI")
                .patternLine("PPP")
                .addCriterion("has_item", hasItem(UCItems.PIXELS.get()))
                .build(consumer);
        allAround(UCItems.GLASSES_PIXELS.get(), UCItems.GLASSES_3D.get(), UCItems.PIXELS.get()).build(consumer);
        recipe(UCItems.PONCHO.get(), 1)
                .key('P', UCItems.INVISIFEATHER.get())
                .patternLine("P P")
                .patternLine("PPP")
                .patternLine("PPP")
                .addCriterion("has_item", hasItem(UCItems.INVISIFEATHER.get()))
                .build(consumer);
        shapeless(UCItems.ABSTRACT_SEED.get(), UCItems.ABSTRACT.get(), 1).build(consumer);
        recipe(UCItems.ARTISIA_SEED.get(), 1)
                .key('S', UCItems.NORMAL_SEED.get())
                .key('C', Blocks.CRAFTING_TABLE)
                .patternLine(" S ")
                .patternLine("SCS")
                .patternLine(" S ")
                .addCriterion("has_item", hasItem(UCItems.NORMAL_SEED.get()))
                .build(consumer);
        shapeless(UCItems.DIRIGIBLE_SEED.get(), UCItems.LARGE_PLUM.get(), 2).build(consumer);
        recipe(UCItems.SPIRITBAIT.get(), 1)
                .key('V', Blocks.VINE)
                .patternLine("V V")
                .patternLine(" V ")
                .patternLine("V V")
                .addCriterion("has_item", hasItem(Blocks.VINE))
                .build(consumer);
        recipe(UCBlocks.SUN_BLOCK.get(), 1)
                .key('E', UCItems.SAVAGEESSENCE.get())
                .key('G', UCBlocks.INVISIBILIA_GLASS.get())
                .patternLine("EGE")
                .patternLine("GEG")
                .patternLine("EGE")
                .addCriterion("has_item", hasItem(UCItems.SAVAGEESSENCE.get()))
                .build(consumer);
        recipe(UCBlocks.LILY_ENDER.get(), 1)
                .key('P', UCItems.LILYTWINE.get())
                .key('L', Blocks.LILY_PAD)
                .key('E', Items.ENDER_EYE)
                .patternLine(" E ")
                .patternLine("PLP")
                .patternLine(" P ")
                .addCriterion("has_item", hasItem(Blocks.LILY_PAD))
                .build(consumer);
        recipe(UCItems.THUNDERPANTZ.get(), 1)
                .key('W', Blocks.BLUE_WOOL)
                .key('P', Items.LEATHER_LEGGINGS)
                .key('F', UCItems.INVISIFEATHER.get())
                .patternLine("WWW")
                .patternLine("WPW")
                .patternLine("WFW")
                .addCriterion("has_item", hasItem(UCItems.INVISIFEATHER.get()))
                .build(consumer);
        recipe(UCBlocks.TOTEMHEAD.get(), 1)
                .key('S', Items.STICK)
                .key('L', Blocks.LAPIS_BLOCK)
                .key('M', UCItems.MILLENNIUMEYE.get())
                .patternLine("LLL")
                .patternLine("LML")
                .patternLine(" S ")
                .addCriterion("has_item", hasItem(UCItems.MILLENNIUMEYE.get()))
                .build(consumer);
        recipe(UCItems.WILDWOOD_STAFF.get(), 1)
                .key('S', Items.STICK)
                .key('N', UCItems.ENDERSNOOKER.get())
                .key('E', UCItems.SAVAGEESSENCE.get())
                .patternLine(" SE")
                .patternLine(" NS")
                .patternLine("S  ")
                .addCriterion("has_item", hasItem(UCItems.SAVAGEESSENCE.get()))
                .build(consumer);
        recipe(UCBlocks.RUINEDBRICKSCARVED.get(), 1)
                .key('R', UCBlocks.RUINEDBRICKS_SLAB.get())
                .patternLine("R")
                .patternLine("R")
                .addCriterion("has_item", hasItem(UCBlocks.RUINEDBRICKS_SLAB.get()))
                .build(consumer);
        recipe(UCBlocks.OBTUSE_PLATFORM.get(), 1)
                .key('D', UCBlocks.DARK_BLOCK.get())
                .key('G', UCBlocks.INVISIBILIA_GLASS.get())
                .key('T', UCItems.LILYTWINE.get())
                .patternLine("TGT")
                .patternLine("GDG")
                .patternLine("TGT")
                .addCriterion("has_item", hasItem(UCBlocks.DARK_BLOCK.get()))
                .build(consumer);
        recipe(UCBlocks.FLYWOOD_TRAPDOOR.get(), 2)
                .key('P', UCBlocks.FLYWOOD_PLANKS.get())
                .patternLine("PPP")
                .patternLine("PPP")
                .addCriterion("has_item", hasItem(UCBlocks.FLYWOOD_PLANKS.get()))
                .build(consumer);
        recipe(UCBlocks.ROSEWOOD_TRAPDOOR.get(), 2)
                .key('P', UCBlocks.ROSEWOOD_PLANKS.get())
                .patternLine("PPP")
                .patternLine("PPP")
                .addCriterion("has_item", hasItem(UCBlocks.ROSEWOOD_PLANKS.get()))
                .build(consumer);
    }

    @SuppressWarnings("deprecation")
    private void specialRecipe(Consumer<IFinishedRecipe> consumer, SpecialRecipeSerializer<?> serializer) {

        ResourceLocation name = Registry.RECIPE_SERIALIZER.getKey(serializer);
        if (name != null)
            CustomRecipeBuilder.customRecipe(serializer).build(consumer, UniqueCrops.MOD_ID + ":dynamic/" + name.getPath());
    }

    private ShapedRecipeBuilder recipe(IItemProvider output, int count) {

        return ShapedRecipeBuilder.shapedRecipe(output, count);
    }

    private ShapelessRecipeBuilder shapeless(IItemProvider output, IItemProvider input, int count) {

        return ShapelessRecipeBuilder.shapelessRecipe(output, count).addIngredient(input).addCriterion("has_item", hasItem(input));
    }

    private ShapedRecipeBuilder allAround(IItemProvider output, IItemProvider center, IItemProvider surrounding) {

        return ShapedRecipeBuilder.shapedRecipe(output, 1)
                .key('A', center)
                .key('B', surrounding)
                .patternLine("BBB").patternLine("BAB").patternLine("BBB")
                .addCriterion("has_item", hasItem(center));
    }

    private ShapedRecipeBuilder storage(IItemProvider output, IItemProvider input) {

        return ShapedRecipeBuilder.shapedRecipe(output)
                .key('S', input)
                .patternLine("SSS").patternLine("SSS").patternLine("SSS")
                .addCriterion("has_item", hasItem(input));
    }

    private ShapedRecipeBuilder slabs(IItemProvider output, IItemProvider input) {

        return ShapedRecipeBuilder.shapedRecipe(output, 6)
                .addCriterion("has_item", hasItem(input))
                .key('S', input)
                .patternLine("SSS");
    }

    private ShapedRecipeBuilder stairs(IItemProvider output, IItemProvider input) {

        return ShapedRecipeBuilder.shapedRecipe(output, 4)
                .addCriterion("has_item", hasItem(input))
                .key('S', input)
                .patternLine("  S")
                .patternLine(" SS")
                .patternLine("SSS");
    }
}
