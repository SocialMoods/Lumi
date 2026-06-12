package cn.nukkit.event.inventory;

import cn.nukkit.Player;
import cn.nukkit.inventory.Inventory;

/**
 * @author Box
 * Nukkit Project
 */
public class InventoryCloseEvent extends InventoryEvent {

    private final Player who;

    public InventoryCloseEvent(Inventory inventory, Player who) {
        super(inventory);
        this.who = who;
    }

    public Player getPlayer() {
        return this.who;
    }
}
