package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;

public class PlayerMissedSwingEvent extends PlayerEvent implements Cancellable {

    public PlayerMissedSwingEvent(Player player) {
        this.player = player;
    }
}
