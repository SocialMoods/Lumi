package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.block.customblock.properties.BlockProperties;
import cn.nukkit.block.properties.BlockPropertiesHelper;
import cn.nukkit.block.properties.VanillaProperties;
import cn.nukkit.block.properties.enums.CrackedState;
import cn.nukkit.block.properties.enums.TurtleEggCount;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityLiving;
import cn.nukkit.entity.mob.EntityGhast;
import cn.nukkit.entity.mob.EntityPhantom;
import cn.nukkit.entity.mob.EntitySilverfish;
import cn.nukkit.entity.passive.EntityBat;
import cn.nukkit.entity.passive.EntityChicken;
import cn.nukkit.event.Event;
import cn.nukkit.event.block.BlockGrowEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.TurtleEggHatchEvent;
import cn.nukkit.event.entity.EntityInteractEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.enchantment.EnchantmentID;
import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.level.particle.BoneMealParticle;
import cn.nukkit.math.BlockFace;
import cn.nukkit.network.protocol.LevelSoundEventPacket;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class BlockTurtleEgg extends BlockTransparentMeta implements BlockPropertiesHelper {
    private static final BlockProperties PROPERTIES = new BlockProperties(VanillaProperties.TURTLE_EGG_COUNT, VanillaProperties.CRACKED_STATE);

    public BlockTurtleEgg() {
        this(0);
    }

    public BlockTurtleEgg(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Turtle Egg";
    }

    @Override
    public int getId() {
        return TURTLE_EGG;
    }

    @Override
    public BlockProperties getBlockProperties() {
        return PROPERTIES;
    }

    @Override
    public double getResistance() {
        return 0.5;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public boolean canSilkTouch() {
        return true;
    }

    @Override
    public Item[] getDrops(Item item) {
        return Item.EMPTY_ARRAY;
    }

    @Override
    public boolean hasEntityCollision() {
        return true;
    }

    @Override
    public double getMinX() {
        return this.x + 0.2;
    }

    @Override
    public double getMinZ() {
        return this.z + 0.2;
    }

    @Override
    public double getMaxX() {
        return this.x + 0.8;
    }

    @Override
    public double getMaxY() {
        return this.y + 0.46;
    }

    @Override
    public double getMaxZ() {
        return this.z + 0.8;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(@NotNull Item item, Player player) {
        if (item.getBlockUnsafe() != null && item.getBlockId() == TURTLE_EGG && (player == null || !player.isSneaking())) {
            TurtleEggCount eggCount = getEggCount();
            if (eggCount == TurtleEggCount.FOUR_EGG) {
                return false;
            }
            BlockTurtleEgg newState = new BlockTurtleEgg();
            newState.setEggCount(eggCount.next());
            BlockPlaceEvent placeEvent = new BlockPlaceEvent(
                    player,
                    newState,
                    this,
                    down(),
                    item
            );
            if (placeEvent.isCancelled()) {
                return false;
            }
            if (!this.level.setBlock(this, placeEvent.getBlock(), true, true)) {
                return false;
            }
            item.setCount(item.getCount() - 1);

            if (down().getId() == SAND) {
                this.level.addParticle(new BoneMealParticle(this));
            }

            return true;
        }

        return false;
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_RANDOM) {
            if (down().getId() == BlockID.SAND) {
                float celestialAngle = level.calculateCelestialAngle(level.getTime(), 1);
                ThreadLocalRandom random = ThreadLocalRandom.current();
                if (0.70 > celestialAngle && celestialAngle > 0.65 || random.nextInt(500) == 0) {
                    CrackedState crackState = getCracks();
                    if (crackState != CrackedState.MAX_CRACKED) {
                        BlockTurtleEgg newState = (BlockTurtleEgg) this.clone();
                        newState.setCracks(crackState.next());
                        BlockGrowEvent event = new BlockGrowEvent(this, newState);
                        this.level.getServer().getPluginManager().callEvent(event);
                        if (!event.isCancelled()) {
                            level.addSound(this, Sound.BLOCK_TURTLE_EGG_CRACK, 0.7f, 0.9f + random.nextFloat() * 0.2f);
                            this.level.setBlock(this, event.getNewState(), true, true);
                        }
                    } else {
                        hatch();
                    }
                }
            }
            return type;
        }
        return 0;
    }

    public void hatch() {
        hatch(getEggCount());
    }

    public void hatch(TurtleEggCount eggs) {
        hatch(eggs, new BlockAir());
    }

    public void hatch(TurtleEggCount eggs, Block newState) {
        TurtleEggHatchEvent turtleEggHatchEvent = new TurtleEggHatchEvent(this, eggs.ordinal() + 1, newState);
        turtleEggHatchEvent.setRecalculateOnFailure(false);
        if (turtleEggHatchEvent.call()) {
            int eggsHatching = turtleEggHatchEvent.getEggsHatching();
            level.addSound(this, Sound.BLOCK_TURTLE_EGG_CRACK);

            boolean hasFailure = false;
            for (int i = 0; i < eggsHatching; i++) {

                this.level.addSound(this, Sound.BLOCK_TURTLE_EGG_CRACK);

                Entity turtle = Entity.createEntity("Turtle", this.add(0.5, 0, 0.5));
                turtle.spawnToAll();

                if (turtleEggHatchEvent.isRecalculateOnFailure()) {
                    turtleEggHatchEvent.setEggsHatching(turtleEggHatchEvent.getEggsHatching() - 1);
                    hasFailure = true;
                }
            }

            if (hasFailure) {
                turtleEggHatchEvent.recalculateNewState();
            }

            this.level.setBlock(this, turtleEggHatchEvent.getNewState(), true, true);
        }
    }

    @Override
    public void onEntityCollide(Entity entity) {
        if (entity instanceof EntityLiving
                && !(entity instanceof EntityChicken)
                && !(entity instanceof EntityBat)
                && !(entity instanceof EntityGhast)
                && !(entity instanceof EntityPhantom)
                && entity.getY() >= this.getMaxY() - 0.01) {
            Event ev;

            if (entity instanceof Player) {
                ev = new PlayerInteractEvent((Player) entity, null, this, null, PlayerInteractEvent.Action.PHYSICAL);
            } else {
                ev = new EntityInteractEvent(entity, this);
            }

            boolean cracked = false;

            if (entity.fallDistance > 0) {
                System.out.println(entity.fallDistance);
                cracked = ThreadLocalRandom.current().nextInt(3) != 0;
            } else {
                cracked = ThreadLocalRandom.current().nextInt(100) > 0;
            }

            ev.setCancelled(cracked);
            if (ev.call()) {
                this.level.useBreakOn(this, null, null, true);
            }
        }
    }

    @Override
    public Item toItem() {
        return new ItemBlock(this, 0);
    }

    @Override
    public boolean onBreak(Item item) {
        TurtleEggCount eggCount = getEggCount();
        if (item.getEnchantment(EnchantmentID.ID_SILK_TOUCH) == null) {
            this.level.addSound(this, Sound.BLOCK_TURTLE_EGG_CRACK);
        }
        if (eggCount == TurtleEggCount.ONE_EGG) {
            return super.onBreak(item);
        } else {
            setEggCount(eggCount.before());
            return this.level.setBlock(this, this, true, true);
        }
    }

    @Override
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, Player player) {
        if (!isValidSupport(block.down(1, 0))) {
            return false;
        }

        if (this.level.setBlock(this, this, true, true)) {
            if (down().getId() == BlockID.SAND) {
                this.level.addParticle(new BoneMealParticle(this));
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidSupport(Block support) {
        return support.isSolid(BlockFace.UP) || support instanceof BlockWall;
    }

    @Override
    public WaterloggingType getWaterloggingType() {
        return WaterloggingType.WHEN_PLACED_IN_WATER;
    }

    public CrackedState getCracks() {
        return getPropertyValue(VanillaProperties.CRACKED_STATE);
    }

    public void setCracks(CrackedState cracks) {
        setPropertyValue(VanillaProperties.CRACKED_STATE, cracks);
    }

    public TurtleEggCount getEggCount() {
        return getPropertyValue(VanillaProperties.TURTLE_EGG_COUNT);
    }

    public void setEggCount(TurtleEggCount eggCount) {
        setPropertyValue(VanillaProperties.TURTLE_EGG_COUNT, eggCount);
    }
}
