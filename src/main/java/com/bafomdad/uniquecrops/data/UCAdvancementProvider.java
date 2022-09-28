package com.bafomdad.uniquecrops.data;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.init.UCBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.bafomdad.uniquecrops.init.UCItems.*;

public class UCAdvancementProvider extends AdvancementProvider {

    private Advancement artisiaCraft;

    public UCAdvancementProvider(DataGenerator gen) {

        super(gen);
    }

    @Override
    protected void registerAdvancements(@NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper file) {

        Advancement root = Advancement.Builder.advancement()
                .display(rootDisplay(UCBlocks.RUINEDBRICKS.get(), new ResourceLocation(UniqueCrops.MOD_ID, "textures/block/oldgravel.png")))
                .addCriterion("seed", onPickup(NORMAL_SEED.get()))
                .save(consumer, main("root"));
        Advancement seedPickup = Advancement.Builder.advancement()
                .display(simple(NORMAL_SEED.get(), "seedPickup"))
                .parent(root)
                .addCriterion("seed", onPickup(NORMAL_SEED.get()))
                .save(consumer, main("seed_pickup"));
        Advancement bookGuide = Advancement.Builder.advancement()
                .display(simple(BOOK_GUIDE.get(), "craftingGuideBook"))
                .parent(seedPickup)
                .addCriterion("seed", onPickup(BOOK_GUIDE.get()))
                .save(consumer, main("guidebook_craft"));
        artisiaCraft = Advancement.Builder.advancement()
                .display(simple(ARTISIA_SEED.get(), "craftingArtisia"))
                .parent(bookGuide)
                .addCriterion("seed", onPickup(ARTISIA_SEED.get()))
                .save(consumer, main("artisia_craft"));
        cropAdvancement(ABSTRACT_SEED, "craftingAbstract", consumer);
        cropAdvancement(CINDERBELLA_SEED, "craftingCinderbella", consumer);
        cropAdvancement(COBBLONIA_SEED, "craftingCobblonia", consumer);
        cropAdvancement(COLLIS_SEED, "craftingCollis", consumer);
        cropAdvancement(DEVILSNARE_SEED, "craftingDevilsnare", consumer);
        cropAdvancement(DIRIGIBLE_SEED, "craftingDirigible", consumer);
        cropAdvancement(DYEIUS_SEED, "craftingDyeius", consumer);
        cropAdvancement(ENDERLILY_SEED, "craftingEnderlily", consumer);
        cropAdvancement(EULA_SEED, "craftingEula", consumer);
        cropAdvancement(FEROXIA_SEED, "craftingFeroxia", consumer);
        cropAdvancement(IMPERIA_SEED, "craftingImperia", consumer);
        cropAdvancement(INVISIBILIA_SEED, "craftingInvisibilia", consumer);
        cropAdvancement(MALLEATORIS_SEED, "craftingMalleatoris", consumer);
        cropAdvancement(MARYJANE_SEED, "craftingMaryjane", consumer);
        cropAdvancement(MERLINIA_SEED, "craftingMerlinia", consumer);
        cropAdvancement(MILLENNIUM_SEED, "craftingMillennium", consumer);
        cropAdvancement(MUSICA_SEED, "craftingMusica", consumer);
        cropAdvancement(PETRAMIA_SEED, "craftingPetramia", consumer);
        cropAdvancement(PIXELSIUS_SEED, "craftingPixelsius", consumer);
        cropAdvancement(PRECISION_SEED, "craftingPrecision", consumer);
        cropAdvancement(KNOWLEDGE_SEED, "craftingKnowledge", consumer);
        cropAdvancement(WAFFLONIA_SEED, "craftingWafflonia", consumer);
        cropAdvancement(WEEPINGBELLS_SEED, "craftingWeepingbells", consumer);
        cropAdvancement(HEXIS_SEED, "craftingHexis", consumer);
        cropAdvancement(LACUSIA_SEED, "craftingLacusia", consumer);
        cropAdvancement(DONUTSTEEL_SEED, "craftingDonutsteel", consumer);
        cropAdvancement(QUARRY_SEED, "craftingFossura", consumer);
        cropAdvancement(INSTABILIS_SEED, "craftingInstabilis", consumer);
        cropAdvancement(SUCCO_SEED, "craftingSucco", consumer);
        cropAdvancement(INDUSTRIA_SEED, "craftingIndustria", consumer);
        cropAdvancement(MAGNES_SEED, "craftingMagnes", consumer);
        Advancement.Builder.advancement()
                .display(hidden(ADVENTUS_SEED.get(), "craftingAdventus", FrameType.TASK))
                .parent(artisiaCraft)
                .addCriterion("seed", onPickup(ADVENTUS_SEED.get()))
                .save(consumer, main("adventus_pickup"));
        Advancement.Builder.advancement()
                .display(hidden(BLESSED_SEED.get(), "craftingBlessed", FrameType.TASK))
                .parent(artisiaCraft)
                .addCriterion("seed", onPickup(BLESSED_SEED.get()))
                .save(consumer, main("blessed_pickup"));
        Advancement.Builder.advancement()
                .display(hidden(STEVE_HEART.get(), "heartPickup", FrameType.CHALLENGE))
                .parent(bookGuide)
                .addCriterion("seed", onPickup(STEVE_HEART.get()))
                .save(consumer, main("heart_pickup"));
        Advancement.Builder.advancement()
                .display(hidden(TERIYAKI.get(), "teriyakiPickup", FrameType.CHALLENGE))
                .parent(bookGuide)
                .addCriterion("seed", onPickup(TERIYAKI.get()))
                .save(consumer, main("teriyaki_pickup"));
    }

