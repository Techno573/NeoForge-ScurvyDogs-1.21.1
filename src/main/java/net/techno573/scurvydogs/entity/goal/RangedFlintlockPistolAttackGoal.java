package net.techno573.scurvydogs.entity.goal;

import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.techno573.scurvydogs.item.custom.FlintlockPistolItem;

import java.util.EnumSet;

public class RangedFlintlockPistolAttackGoal<T extends Mob & CrossbowAttackMob> extends RangedCrossbowAttackGoal<T> {
    public static final UniformInt PATHFINDING_DELAY_RANGE = TimeUtil.rangeOfSeconds(1, 2);
    private FlintlockPistolState flintlockPistolState;
    private final T mob;
    private final double speedModifier;
    private final float attackRadiusSqr;
    private int seeTime;
    private int attackDelay;
    private int updatePathDelay;

    public RangedFlintlockPistolAttackGoal(T p_25814_, double p_25815_, float p_25816_) {
        super(p_25814_, p_25815_, p_25816_);
        this.flintlockPistolState = FlintlockPistolState.UNCHARGED;
        this.mob = p_25814_;
        this.speedModifier = p_25815_;
        this.attackRadiusSqr = p_25816_ * p_25816_;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }


    @Override
    public void tick() {
        LivingEntity livingentity = mob.getTarget();
        if (livingentity != null) {
            boolean flag = this.mob.getSensing().hasLineOfSight(livingentity);
            boolean flag1 = this.seeTime > 0;
            if (flag != flag1) {
                this.seeTime = 0;
            }

            if (flag) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }

            double d0 = this.mob.distanceToSqr(livingentity);
            boolean flag2 = (d0 > (double)this.attackRadiusSqr || this.seeTime < 5) && this.attackDelay == 0;
            if (flag2) {
                --this.updatePathDelay;
                if (this.updatePathDelay <= 0) {
                    this.mob.getNavigation().moveTo(livingentity, this.canRun() ? this.speedModifier : this.speedModifier * (double)0.5F);
                    this.updatePathDelay = PATHFINDING_DELAY_RANGE.sample(this.mob.getRandom());
                }
            } else {
                this.updatePathDelay = 0;
                this.mob.getNavigation().stop();
            }

            this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
            if (this.flintlockPistolState == FlintlockPistolState.UNCHARGED) {
                if (!flag2) {
                    this.mob.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.mob, (item) -> item instanceof FlintlockPistolItem));
                    this.flintlockPistolState = FlintlockPistolState.CHARGING;
                    ((CrossbowAttackMob)this.mob).setChargingCrossbow(true);
                }
            } else if (this.flintlockPistolState == FlintlockPistolState.CHARGING) {
                if (!this.mob.isUsingItem()) {
                    this.flintlockPistolState = FlintlockPistolState.UNCHARGED;
                }

                int i = this.mob.getTicksUsingItem();
                ItemStack itemstack = this.mob.getUseItem();
                if (i >= FlintlockPistolItem.getChargeDuration(itemstack, this.mob)) {
                    this.mob.releaseUsingItem();
                    this.flintlockPistolState = FlintlockPistolState.CHARGED;
                    this.attackDelay = 20 + this.mob.getRandom().nextInt(20);
                    ((CrossbowAttackMob)this.mob).setChargingCrossbow(false);
                }
            } else if (this.flintlockPistolState == FlintlockPistolState.CHARGED) {
                --this.attackDelay;
                if (this.attackDelay == 0) {
                    this.flintlockPistolState = FlintlockPistolState.READY_TO_ATTACK;
                }
            } else if (this.flintlockPistolState == FlintlockPistolState.READY_TO_ATTACK && flag) {
                ((RangedAttackMob)this.mob).performRangedAttack(livingentity, 1.0F);
                this.flintlockPistolState = FlintlockPistolState.UNCHARGED;
            }
        }
    }

    private boolean canRun() {
        return this.flintlockPistolState == FlintlockPistolState.UNCHARGED.UNCHARGED;
    }

    public FlintlockPistolState getCrossbowState() {
        return flintlockPistolState;
    }

    enum FlintlockPistolState {
        UNCHARGED,
        CHARGING,
        CHARGED,
        READY_TO_ATTACK;
    }
}
