package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimMaterialTypes;
import cn.nukkit.network.protocol.types.TrimMaterial;

public class ItemLapisLazuli extends StringItemBase implements ItemTrimMaterial {

    public ItemLapisLazuli() {
        super(LAPIS_LAZULI, "Lapis Lazuli");
    }

    @Override
    public TrimMaterial getMaterial() {
        return ItemTrimMaterialTypes.MATERIAL_LAPIS;
    }
}
