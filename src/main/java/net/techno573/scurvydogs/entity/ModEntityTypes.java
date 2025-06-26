package net.techno573.scurvydogs.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.techno573.scurvydogs.ScurvyDogsMod;
import net.techno573.scurvydogs.entity.custom.BucaneerEntity;
import net.techno573.scurvydogs.entity.custom.FlintlockPelletEntity;

public class ModEntityTypes {
    private static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ScurvyDogsMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>,EntityType<BucaneerEntity>> BUCCANEER = ENTITIES.register(
            "buccaneer",
            () -> EntityType.Builder.of(BucaneerEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(ScurvyDogsMod.MOD_ID, "buccaneer").toString())
    );

    public static final DeferredHolder<EntityType<?>,EntityType<FlintlockPelletEntity>> FLINTLOCK_PELLET = ENTITIES.register(
            "flintlock_pellet",
            () -> EntityType.Builder.of(FlintlockPelletEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(ScurvyDogsMod.MOD_ID, "flintlock_pellet").toString())
    );

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
