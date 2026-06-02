package cn.nukkit.event.inventory;

import cn.nukkit.entity.projectile.EntityThrownTrident;
import cn.nukkit.event.Cancellable;
import cn.nukkit.inventory.Inventory;

public class InventoryPickupTridentEvent extends InventoryEvent implements Cancellable {

    private final EntityThrownTrident trident;

    public InventoryPickupTridentEvent(Inventory inventory, EntityThrownTrident trident) {
        super(inventory);
        this.trident = trident;
    }

    public EntityThrownTrident getTrident() {
        return trident;
    }
}
