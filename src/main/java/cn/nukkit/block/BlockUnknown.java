package cn.nukkit.block;

import cn.nukkit.item.ItemTool;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class BlockUnknown extends BlockMeta {

    private final int id;

    public BlockUnknown(int id) {
        this(id, 0);
    }

    public BlockUnknown(int id, Integer meta) {
        super(meta);
        this.id = id;
    }

    @Override
    public double getHardness() {
        return 0.8;
    }

    @Override
    public double getResistance() {
        return 4;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return "Unknown";
    }
}
