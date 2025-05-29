package net.techno573.scurvydogs.item.client;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class DefaultHatItemColor implements ItemColor {

    @Override
    public int getColor(ItemStack itemStack, int i) {
        if (i == 0) {
            return (DyedItemColor.getOrDefault(itemStack, DyedItemColor.LEATHER_COLOR));
        } else {return DyeColor.WHITE.getTextureDiffuseColor();}
    }
}
