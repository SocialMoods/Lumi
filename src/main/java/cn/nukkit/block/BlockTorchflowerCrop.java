package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.block.BlockGrowEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBoneMeal;
import cn.nukkit.item.ItemNamespaceId;
import cn.nukkit.level.Level;
import cn.nukkit.level.particle.BoneMealParticle;
import cn.nukkit.math.BlockFace;
import cn.nukkit.utils.Utils;

public class BlockTorchflowerCrop extends BlockCrops {

    public BlockTorchflowerCrop(int meta) {
        super(meta);
    }

    public BlockTorchflowerCrop() {
        this(0);
    }

    @Override
    public String getName() {
        return "Torchflower Crop";
    }

    @Override
    public int getId() {
        return TORCHFLOWER_CROP;
    }

    @Override
    public String getIdentifier() {
        return "minecraft:torchflower_crop";
    }

    @Override
    public boolean onActivate(Item item, Player player) {
        if (!(item instanceof ItemBoneMeal)) {
            return false;
        }
        
        Block block;
        if (this.getPropertyValue(GROWTH) < 2) {
            BlockCrops clone = (BlockCrops) this.clone();
            int newGrowth = clone.getPropertyValue(GROWTH) + Utils.random.nextInt(3) + 2;
            clone.setPropertyValue(GROWTH, Math.min(newGrowth, GROWTH.getMaxValue()));
            block = clone;
        } else {
            block = Block.get(BlockID.TORCHFLOWER);
        }
        
        BlockGrowEvent ev = new BlockGrowEvent(this, block);
        if (!ev.call()) {
            return false;
        }

        this.getLevel().setBlock(this, ev.getNewState(), false, true);
        this.level.addParticle(new BoneMealParticle(this));

        if (player != null && !player.isCreative()) {
            item.count--;
        }

        return true;
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (this.down().getId() != FARMLAND) {
                this.getLevel().useBreakOn(this, null, null, true);
                return Level.BLOCK_UPDATE_NORMAL;
            }
        } else if (type == Level.BLOCK_UPDATE_RANDOM) {
            if (Utils.random.nextInt(2) == 1) {
                Block block;
                if (this.getPropertyValue(GROWTH) < 2) {
                    BlockCrops clone = (BlockCrops) this.clone();
                    int newGrowth = clone.getPropertyValue(GROWTH) + Utils.random.nextInt(3) + 2;
                    clone.setPropertyValue(GROWTH, Math.min(newGrowth, GROWTH.getMaxValue()));
                    block = clone;
                } else {
                    block = Block.get(BlockID.TORCHFLOWER);
                }

                BlockGrowEvent ev = new BlockGrowEvent(this, block);

                if (ev.call()) {
                    this.getLevel().setBlock(this, ev.getNewState(), false, true);
                } else {
                    return Level.BLOCK_UPDATE_RANDOM;
                }
            } else {
                return Level.BLOCK_UPDATE_RANDOM;
            }
        }

        return 0;
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{
                Item.get(ItemNamespaceId.TORCHFLOWER_SEEDS)
        };
    }
}
