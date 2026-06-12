package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.block.Block;

public class PlayerBedLeaveEvent extends PlayerEvent {

    private final Block bed;

    public PlayerBedLeaveEvent(Player player, Block bed) {
        this.player = player;
        this.bed = bed;
    }

    public Block getBed() {
        return bed;
    }
}
