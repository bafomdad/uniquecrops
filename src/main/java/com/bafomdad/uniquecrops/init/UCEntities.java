package com.bafomdad.uniquecrops.init;

import com.bafomdad.uniquecrops.UniqueCrops;
import com.bafomdad.uniquecrops.entities.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class UCEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, UniqueCrops.MOD_ID);

    public static final RegistryObject<EntityType<CookingItemEntity>> COOKING_ITEM = register("cookingitem", () -> EntityType.Builder.<CookingItemEntity>of(CookingItemEntity::new, MobCategory.MISC)
            .sized(0.25F, 0.25F)
            .setTrackingRange(6)
            .build("cookingitem")
    );
    public static final RegistryObject<EntityType<DonkItemEntity>> YEET_ITEM = register("donkitem", () -> EntityType.Builder.<DonkItemEntity>of(DonkItemEntity::new, MobCategory.MISC)
            .sized(0.25F, 0.25F)
            .setTrackingRange(6)
            .build("donkitem")
    );
    public static final RegistryObject<EntityType<BattleCropEntity>> BATTLE_CROP = register("battlecrop", () -> EntityType.Builder.of(BattleCropEntity::new, MobCategory.CREATURE)
            .sized(0.6F, 0.4F)
            .setTrackingRange(10)
            .build("battlecrop")
    );
    public static final RegistryObject<EntityType<MovingCropEntity>> MOVING_CROP = register("movingcrop", () -> EntityType.Builder.of(MovingCropEntity::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .setTrackingRange(10)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .build("movingcrop")
    );
    public static final RegistryObject<EntityType<WeepingEyeEntity>> WEEPING_EYE = register("weepingeye", () -> EntityType.Builder.<WeepingEyeEntity>of(WeepingEyeEntity::new, MobCategory.MISC)
            .sized(0.25F, 0.25F)
            .setTrackingRange(4)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .build("weepingeye")
    );
    public static final RegistryObject<EntityType<EulaBookEntity>> THROWABLE_BOOK = register("throwable_book", () -> EntityType.Builder.<EulaBookEntity>of(EulaBookEntity::new, MobCategory.MISC)
            .sized(0.25F, 0.25F)
            .setTrackingRange(4)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .build("throwable_book")
    );

    public static void registerAttributes(EntityAttributeCreationEvent event) {

        event.put(BATTLE_CROP.get(), BattleCropEntity.registerAttributes().build());
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> register(String id, Supplier<EntityType<E>> type) {

        return ENTITIES.register(id, type);
    }
}
