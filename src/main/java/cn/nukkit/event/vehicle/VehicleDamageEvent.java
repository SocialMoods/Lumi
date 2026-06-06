package cn.nukkit.event.vehicle;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityVehicle;
import cn.nukkit.event.Cancellable;

public class VehicleDamageEvent extends VehicleEvent implements Cancellable {

    private final Entity attacker;
    private double damage;

    public VehicleDamageEvent(EntityVehicle vehicle, Entity attacker, double damage) {
        super(vehicle);
        this.attacker = attacker;
        this.damage = damage;
    }

    public Entity getAttacker() {
        return attacker;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
