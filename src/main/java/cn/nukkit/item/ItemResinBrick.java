package cn.nukkit.item;

import cn.nukkit.item.trim.ItemTrimMaterialTypes;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.network.protocol.types.TrimMaterial;

public class ItemResinBrick extends StringItemBase implements ItemTrimMaterial {
    public ItemResinBrick() {
        super(RESIN_BRICK, "Resin Brick");
    }

    @Override
    public boolean isSupportedOn(int protocolId) {
        return protocolId >= ProtocolInfo.v1_21_50;
    }

    @Override
    public TrimMaterial getMaterial() {
        return ItemTrimMaterialTypes.MATERIAL_RESIN;
    }
}
