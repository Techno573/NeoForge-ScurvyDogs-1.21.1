package net.techno573.scurvydogs.entity.armor.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.techno573.scurvydogs.item.custom.HatItem;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.Color;

public class HatArmorRenderer extends GeoArmorRenderer<HatItem> {

    public HatArmorRenderer(GeoModel<HatItem> model) {
        super(model);
    }

    private boolean isBoneUndyeable(GeoBone bone) {
        return "feather".equals(bone.getName());
    }

    @Override
    public void renderRecursively(PoseStack poseStack, HatItem animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        if (isBoneUndyeable(bone)) {
            super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, Color.WHITE.argbInt());
        } else {
            super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        }

    }
}
