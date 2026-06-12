package cn.nukkit.event.block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.Cancellable;

/**
 * Event for door Interactions.
 * @author Snake1999 on 2016/1/22.
 */
public class DoorToggleEvent extends BlockUpdateEvent implements Cancellable {

    private Player player;

    /**
     * Event for player door interactions.
     * @param block Door block that has been affected by the player.
     * @param player Player that is interacting with the door.
     */
    public DoorToggleEvent(Block block, Player player) {
        super(block);
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
