package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.MovingObjectPosition;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.LevelSoundEventPacket;
import cn.nukkit.network.protocol.ProtocolInfo;

import java.util.EnumMap;
import java.util.Map;

public abstract class ItemSpear extends StringItemToolBase {

    private static final double MIN_REACH = 2.0;
    private static final double MAX_REACH = 4.5;
    private static final double CREATIVE_MAX_REACH = 7.5;
    private static final double MIN_RELATIVE_SPEED = 4.6;
    private static final double MIN_KNOCKBACK_SPEED = 5.1;

    public ItemSpear(String id, String name) {
        super(id, name);
    }

    @Override
    public boolean isSpear() {
        return true;
    }

    @Override
    public int getMaxDurability() {
        return this.getStats().durability();
    }

    @Override
    public int getAttackDamage() {
        return this.getStats().damage();
    }

    @Override
    public int getTier() {
        return this.getStats().tier();
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        player.getLevel().addLevelSoundEvent(player, this.getUseSound());
        return true;
    }

    @Override
    public boolean canRelease() {
        return true;
    }

    @Override
    public boolean onRelease(Player player, int ticksUsed) {
        return true;
    }

    public double getMinimumReach() {
        return MIN_REACH;
    }

    public double getMaximumReach(boolean creative) {
        return creative ? CREATIVE_MAX_REACH : MAX_REACH;
    }

    public int getChargeDelay() {
        return this.getStats().chargeDelay();
    }

    public boolean canDealChargeDamage(int ticksUsed, double relativeSpeed) {
        SpearStats stats = this.getStats();
        return ticksUsed >= stats.chargeDelay()
                && ticksUsed <= stats.damageDuration()
                && relativeSpeed >= MIN_RELATIVE_SPEED;
    }

    public boolean canChargeKnockBack(int ticksUsed, double forwardSpeed) {
        return ticksUsed >= this.getStats().chargeDelay()
                && ticksUsed <= this.getStats().knockbackDuration()
                && forwardSpeed >= MIN_KNOCKBACK_SPEED;
    }

    public boolean canChargeDismount(int ticksUsed, double forwardSpeed) {
        SpearStats stats = this.getStats();
        return ticksUsed >= stats.chargeDelay()
                && ticksUsed <= stats.dismountDuration()
                && forwardSpeed >= stats.dismountSpeed();
    }
    
    public int getChargeDamage(double relativeSpeed) {
        return this.getAttackDamage() + (int) Math.floor(relativeSpeed * this.getStats().damageMultiplier());
    }

    public int getAttackHitSound() {
        return switch (this.getNamespaceId()) {
            case WOODEN_SPEAR -> LevelSoundEventPacket.SOUND_WOODEN_SPEAR_ATTACK_HIT;
            case STONE_SPEAR -> LevelSoundEventPacket.SOUND_STONE_SPEAR_ATTACK_HIT;
            case COPPER_SPEAR -> LevelSoundEventPacket.SOUND_COPPER_SPEAR_ATTACK_HIT;
            case IRON_SPEAR -> LevelSoundEventPacket.SOUND_IRON_SPEAR_ATTACK_HIT;
            case GOLDEN_SPEAR -> LevelSoundEventPacket.SOUND_GOLDEN_SPEAR_ATTACK_HIT;
            case DIAMOND_SPEAR -> LevelSoundEventPacket.SOUND_DIAMOND_SPEAR_ATTACK_HIT;
            case NETHERITE_SPEAR -> LevelSoundEventPacket.SOUND_NETHERITE_SPEAR_ATTACK_HIT;
            default -> LevelSoundEventPacket.SOUND_SPEAR_ATTACK_HIT;
        };
    }

    public int getAttackMissSound() {
        return switch (this.getNamespaceId()) {
            case WOODEN_SPEAR -> LevelSoundEventPacket.SOUND_WOODEN_SPEAR_ATTACK_MISS;
            case STONE_SPEAR -> LevelSoundEventPacket.SOUND_STONE_SPEAR_ATTACK_MISS;
            case COPPER_SPEAR -> LevelSoundEventPacket.SOUND_COPPER_SPEAR_ATTACK_MISS;
            case IRON_SPEAR -> LevelSoundEventPacket.SOUND_IRON_SPEAR_ATTACK_MISS;
            case GOLDEN_SPEAR -> LevelSoundEventPacket.SOUND_GOLDEN_SPEAR_ATTACK_MISS;
            case DIAMOND_SPEAR -> LevelSoundEventPacket.SOUND_DIAMOND_SPEAR_ATTACK_MISS;
            case NETHERITE_SPEAR -> LevelSoundEventPacket.SOUND_NETHERITE_SPEAR_ATTACK_MISS;
            default -> LevelSoundEventPacket.SOUND_SPEAR_ATTACK_MISS;
        };
    }

