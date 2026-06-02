package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;

public class PlayerChunkRequestEvent extends PlayerEvent implements Cancellable {

    private final int chunkX;
    private final int chunkZ;

    public PlayerChunkRequestEvent(Player player, int chunkX, int chunkZ) {
        this.player = player;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }
}
