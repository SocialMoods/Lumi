package cn.nukkit.event.player;

import cn.nukkit.Player;

public class PlayerInitializedEvent extends PlayerEvent {

    public PlayerInitializedEvent(Player player) {
        this.player = player;
    }
}
