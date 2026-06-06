package cn.nukkit.event.vehicle;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityVehicle;
import cn.nukkit.event.Cancellable;

public class VehicleDestroyEvent extends VehicleEvent implements Cancellable {

    private final Entity attacker;

    public VehicleDestroyEvent(EntityVehicle vehicle, Entity attacker) {
        super(vehicle);
        this.attacker = attacker;
    }

    public Entity getAttacker() {
        return attacker;
    }
}
