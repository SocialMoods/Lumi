package cn.nukkit.entity.mob;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.entity.projectile.EntityArrow;
import cn.nukkit.entity.projectile.EntityProjectile;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityShootBowEvent;
import cn.nukkit.event.entity.ProjectileLaunchEvent;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemNamespaceId;
import cn.nukkit.level.Location;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.LevelSoundEventPacket;
import cn.nukkit.network.protocol.MobEquipmentPacket;
import cn.nukkit.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.FastMath;

import java.util.concurrent.ThreadLocalRandom;

public class EntityPiglin extends EntityWalkingMob {

    public final static int NETWORK_ID = 123;

    private int angry;
    private boolean angryFlagSet;
    private int admiringTicks = 0;
    private boolean isAdmiring = false;

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    public EntityPiglin(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getKillExperience() {
        return 5;
    }

    @Override
    protected void initEntity() {
        this.setMaxHealth(16);

        super.initEntity();
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.95f;
    }

    @Override
    public void attackEntity(Entity player) {
        if (!this.isAngry()) {
            return;
        }
        if (this.attackDelay > 80 && Utils.rand(1, 32) < 4 && this.distanceSquared(player) <= 100) {
            this.attackDelay = 0;

            double f = 1.5;
            double yaw = this.yaw;
            double pitch = this.pitch;
            double yawR = FastMath.toRadians(yaw);
            double pitchR = FastMath.toRadians(pitch);
            Location pos = new Location(this.x - Math.sin(yawR) * Math.cos(pitchR) * 0.5, this.y + this.getHeight() - 0.18,
                    this.z + Math.cos(yawR) * Math.cos(pitchR) * 0.5, yaw, pitch, this.level);

            if (this.getLevel().getBlockIdAt(pos.getFloorX(), pos.getFloorY(), pos.getFloorZ()) == Block.AIR) {
                Entity k = Entity.createEntity("Arrow", pos, this);
                if (!(k instanceof EntityArrow arrow)) {
                    return;
                }

                setProjectileMotion(arrow, pitch, yawR, pitchR, f);

                EntityShootBowEvent ev = new EntityShootBowEvent(this, Item.get(Item.ARROW, 0, 1), arrow, f);
                this.server.getPluginManager().callEvent(ev);

                EntityProjectile projectile = ev.getProjectile();
                if (ev.isCancelled()) {
                    projectile.close();
                } else {
                    ProjectileLaunchEvent launch = new ProjectileLaunchEvent(projectile);
                    this.server.getPluginManager().callEvent(launch);
                    if (launch.isCancelled()) {
                        projectile.close();
                    } else {
                        projectile.namedTag.putDouble("damage", 4);
                        projectile.spawnToAll();
                        ((EntityArrow) projectile).setPickupMode(EntityArrow.PICKUP_NONE);
                        this.level.addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_CROSSBOW_SHOOT);
                    }
                }
            }
        }
    }

    public boolean isAngry() {
        return this.angry > 0;
    }

    public void setAngry(int val) {
        this.angry = val;
        this.setDataFlag(DATA_FLAGS, DATA_FLAG_CHARGED, val > 0);
        this.angryFlagSet = val > 0;
    }

    private static boolean isWearingGold(Player p) {
        PlayerInventory i = p.getInventory();
        if (i == null) {
            return false;
        }
        return i.getHelmetFast().getId() == Item.GOLD_HELMET ||
                i.getChestplateFast().getId() == Item.GOLD_CHESTPLATE ||
                i.getLeggingsFast().getId() == Item.GOLD_LEGGINGS ||
                i.getBootsFast().getId() == Item.GOLD_BOOTS;
    }

    @Override
    public boolean attack(EntityDamageEvent ev) {
        super.attack(ev);

        if (!ev.isCancelled() && ev instanceof EntityDamageByEntityEvent) {
            if (((EntityDamageByEntityEvent) ev).getDamager() instanceof Player) {
                this.setAngry(600);
            }
        }

        return true;
    }

    @Override
    public boolean targetOption(EntityCreature creature, double distance) {
        if (this.isAdmiring) {
            return false;
        }

        if (distance <= 100 && this.isAngry() && creature instanceof EntityPiglin && !((EntityPiglin) creature).isAngry()) {
            ((EntityPiglin) creature).setAngry(600);
        }

        boolean hasTarget = false;

        if (creature instanceof Player player) {

            boolean hasGoldInHand = !player.getInventory().getItemInHand().isNull() &&
                    player.getInventory().getItemInHand().getId() == Item.GOLD_INGOT;

            if (hasGoldInHand && !this.isAngry()) {
                hasTarget = distance <= 8;
                if (hasTarget && !this.angryFlagSet) {
                    this.setDataFlag(DATA_FLAGS, DATA_FLAG_CHARGED, false);
                    this.angryFlagSet = false;
                }
            } else {
                hasTarget = (this.isAngry() || !isWearingGold(player)) &&
                        super.targetOption(creature, distance);

                if (hasTarget) {
                    if (!this.angryFlagSet) {
                        this.setDataFlag(DATA_FLAGS, DATA_FLAG_CHARGED, true);
                        this.angryFlagSet = true;
                    }
                } else {
                    if (this.angryFlagSet) {
                        this.setDataFlag(DATA_FLAGS, DATA_FLAG_CHARGED, false);
                        this.angryFlagSet = false;
                        this.stayTime = 100;
                    }
                }
            }
        }

        return hasTarget;
    }

