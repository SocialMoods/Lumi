package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;

public class PlayerToggleFlightEvent extends PlayerEvent implements Cancellable {

    protected final boolean isFlying;

    public PlayerToggleFlightEvent(Player player, boolean isFlying) {
        this.player = player;
        this.isFlying = isFlying;
    }

    public boolean isFlying() {
        return this.isFlying;
    }
}
