package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;

public class PlayerToggleSprintEvent extends PlayerEvent implements Cancellable {

    protected final boolean isSprinting;

    public PlayerToggleSprintEvent(Player player, boolean isSprinting) {
        this.player = player;
        this.isSprinting = isSprinting;
    }

    public boolean isSprinting() {
        return this.isSprinting;
    }
}
