package net.techno573.scurvydogs.entity.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.techno573.scurvydogs.entity.goal.RangedFlintlockPistolAttackGoal;
import net.techno573.scurvydogs.item.ModItems;
import net.techno573.scurvydogs.item.custom.FlintlockPistolItem;
import org.jetbrains.annotations.Nullable;

public class BucaneerEntity extends AbstractIllager implements CrossbowAttackMob, InventoryCarrier {
    public BucaneerEntity(EntityType<? extends BucaneerEntity> entityType, Level level) {
        super(entityType, level);
    }
    private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW;
    private final SimpleContainer inventory = new SimpleContainer(5);
    @Override
    public void applyRaidBuffs(ServerLevel serverLevel, int i, boolean b) {

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(3, new RangedFlintlockPistolAttackGoal<>(this, 1.0F, 8.0F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0F, false));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[]{Raider.class})).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return null;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        int i = random.nextInt(2);
        if (i == 0) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.IRON_CUTLASS.get()));
        } else {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.FLINTLOCK_PISTOL.get()));
        }

        int j = random.nextInt(4);
        if (j == 0) {
            ItemStack hat = new ItemStack(ModItems.BANDANA.get());
            hat.set(DataComponents.DYED_COLOR,new DyedItemColor(DyeColor.RED.getTextureDiffuseColor(),true));
            this.setItemSlot(EquipmentSlot.HEAD, hat);
        } else if (j == 1) {
            ItemStack hat = new ItemStack(ModItems.BICORN_HAT.get());
            hat.set(DataComponents.DYED_COLOR,new DyedItemColor(5388586,true));
            this.setItemSlot(EquipmentSlot.HEAD, hat);
        } else if (j == 2) {
            ItemStack hat = new ItemStack(ModItems.TRICORN_HAT.get());
            hat.set(DataComponents.DYED_COLOR,new DyedItemColor(5388586,true));
            this.setItemSlot(EquipmentSlot.HEAD, hat);
        }
    }

    @Override
    public void setChargingCrossbow(boolean chargingCrossbow) {
        this.entityData.set(IS_CHARGING_CROSSBOW, chargingCrossbow);
    }

    @Override
    public void onCrossbowAttackPerformed() {
        this.noActionTime = 0;
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float v) {
        this.performCrossbowAttack(this, 1.6F);
    }

    @Override
    public SimpleContainer getInventory() {
        return this.inventory;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_CHARGING_CROSSBOW, false);
    }

    public boolean isChargingCrossbow() {
        return (Boolean)this.entityData.get(IS_CHARGING_CROSSBOW);
    }

    static {
        IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(BucaneerEntity.class, EntityDataSerializers.BOOLEAN);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomsource = level.getRandom();
        this.populateDefaultEquipmentSlots(randomsource,difficulty);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    public static EntityDataAccessor<Boolean> getIsChargingCrossbow() {
        return IS_CHARGING_CROSSBOW;
    }

    @Override
    public void performCrossbowAttack(LivingEntity user, float velocity) {
        InteractionHand interactionhand = ProjectileUtil.getWeaponHoldingHand(user, (item) -> item instanceof FlintlockPistolItem);
        ItemStack itemstack = user.getItemInHand(interactionhand);
        Item var6 = itemstack.getItem();
        if (var6 instanceof FlintlockPistolItem pistolItem) {
            pistolItem.performShooting(user.level(), user, interactionhand, itemstack, 4.8f, (float)(14 - user.level().getDifficulty().getId() * 4), this.getTarget());
        }

        this.onCrossbowAttackPerformed();
    }
}
