package net.techno573.scurvydogs.entity;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

public class ModEntityAttributes {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(
                ModEntityTypes.BUCCANEER.get(),
                Monster.createMonsterAttributes()
                        .add(Attributes.MAX_HEALTH, 20.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.35)
                        .add(Attributes.ATTACK_DAMAGE, 5.0)
                        .add(Attributes.ARMOR, 2.0)
                        .add(Attributes.FOLLOW_RANGE, 32.0)
                        .build()
        );
    }
}
