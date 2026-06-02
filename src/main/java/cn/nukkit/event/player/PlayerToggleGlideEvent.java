package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;

public class PlayerToggleGlideEvent extends PlayerEvent implements Cancellable {

    protected final boolean isGliding;

    public PlayerToggleGlideEvent(Player player, boolean isGliding) {
        this.player = player;
        this.isGliding = isGliding;
    }

    public boolean isGliding() {
        return this.isGliding;
    }
}
