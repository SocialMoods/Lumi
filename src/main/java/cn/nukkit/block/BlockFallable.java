package cn.nukkit.block;

import cn.nukkit.entity.item.EntityFallingBlock;
import cn.nukkit.event.block.BlockFallEvent;
import cn.nukkit.level.Level;
import cn.nukkit.nbt.tag.*;

/**
 * @author rcsuperman
 * Nukkit Project
 */
public abstract class BlockFallable extends BlockSolidMeta {

    public BlockFallable() {
        super(0);
    }

    public BlockFallable(int meta) {
        super(meta);
    }

    @Override
    public int onUpdate(int type) {
        if (type != Level.BLOCK_UPDATE_NORMAL || !this.getLevel().randomTickingEnabled()) {
            return type;
        }

        Block down = this.down();
        if (down.getId() == AIR || down instanceof BlockLiquid || down instanceof BlockFire) {
            BlockFallEvent event = new BlockFallEvent(this);
            if (!event.call()) {
                return type;
            }

           drop(new CompoundTag());
        }
        return type;
    }

    public void drop(CompoundTag customNbt) {
        this.level.setBlock(this, Block.get(Block.AIR), true, true);
        EntityFallingBlock fall = createFallingEntity(customNbt);

        fall.spawnToAll();
    }

    protected EntityFallingBlock createFallingEntity(CompoundTag customNbt) {
        CompoundTag nbt = new CompoundTag()
                .putList(new ListTag<DoubleTag>("Pos")
                        .add(new DoubleTag("", this.x + 0.5))
                        .add(new DoubleTag("", this.y))
                        .add(new DoubleTag("", this.z + 0.5)))
                .putList(new ListTag<DoubleTag>("Motion")
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0))
                        .add(new DoubleTag("", 0)))

                .putList(new ListTag<FloatTag>("Rotation")
                        .add(new FloatTag("", 0))
                        .add(new FloatTag("", 0)))
                .putInt("TileID", this.getId())
                .putByte("Data", this.getDamage());

        for (Tag customTag : customNbt.getAllTags()) {
            nbt.put(customTag.getName(), customTag.copy());
        }
        return new EntityFallingBlock(this.getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt);
    }
}
