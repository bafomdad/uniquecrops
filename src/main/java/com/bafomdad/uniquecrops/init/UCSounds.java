package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UCSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UniqueCrops.MOD_ID);

    public static final RegistryObject<SoundEvent> OOF = createSound("oof");
    public static final RegistryObject<SoundEvent> NEON_SIGNS = createSound("neonsigns");
    public static final RegistryObject<SoundEvent> FAR_AWAY = createSound("faraway");
    public static final RegistryObject<SoundEvent> TAXI = createSound("taxi");
    public static final RegistryObject<SoundEvent> SIMPLY = createSound("simply");

    private static RegistryObject<SoundEvent> createSound(String name) {

        RegistryObject<SoundEvent> sound = SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(UniqueCrops.MOD_ID, name)));
        return sound;
    }
}
