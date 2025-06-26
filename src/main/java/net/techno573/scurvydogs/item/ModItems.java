package net.techno573.scurvydogs.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.techno573.scurvydogs.ScurvyDogsMod;
import net.techno573.scurvydogs.entity.ModEntityTypes;
import net.techno573.scurvydogs.item.custom.CutlassItem;
import net.techno573.scurvydogs.item.custom.FlintlockPelletItem;
import net.techno573.scurvydogs.item.custom.FlintlockPistolItem;
import net.techno573.scurvydogs.item.custom.HatItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ScurvyDogsMod.MOD_ID);

    //Tricorn Hat
    public static final DeferredItem<Item> TRICORN_HAT = ITEMS.registerItem("tricorn_hat", HatItem::new, new Item.Properties()
            .durability(60)
            .attributes(ItemAttributeModifiers.builder().add(
                    Attributes.ARMOR,new AttributeModifier(ResourceLocation.withDefaultNamespace("armor"),1.0d,
                            AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HEAD).build()));
    //Fancy Tricorn Hat
    public static final DeferredItem<Item> FANCY_TRICORN_HAT = ITEMS.registerItem("fancy_tricorn_hat", HatItem::new, new Item.Properties()
            .durability(60)
            .attributes(ItemAttributeModifiers.builder().add(
                    Attributes.ARMOR,new AttributeModifier(ResourceLocation.withDefaultNamespace("armor"),1.0d,
                            AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HEAD).build()));
    //Bicorn Hat
    public static final DeferredItem<Item> BICORN_HAT = ITEMS.registerItem("bicorn_hat", HatItem::new,
            new Item.Properties()
            .durability(60)
            .attributes(ItemAttributeModifiers.builder().add(
                    Attributes.ARMOR,new AttributeModifier(ResourceLocation.withDefaultNamespace("armor"),1.0d,
                            AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HEAD).build()));
    //Fancy Bicorn Hat
    public static final DeferredItem<Item> FANCY_BICORN_HAT = ITEMS.registerItem("fancy_bicorn_hat", HatItem::new, new Item.Properties()
            .durability(60)
            .attributes(ItemAttributeModifiers.builder().add(
                    Attributes.ARMOR,new AttributeModifier(ResourceLocation.withDefaultNamespace("armor"),1.0d,
                            AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HEAD).build()));
    //Bandana
    public static final DeferredItem<Item> BANDANA = ITEMS.registerItem("bandana", HatItem::new, new Item.Properties()
            .durability(60)
            .attributes(ItemAttributeModifiers.builder().add(
                    Attributes.ARMOR,new AttributeModifier(ResourceLocation.withDefaultNamespace("armor"),1.0d,
                            AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HEAD).build()));

    //Wooden Cutlass
    public static final DeferredItem<CutlassItem> WOODEN_CUTLASS = ITEMS.register("wooden_cutlass", () -> new CutlassItem(Tiers.WOOD,
            new Item.Properties().attributes(CutlassItem.createAttributes(Tiers.WOOD,2.0f,-2.0f))));
    //Stone Cutlass
    public static final DeferredItem<CutlassItem> STONE_CUTLASS = ITEMS.register("stone_cutlass", () -> new CutlassItem(Tiers.STONE,
            new Item.Properties().attributes(CutlassItem.createAttributes(Tiers.STONE,2.0f,-2.0f))));
    //Iron Cutlass
    public static final DeferredItem<CutlassItem> IRON_CUTLASS = ITEMS.register("iron_cutlass", () -> new CutlassItem(Tiers.IRON,
            new Item.Properties().attributes(CutlassItem.createAttributes(Tiers.IRON,2.0f,-2.0f))));
    //Golden Cutlass
    public static final DeferredItem<CutlassItem> GOLDEN_CUTLASS = ITEMS.register("golden_cutlass", () -> new CutlassItem(Tiers.GOLD,
            new Item.Properties().attributes(CutlassItem.createAttributes(Tiers.GOLD,2.0f,-2.0f))));
    //Diamond Cutlass
    public static final DeferredItem<CutlassItem> DIAMOND_CUTLASS = ITEMS.register("diamond_cutlass", () -> new CutlassItem(Tiers.DIAMOND,
            new Item.Properties().attributes(CutlassItem.createAttributes(Tiers.DIAMOND,2.0f,-2.0f))));
    //Netherite Cutlass
    public static final DeferredItem<CutlassItem> NETHERITE_CUTLASS = ITEMS.register("netherite_cutlass", () -> new CutlassItem(Tiers.NETHERITE,
            new Item.Properties().attributes(CutlassItem.createAttributes(Tiers.NETHERITE,2.0f,-2.0f))));

    //Flintlock Pistol
    public static final DeferredItem<FlintlockPistolItem> FLINTLOCK_PISTOL = ITEMS.register("flintlock_pistol",() -> new FlintlockPistolItem(new Item.Properties()
            .component(DataComponents.MAX_STACK_SIZE,1)));
    //Flintlock Pellet
    public static final DeferredItem<FlintlockPelletItem> FLINTLOCK_PELLET = ITEMS.register("flintlock_pellet",() -> new FlintlockPelletItem(new Item.Properties()));

    //Buccaneer Spawn Egg
    public static final DeferredItem<Item> BUCCANEER_SPAWN_EGG = ITEMS.register("buccaneer_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.BUCCANEER,0x202020,0xfafafa,new Item.Properties()));

}
