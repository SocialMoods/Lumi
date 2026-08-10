package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimMaterialTypes;
import cn.nukkit.network.protocol.types.TrimMaterial;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class ItemDiamond extends Item implements ItemTrimMaterial {

    public ItemDiamond() {
        this(0, 1);
    }

    public ItemDiamond(Integer meta) {
        this(meta, 1);
    }

    public ItemDiamond(Integer meta, int count) {
        super(DIAMOND, 0, count, "Diamond");
    }

    @Override
    public TrimMaterial getMaterial() {
        return ItemTrimMaterialTypes.MATERIAL_DIAMOND;
    }
}
