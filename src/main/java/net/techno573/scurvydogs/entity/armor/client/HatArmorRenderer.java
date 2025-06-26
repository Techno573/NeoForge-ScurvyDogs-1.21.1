package net.techno573.scurvydogs.entity.armor.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.common.Mod;
import net.techno573.scurvydogs.item.ModItems;
import net.techno573.scurvydogs.item.custom.HatItem;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;
import software.bernie.geckolib.util.Color;

public class HatArmorRenderer extends GeoArmorRenderer<HatItem> {

    public HatArmorRenderer(GeoModel<HatItem> model) {
        super(model);
    }

    @Override
    public GeoModel<HatItem> getGeoModel() {
        return super.getGeoModel();
    }

    private boolean isBoneUndyeable(GeoBone bone) {
        return "feather".equals(bone.getName());
    }

    private boolean isBoneHatRoot(GeoBone bone) {
        return "hat_root".equals(bone.getName());
    }

    private boolean isHatWornByVillagerHead() {
        return this.currentEntity instanceof ZombieVillager || this.currentEntity instanceof AbstractIllager || this.currentEntity instanceof Villager;
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
