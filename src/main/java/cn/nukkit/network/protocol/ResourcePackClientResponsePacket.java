package cn.nukkit.network.protocol;

import lombok.ToString;

import java.util.UUID;

@ToString
public class ResourcePackClientResponsePacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.RESOURCE_PACK_CLIENT_RESPONSE_PACKET;
    private static final String[] STATUS_NAMES = {"cancel", "downloading", "downloadingfinished", "resourcepackstackfinished"};

    public static final byte STATUS_REFUSED = 1;
    public static final byte STATUS_SEND_PACKS = 2;
    public static final byte STATUS_HAVE_ALL_PACKS = 3;
    public static final byte STATUS_COMPLETED = 4;

    public byte responseStatus;
    public Entry[] packEntries;

    @Override
    public void decode() {
        if (this.protocol >= ProtocolInfo.v1_26_40) {
            this.responseStatus = (byte) (this.getUnsignedVarInt() + 1);
            this.getString();
            this.packEntries = new Entry[0];
            if (this.responseStatus == STATUS_SEND_PACKS) {
                this.packEntries = new Entry[Math.min((int) this.getUnsignedVarInt(), 1024)];
                for (int i = 0; i < this.packEntries.length; i++) this.packEntries[i] = readEntry();
            }
        } else {
            this.responseStatus = (byte) this.getByte();
            this.packEntries = new Entry[Math.min(this.getLShort(), 1024)];
            for (int i = 0; i < this.packEntries.length; i++) this.packEntries[i] = readEntry();
        }
    }

    private Entry readEntry() {
        String[] entry = this.getString().split("_", 3);
        return new Entry(UUID.fromString(entry[0]), entry.length > 1 ? entry[1] : "1.2.0");
    }

    @Override
    public void encode() {
        this.reset();
        if (this.protocol >= ProtocolInfo.v1_26_40) {
            int ordinal = Math.max(0, this.responseStatus - 1);
            this.putUnsignedVarInt(ordinal);
            this.putString(STATUS_NAMES[Math.min(ordinal, STATUS_NAMES.length - 1)]);
            if (this.responseStatus == STATUS_SEND_PACKS) {
                this.putUnsignedVarInt(this.packEntries.length);
                for (Entry entry : this.packEntries) this.putString(entry.uuid + "_" + entry.version);
            }
        } else {
            this.putByte(this.responseStatus);
            this.putLShort(this.packEntries.length);
            for (Entry entry : this.packEntries) this.putString(entry.uuid + "_" + entry.version);
        }
    }
    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @ToString
    public static class Entry {

        public final UUID uuid;
        public final String version;

        public Entry(UUID uuid, String version) {
            this.uuid = uuid;
            this.version = version;
        }
    }
}
