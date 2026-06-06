package cn.nukkit.event.vehicle;

import cn.nukkit.entity.item.EntityVehicle;

public class VehicleUpdateEvent extends VehicleEvent {

    public VehicleUpdateEvent(EntityVehicle vehicle) {
        super(vehicle);
    }
}
