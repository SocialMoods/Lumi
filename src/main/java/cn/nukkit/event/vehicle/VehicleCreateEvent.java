package cn.nukkit.event.vehicle;

import cn.nukkit.entity.item.EntityVehicle;
import cn.nukkit.event.Cancellable;

public class VehicleCreateEvent extends VehicleEvent implements Cancellable {

    public VehicleCreateEvent(EntityVehicle vehicle) {
        super(vehicle);
    }
}
