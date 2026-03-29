package cn.nukkit.event.inventory;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.inventory.Inventory;
import lombok.Getter;

public class AnvilInventoryChangeEvent extends InventoryEvent implements Cancellable {

    @Getter
    private static final HandlerList handlers = new HandlerList();

    public AnvilInventoryChangeEvent(Inventory inventory) {
        super(inventory);
    }
}
