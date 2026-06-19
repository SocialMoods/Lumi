package cn.nukkit.block;

import cn.nukkit.block.data.BlockColor;

public class BlockSulfurSpike extends BlockPointedDripstone {

    public BlockSulfurSpike() {
        this(0);
    }

    public BlockSulfurSpike(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Sulfur Spike";
    }

    @Override
    public int getId() {
        return SULFUR_SPIKE;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.YELLOW_BLOCK_COLOR;
    }

    @Override
    public String getIdentifier() {
        return "minecraft:sulfur_spike";
    }
}
