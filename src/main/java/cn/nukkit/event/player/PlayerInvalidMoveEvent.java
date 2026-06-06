package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;

/**
 * call when a player moves wrongly
 *
 * @author WilliamGao
 */

public class PlayerInvalidMoveEvent extends PlayerEvent implements Cancellable {

    private boolean revert;

    public PlayerInvalidMoveEvent(Player player, boolean revert) {
        this.player = player;
        this.revert = revert;
    }

    public boolean isRevert() {
        return this.revert;
    }

    public void setRevert(boolean revert) {
        this.revert = revert;
    }
}
