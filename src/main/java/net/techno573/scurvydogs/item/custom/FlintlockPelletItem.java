package net.techno573.scurvydogs.item.custom;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.techno573.scurvydogs.entity.ModEntityTypes;
import net.techno573.scurvydogs.entity.custom.FlintlockPelletEntity;

public class FlintlockPelletItem extends Item implements ProjectileItem {

    public FlintlockPelletItem(Properties properties) {
        super(properties);
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        return new FlintlockPelletEntity(ModEntityTypes.FLINTLOCK_PELLET.get(),level);
    }

    @Override
    public void shoot(Projectile projectile, double x, double y, double z, float velocity, float inaccuracy) {
        FlintlockPelletEntity pellet = new FlintlockPelletEntity(ModEntityTypes.FLINTLOCK_PELLET.get(),projectile.level());
        ProjectileItem.super.shoot(pellet, x, y, z, velocity, inaccuracy);
    }
}
