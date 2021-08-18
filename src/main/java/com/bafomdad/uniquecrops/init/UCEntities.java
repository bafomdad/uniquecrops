package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UCEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, UniqueCrops.MOD_ID);

    public static final RegistryObject<EntityType<CookingItemEntity>> COOKING_ITEM = register("cookingitem", EntityType.Builder.<CookingItemEntity>of(CookingItemEntity::new, EntityClassification.MISC)
            .sized(0.25F, 0.25F)
            .setTrackingRange(6)
            .build("cookingitem")
    );
    public static final RegistryObject<EntityType<DonkItemEntity>> YEET_ITEM = register("donkitem", EntityType.Builder.<DonkItemEntity>of(DonkItemEntity::new, EntityClassification.MISC)
            .sized(0.25F, 0.25F)
            .setTrackingRange(6)
            .build("donkitem")
    );
    public static final RegistryObject<EntityType<BattleCropEntity>> BATTLE_CROP = register("battlecrop", EntityType.Builder.of(BattleCropEntity::new, EntityClassification.CREATURE)
            .sized(0.6F, 0.4F)
            .setTrackingRange(10)
            .build("battlecrop")
    );
    public static final RegistryObject<EntityType<MovingCropEntity>> MOVING_CROP = register("movingcrop", EntityType.Builder.of(MovingCropEntity::new, EntityClassification.MISC)
            .sized(0.5F, 0.5F)
            .setTrackingRange(10)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .build("movingcrop")
    );
    public static final RegistryObject<EntityType<WeepingEyeEntity>> WEEPING_EYE = register("weepingeye", EntityType.Builder.<WeepingEyeEntity>of(WeepingEyeEntity::new, EntityClassification.MISC)
            .sized(0.25F, 0.25F)
            .setTrackingRange(4)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .build("weepingeye")
    );
    public static final RegistryObject<EntityType<EulaBookEntity>> THROWABLE_BOOK = register("throwable_book", EntityType.Builder.<EulaBookEntity>of(EulaBookEntity::new, EntityClassification.MISC)
            .sized(0.25F, 0.25F)
            .setTrackingRange(4)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .build("throwable_book")
    );

    public static void registerAttributes(EntityAttributeCreationEvent event) {

        event.put(BATTLE_CROP.get(), BattleCropEntity.registerAttributes().build());
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> register(String id, EntityType<E> type) {

        return ENTITIES.register(id, () -> type);
    }
}
