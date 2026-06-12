package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;

public class PlayerToggleSneakEvent extends PlayerEvent implements Cancellable {

    protected final boolean isSneaking;

    public PlayerToggleSneakEvent(Player player, boolean isSneaking) {
        this.player = player;
        this.isSneaking = isSneaking;
    }

    public boolean isSneaking() {
        return this.isSneaking;
    }
}
