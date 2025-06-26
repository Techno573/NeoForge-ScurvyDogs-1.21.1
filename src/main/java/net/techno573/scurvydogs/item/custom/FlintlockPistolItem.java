package net.techno573.scurvydogs.item.custom;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.techno573.scurvydogs.entity.ModEntityTypes;
import net.techno573.scurvydogs.entity.custom.FlintlockPelletEntity;
import net.techno573.scurvydogs.item.ModItems;
import net.techno573.scurvydogs.sound.ModSounds;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FlintlockPistolItem extends CrossbowItem {
    public FlintlockPistolItem(Properties properties) {
        super(properties);
    }

    private static final float MAX_CHARGE_DURATION = 1.75F;
    public static final int DEFAULT_RANGE = 16;
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;
    private static final float PROJECTILE_POWER = 4.8F;
    private static final ChargingSounds DEFAULT_SOUNDS;

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return new Predicate<ItemStack>() {
            @Override
            public boolean test(ItemStack stack) {
                return stack.getItem() == ModItems.FLINTLOCK_PELLET.get();
            }
        };
    }

    @Override
    public ItemStack getDefaultCreativeAmmo(@Nullable Player player, ItemStack projectileWeaponItem) {
        return super.getDefaultCreativeAmmo(player, new ItemStack(ModItems.FLINTLOCK_PELLET.get()));
    }

    @Override
    public int getDefaultProjectileRange() {
        return DEFAULT_RANGE;
    }

    private static float getShootingPower(ChargedProjectiles projectile) {
        return PROJECTILE_POWER;
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        Vector3f vector3f;
        if (target != null) {
            double d0 = target.getX() - shooter.getX();
            double d1 = target.getZ() - shooter.getZ();
            double d2 = Math.sqrt(d0 * d0 + d1 * d1);
            double d3 = target.getY(0.3333333333333333) - projectile.getY() + d2 * (double)0.2F;
            vector3f = getProjectileShotVector(shooter, new Vec3(d0, d3, d1), angle);
        } else {
            Vec3 vec3 = shooter.getUpVector(1.0F);
            Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(angle * ((float)Math.PI / 180F), vec3.x, vec3.y, vec3.z);
            Vec3 vec31 = shooter.getViewVector(1.0F);
            vector3f = vec31.toVector3f().rotate(quaternionf);
        }

        projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), velocity, inaccuracy);
        float f = getShotPitch(shooter.getRandom(), index);
        ServerLevel serverLevel = (ServerLevel) shooter.level();
        serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE,
                projectile.getX() + shooter.getViewVector(1.0f).x(),
                projectile.getY() + shooter.getViewVector(1.0f).y(),
                projectile.getZ() + shooter.getViewVector(1.0f).z(),
                8,
                0.05,0.05,0.05,
                0.05);
        shooter.level().playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), ModSounds.FLINTLOCK_PISTOL_SHOT, shooter.getSoundSource(), 1.0F, f);
    }

    private static Vector3f getProjectileShotVector(LivingEntity shooter, Vec3 distance, float angle) {
        Vector3f vector3f = distance.toVector3f().normalize();
        Vector3f vector3f1 = (new Vector3f(vector3f)).cross(new Vector3f(0.0F, 1.0F, 0.0F));
        if ((double)vector3f1.lengthSquared() <= 1.0E-7) {
            Vec3 vec3 = shooter.getUpVector(1.0F);
            vector3f1 = (new Vector3f(vector3f)).cross(vec3.toVector3f());
        }

        Vector3f vector3f2 = (new Vector3f(vector3f)).rotateAxis(((float)Math.PI / 2F), vector3f1.x, vector3f1.y, vector3f1.z);
        return (new Vector3f(vector3f)).rotateAxis(angle * ((float)Math.PI / 180F), vector3f2.x, vector3f2.y, vector3f2.z);
    }

    private static float getShotPitch(RandomSource random, int index) {
        return index == 0 ? 1.0F : getRandomShotPitch((index & 1) == 1, random);
    }

    private static float getRandomShotPitch(boolean isHighPitched, RandomSource random) {
        float f = isHighPitched ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
        if (!level.isClientSide) {
            float f = (float)(stack.getUseDuration(livingEntity) - count) / (float)getChargeDuration(stack, livingEntity);
            if (f < 0.25F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (f >= 0.25F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                DEFAULT_SOUNDS.start().ifPresent((p_352849_) -> level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), p_352849_.value(), SoundSource.PLAYERS, 1.0F, 1.0F));
            }

            if (f >= 1.0F && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                DEFAULT_SOUNDS.mid().ifPresent((p_352855_) -> level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), p_352855_.value(), SoundSource.PLAYERS, 1.0F, 1.0F));
            }
        }

    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        ChargedProjectiles chargedprojectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedprojectiles != null && !chargedprojectiles.isEmpty()) {
            ItemStack itemstack = chargedprojectiles.getItems().get(0);
            tooltipComponents.add(Component.translatable("item.minecraft.crossbow.projectile").append(CommonComponents.SPACE).append(itemstack.getDisplayName()));
        }

    }

    static {
        DEFAULT_SOUNDS = new ChargingSounds(Optional.of(ModSounds.FLINTLOCK_PISTOL_CHARGE_START), Optional.of(ModSounds.FLINTLOCK_PISTOL_CHARGE_MIDDLE), Optional.of(ModSounds.FLINTLOCK_PISTOL_CHARGE_END));
    }

    @Override
    public void performShooting(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, float velocity, float inaccuracy, @javax.annotation.Nullable LivingEntity target) {
        if (level instanceof ServerLevel serverlevel) {
            if (shooter instanceof Player player) {
                if (EventHooks.onArrowLoose(weapon, shooter.level(), player, 1, true) < 0) {
                    return;
                }
            }

            ChargedProjectiles chargedprojectiles = weapon.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);
            if (chargedprojectiles != null && !chargedprojectiles.isEmpty()) {
                this.shoot(serverlevel, shooter, hand, weapon, chargedprojectiles.getItems(), velocity, inaccuracy, shooter instanceof Player, target);
                if (shooter instanceof ServerPlayer serverplayer) {
                    serverplayer.awardStat(Stats.ITEM_USED.get(weapon.getItem()));
                }
            }
        }
    }

    public static int getChargeDuration(ItemStack stack, LivingEntity shooter) {
        float f = EnchantmentHelper.modifyCrossbowChargingTime(stack, shooter, MAX_CHARGE_DURATION);
        return Mth.floor(f * 20.0F);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        ChargedProjectiles chargedprojectiles = itemstack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedprojectiles != null && !chargedprojectiles.isEmpty()) {
            this.performShooting(level, player, hand, itemstack, getShootingPower(chargedprojectiles), 1.0F, null);
            return InteractionResultHolder.consume(itemstack);
        } else if (!player.getProjectile(itemstack).isEmpty()) {
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        Projectile projectile = new FlintlockPelletEntity(ModEntityTypes.FLINTLOCK_PELLET.get(),level);
        projectile.setOwner(shooter);
        projectile.setPos(shooter.getX(),shooter.getEyeY() - 0.15,shooter.getZ());

        return projectile;
    }

    public static ChargingSounds getDefaultSounds() {
        return DEFAULT_SOUNDS;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        int i = this.getUseDuration(stack, entityLiving) - timeLeft;
        float f = getPowerForTime(i, stack, entityLiving);
        if (f >= 1.0F && !isCharged(stack) && tryLoadProjectiles(entityLiving, stack)) {
            DEFAULT_SOUNDS.end().ifPresent((p_352852_) -> level.playSound(null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), p_352852_.value(), entityLiving.getSoundSource(), 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F));
        }
    }

    private static boolean tryLoadProjectiles(LivingEntity shooter, ItemStack crossbowStack) {
        List<ItemStack> list = draw(crossbowStack, shooter.getProjectile(crossbowStack), shooter);
        if (!list.isEmpty()) {
            crossbowStack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(list));
            return true;
        } else {
            return false;
        }
    }

    private static float getPowerForTime(int timeLeft, ItemStack stack, LivingEntity shooter) {
        float f = (float)timeLeft / (float)getChargeDuration(stack, shooter);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return getChargeDuration(stack,entity) + 3;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CROSSBOW;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 546;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        return super.damageItem(stack, amount, entity, onBroken);
    }

    @Override
    protected void shoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, List<ItemStack> projectileItems, float velocity, float inaccuracy, boolean isCrit, @Nullable LivingEntity target) {
        super.shoot(level, shooter, hand, weapon, projectileItems, velocity, inaccuracy, isCrit, target);
    }
}
