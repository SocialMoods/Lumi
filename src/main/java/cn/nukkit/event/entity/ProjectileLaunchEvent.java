package cn.nukkit.event.entity;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.projectile.EntityProjectile;
import cn.nukkit.event.Cancellable;

public class ProjectileLaunchEvent extends EntityEvent implements Cancellable {

    public ProjectileLaunchEvent(EntityProjectile entity) {
        this.entity = entity;
    }

    @Override
    public EntityProjectile getEntity() {
        return (EntityProjectile) this.entity;
    }

    public Entity getShooter() {
        return this.getEntity().shootingEntity;
    }
}
