package cn.nukkit.item;

import cn.nukkit.block.Block;
import cn.nukkit.item.trim.ItemTrimMaterialTypes;
import cn.nukkit.network.protocol.types.TrimMaterial;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class ItemRedstone extends Item implements ItemTrimMaterial {

    public ItemRedstone() {
        this(0, 1);
    }

    public ItemRedstone(Integer meta) {
        this(meta, 1);
    }

    public ItemRedstone(Integer meta, int count) {
        super(REDSTONE, meta, count, "Redstone Dust");
        this.block = Block.get(REDSTONE_WIRE);
    }

    @Override
    public TrimMaterial getMaterial() {
        return ItemTrimMaterialTypes.MATERIAL_REDSTONE;
    }
}
