package cn.nukkit.item;

import cn.nukkit.network.protocol.ProtocolInfo;

public class ItemRecordBounce extends ItemRecord implements StringItem {
    public ItemRecordBounce() {
        super(STRING_IDENTIFIED_ITEM, 0, 1);
    }

    @Override
    public String getSoundId() {
        return "record.bounce";
    }

    @Override
    public String getNamespaceId() {
        return MUSIC_DISC_BOUNCE;
    }

    @Override
    public boolean isSupportedOn(int protocolId) {
        return protocolId >= ProtocolInfo.v1_26_30;
    }
}
