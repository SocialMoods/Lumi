package cn.nukkit.entity.projectile;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.LongEntityData;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.entity.EntityDamageByChildEntityEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.event.entity.ProjectileHitEvent;
import cn.nukkit.event.weather.LightningStrikeEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.MovingObjectPosition;
import cn.nukkit.level.Sound;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.*;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.IntTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.LevelSoundEventPacket;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

/**
 * Created by PetteriM1
 */
public class EntityThrownTrident extends EntitySlenderProjectile {

    public static final int NETWORK_ID = 73;
    private static final String TAG_PICKUP = "pickup";
    private static final String TAG_TRIDENT = "Trident";
    private static final String TAG_FAVORED_SLOT = "favoredSlot";
    private static final String TAG_CREATIVE = "isCreative";
    private static final String TAG_PLAYER = "player";
    private static final String NAME_TRIDENT = "Trident";
    private static final Vector3 defaultCollisionPos = new Vector3(0, 0, 0);
    private static final BlockVector3 defaultStuckToBlockPos = new BlockVector3(0, 0, 0);
    public boolean alreadyCollided;
    protected Item trident;
    // Default Values
    protected float gravity = 0.04f;
    protected float drag = 0.01f;
    protected int pickupMode;
    private Vector3 collisionPos;
    private BlockVector3 stuckToBlockPos;
    private int favoredSlot;
    private boolean player;
    // Enchantment
    private int loyaltyLevel;
    private boolean hasChanneling;
    private int riptideLevel;
    private int impalingLevel;

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.05f;
    }

    @Override
    public float getLength() {
        return 0.25f;
    }

    @Override
    public float getHeight() {
        return 0.05f;
    }

    @Override
    public float getGravity() {
        return 0.05f;
    }

    @Override
    public float getDrag() {
        return 0.01f;
    }

    public EntityThrownTrident(FullChunk chunk, CompoundTag nbt) {
        this(chunk, nbt, null);
    }

    public EntityThrownTrident(FullChunk chunk, CompoundTag nbt, Entity shootingEntity) {
        super(chunk, nbt, shootingEntity);
    }

    @Override
    protected void initEntity() {
        super.initEntity();

        this.pickupMode = namedTag.contains(TAG_PICKUP) ? namedTag.getByte(TAG_PICKUP) : PICKUP_ANY;
        this.favoredSlot = namedTag.contains(TAG_FAVORED_SLOT) ? namedTag.getInt(TAG_FAVORED_SLOT) : -1;
        this.player = !namedTag.contains(TAG_PLAYER) || namedTag.getBoolean(TAG_PLAYER);

        if (namedTag.contains(TAG_CREATIVE)) {
            if (pickupMode == PICKUP_ANY && namedTag.getBoolean(TAG_CREATIVE)) {
                pickupMode = PICKUP_CREATIVE;
            }
            namedTag.remove(TAG_CREATIVE);
        }

        if (namedTag.contains(TAG_TRIDENT)) {
            this.trident = NBTIO.getItemHelper(namedTag.getCompound(TAG_TRIDENT));
            this.loyaltyLevel = this.trident.getEnchantmentLevel(Enchantment.ID_TRIDENT_LOYALTY);
            this.hasChanneling = this.trident.hasEnchantment(Enchantment.ID_TRIDENT_CHANNELING);
            this.riptideLevel = this.trident.getEnchantmentLevel(Enchantment.ID_TRIDENT_RIPTIDE);
            this.impalingLevel = this.trident.getEnchantmentLevel(Enchantment.ID_TRIDENT_IMPALING);
        } else {
            this.trident = Item.get(0);
            this.loyaltyLevel = 0;
            this.hasChanneling = false;
            this.riptideLevel = 0;
            this.impalingLevel = 0;
        }

        if (namedTag.contains("CollisionPos")) {
            ListTag<DoubleTag> collisionPosList = this.namedTag.getList("CollisionPos", DoubleTag.class);
            collisionPos = new Vector3(collisionPosList.get(0).data, collisionPosList.get(1).data, collisionPosList.get(2).data);
        } else {
            collisionPos = defaultCollisionPos.clone();
        }

        if (namedTag.contains("StuckToBlockPos")) {
            ListTag<IntTag> stuckToBlockPosList = this.namedTag.getList("StuckToBlockPos", IntTag.class);
            stuckToBlockPos = new BlockVector3(stuckToBlockPosList.get(0).data, stuckToBlockPosList.get(1).data, stuckToBlockPosList.get(2).data);
        } else {
            stuckToBlockPos = defaultStuckToBlockPos.clone();
        }
    }

    @Override
    public void saveNBT() {
        super.saveNBT();

        this.namedTag.put(TAG_TRIDENT, NBTIO.putItemHelper(this.trident, true));
        this.namedTag.putByte(TAG_PICKUP, this.pickupMode);
        this.namedTag.putList(new ListTag<DoubleTag>("CollisionPos")
                .add(new DoubleTag("0", this.collisionPos.x))
                .add(new DoubleTag("1", this.collisionPos.y))
                .add(new DoubleTag("2", this.collisionPos.z))
        );
        this.namedTag.putList(new ListTag<IntTag>("StuckToBlockPos")
                .add(new IntTag("0", this.stuckToBlockPos.x))
                .add(new IntTag("1", this.stuckToBlockPos.y))
                .add(new IntTag("2", this.stuckToBlockPos.z))
        );
        this.namedTag.putInt(TAG_FAVORED_SLOT, this.favoredSlot);
        this.namedTag.putBoolean(TAG_PLAYER, this.player);
    }

    public Item getItem() {
        return this.trident != null ? this.trident.clone() : Item.get(0);
    }

    public void setItem(Item item) {
        this.trident = item.clone();
        this.loyaltyLevel = this.trident.getEnchantmentLevel(Enchantment.ID_TRIDENT_LOYALTY);
        this.hasChanneling = this.trident.hasEnchantment(Enchantment.ID_TRIDENT_CHANNELING);
        this.riptideLevel = this.trident.getEnchantmentLevel(Enchantment.ID_TRIDENT_RIPTIDE);
        this.impalingLevel = this.trident.getEnchantmentLevel(Enchantment.ID_TRIDENT_IMPALING);
    }

    public void setCritical() {
        this.setCritical(true);
    }

    public boolean isCritical() {
        return this.getDataFlag(DATA_FLAGS, DATA_FLAG_CRITICAL);
    }

    public void setCritical(boolean value) {
        this.setDataFlag(DATA_FLAGS, DATA_FLAG_CRITICAL, value);
    }

    @Override
    public int getResultDamage() {
        int base = super.getResultDamage();

        if (this.isCritical()) {
            base += ThreadLocalRandom.current().nextInt(base / 2 + 2);
        }

        return base;
    }

    @Override
    protected double getBaseDamage() {
        return 8;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (this.closed) {
            return false;
        }

        if (this.age > 1200 && this.pickupMode < 1) {
            this.close();
            return false;
        }

        if (this.isCollided && !this.hadCollision) {
            this.getLevel().addSound(this, Sound.ITEM_TRIDENT_HIT_GROUND);
        }

        boolean hasUpdate = super.onUpdate(currentTick);

        if (this.onGround || this.hadCollision) {
            this.setCritical(false);
        }

        if (this.noClip) {
            if (this.canReturnToShooter()) {
                Entity shooter = this.shootingEntity;
                double force = 0.05d * (double) loyaltyLevel;
                Vector3 vector3 = new Vector3(shooter.x - this.x, shooter.y + shooter.getEyeHeight() - this.y, shooter.z - this.z);
                this.setPosition(new Vector3(this.x + vector3.x * force, this.y + vector3.y * force, this.z + vector3.z * force));
                this.setMotion(vector3.multiply(force));
                hasUpdate = true;
            } else {
                if (!this.closed && level.getGameRules().getBoolean(GameRule.DO_ENTITY_DROPS)) {
                    this.level.dropItem(this, this.trident);
                }
                this.close();
            }
        }

        return hasUpdate;
    }

    @Override
    public void onCollideWithEntity(Entity entity) {
        if (this.noClip) {
            return;
        }

        if (this.alreadyCollided) {
            this.move(this.motionX, this.motionY, this.motionZ);
            return;
        }

        ProjectileHitEvent hitEvent = new ProjectileHitEvent(this, MovingObjectPosition.fromEntity(entity));
        this.server.getPluginManager().callEvent(hitEvent);
        if (hitEvent.isCancelled()) {
            return;
        }
        float damage = this.getResultDamage();
        if (this.impalingLevel > 0 && (entity.isInsideOfWater() || (entity.getLevel().isRaining() && entity.getLevel().canBlockSeeSky(entity)))) {
            damage = damage + (2.5f * (float) this.impalingLevel);
        }

        EntityDamageEvent ev;
        if (this.shootingEntity == null) {
            ev = new EntityDamageByEntityEvent(this, entity, DamageCause.PROJECTILE, damage);
        } else {
            ev = new EntityDamageByChildEntityEvent(this.shootingEntity, this, entity, DamageCause.PROJECTILE, damage);
        }
        entity.attack(ev);
        this.hadCollision = true;
        this.onHit();
        this.setCollisionPos(this);
        this.setMotion(new Vector3(this.getMotion().getX() * -0.01, this.getMotion().getY() * -0.1, this.getMotion().getZ() * -0.01));

        if (trident != null && level.isThundering() && this.hasChanneling && this.canSeeSky()) {
            EntityLightning bolt = new EntityLightning(this.getChunk(), getDefaultNBT(this));
            LightningStrikeEvent strikeEvent = new LightningStrikeEvent(level, bolt);
            server.getPluginManager().callEvent(strikeEvent);
            if (!strikeEvent.isCancelled()) {
                bolt.spawnToAll();
                level.addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_ITEM_TRIDENT_THUNDER);
            } else {
                bolt.setEffect(false);
            }
        }

        if (this.canReturnToShooter()) {
            this.getLevel().addSound(this, Sound.ITEM_TRIDENT_RETURN);
            this.setNoClip(true);
            this.hadCollision = false;
            this.setTridentRope(true);
        }
    }

    @Override
    public boolean move(double dx, double dy, double dz) {
        if (dx == 0 && dz == 0 && dy == 0) {
            return true;
        }

        this.ySize *= 0.4;

        double movX = dx;
        double movY = dy;
        double movZ = dz;

        final EntitySlenderProjectile projectile = this;
        final Entity shootEntity = shootingEntity;
        final int ticks = ticksLived;

        AxisAlignedBB currentAABB = this.boundingBox.clone();
        var dirVector = new Vector3(dx, dy, dz).multiply(1 / (double) SPLIT_NUMBER);

        Entity collisionEntity = null;
        Block collisionBlock = null;
        for (int i = 0; i < SPLIT_NUMBER; ++i) {
            Block[] collisionBlocks = this.level.getCollisionBlocks(currentAABB.offset(dirVector.x, dirVector.y, dirVector.z));
            List<Block> filteredBlocks;
            if (this.canPassThroughBarrier()) {
                filteredBlocks = Arrays.stream(collisionBlocks).filter(block -> block.getId() != BlockID.BARRIER).toList();
            } else {
                filteredBlocks = Arrays.asList(collisionBlocks);
            }
            Entity[] collisionEntities = this.getLevel().getCollidingEntities(currentAABB, this);
            if (filteredBlocks.size() != 0) {
                currentAABB.offset(-dirVector.x, -dirVector.y, -dirVector.z);
                collisionBlock = filteredBlocks.stream().min(Comparator.comparingDouble(projectile::distanceSquared)).get();
                break;
            }
            collisionEntity = Arrays.stream(collisionEntities)
                    .filter(Predicate.not(entity -> (entity == shootEntity && ticks < 5) ||
                            (entity instanceof Player && ((Player) entity).getGamemode() == Player.SPECTATOR)))
                    .min(Comparator.comparingDouble(o -> o.distanceSquared(projectile)))
                    .orElse(null);
            if (collisionEntity != null) {
                break;
            }
        }
        Vector3 centerPoint1 = new Vector3((currentAABB.getMinX() + currentAABB.getMaxX()) / 2,
                (currentAABB.getMinY() + currentAABB.getMaxY()) / 2,
                (currentAABB.getMinZ() + currentAABB.getMaxZ()) / 2);
        //collide with entity
        if (collisionEntity != null) {
            MovingObjectPosition movingObject = new MovingObjectPosition();
            movingObject.typeOfHit = 1;
            movingObject.entityHit = collisionEntity;
            movingObject.hitVector = centerPoint1;
            onCollideWithEntity(movingObject.entityHit);
            return true;
        }

        Vector3 centerPoint2 = new Vector3((this.boundingBox.getMinX() + this.boundingBox.getMaxX()) / 2,
                (this.boundingBox.getMinY() + this.boundingBox.getMaxY()) / 2,
                (this.boundingBox.getMinZ() + this.boundingBox.getMaxZ()) / 2);
        Vector3 diff = centerPoint1.subtract(centerPoint2);
        if (dy > 0) {
            if (diff.getY() + 0.001 < dy) {
                dy = diff.getY();
            }
        }
        if (dy < 0) {
            if (diff.getY() - 0.001 > dy) {
                dy = diff.getY();
            }
        }
        if (dx > 0) {
            if (diff.getX() + 0.001 < dx) {
                dx = diff.getX();
            }
        }
        if (dx < 0) {
            if (diff.getX() - 0.001 > dx) {
                dx = diff.getX();
            }
        }
        if (dz > 0) {
            if (diff.getZ() + 0.001 < dz) {
                dz = diff.getZ();
            }
        }
        if (dz < 0) {
            if (diff.getZ() - 0.001 > dz) {
                dz = diff.getZ();
            }
        }
        this.boundingBox.offset(0, dy, 0);
        this.boundingBox.offset(dx, 0, 0);
        this.boundingBox.offset(0, 0, dz);
        this.x = (this.boundingBox.getMinX() + this.boundingBox.getMaxX()) / 2;
        this.y = this.boundingBox.getMinY() - this.ySize;
        this.z = (this.boundingBox.getMinZ() + this.boundingBox.getMaxZ()) / 2;

        this.checkChunks();

        this.checkGroundState(movX, movY, movZ, dx, dy, dz);
        this.updateFallState(this.onGround);

        if (movX != dx) {
            this.motionX = 0;
        }
        if (movY != dy) {
            this.motionY = 0;
        }
        if (movZ != dz) {
            this.motionZ = 0;
        }

        //collide with block
        if (this.isCollided && !this.hadCollision) {
            this.hadCollision = true;
            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
            BVector3 bVector3 = BVector3.fromPos(new Vector3(dx, dy, dz));
            BlockFace blockFace = BlockFace.fromHorizontalAngle(bVector3.getYaw());
            Block block = level.getBlock(this.getFloorX(), this.getFloorY(), this.getFloorZ()).getSide(blockFace);
            if (block.getId() == 0) {
                blockFace = BlockFace.DOWN;
                block = level.getBlock(this.getFloorX(), this.getFloorY(), this.getFloorZ()).down();
            }
            if (block.getId() == 0) {
                blockFace = BlockFace.UP;
                block = level.getBlock(this.getFloorX(), this.getFloorY(), this.getFloorZ()).up();
            }
            if (block.getId() == 0 && collisionBlock != null) {
                block = collisionBlock;
            }
            ProjectileHitEvent hitEvent = new ProjectileHitEvent(this, MovingObjectPosition.fromBlock(block.getFloorX(), block.getFloorY(), block.getFloorZ(), blockFace, this));
            this.server.getPluginManager().callEvent(hitEvent);
            if (!hitEvent.isCancelled()) {
                this.onHit();
                this.onHitGround(getPosition().add(dirVector.x, dirVector.y, dirVector.z));
            }
        }

        return true;
    }

    @Override
    public void onHit() {
        this.getLevel().addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_ITEM_TRIDENT_HIT);
    }

    @Override
    public void onHitGround(Vector3 vector3) {
        if (this.noClip) {
            return;
        }
        super.onHitGround(vector3);
        this.setStuckToBlockPos(new BlockVector3(vector3.getFloorX(), vector3.getFloorY(), vector3.getFloorZ()));
        if (this.canReturnToShooter()) {
            this.getLevel().addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_ITEM_TRIDENT_RETURN);
            this.noClip = true;
            this.setTridentRope(true);
        }
    }

    public int getPickupMode() {
        return this.pickupMode;
    }

    public void setPickupMode(int pickupMode) {
        this.pickupMode = pickupMode;
    }

    public Vector3 getCollisionPos() {
        return collisionPos;
    }

    public void setCollisionPos(Vector3 collisionPos) {
        this.collisionPos = collisionPos;
    }

    public BlockVector3 getStuckToBlockPos() {
        return stuckToBlockPos;
    }

    public void setStuckToBlockPos(BlockVector3 stuckToBlockPos) {
        this.stuckToBlockPos = stuckToBlockPos;
    }

    public int getFavoredSlot() {
        return favoredSlot;
    }

    public void setFavoredSlot(int favoredSlot) {
        this.favoredSlot = favoredSlot;
    }

    public boolean isCreative() {
        return getPickupMode() == EntityProjectile.PICKUP_CREATIVE;
    }

    public boolean isPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }

    public int getLoyaltyLevel() {
        return loyaltyLevel;
    }

    public void setLoyaltyLevel(int loyaltyLevel) {
        this.loyaltyLevel = loyaltyLevel;
        if (loyaltyLevel > 0) {
            this.trident.addEnchantment(Enchantment.getEnchantment(Enchantment.ID_TRIDENT_LOYALTY).setLevel(loyaltyLevel));
        } else {
            // TODO: this.trident.removeEnchantment(Enchantment.ID_TRIDENT_LOYALTY);
        }
    }

    public boolean hasChanneling() {
        return hasChanneling;
    }

    public void setChanneling(boolean hasChanneling) {
        this.hasChanneling = hasChanneling;
        if (hasChanneling) {
            this.trident.addEnchantment(Enchantment.getEnchantment(Enchantment.ID_TRIDENT_CHANNELING));
        } else {
            // TODO: this.trident.removeEnchantment(Enchantment.ID_TRIDENT_CHANNELING);
        }
    }

    public int getRiptideLevel() {
        return riptideLevel;
    }

    public void setRiptideLevel(int riptideLevel) {
        this.riptideLevel = riptideLevel;
        if (riptideLevel > 0) {
            this.trident.addEnchantment(Enchantment.getEnchantment(Enchantment.ID_TRIDENT_RIPTIDE).setLevel(riptideLevel));
        } else {
            // TODO: this.trident.removeEnchantment(Enchantment.ID_TRIDENT_RIPTIDE);
        }
    }

    public int getImpalingLevel() {
        return impalingLevel;
    }

    public void setImpalingLevel(int impalingLevel) {
        this.impalingLevel = impalingLevel;
        if (impalingLevel > 0) {
            this.trident.addEnchantment(Enchantment.getEnchantment(Enchantment.ID_TRIDENT_IMPALING).setLevel(impalingLevel));
        } else {
            // TODO: this.trident.removeEnchantment(Enchantment.ID_TRIDENT_IMPALING);
        }
    }

    public boolean getTridentRope() {
        return this.getDataFlag(DATA_FLAGS, DATA_FLAG_SHOW_TRIDENT_ROPE);
    }

    public void setRope(boolean tridentRope) { //兼容PM1E
        this.setTridentRope(tridentRope);
    }

    public void setTridentRope(boolean tridentRope) {
        if (tridentRope) {
            this.setDataProperty(new LongEntityData(DATA_OWNER_EID, this.shootingEntity.getId()));
        } else {
            this.setDataProperty(new LongEntityData(DATA_OWNER_EID, -1));
        }
        this.setDataFlag(DATA_FLAGS, DATA_FLAG_SHOW_TRIDENT_ROPE, tridentRope);
    }

    public boolean canReturnToShooter() {
        if (this.loyaltyLevel <= 0) {
            return false;
        }

        if (this.getCollisionPos().equals(defaultCollisionPos) && this.getStuckToBlockPos().equals(defaultStuckToBlockPos)) {
            return false;
        }

        Entity shooter = this.shootingEntity;
        if (shooter != null) {
            if (shooter.isAlive() && shooter instanceof Player) {
                return !(((Player) shooter).isSpectator());
            }
        }
        return false;
    }
}
