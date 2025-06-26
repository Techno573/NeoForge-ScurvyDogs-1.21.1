package net.techno573.scurvydogs.entity.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class FlintlockPelletEntity extends Projectile {

    private static final double PELLET_BASE_DAMAGE = (double) 2.0F;
    private ItemStack firedFromWeapon;

    public FlintlockPelletEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        this.firedFromWeapon = null;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        this.discard();
    }

    @Override
    protected void onDeflection(@Nullable Entity entity, boolean deflectedByPlayer) {
        super.onDeflection(entity, deflectedByPlayer);
        this.remove(RemovalReason.DISCARDED);
    }

    public static double getPelletBaseDamage() {
        return PELLET_BASE_DAMAGE;
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
            this.hitTargetOrDeflectSelf(hitresult);
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();
        float f;
        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                float f1 = 0.25F;
                this.level().addParticle(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), vec3.x, vec3.y, vec3.z);
            }

            f = 0.8F;
        } else {
            for (int i = 0; i < 4; ++i) {
                float f1 = 0.25F;
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
            f = 0.99F;
        }

        this.setDeltaMovement(vec3.scale((double) f));
        this.applyGravity();
        this.setPos(d0, d1, d2);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        float f = (float) this.getDeltaMovement().length();
        double d0 = PELLET_BASE_DAMAGE;
        Entity entity1 = this.getOwner();
        assert (entity1 != null ? entity1 : this) instanceof LivingEntity;
        DamageSource damagesource = this.damageSources().mobProjectile(this, (LivingEntity) entity1);
        if (this.getWeaponItem() != null) {
            Level var9 = this.level();
            if (var9 instanceof ServerLevel serverlevel) {
                d0 = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, (float) d0);
            }
        }

        int j = Mth.ceil(Mth.clamp((double) f * d0, (double) 0.0F, (double) Integer.MAX_VALUE));

        if (entity1 instanceof LivingEntity livingentity1) {
            livingentity1.setLastHurtMob(entity);
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int i = entity.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            entity.igniteForSeconds(5.0F);
        }

        if (entity.hurt(damagesource, (float) j)) {
            if (flag) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;

                this.doKnockback(livingentity, damagesource);
                Level var13 = this.level();
                if (var13 instanceof ServerLevel) {
                    ServerLevel serverlevel1 = (ServerLevel) var13;
                    EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, livingentity, damagesource, this.getWeaponItem());
                }

                if (livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()) {
                    ((ServerPlayer) entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }

                if (!this.level().isClientSide && entity1 instanceof ServerPlayer) {
                    ServerPlayer serverplayer = (ServerPlayer) entity1;
                }
            }

            this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));

        } else {
            entity.setRemainingFireTicks(i);
            this.deflect(ProjectileDeflection.REVERSE, entity, this.getOwner(), false);
            this.setDeltaMovement(this.getDeltaMovement().scale(0.2));
            if (!this.level().isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7) {
                this.discard();
            }
        }
        this.discard();
    }

    protected void doKnockback(LivingEntity entity, DamageSource damageSource) {
        float var10000;
        label18: {
            if (this.firedFromWeapon != null) {
                Level var6 = this.level();
                if (var6 instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel)var6;
                    var10000 = EnchantmentHelper.modifyKnockback(serverlevel, this.firedFromWeapon, entity, damageSource, 0.0F);
                    break label18;
                }
            }

            var10000 = 0.0F;
        }

        double d0 = var10000;
        if (d0 > (double)0.0F) {
            double d1 = Math.max(0.0F, (double)1.0F - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            Vec3 vec3 = this.getDeltaMovement().multiply(1.0F, 0.0F, 1.0F).normalize().scale(d0 * 0.6 * d1);
            if (vec3.lengthSqr() > (double)0.0F) {
                entity.push(vec3.x, 0.1, vec3.z);
            }
        }

    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.discard();
    }

    public ItemStack getWeaponItem() {
        return this.firedFromWeapon;
    }
}
