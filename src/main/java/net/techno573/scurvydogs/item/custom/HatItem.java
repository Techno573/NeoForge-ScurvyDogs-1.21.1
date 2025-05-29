package net.techno573.scurvydogs.item.custom;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.techno573.scurvydogs.entity.armor.client.*;
import net.techno573.scurvydogs.item.ModItems;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public final class HatItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public HatItem(Properties properties) {
        super(ArmorMaterials.LEATHER, Type.HELMET, properties);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {
                if(this.renderer == null)
                    if (itemStack.getItem() == ModItems.BANDANA.asItem()) {
                        this.renderer = new HatArmorRenderer(new BandanaHatModel());
                    } else if (itemStack.getItem() == ModItems.BICORN_HAT.asItem()) {
                        this.renderer = new HatArmorRenderer(new BicornHatModel());
                    } else if (itemStack.getItem() == ModItems.FANCY_BICORN_HAT.asItem()) {
                        this.renderer = new HatArmorRenderer(new FancyBicornHatModel());
                    } else if (itemStack.getItem() == ModItems.TRICORN_HAT.asItem()) {
                        this.renderer = new HatArmorRenderer(new TricornHatModel());
                    } else if (itemStack.getItem() == ModItems.FANCY_TRICORN_HAT.asItem()) {
                        this.renderer = new HatArmorRenderer(new FancyTricornHatModel());
                    } else {this.renderer = new HatArmorRenderer(new BandanaHatModel());}
                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