    public int attackInView(Player player, boolean kinetic) {
        Vector3 start = player.getEyePosition();
        Vector3 direction = player.getDirectionVector();
        double maximumReach = this.getMaximumReach(player.isCreative());
        Vector3 end = start.add(direction.multiply(maximumReach));
        int ticksUsed = kinetic ? player.getServer().getTick() - player.getStartActionTick() : 0;
        int hitCount = 0;

        for (Entity target : player.getLevel().getEntities()) {
            if (target == player || !target.isAlive()) {
                continue;
            }

            if (target instanceof Player targetPlayer
                    && (targetPlayer.isSpectator() || !player.getLevel().getGameRules().getBoolean(GameRule.PVP))) {
                continue;
            }

            AxisAlignedBB hitbox = target.boundingBox.grow(0.25, 0.25, 0.25);
            MovingObjectPosition collision = hitbox.calculateIntercept(start, end);
            if (collision == null) {
                continue;
            }

            double distance = start.distance(collision.hitVector);
            if (distance < this.getMinimumReach() || distance > maximumReach) {
                continue;
            }

            Vector3 playerVelocity = player.speed == null
                    ? player.getMotion().multiply(20)
                    : player.speed.multiply(-20);
            Vector3 targetVelocity = target.getMotion().multiply(20);
            double forwardSpeed = playerVelocity.dot(direction);
            double relativeSpeed = playerVelocity.subtract(targetVelocity).dot(direction);
            boolean chargedHit = kinetic && this.canDealChargeDamage(ticksUsed, relativeSpeed);
            if (kinetic && !chargedHit) {
                continue;
            }

            float damage = chargedHit ? this.getChargeDamage(relativeSpeed) : this.getAttackDamage(player);
            Enchantment[] enchantments = this.getEnchantments();
            for (Enchantment enchantment : enchantments) {
                damage += (float) enchantment.getDamageBonus(target, player);
            }

            Map<EntityDamageEvent.DamageModifier, Float> modifiers = new EnumMap<>(EntityDamageEvent.DamageModifier.class);
            modifiers.put(EntityDamageEvent.DamageModifier.BASE, damage);
            float knockBack = chargedHit && !this.canChargeKnockBack(ticksUsed, forwardSpeed) ? 0 : 0.3f;
            EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(
                    player, target, EntityDamageEvent.DamageCause.ENTITY_ATTACK, modifiers, knockBack, enchantments
            );
            event.setBreakShield(this.canBreakShield());

            if (!target.attack(event)) {
                continue;
            }

            if (chargedHit && this.canChargeDismount(ticksUsed, forwardSpeed) && target.getRiding() != null) {
                target.getRiding().dismountEntity(target);
            }

            player.getLevel().addLevelSoundEvent(target, this.getAttackHitSound());
            for (Enchantment enchantment : enchantments) {
                enchantment.doPostAttack(player, target);
            }

            if (!player.isCreative()) {
                this.useOn(target);
                player.getInventory().setItemInHand(this);
            }
            hitCount++;
        }

        return hitCount;
    }

    private int getUseSound() {
        return switch (this.getNamespaceId()) {
            case WOODEN_SPEAR -> LevelSoundEventPacket.SOUND_WOODEN_SPEAR_USE;
            case STONE_SPEAR -> LevelSoundEventPacket.SOUND_STONE_SPEAR_USE;
            case COPPER_SPEAR -> LevelSoundEventPacket.SOUND_COPPER_SPEAR_USE;
            case IRON_SPEAR -> LevelSoundEventPacket.SOUND_IRON_SPEAR_USE;
            case GOLDEN_SPEAR -> LevelSoundEventPacket.SOUND_GOLDEN_SPEAR_USE;
            case DIAMOND_SPEAR -> LevelSoundEventPacket.SOUND_DIAMOND_SPEAR_USE;
            case NETHERITE_SPEAR -> LevelSoundEventPacket.SOUND_NETHERITE_SPEAR_USE;
            default -> LevelSoundEventPacket.SOUND_SPEAR_USE;
        };
    }

    private SpearStats getStats() {
        return switch (this.getNamespaceId()) {
            case WOODEN_SPEAR -> new SpearStats(1, 60, ItemTool.TIER_WOODEN, 13, 15, 0.70, 300, 200, 100, 14.0);
            case STONE_SPEAR -> new SpearStats(2, 130, ItemTool.TIER_STONE, 15, 14, 0.82, 275, 180, 90, 13.0);
            case COPPER_SPEAR -> new SpearStats(2, 190, ItemTool.TIER_COPPER, 17, 13, 0.82, 250, 165, 80, 12.0);
            case IRON_SPEAR -> new SpearStats(3, 250, ItemTool.TIER_IRON, 19, 12, 0.95, 225, 135, 50, 11.0);
            case GOLDEN_SPEAR -> new SpearStats(1, 30, ItemTool.TIER_GOLD, 19, 14, 0.70, 275, 170, 70, 13.0);
            case DIAMOND_SPEAR -> new SpearStats(4, 1560, ItemTool.TIER_DIAMOND, 21, 10, 1.075, 200, 130, 60, 10.0);
            case NETHERITE_SPEAR -> new SpearStats(5, 2030, ItemTool.TIER_NETHERITE, 23, 8, 1.20, 175, 110, 50, 9.0);
            default -> throw new IllegalStateException("Unknown spear type: " + this.getNamespaceId());
        };
    }

    private record SpearStats(int damage, int durability, int tier, int jabCooldown, int chargeDelay,
                              double damageMultiplier, int damageDuration, int knockbackDuration,
                              int dismountDuration, double dismountSpeed) {
    }

    @Override
    public boolean isSupportedOn(int protocolId) {
        return protocolId >= ProtocolInfo.v1_21_130;
    }
}