    @Override
    public void spawnTo(Player player) {
        super.spawnTo(player);

        MobEquipmentPacket pk = new MobEquipmentPacket();
        pk.eid = this.getId();
        pk.item = Item.get(Item.CROSSBOW, 0, 1);
        pk.hotbarSlot = 0;
        player.dataPacket(pk);
    }

    @Override
    public int nearbyDistanceMultiplier() {
        return 20;
    }

    @Override
    public boolean entityBaseTick(int tickDiff) {
        if (this.angry > 0) {
            if (this.angry == 1) {
                this.setAngry(0);
            } else {
                this.angry--;
            }
        }

        if (this.isAdmiring) {
            this.admiringTicks++;

            this.pitch = 45f;

            if (this.admiringTicks % 40 == 0) {
                this.level.addSound(this, cn.nukkit.level.Sound.MOB_PIGLIN_ADMIRING_ITEM);
            }

            if (this.admiringTicks >= 160) {
                this.completeTrade();
            }

            super.updateMovement();
            return true;
        }

        return super.entityBaseTick(tickDiff);
    }

    public void startTrading() {
        if (this.isAngry() || this.isAdmiring) {
            return;
        }

        this.isAdmiring = true;
        this.admiringTicks = 0;
        this.setDataFlag(DATA_FLAGS, DATA_FLAG_ADMIRING, true);

        this.stayTime = 200;
        this.setImmobile(true);

        this.pitch = 45f;

        this.sendOffhandItemToViewers(Item.get(Item.GOLD_INGOT));

        this.level.addSound(this, cn.nukkit.level.Sound.MOB_PIGLIN_ADMIRING_ITEM);
    }

    private void completeTrade() {
        this.isAdmiring = false;
        this.admiringTicks = 0;
        this.setDataFlag(DATA_FLAGS, DATA_FLAG_ADMIRING, false);

        this.pitch = 0f;

        this.sendOffhandItemToViewers(Item.get(Item.AIR));

        Item drop = getTradeItem();
        if (drop != null) {
            this.level.dropItem(this.add(0, 1.3, 0), drop);
        }

        this.setImmobile(false);

        this.level.addSound(this, cn.nukkit.level.Sound.MOB_PIGLIN_CELEBRATE);
    }

    private void sendOffhandItemToViewers(Item item) {
        MobEquipmentPacket pk = new MobEquipmentPacket();
        pk.eid = this.getId();
        pk.item = item;
        pk.inventorySlot = 1;
        pk.hotbarSlot = 1;
        pk.windowId = 0;

        for (Player player : this.getViewers().values()) {
            player.dataPacket(pk);
        }
    }

    private Item getTradeItem() {
        int random = ThreadLocalRandom.current().nextInt(459);

        if (random < 5) {
            return Item.get(ItemNamespaceId.ENCHANTED_BOOK);
        } else if (random < 13) {
            return Item.get(ItemNamespaceId.IRON_BOOTS);
        } else if (random < 21) {
            return Item.get(ItemNamespaceId.SPLASH_POTION, 12);
        } else if (random < 29) {
            return Item.get(ItemNamespaceId.POTION, 12);
        } else if (random < 39) {
            return Item.get(ItemNamespaceId.POTION);
        } else if (random < 49) {
            return Item.get(ItemNamespaceId.IRON_NUGGET, 0, ThreadLocalRandom.current().nextInt(10, 37));
        } else if (random < 59) {
            return Item.get(ItemNamespaceId.ENDER_PEARL, 0, ThreadLocalRandom.current().nextInt(2, 5));
        } else if (random < 79) {
            return Item.get(ItemNamespaceId.STRING, 0, ThreadLocalRandom.current().nextInt(3, 10));
        } else if (random < 99) {
            return Item.get(ItemNamespaceId.QUARTZ, 0, ThreadLocalRandom.current().nextInt(5, 13));
        } else if (random < 139) {
            return Item.get(Block.OBSIDIAN);
        } else if (random < 179) {
            return Item.get(Block.CRYING_OBSIDIAN, 0, ThreadLocalRandom.current().nextInt(1, 4));
        } else if (random < 219) {
            return Item.get(ItemNamespaceId.FIRE_CHARGE);
        } else if (random < 259) {
            return Item.get(ItemNamespaceId.LEATHER, 0, ThreadLocalRandom.current().nextInt(2, 5));
        } else if (random < 299) {
            return Item.get(ItemNamespaceId.SOUL_SAND, 0, ThreadLocalRandom.current().nextInt(2, 9));
        } else if (random < 339) {
            return Item.get(ItemNamespaceId.NETHER_BRICK, 0, ThreadLocalRandom.current().nextInt(2, 9));
        } else if (random < 379) {
            return Item.get(ItemNamespaceId.ARROW, 0, ThreadLocalRandom.current().nextInt(6, 12));
        } else if (random < 419) {
            return Item.get(Block.GRAVEL, 0, ThreadLocalRandom.current().nextInt(8, 17));
        } else {
            return Item.get(Block.BLACKSTONE, 0, ThreadLocalRandom.current().nextInt(8, 17));
        }
    }

    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        if (this.isAngry() || this.isAdmiring) {
            return false;
        }

        if (item.getId() == Item.GOLD_INGOT) {
            if (player.getGamemode() != Player.CREATIVE) {
                player.getInventory().decreaseCount(player.getInventory().getHeldItemIndex());
            }

            this.lookAt(player);
            this.startTrading();
            return true;
        }

        return super.onInteract(player, item, clickedPos);
    }
}