    private DisplayInfo rootDisplay(ItemLike icon, ResourceLocation background) {

        return new DisplayInfo(new ItemStack(icon.asItem()),
                new TranslatableComponent("itemGroup.uniquecrops"),
                new TranslatableComponent("uniquecrops.desc"),
                background, FrameType.TASK, false, false, false);
    }

    private void cropAdvancement(RegistryObject<BlockItem> icon, String name, Consumer<Advancement> consumer) {

        Advancement.Builder.advancement()
                .display(simple(icon.get(), name))
                .parent(artisiaCraft)
                .addCriterion("seed", onPickup(icon.get()))
//                .addCriterion("artisia", onPickup(ARTISIA_SEED.get()))
                .save(consumer, main(format(name)));
    }

    private String format(String name) {

        int index = 0;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                index = i;
                break;
            }
        }
        StringBuilder sb = new StringBuilder(name);
        sb.insert(index, "_");
        return sb.toString().toLowerCase();
    }

    private DisplayInfo simple(ItemLike icon, String name) {

        String str = "advancement.uniquecrops:" + name;
        return new DisplayInfo(new ItemStack(icon.asItem()),
                new TranslatableComponent(str),
                new TranslatableComponent(str + ".desc"),
                null, FrameType.TASK, true, true, false);
    }

    private DisplayInfo hidden(ItemLike icon, String name, FrameType frame) {

        String str = "advancement.uniquecrops:" + name;
        return new DisplayInfo(new ItemStack(icon.asItem()),
                new TranslatableComponent(str),
                new TranslatableComponent(str + ".desc"),
                null, frame, true, true, true);
    }

    private InventoryChangeTrigger.TriggerInstance onPickup(ItemLike... items) {

        return InventoryChangeTrigger.TriggerInstance.hasItems(matchItems(items));
    }

    private ItemPredicate matchItems(ItemLike... items) {

        return ItemPredicate.Builder.item().of(items).build();
    }

    private String main(String name) {

        return "uniquecrops:main/" + name;
    }

    @Override
    public @NotNull String getName() {

        return "unique crops advancements";
    }
}
