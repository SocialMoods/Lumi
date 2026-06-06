package cn.nukkit.event.entity;

import cn.nukkit.entity.item.EntityItem;

/**
 * @author MagicDroidX
 * Nukkit Project
 */
public class ItemSpawnEvent extends EntityEvent {

    public ItemSpawnEvent(EntityItem item) {
        this.entity = item;
    }

    @Override
    public EntityItem getEntity() {
        return (EntityItem) this.entity;
    }
}
