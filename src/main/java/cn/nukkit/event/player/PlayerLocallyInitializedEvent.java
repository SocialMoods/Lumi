package cn.nukkit.event.player;

import cn.nukkit.Player;

/**
 * @author Extollite
 * Nukkit Project
 */
public class PlayerLocallyInitializedEvent extends PlayerEvent {

    public PlayerLocallyInitializedEvent(Player player) {
        this.player = player;
    }
}
