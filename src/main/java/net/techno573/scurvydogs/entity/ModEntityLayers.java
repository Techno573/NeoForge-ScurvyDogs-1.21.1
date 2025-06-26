package net.techno573.scurvydogs.entity;

import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.techno573.scurvydogs.entity.client.model.BuccaneerModel;
import net.techno573.scurvydogs.entity.client.model.FlintlockPelletEntityModel;

public class ModEntityLayers {
    @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BuccaneerModel.LAYER_LOCATION, BuccaneerModel::createBodyLayer);
        event.registerLayerDefinition(BuccaneerModel.OUTER_ARMOR_LAYER_LOCATION, () -> BuccaneerModel.createArmorLayer(new CubeDeformation(1.0f)));
        event.registerLayerDefinition(BuccaneerModel.INNER_ARMOR_LAYER_LOCATION, () -> BuccaneerModel.createArmorLayer(new CubeDeformation(0.5f)));

        event.registerLayerDefinition(FlintlockPelletEntityModel.LAYER_LOCATION, FlintlockPelletEntityModel::createBodyLayer);
    }
}
