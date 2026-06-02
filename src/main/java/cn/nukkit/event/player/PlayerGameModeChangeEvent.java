package cn.nukkit.event.player;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;

public class PlayerGameModeChangeEvent extends PlayerEvent implements Cancellable {

    protected final int gamemode;

    protected AdventureSettings newAdventureSettings;

    public PlayerGameModeChangeEvent(Player player, int newGameMode, AdventureSettings newAdventureSettings) {
        this.player = player;
        this.gamemode = newGameMode;
        this.newAdventureSettings = newAdventureSettings;
    }

    public int getNewGamemode() {
        return gamemode;
    }

    public AdventureSettings getNewAdventureSettings() {
        return newAdventureSettings;
    }

    public void setNewAdventureSettings(AdventureSettings newAdventureSettings) {
        this.newAdventureSettings = newAdventureSettings;
    }
}
