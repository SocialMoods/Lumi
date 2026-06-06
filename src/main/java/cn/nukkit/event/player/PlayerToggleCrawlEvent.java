package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;

/**
 * @author CreeperFace
 */
public class PlayerToggleCrawlEvent extends PlayerEvent implements Cancellable {

    private final boolean isCrawling;

    public PlayerToggleCrawlEvent(Player player, boolean isCrawling) {
        this.player = player;
        this.isCrawling = isCrawling;
    }

    public boolean isCrawling() {
        return this.isCrawling;
    }
}
