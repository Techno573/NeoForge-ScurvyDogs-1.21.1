package net.techno573.scurvydogs.entity.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.techno573.scurvydogs.ScurvyDogsMod;
import net.techno573.scurvydogs.entity.client.model.BuccaneerModel;
import net.techno573.scurvydogs.entity.custom.BucaneerEntity;

public class BuccaneerRenderer extends HumanoidMobRenderer<BucaneerEntity, BuccaneerModel<BucaneerEntity>> {

    ResourceLocation BUCANEER = ResourceLocation.fromNamespaceAndPath(ScurvyDogsMod.MOD_ID,"textures/entity/buccaneer.png");

    public BuccaneerRenderer(EntityRendererProvider.Context context) {
        super(context, new BuccaneerModel<>(context.bakeLayer(BuccaneerModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new BuccaneerModel(context.bakeLayer(BuccaneerModel.INNER_ARMOR_LAYER_LOCATION)), new BuccaneerModel(context.bakeLayer(BuccaneerModel.OUTER_ARMOR_LAYER_LOCATION)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(BucaneerEntity bucaneerEntity) {
        return BUCANEER;
    }
}
