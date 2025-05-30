package net.techno573.scurvydogs.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.techno573.scurvydogs.ScurvyDogsMod;
import net.techno573.scurvydogs.entity.custom.ProceduralSpider;
import software.bernie.geckolib.model.GeoModel;

public class ProceduralSpiderModel extends GeoModel<ProceduralSpider> {
    private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(ScurvyDogsMod.MOD_ID, "geo/entity/spider.geo.json");
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ScurvyDogsMod.MOD_ID, "textures/entity/spider.png");
    private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(ScurvyDogsMod.MOD_ID, "animations/spider.animation.json");

    @Override
    public ResourceLocation getModelResource(ProceduralSpider animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(ProceduralSpider animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(ProceduralSpider animatable) {
        return ANIMATION;
    }


}
