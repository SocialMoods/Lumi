package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.entity.data.skin.Skin;
import cn.nukkit.event.Cancellable;

/**
 * @author KCodeYT
 * Nukkit Project
 */
public class PlayerChangeSkinEvent extends PlayerEvent implements Cancellable {

    private final Skin skin;

    public PlayerChangeSkinEvent(Player player, Skin skin) {
        this.player = player;
        this.skin = skin;
    }

    public Skin getSkin() {
        return this.skin;
    }
}
