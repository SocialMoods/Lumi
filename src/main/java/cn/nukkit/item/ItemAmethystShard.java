package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimMaterialTypes;
import cn.nukkit.network.protocol.types.TrimMaterial;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class ItemAmethystShard extends StringItemBase implements ItemTrimMaterial {

    public ItemAmethystShard() {
        super(ItemNamespaceId.AMETHYST_SHARD, "Amethyst Shard");
    }

    @Override
    public TrimMaterial getMaterial() {
        return ItemTrimMaterialTypes.MATERIAL_AMETHYST;
    }
}
