package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimMaterialTypes;
import cn.nukkit.network.protocol.types.TrimMaterial;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class ItemIngotCopper extends StringItemBase implements ItemTrimMaterial {

    public ItemIngotCopper() {
        super(COPPER_INGOT, "Copper Ingot");
    }

    @Override
    public TrimMaterial getMaterial() {
        return ItemTrimMaterialTypes.MATERIAL_COPPER;
    }
}
