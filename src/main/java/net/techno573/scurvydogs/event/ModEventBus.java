package net.techno573.scurvydogs.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.techno573.scurvydogs.item.ModItems;
import net.techno573.scurvydogs.item.client.DefaultHatItemColor;

public class ModEventBus {
    @SubscribeEvent
    public void registerItemColors(RegisterColorHandlersEvent.Item event){
        event.register(new DefaultHatItemColor(),
                ModItems.BANDANA,
                ModItems.BICORN_HAT,
                ModItems.FANCY_BICORN_HAT,
                ModItems.TRICORN_HAT,
                ModItems.FANCY_TRICORN_HAT
        );
    }
}
