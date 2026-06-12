package cn.nukkit.event.entity;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.Cancellable;

public class EntityFreezeEvent extends EntityEvent implements Cancellable {
    private final Entity entity;

    public EntityFreezeEvent(Entity human) {
        this.entity = human;
    }
}