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

    public static final RegistryObject<EntityType<CookingItemEntity>> COOKING_ITEM = register("cookingitem", EntityType.Builder.<CookingItemEntity>create(CookingItemEntity::new, EntityClassification.MISC)
            .size(0.25F, 0.25F)
            .trackingRange(6)
            .build("cookingitem")
    );
    public static final RegistryObject<EntityType<DonkItemEntity>> YEET_ITEM = register("donkitem", EntityType.Builder.<DonkItemEntity>create(DonkItemEntity::new, EntityClassification.MISC)
            .size(0.25F, 0.25F)
            .trackingRange(6)
            .build("donkitem")
    );
    public static final RegistryObject<EntityType<BattleCropEntity>> BATTLE_CROP = register("battlecrop", EntityType.Builder.create(BattleCropEntity::new, EntityClassification.CREATURE)
            .size(0.6F, 0.4F)
            .trackingRange(10)
            .build("battlecrop")
    );
    public static final RegistryObject<EntityType<MovingCropEntity>> MOVING_CROP = register("movingcrop", EntityType.Builder.create(MovingCropEntity::new, EntityClassification.MISC)
            .size(0.5F, 0.5F)
            .trackingRange(10)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .build("movingcrop")
    );
    public static final RegistryObject<EntityType<WeepingEyeEntity>> WEEPING_EYE = register("weepingeye", EntityType.Builder.<WeepingEyeEntity>create(WeepingEyeEntity::new, EntityClassification.MISC)
            .size(0.25F, 0.25F)
            .trackingRange(4)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .build("weepingeye")
    );
    public static final RegistryObject<EntityType<EulaBookEntity>> THROWABLE_BOOK = register("throwable_book", EntityType.Builder.<EulaBookEntity>create(EulaBookEntity::new, EntityClassification.MISC)
            .size(0.25F, 0.25F)
            .trackingRange(4)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .build("throwable_book")
    );

    public static void registerAttributes(EntityAttributeCreationEvent event) {

        event.put(BATTLE_CROP.get(), BattleCropEntity.registerAttributes().create());
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> register(String id, EntityType<E> type) {

        return ENTITIES.register(id, () -> type);
    }
}
