package net.techno573.scurvydogs.sound;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.techno573.scurvydogs.ScurvyDogsMod;

import java.util.function.Supplier;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, ScurvyDogsMod.MOD_ID);

    public static final Holder<SoundEvent> FLINTLOCK_PISTOL_CHARGE_START = registerSoundEvent("item.flintlock_pistol.charge_start");
    public static final Holder<SoundEvent> FLINTLOCK_PISTOL_CHARGE_MIDDLE = registerSoundEvent("item.flintlock_pistol.charge_middle");
    public static final Holder<SoundEvent> FLINTLOCK_PISTOL_CHARGE_END = registerSoundEvent("item.flintlock_pistol.charge_end");
    public static final Holder<SoundEvent> FLINTLOCK_PISTOL_SHOT = registerSoundEvent("item.flintlock_pistol.shot");

    private static Holder<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ScurvyDogsMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
