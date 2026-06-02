package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;

/**
 * @author GoodLucky777
 */
public class PlayerToggleSpinAttackEvent extends PlayerEvent implements Cancellable {

    private final boolean isSpinAttacking;

    public PlayerToggleSpinAttackEvent(Player player, boolean isSpinAttacking) {
        this.player = player;
        this.isSpinAttacking = isSpinAttacking;
    }

    public boolean isSpinAttacking() {
        return this.isSpinAttacking;
    }
}
