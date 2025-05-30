package net.techno573.scurvydogs.entity;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.techno573.scurvydogs.ScurvyDogsMod;
import net.techno573.scurvydogs.entity.client.ProceduralSpiderRenderer;
import net.techno573.scurvydogs.entity.custom.ProceduralSpider;

public class ModEntities {
    private static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ScurvyDogsMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>,EntityType<ProceduralSpider>> PROCEDURAL_SPIDER = ENTITIES.register(
            "procedural_spider",
            () -> EntityType.Builder.of(ProceduralSpider::new, MobCategory.MONSTER)
                    .sized(1.4f, 0.9f)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(ScurvyDogsMod.MOD_ID, "procedural_spider").toString())
    );

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
