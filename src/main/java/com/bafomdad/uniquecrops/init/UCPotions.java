package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.potions.PotionEnnui;
import com.bafomdad.uniquecrops.potions.PotionIgnorance;
import com.bafomdad.uniquecrops.potions.PotionReverse;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class UCPotions {

    public static final DeferredRegister<Effect> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, UniqueCrops.MOD_ID);

    public static final RegistryObject<Effect> ENNUI = register("ennui", PotionEnnui::new);
    public static final RegistryObject<Effect> IGNORANCE = register("ignorance", PotionIgnorance::new);
    public static final RegistryObject<Effect> REVERSE = register("reverse", PotionReverse::new);

    public static <E extends Effect> RegistryObject<E> register(String name, Supplier<? extends E> supplier) {

        return POTIONS.register(name, supplier);
    }
}
