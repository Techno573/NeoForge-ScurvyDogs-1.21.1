package net.techno573.scurvydogs.entity.armor.client;

import net.minecraft.resources.ResourceLocation;
import net.techno573.scurvydogs.ScurvyDogsMod;
import net.techno573.scurvydogs.item.custom.HatItem;
import software.bernie.geckolib.model.GeoModel;

public class BandanaHatModel extends GeoModel<HatItem> {
    @Override
    public ResourceLocation getModelResource(HatItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(ScurvyDogsMod.MOD_ID,"geo/armor/bandana.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HatItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(ScurvyDogsMod.MOD_ID,"textures/models/armor/bandana.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HatItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(ScurvyDogsMod.MOD_ID,"animations/empty_animation.json");
    }
}
