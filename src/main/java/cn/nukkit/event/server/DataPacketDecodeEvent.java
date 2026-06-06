package cn.nukkit.event.server;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.network.protocol.DataPacket;

/**
 * @author Zwuiix
 * Lumi Project
 */
public class DataPacketDecodeEvent extends ServerEvent implements Cancellable {

    private final DataPacket packet;
    private final Player player;

    public DataPacketDecodeEvent(Player player, DataPacket packet) {
        this.packet = packet;
        this.player = player;
    }

    public DataPacket getPacket() {
        return packet;
    }

    public Player getPlayer() {
        return player;
    }
}
