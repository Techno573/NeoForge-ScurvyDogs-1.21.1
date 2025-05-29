package net.techno573.scurvydogs.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.techno573.scurvydogs.ScurvyDogsMod;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, ScurvyDogsMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SCURVY_DOGS_TAB = CREATIVE_MODE_TABS.register("scurvy_dogs_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.scurvy_dogs")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.FANCY_TRICORN_HAT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.TRICORN_HAT.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                output.accept(ModItems.FANCY_TRICORN_HAT.get());
                output.accept(ModItems.BICORN_HAT.get());
                output.accept(ModItems.FANCY_BICORN_HAT.get());
                output.accept(ModItems.BANDANA.get());
            }).build());
}
