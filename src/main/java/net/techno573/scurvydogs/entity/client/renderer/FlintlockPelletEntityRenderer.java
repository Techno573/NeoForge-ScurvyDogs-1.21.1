package net.techno573.scurvydogs.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.techno573.scurvydogs.ScurvyDogsMod;
import net.techno573.scurvydogs.entity.client.model.FlintlockPelletEntityModel;
import net.techno573.scurvydogs.entity.custom.FlintlockPelletEntity;

public class FlintlockPelletEntityRenderer<T extends FlintlockPelletEntity> extends EntityRenderer<T> {

    ResourceLocation FLINTLOCK_PELLET = ResourceLocation.fromNamespaceAndPath(ScurvyDogsMod.MOD_ID, "textures/entity/projectile/flintlock_pellet.png");
    private FlintlockPelletEntityModel model;

    public FlintlockPelletEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FlintlockPelletEntityModel(context.bakeLayer(FlintlockPelletEntityModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(T t) {
        return FLINTLOCK_PELLET;
    }

    @Override
    public void render(T p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(bufferSource,this.model.renderType(this.getTextureLocation(p_entity)),false,false);
        this.model.renderToBuffer(poseStack,vertexConsumer,packedLight, OverlayTexture.NO_OVERLAY);
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
